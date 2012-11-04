package menu.app;

import java.sql.Date;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class for interactig with the SQLite database on the device This class stores
 * the lunches that the service retrieves in the device so that the need to
 * update is reduced and the app can cope without a connection for as long as it
 * has lunches stored.
 * 
 * @author Oskar Ehnstrom
 * 
 */
public class LunchDatabase extends SQLiteOpenHelper {

	private static final int VERSION = 5;
	private static final String DB_NAME = "Lunches_db";
	private static final String TABLE_NAME = "Lunch_Table";
	private static final String KEY_ID = "id";
	private static final String KEY_DATE = "date";
	private static final String KEY_MAIN = "maincourse";
	private static final String KEY_VEGE = "vege";
	private static final String KEY_SALAD = "salad";
	private static final String KEY_SOUP = "soup";
	private static final String KEY_ALACARTE = "alacarte";
	private static final String KEY_EXTRA = "extra";
	private static final String KEY_WEEKDAY = "weekday";

	public LunchDatabase(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY," + KEY_DATE + " DATE," + KEY_MAIN
				+ " TEXT," + KEY_VEGE + " TEXT," + KEY_SALAD + " TEXT,"
				+ KEY_SOUP + " TEXT," + KEY_ALACARTE + " TEXT," + KEY_EXTRA
				+ " TEXT," + KEY_WEEKDAY + " TEXT )";
		db.execSQL(CREATE_SQL);
	}

	/**
	 * On upgrade of the database the old database is dropped and a new one is
	 * created. Might fix some day.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	/**
	 * Add a lunch to the database
	 * 
	 * @param lunch
	 */
	public void addLunch(LunchObject lunch) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DATE, lunch.getDateInSQL());
		values.put(KEY_MAIN, lunch.getMain());
		values.put(KEY_VEGE, lunch.getVege());
		values.put(KEY_SOUP, lunch.getSoup());
		values.put(KEY_SALAD, lunch.getSalad());
		values.put(KEY_ALACARTE, lunch.getAlacarte());
		values.put(KEY_EXTRA, lunch.getExtra());
		values.put(KEY_WEEKDAY, lunch.getWeekday());

		db.replace(TABLE_NAME, null, values);

		db.close();
	}

	/**
	 * Return the lunch object for the specified date If there is no lunch for
	 * that date return null.
	 * 
	 * @param date
	 *            java.util.Date
	 * @return LunchObject or null if none found.
	 */
	public LunchObject getLunch(java.util.Date date0) {
		Date date = new Date(date0.getTime());
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_DATE,
				KEY_MAIN, KEY_VEGE, KEY_SALAD, KEY_SOUP, KEY_ALACARTE,
				KEY_EXTRA, KEY_WEEKDAY }, KEY_DATE + "=?",
				new String[] { date.toString() }, null, null, null, null);

		// If no results
		if (cursor.getCount() < 1) {
			return null;
		}

		// Mode to first (and only) result
		if (cursor != null)
			cursor.moveToFirst();

		// Check the date format
		Date d;
		try {
			d = Date.valueOf(cursor.getString(1));
		} catch (IllegalArgumentException e) {
			return null;
		}

		LunchObject lunch = new LunchObject(d);
		lunch.setMain(cursor.getString(2));
		lunch.setVege(cursor.getString(3));
		lunch.setSalad(cursor.getString(4));
		lunch.setSoup(cursor.getString(5));
		lunch.setAlacarte(cursor.getString(6));
		lunch.setExtra(cursor.getString(7));
		lunch.setWeekday(cursor.getString(8));

		db.close();

		return lunch;
	}

	private LunchObject getNextLunch(int days) {
		Calendar today = Calendar.getInstance();
		if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			today.add(Calendar.DATE, 2);
		} else if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			today.add(Calendar.DATE, 1);
		}
		
		
		today.add(Calendar.DATE, days);
		
		return this.getLunch(today.getTime());
	}
	
	public LunchObject getCurrentLunch(){
		return this.getNextLunch(0);
	}
	
	public LunchObject[] getWeek(){
		LunchObject[] week = new LunchObject[5];
		
		for (int i = 0; i < week.length; i++){
			week[i] = this.getNextLunch(i);
		}
		
		return week;
	}
}
