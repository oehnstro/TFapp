package menu.app;

import java.sql.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class for interactig with the SQLite database on the device
 * This class stores the lunches that the service retrieves in 
 * the device so there is no need to update as often.
 * 
 * @author Oskar Ehnstrom
 *
 */
public class LunchDatabase extends SQLiteOpenHelper {

	private static final int VERSION = 1;
	private static final String DB_NAME = "Lunches_db";
	private static final String TABLE_NAME = "Lunch_table";
	private static final String KEY_ID = "id";
	private static final String KEY_DATE = "date";
	private static final String KEY_MAIN = "maincourse";

	public LunchDatabase(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY," + KEY_DATE + " DATE," + KEY_MAIN
				+ " TEXT" + ")";
		db.execSQL(CREATE_SQL);
	}

	/**
	 * On upgrade of the database the old database is dropped
	 * and a new one is created. Might fix some day.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	/**
	 * Add a lunch to the database
	 * @param lunch
	 */
	public void addLunch(LunchObject lunch) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DATE, lunch.getDate());
		values.put(KEY_MAIN, lunch.getMain());

		db.insert(TABLE_NAME, null, values);
		db.close();
	}

	/**
	 * Return the lunch object for the specified date If there is no lunch for
	 * that date return null.
	 * 
	 * @param date
	 * @return LunchObject or null if none found.
	 */
	public LunchObject getLunch(Date date) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_DATE,
				KEY_MAIN }, KEY_DATE + "=?", new String[] { date.toString() },
				null, null, null, null);

		// If no results
		if (cursor.getCount() < 1)
			return null;

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
		
		// TODO: Fix only main shown
		LunchObject lunch = new LunchObject(d, cursor.getString(2),
				cursor.getString(2), cursor.getString(2), cursor.getString(2),
				cursor.getString(2));

		return lunch;
	}
}
