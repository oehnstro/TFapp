package menu.widget;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.widget.RemoteViews;

public class Menu extends Service {

	@Override
	public void onStart(Intent intent, int startId) {
		RemoteViews updateViews = new RemoteViews(this.getPackageName(),
				R.layout.menu_widget_main);

		// Check time. If after 16:00 show tomorrow's menu.
		int tomorrow = 0;
		Date d = new Date();
		if (d.getHours() >= 16) {
			tomorrow = 1;
			d.setDate(d.getDate() + 1);
			while (d.getDay() == 7
					|| d.getDay() == 1) {
				d.setDate(d.getDate() + 1);
			}
		}
		SimpleDateFormat date = new SimpleDateFormat("EEEE d.M");

		// Try to contact API
		String response = date.format(d) + "\n";
		try {
			URL uri = new URL("http://api.teknolog.fi/taffa/sv/" + tomorrow
					+ "/");
			URLConnection connection = uri.openConnection();
			connection.connect();
			InputStream is = connection.getInputStream();

			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(is));
			String s = "";
			while ((s = buffer.readLine()) != null) {
				response += s + "\n";
			}
			is.close();

		} catch (Exception e) {
			e.printStackTrace();
			response = "Unable to update. Touch to try again.";
		}

		updateViews.setTextViewText(R.id.textMenu, response);

		// Update widget
		ComponentName thisWidget = new ComponentName(this, FoodWidget.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		manager.updateAppWidget(thisWidget, updateViews);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
