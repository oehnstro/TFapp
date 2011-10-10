package menu.widget;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class Menu extends Service {

	@Override
	public void onStart(Intent intent, int startId) {
		RemoteViews updateViews = new RemoteViews(this.getPackageName(),
				R.layout.menu_widget_main);
		
		String response = "";
		try {
			URL uri = new URL("http://api.teknolog.fi/taffa/sv/today/");
			URLConnection connection = uri.openConnection();
			connection.connect();
			InputStream is = connection.getInputStream();
			
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(is));
			String s = "";
			while ((s = buffer.readLine()) != null) {
				response += s+"\n";
			}
			is.close();	
			

		} catch (Exception e) {
			e.printStackTrace();
			response = "Kunde inte uppdatera.";
		}
		
		
		
		updateViews.setTextViewText(R.id.textMenu, response);
				
		// Push update for this widget to the home screen
		ComponentName thisWidget = new ComponentName(this, FoodWidget.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		manager.updateAppWidget(thisWidget, updateViews);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
