package menu.widget;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.RemoteViews;

public class Menu extends Service {

	private static final String PREFS_NAME = "menu.widget.WidgetConf";

	private static final String[][] weekdays = {
			{ "", "Söndag", "Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag",
					"Lördag" },
			{ "", "Sunnuntai", "Maanantai", "Tiistai", "Keskiviikko", "Torstai",
					"Perjantai", "Lauantai" },
			{ "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
					"Saturday" } };

	@Override
	public void onStart(Intent intent, int startId) {
System.out.println("asdfasfdas");
		RemoteViews updateViews = new RemoteViews(this.getPackageName(),
				R.layout.menu_widget_main);

		// Set temp text
		updateViews.setTextViewText(R.id.textMenu, "Loading");

		// Check time. If after 16:00 show tomorrow's menu.
		// If saturday or sunday, show monday's menu
		int tomorrow = 0;
		Calendar now = Calendar.getInstance();
		if (now.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			now.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) + 2);
		} else if (now.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			now.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) + 1);
		} else if (now.get(Calendar.HOUR_OF_DAY) >= 16) {
			tomorrow = 1;
			now.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) + 1);
		}

		SimpleDateFormat date = new SimpleDateFormat("d.M");

		// Get language from preferences
		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
		String prefix = prefs.getString("lang", "English");

		String langId = "en";
		int langInt = 2;
		if (prefix.startsWith("Suomi")) {
			langId = "fi";
			langInt = 1;
		} else if (prefix.startsWith("Svenska")) {
			langId = "sv";
			langInt = 0;
		}
		
		//Set date
		String dateString = weekdays[langInt][now.get(Calendar.DAY_OF_WEEK)]
				+ " " + date.format(now.getTime());
		updateViews.setTextViewText(R.id.date, dateString);


		// Try to contact API
		String response = "";
		try {
			URL uri = new URL("http://api.teknolog.fi/taffa/" + langId + "/"
					+ tomorrow + "/");
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
