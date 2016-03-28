package groupme.teamspoiler.android.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;

/**
 * Created by hirats on 3/18/2016.
 */
public class DatabaseHelperClass extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "remind_me.db";

    //Table names
    public static final String TABLE_CATEGORIES = "CATEGORIES";
    public static final String TABLE_ITEMS = "ITEMS";
    public static final String TABLE_FRIENDS = "FRIENDS";

    //COMMON column names
    public static final String KEY_ID = "ID";

    //CATEGORIES table - column names
    public static final String CATEGORY_NAME = "NAME";

    //ITEMS table - column names
    public static final String ITEM_NAME = "NAME";
    public static final String ITEM_DATE = "EXPIRATION_DATE";
    public static final String ITEM_NOTE = "NOTE";
    public static final String ITEM_CATEGORY_KEY_ID = "CATEGORY_ID";

    //FRIENDS table - column names
    public static final String FRIEND_NAME = "NAME";
    public static final String FRIEND_USERNAME = "USERNAME";


    //Table creations
    public static final String CREATE_TABLE_CATEGORIES =
            String.format("CREATE table %1$s (%2$s INTEGER PRIMARY KEY AUTOINCREMENT, %3$s TEXT)",
                    TABLE_CATEGORIES, KEY_ID, CATEGORY_NAME);

    public static final String CREATE_TABLE_ITEMS =
            String.format("CREATE table %1$s (%2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "%3$s TEXT, %4$s DATE, %5$s TEXT, %6$s INTEGER, FOREIGN KEY(%6$s) REFERENCES %7$s(%8$s))",
                    TABLE_ITEMS, KEY_ID, ITEM_NAME, ITEM_DATE, ITEM_NOTE, ITEM_CATEGORY_KEY_ID, TABLE_CATEGORIES, KEY_ID);

    public static final String CREATE_TABLE_FRIENDS =
            String.format("CREATE table %1$s (%2$s INTEGER PRIMARY KEY AUTOINCREMENT, %3$s TEXT, %4$s TEXT)",
                    TABLE_FRIENDS, KEY_ID, FRIEND_NAME, FRIEND_USERNAME);

    public DatabaseHelperClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelperClass(Context context) {
        super(context, DATABASE_NAME, null, 1);
        getWritableDatabase();
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_ITEMS);
        db.execSQL(CREATE_TABLE_FRIENDS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        onCreate(db);
    }

    public boolean addCategory(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_NAME, name);
        return (db.insert(TABLE_CATEGORIES, null, contentValues) != -1);
    }
    public boolean addItem(String name, String categoryID, LocalDateTime date, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME, name);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        contentValues.put(ITEM_DATE, formatter.print(date));
        contentValues.put(ITEM_NOTE, note);
        contentValues.put(ITEM_CATEGORY_KEY_ID, categoryID);
        return (db.insert(TABLE_ITEMS, null, contentValues) != -1);
    }
    /*    public boolean addItem(String name, String categoryName, LocalDateTime date, String note) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ITEM_NAME, name);
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            contentValues.put(ITEM_DATE, formatter.print(date));
            contentValues.put(ITEM_NOTE, note);
            String query = String.format("SELECT %1$s FROM %2$s WHERE %3$s = ?", KEY_ID, TABLE_CATEGORIES, CATEGORY_NAME);
            Cursor result = db.rawQuery(query, new String[]{categoryName});
            if(result.getCount() > 0) {
                result.moveToNext();
                contentValues.put(ITEM_CATEGORY_KEY_ID, result.getString(0));
                return (db.insert(TABLE_ITEMS, null, contentValues) != -1);
            }
            return false;
        }*/
    public boolean addFriend(String name, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FRIEND_NAME, name);
        contentValues.put(FRIEND_USERNAME, username);
        return (db.insert(TABLE_FRIENDS, null, contentValues) != -1);
    }

    public Cursor getCategoryData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s", TABLE_CATEGORIES);
        Cursor result = db.rawQuery(query, null);
        return result;
    }
    public Cursor getItemData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s", TABLE_ITEMS);
        Cursor result = db.rawQuery(query, null);
        return result;
    }
    public Cursor getFriendData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s", TABLE_FRIENDS);
        Cursor result = db.rawQuery(query, null);
        return result;
    }

    public boolean updateCategory(String id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_NAME, name);
        db.update(TABLE_CATEGORIES, contentValues, "ID = ?", new String[] {id});
        return true;
    }
    public boolean updateItem(String id, String name, String categoryID, LocalDateTime date, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME, name);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        contentValues.put(ITEM_DATE, formatter.print(date));
        contentValues.put(ITEM_NOTE, note);
        contentValues.put(ITEM_CATEGORY_KEY_ID, categoryID);
        db.update(TABLE_ITEMS,contentValues,"ID = ?", new String[] {id});
        return true;
    }
    public boolean updateFriend(String id, String name, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FRIEND_NAME, name);
        contentValues.put(FRIEND_USERNAME, username);
        db.update(TABLE_FRIENDS,contentValues,"ID = ?", new String[] {id});
        return true;
    }

    public Integer deleteCategory(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CATEGORIES, "ID = ?", new String[] {id});
    }
    public Integer deleteItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ITEMS, "ID = ?", new String[] {id});
    }
    public Integer deleteFriend(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FRIENDS, "ID = ?", new String[] {id});
    }

    /*public boolean updateData(String id, String name, String surname, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_1, id);
        contentValues.put(ITEM_NAME, name);
        contentValues.put(ITEM_DATE, surname);
        contentValues.put(ITEM_NOTE, marks);
        db.update(TABLE_ITEMS,contentValues,"ID = ?", new String[] {id});
        return true;
    }

    public Integer delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ITEMS, "ID = ?", new String[] {id});
    }*/
}
