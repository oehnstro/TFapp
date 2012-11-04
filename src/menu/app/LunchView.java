package menu.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LunchView extends Activity {

	private LunchDatabase lunches;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_lunch);
		this.context = getBaseContext();

		Intent intent = new Intent(getBaseContext(), LunchUpdateService.class);
		getApplication().startService(intent);

		lunches = new LunchDatabase(context);

		Intent i = new Intent("menu.app.LunchUpdateService");
		startService(i);

		updateMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_update:
			startService(new Intent(this, LunchUpdateService.class));
			updateMenu();
			return true;
		case R.id.menu_about:
			Intent j = new Intent(LunchView.this, AboutView.class);
			startActivity(j);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void updateMenu() {

		LinearLayout main_layout = (LinearLayout) findViewById(R.id.main_layout);

		for (LunchObject lunch : lunches.getWeek()) {
			if (lunch != null) {
				View view = getLayoutInflater().inflate(R.layout.lunch_day,
						main_layout, false);

				TextView menu = (TextView) view.findViewById(R.id.lunchmenu);
				TextView header = (TextView) view
						.findViewById(R.id.lunch_header);

				menu.setText(lunch.getMenuAsString());
				header.setText(lunch.getWeekday() + " " + lunch.getDateShort());

				main_layout.addView(view);
			}
		}
	}

}
