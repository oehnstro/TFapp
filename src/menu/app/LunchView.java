package menu.app;

import java.sql.Date;

import menu.widget.R;
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
	    
	    lunches = new LunchDatabase(getBaseContext());
	    
	    TextView menu = (TextView) findViewById(R.id.lunchmenu);
	    LunchObject lunch = lunches.getLunch(new Date(0));
	    if (lunch != null)
	    	menu.setText(lunch.getMain());
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.menu, menu);
		return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent i = new Intent(LunchView.this, AboutView.class);
    	startActivity(i);
    	
		return true;
    }
}
