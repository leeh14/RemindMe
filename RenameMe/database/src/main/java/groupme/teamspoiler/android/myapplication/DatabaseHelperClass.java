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

import groupme.teamspoiler.android.myapplication.DataElements.*;

/**
 * Created by hirats on 3/18/2016.
 */
public class DatabaseHelperClass extends SQLiteOpenHelper {
    //Initialize the helper class as a singleton
    public static DatabaseHelperClass sInstance;
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

    public static DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    /*public DatabaseHelperClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }*/

    private DatabaseHelperClass(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static synchronized DatabaseHelperClass getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelperClass(context.getApplicationContext());
        }
        return sInstance;
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

    public boolean addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_NAME, category.getName());
        long row = db.insert(TABLE_CATEGORIES, null, contentValues);
        if (row != -1) {
            category.setID(getLastCategory().getID());
            return true;
        }
        return false;
    }
    public boolean addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME, item.getName());
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        contentValues.put(ITEM_DATE, formatter.print(item.getDate()));
        contentValues.put(ITEM_NOTE, item.getNote());
        contentValues.put(ITEM_CATEGORY_KEY_ID, item.getCategoryID());
        long row = db.insert(TABLE_ITEMS, null, contentValues);
        if (row != -1) {
            item.setID(getLastItem().getID());
            return true;
        }
        return false;
    }
    public boolean addFriend(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FRIEND_NAME, friend.getName());
        contentValues.put(FRIEND_USERNAME, friend.getUsername());
        long row = db.insert(TABLE_FRIENDS, null, contentValues);
        if (row != -1) {
            friend.setID(getLastFriend().getID());
            return true;
        }
        return false;    }

    public IterableMap<Category> getCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s", TABLE_CATEGORIES);
        Cursor result = db.rawQuery(query, null);
        IterableMap<Category> categories = new IterableMap<Category>();
        while (result.moveToNext()) {
            categories.add(getCategory(result));
        }
        return categories;
    }
    public Category getCategory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s WHERE %2$s = ?", TABLE_CATEGORIES, KEY_ID);
        Cursor result = db.rawQuery(query, new String[]{Integer.toString(id)});
        if (result.getCount() > 0) {
            result.moveToFirst();
            return getCategory(result);
        }
        return null;
    }
    private static Category getCategory(Cursor result) {
        int idIndex = result.getColumnIndex(KEY_ID);
        int nameIndex = result.getColumnIndex(CATEGORY_NAME);
        Category category = new Category(Integer.parseInt(result.getString(idIndex)), result.getString(nameIndex));
        return category;
    }
    private Category getLastCategory() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s ORDER BY %2$s DESC LIMIT 1", TABLE_CATEGORIES, KEY_ID);
        Cursor result = db.rawQuery(query, null);
        if (result.getCount() > 0) {
            result.moveToFirst();
            return getCategory(result);
        }
        return null;
    }
    public IterableMap<Item> getItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s", TABLE_ITEMS);
        Cursor result = db.rawQuery(query, null);
        IterableMap<Item> items = new IterableMap<Item>();
        while (result.moveToNext()) {
            items.add(getItem(result));
        }
        return items;
    }
    public IterableMap<Item> getItems(int categoryID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s WHERE %2$s = ?", TABLE_ITEMS, ITEM_CATEGORY_KEY_ID);
        Cursor result = db.rawQuery(query, new String[]{Integer.toString(categoryID)});
        IterableMap<Item> items = new IterableMap<Item>();
        while (result.moveToNext()) {
            items.add(getItem(result));
        }
        return items;
    }
    public Item getItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s WHERE %2$s = ?", TABLE_ITEMS, KEY_ID);
        Cursor result = db.rawQuery(query, new String[]{Integer.toString(id)});
        if (result.getCount() > 0) {
            result.moveToFirst();
            return getItem(result);
        }
        return null;
    }
    private static Item getItem(Cursor result) {
        int idIndex = result.getColumnIndex(KEY_ID);
        int nameIndex = result.getColumnIndex(ITEM_NAME);
        int dateIndex = result.getColumnIndex(ITEM_DATE);
        int noteIndex = result.getColumnIndex(ITEM_NOTE);
        int categoryIDIndex = result.getColumnIndex(ITEM_CATEGORY_KEY_ID);
        Item item = new Item(Integer.parseInt(result.getString(idIndex)), result.getString(nameIndex), Integer.parseInt(result.getString(categoryIDIndex)));
        try {
            item.setDate(LocalDateTime.parse(result.getString(dateIndex), FORMATTER));
        }
        catch (Exception e) {}
        item.setNote(result.getString(noteIndex));
        return item;
    }
    private Item getLastItem() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s ORDER BY %2$s DESC LIMIT 1", TABLE_ITEMS, KEY_ID);
        Cursor result = db.rawQuery(query, null);
        if (result.getCount() > 0) {
            result.moveToFirst();
            return getItem(result);
        }
        return null;
    }
    public IterableMap<Friend> getFriends() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s", TABLE_FRIENDS);
        Cursor result = db.rawQuery(query, null);
        IterableMap<Friend> friends = new IterableMap<Friend>();
        while (result.moveToNext()) {
            friends.add(getFriend(result));
        }
        return friends;
    }
    public Friend getFriend(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s WHERE %2$s = ?", TABLE_FRIENDS, KEY_ID);
        Cursor result = db.rawQuery(query, new String[]{Integer.toString(id)});
        if (result.getCount() > 0) {
            result.moveToFirst();
            return getFriend(result);
        }
        return null;
    }
    private static Friend getFriend(Cursor result) {
        int idIndex = result.getColumnIndex(KEY_ID);
        int nameIndex = result.getColumnIndex(FRIEND_NAME);
        int usernameIndex = result.getColumnIndex(FRIEND_USERNAME);
        Friend friend = new Friend(Integer.parseInt(result.getString(idIndex)), result.getString(nameIndex), result.getString(usernameIndex));
        return friend;
    }
    private Friend getLastFriend() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s ORDER BY %2$s DESC LIMIT 1", TABLE_FRIENDS, KEY_ID);
        Cursor result = db.rawQuery(query, null);
        if (result.getCount() > 0) {
            result.moveToFirst();
            return getFriend(result);
        }
        return null;
    }

    public boolean updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_NAME, category.getName());
        db.update(TABLE_CATEGORIES, contentValues, "ID = ?", new String[]{Integer.toString(category.getID())});
        return true;
    }
    public boolean updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME, item.getName());
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        contentValues.put(ITEM_DATE, formatter.print(item.getDate()));
        contentValues.put(ITEM_NOTE, item.getNote());
        //contentValues.put(ITEM_CATEGORY_KEY_ID, categoryID);
        db.update(TABLE_ITEMS, contentValues, "ID = ?", new String[]{Integer.toString(item.getID())});
        return true;
    }
    public boolean updateFriend(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FRIEND_NAME, friend.getName());
        contentValues.put(FRIEND_USERNAME, friend.getUsername());
        db.update(TABLE_FRIENDS, contentValues, "ID = ?", new String[]{Integer.toString(friend.getID())});
        return true;
    }

    public Integer deleteCategory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CATEGORIES, "ID = ?", new String[] {Integer.toString(id)});
    }
    public Integer deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ITEMS, "ID = ?", new String[] {Integer.toString(id)});
    }
    public Integer deleteFriend(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FRIENDS, "ID = ?", new String[] {Integer.toString(id)});
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
