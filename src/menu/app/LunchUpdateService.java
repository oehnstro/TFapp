package menu.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import menu.widget.R;
import menu.widget.R.id;
import menu.widget.R.layout;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.RemoteViews;

public class LunchUpdateService extends Service {

	private static final String PREFS_NAME = "menu.widget.WidgetConf";

	@Override
	public void onStart(Intent intent, int startId) {
		RemoteViews updateViews = new RemoteViews(this.getPackageName(),
				R.layout.menu_widget_main);


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
		String dateString;


		// Try to contact API
		String response = "";
		try {
			URL uri = new URL("http://api.teknolog.fi/taffa/" + langId + "json/week/");
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
			return;
		}
		
		JSONArray week;
		try {
			week = new JSONArray(response);
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}


		for (int i = 0; i < week.length(); i++){
			JSONObject json;
			try {
				json = week.getJSONObject(i);
			
			
			
			
			
			
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
		}

		// Update widget
		ComponentName thisWidget = new ComponentName(this, LunchWidget.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		manager.updateAppWidget(thisWidget, updateViews);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
