package menu.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class FoodWidget extends AppWidgetProvider {
	
	// Called at certain intervals
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		//Add listener to menu text
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
		      int appWidgetId = appWidgetIds[i];
		      
		      Intent intent = new Intent(context, Menu.class);
		      PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
		      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.menu_widget_main);
		      views.setOnClickPendingIntent(R.id.textMenu, pendingIntent);

		      appWidgetManager.updateAppWidget(appWidgetId, views);
		    }

		//Update text
		Intent intent = new Intent(context, Menu.class);
		context.startService(intent);

	}
}