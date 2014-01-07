package SQLite;

import java.io.FileNotFoundException;

import eventModel.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class OfflineDatabase extends SQLiteOpenHelper {

	private Model model = Model.getModel();
	private static final String DATABASE_NAME = "event.db";
	private static final String EVENT_TABLE = "Event";
	public static final String EVENT_ID = "eventId";
	public static final String ACCOUNTS = "account";
	private static final String EVENT_CREATE = "CREATE TABLE " + EVENT_TABLE
			+ "(" + EVENT_ID + " integer," + ACCOUNTS + " text not null" + ")";

	public static final String ACCOUNT_TABLE = "Account";
	public static final String ACCOUNT = "account";
	public static final String PASSWORD = "Password";
	private static final String ACCOUNT_CREATE = "CREATE TABLE "
			+ ACCOUNT_TABLE + "(" + ACCOUNT + " text not null," + PASSWORD
			+ " text not null" + ")";

	public OfflineDatabase(Context context) {
		super(context, DATABASE_NAME, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(EVENT_CREATE);
		database.execSQL(ACCOUNT_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(OfflineDatabase.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
		onCreate(db);
	}

	public void addOfflineAccount(String username, String password) {
		if (!login(username, password)) {
			Log.i("create", "new");
			ContentValues cv = new ContentValues();
			cv.put(ACCOUNT, username.trim());
			cv.put(PASSWORD, password.trim());
			getWritableDatabase().insert(ACCOUNT_TABLE, ACCOUNT, cv);
		}
	}

	public boolean login(String userName, String password) {
		Cursor c = getReadableDatabase().rawQuery(
				"SELECT * FROM " + ACCOUNT_TABLE + " WHERE " + ACCOUNT + " = '"
						+ userName.trim() + "' AND " + PASSWORD + " = '"
						+ password.trim() + "'", null);
		if (c.getCount() > 0) {
			return true;
		}
		return false;
	}

	public void setEventFile(String userName, int eventId) {
		Log.d("db event exits1 ", eventExsit(userName, eventId) + "");
		if (!eventExsit(userName, eventId)) {
			ContentValues cv = new ContentValues();
			cv.put(ACCOUNT, userName.trim());
			cv.put(EVENT_ID, eventId + "");
			getWritableDatabase().insert(EVENT_TABLE, null, cv);
			Log.d("db event exits 2", eventExsit(userName, eventId) + "");
		}
	}

	public boolean eventExsit(String userName, int eventId) {
		Cursor c = getReadableDatabase().rawQuery(
				"SELECT * FROM " + EVENT_TABLE + " WHERE " + ACCOUNT + " = '"
						+ userName.trim() + "' AND " + EVENT_ID + " = '"
						+ eventId + "'", null);
		if (c.getCount() > 0) {
			return true;
		}
		return false;
	}

	public int getEventCount(String userName) {
		Cursor c = getReadableDatabase().rawQuery(
				"SELECT * FROM " + EVENT_TABLE + " WHERE account = '"
						+ userName.trim() + "'", null);
		return c.getCount();
	}

	// get Event and read
	public void getEventFile(String userName) {

		Log.e("db account after ", userName.trim() + "");
		Cursor c = getReadableDatabase().rawQuery(
				"SELECT * FROM " + EVENT_TABLE + " WHERE account = '"
						+ userName.trim() + "'", null);
		c.moveToFirst();
		Log.d("number of file", c.getCount() + "");
		for (int i = 0; i < c.getCount(); i++) {
			// read file

			try {
				Log.d("file", c.getInt(c.getColumnIndex(EVENT_ID)) + "");
				model.loadFile(c.getInt(c.getColumnIndex(EVENT_ID)));
				Log.d("db read file event size", model.getEventSet().size()
						+ "");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("file", Environment.getExternalStorageDirectory() + "/event/"
					+ c.getString(c.getColumnIndex(EVENT_ID)));
			c.moveToNext();
		}
	}

	public void delete(String userName, int id) {
		getWritableDatabase().execSQL(
				"DELETE FROM " + EVENT_TABLE + " WHERE " + ACCOUNT + " = '"
						+ userName.trim() + "' AND " + EVENT_ID + " = '" + id
						+ "'");
		
		Log.i("table size", this.getEventCount(userName)+"");
	}

}
