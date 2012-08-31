package menu.app;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.
import menu.widget.R;
import menu.widget.R.id;
import menu.widget.R.layout;

public class WidgetConfView extends Activity {

	private static final String PREFS_NAME = "menu.widget.WidgetConf";

	Spinner spinner;

	int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

	public WidgetConfView() {
		super();
	}

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);

		setResult(RESULT_CANCELED); //If cancelled
		setContentView(R.layout.menu_widget_conf);//Set the conf view
		spinner = (Spinner) findViewById(R.id.languagespinner); //Get the dropdown menu

		//Add listener to save button
		findViewById(R.id.savebutton).setOnClickListener(mOnClickListener);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		// If they gave us an intent without the widget id, just bail.
		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}

	}

	//Click listener
	View.OnClickListener mOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			final Context context = WidgetConfView.this;

			//Get the language
			String language = spinner.getSelectedItem().toString();
			saveLanguage(context, language);

			//Update widget
			LunchWidget.update(context);

			// Make sure we pass back the original appWidgetId
			Intent resultValue = new Intent();
			resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
					mAppWidgetId);
			setResult(RESULT_OK, resultValue);
			finish();
		}
	};

	//Save language to shared preferences
	static void saveLanguage(Context context, String lang) {
		SharedPreferences settings = context
				.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.remove("lang");
		editor.putString("lang", lang);
		editor.commit();
	}
}
