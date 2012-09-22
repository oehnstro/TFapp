package menu.app;

import java.util.Calendar;
import java.util.Date;

import menu.app.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class LunchView extends Activity {

	private LunchDatabase lunches;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_lunch);
		
		Intent intent = new Intent(getBaseContext(), LunchUpdateService.class);
		getApplication().startService(intent);

		lunches = new LunchDatabase(getBaseContext());

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
	
	private void updateMenu(){
		TextView menu = (TextView) findViewById(R.id.lunchmenu);
		TextView header = (TextView) findViewById(R.id.lunch_header);
		LunchObject lunch = lunches.getCurrentLunch();
		if (lunch != null){
			menu.setText(lunch.getMenuAsString());
			header.setText(lunch.getWeekday() + " " + lunch.getDateShort());
		} else {
			Intent i = new Intent("menu.app.LunchUpdateService");
			startService(i);
			menu.setText("Updating, test.");
		}
	}
	
}
