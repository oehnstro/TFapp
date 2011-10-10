package menu.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class FoodWidget extends AppWidgetProvider {
	
	// Kallas med jämna mellanrum
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		Intent intent = new Intent(context, Menu.class);
		context.startService(intent);

	}
}