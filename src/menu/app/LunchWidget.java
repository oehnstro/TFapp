package menu.app;

import menu.app.R;
import menu.app.R.id;
import menu.app.R.layout;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class LunchWidget extends AppWidgetProvider {
		
	// Called at certain intervals
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		// Add listener to menu text
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];

			Intent intent = new Intent(context, LunchView.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
					intent, 0);
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.menu_widget_main);
			views.setOnClickPendingIntent(R.id.textMenu, pendingIntent);

			appWidgetManager.updateAppWidget(appWidgetId, views);
		}

		// Update text
		//Intent intent = new Intent(context, LunchUpdateService.class);
		//context.startService(intent);

	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
	 super.onReceive(context, intent);
	 
	 RemoteViews updateViews = new RemoteViews("menu.app",
				R.layout.menu_widget_main);

	}
}