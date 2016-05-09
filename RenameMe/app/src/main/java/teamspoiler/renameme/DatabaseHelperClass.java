package teamspoiler.renameme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;

import teamspoiler.renameme.DataElements.*;

/**
 * Created by hirats on 3/18/2016.
 */
public class DatabaseHelperClass extends SQLiteOpenHelper {
    /*THE CLASS USED TO ACCESS THE DATABASE. IT IS A SINGLETON OBJECT
    THE DATABASE CONSISTS OF THREE TABLES: CATEGORIES, ITEMS, AND FRIENDS.
    EACH TABLE HAS COLUMNS CORRESPONDING TO PARAMETERS OF ITS CONTENTS, LIKE
    NAME, ID NUMBER, USERNAME, NOTES, AND ETC. THE ROW IN THE TABLE CORRESPOND TO
    ITS CONTENTS, WHICH HAVE VALUES FOR EACH COLUMN.
     */

    /* WITHIN A TABLE, EACH CATEGORY, ITEM, OR FRIEND (DEPENDING ON THE TABLE) HAS
    AN UNIQUE, IMMUTABLE, ID NUMBER. THIS ID IS CALLED A PRIMARY KEY AND IS USEFUL FOR ACCESSING
    THE CONTENTS.
     */

    /*ALL STRINGS REFERENCED BY context.getString(R.id.???) ARE LOCATED IN STRINGS.XML
     */

    //Initialize the helper class as a singleton
    private static DatabaseHelperClass sInstance;
    //The context from which this class is instantiated
    private Context context;

    //A formatter for converting a LocalDateTime object to a string, and vice versa.
    public static DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

    /*public DatabaseHelperClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }*/

    //Private constructor, calls super constructor and sets context
    private DatabaseHelperClass(Context context) {
        super(context, context.getString(R.string.database_name), null, 1);
        this.context = context;
    }

    //Public method to access the singleton DatabaseHelperClass object
    public static synchronized DatabaseHelperClass getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelperClass(context.getApplicationContext());
        }
        return sInstance;
    }

    //Overrided method, that is called the first time the dataase is accessed
    public void onCreate(SQLiteDatabase db) {
        createTableCategories(db);
        createTableItems(db);
        createTableFriends(db);
    }

    //Creates the categories table
    private void createTableCategories(SQLiteDatabase db) {
        String query =
                String.format("CREATE table %1$s (%2$s INTEGER PRIMARY KEY AUTOINCREMENT, %3$s TEXT)",
                        context.getString(R.string.table_categories), context.getString(R.string.key_id), context.getString(R.string.name));
        db.execSQL(query);
    }
    //Creates the items table
    private void createTableItems(SQLiteDatabase db) {
        String query =
                String.format("CREATE table %1$s (%2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "%3$s TEXT, %4$s DATE, %5$s TEXT, %6$s INTEGER, FOREIGN KEY(%6$s) REFERENCES %7$s(%2$s))",
                        context.getString(R.string.table_items), context.getString(R.string.key_id), context.getString(R.string.name),
                        context.getString(R.string.item_date), context.getString(R.string.item_note),
                        context.getString(R.string.item_category_key_id), context.getString(R.string.table_categories));
        db.execSQL(query);
    }
    //Creates the friends table
    private void createTableFriends(SQLiteDatabase db) {
        String query =
                String.format("CREATE table %1$s (%2$s INTEGER PRIMARY KEY AUTOINCREMENT, %3$s TEXT, %4$s TEXT)",
                        context.getString(R.string.table_friends), context.getString(R.string.key_id),
                        context.getString(R.string.name), context.getString(R.string.friend_username));
        db.execSQL(query);
    }

    //Overrided method that is called when dtabase is upgraded, deletes the existing tables
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + context.getString(R.string.table_categories));
        db.execSQL("DROP TABLE IF EXISTS " + context.getString(R.string.table_items));
        db.execSQL("DROP TABLE IF EXISTS " + context.getString(R.string.table_friends));
        onCreate(db);
    }

    //Adds an category to the category table
    public boolean addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (category.getID() != 0)
            contentValues.put(context.getString(R.string.key_id), category.getID());
        contentValues.put(context.getString(R.string.name), category.getName());
        long row = db.insert(context.getString(R.string.table_categories), null, contentValues);
        if (row != -1) {
            category.setID(getLastCategory().getID());
            return true;
        }
        return false;
    }
    //Adds an item to the item table
    public boolean addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (item.getID() != 0)
            contentValues.put(context.getString(R.string.key_id), item.getID());
        contentValues.put(context.getString(R.string.name), item.getName());
        contentValues.put(context.getString(R.string.item_date), FORMATTER.print(item.getDate()));
        contentValues.put(context.getString(R.string.item_note), item.getNote());
        contentValues.put(context.getString(R.string.item_category_key_id), item.getCategoryID());
        long row = db.insert(context.getString(R.string.table_items), null, contentValues);
        if (row != -1) {
            item.setID(getLastItem().getID());
            return true;
        }
        return false;
    }
    //Adds a friend to the friend table
    public boolean addFriend(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (friend.getID() != 0)
            contentValues.put(context.getString(R.string.key_id), friend.getID());
        contentValues.put(context.getString(R.string.name), friend.getName());
        contentValues.put(context.getString(R.string.friend_username), friend.getUsername());
        long row = db.insert(context.getString(R.string.table_friends), null, contentValues);
        if (row != -1) {
            friend.setID(getLastFriend().getID());
            return true;
        }
        return false;
    }

    //Returns all the categories as an iterable map
    public IterableMap<Category> getCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s", context.getString(R.string.table_categories));
        Cursor result = db.rawQuery(query, null);
        IterableMap<Category> categories = new IterableMap<Category>();
        while (result.moveToNext()) {
            categories.add(getCategory(result));
        }
        return categories;
    }
    //Returns the category that has the given id
    public Category getCategory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s WHERE %2$s = ?",
                context.getString(R.string.table_categories), context.getString(R.string.key_id));
        Cursor result = db.rawQuery(query, new String[]{Integer.toString(id)});
        if (result.getCount() > 0) {
            result.moveToFirst();
            return getCategory(result);
        }
        return null;
    }
    //Private method used to get one single category from at the cursor location of a group of categories
    private Category getCategory(Cursor result) {
        int idIndex = result.getColumnIndex(context.getString(R.string.key_id));
        int nameIndex = result.getColumnIndex(context.getString(R.string.name));
        Category category = new Category(Integer.parseInt(result.getString(idIndex)), result.getString(nameIndex));
        return category;
    }
    //Returns the category with the largest ID number, aka the one added most recently
    private Category getLastCategory() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s ORDER BY %2$s DESC LIMIT 1",
                context.getString(R.string.table_categories), context.getString(R.string.key_id));
        Cursor result = db.rawQuery(query, null);
        if (result.getCount() > 0) {
            result.moveToFirst();
            return getCategory(result);
        }
        return null;
    }
    //Returns all the items as an iterable map
    public IterableMap<Item> getItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s", context.getString(R.string.table_items));
        Cursor result = db.rawQuery(query, null);
        IterableMap<Item> items = new IterableMap<Item>();
        while (result.moveToNext()) {
            items.add(getItem(result));
        }
        return items;
    }
    //Returns the items that belong to category with given categoryID
    public IterableMap<Item> getItems(int categoryID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s WHERE %2$s = ?",
                context.getString(R.string.table_items), context.getString(R.string.item_category_key_id));
        Cursor result = db.rawQuery(query, new String[]{Integer.toString(categoryID)});
        IterableMap<Item> items = new IterableMap<Item>();
        while (result.moveToNext()) {
            items.add(getItem(result));
        }
        return items;
    }
    //Returns the item that has the given id
    public Item getItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s WHERE %2$s = ?",
                context.getString(R.string.table_items), context.getString(R.string.key_id));
        Cursor result = db.rawQuery(query, new String[]{Integer.toString(id)});
        if (result.getCount() > 0) {
            result.moveToFirst();
            return getItem(result);
        }
        return null;
    }
    //Private method used to get one single item from at the cursor location of a group of items
    private Item getItem(Cursor result) {
        int idIndex = result.getColumnIndex(context.getString(R.string.key_id));
        int nameIndex = result.getColumnIndex(context.getString(R.string.name));
        int dateIndex = result.getColumnIndex(context.getString(R.string.item_date));
        int noteIndex = result.getColumnIndex(context.getString(R.string.item_note));
        int categoryIDIndex = result.getColumnIndex(context.getString(R.string.item_category_key_id));
        Item item = new Item(Integer.parseInt(result.getString(idIndex)), result.getString(nameIndex), Integer.parseInt(result.getString(categoryIDIndex)));
        try {
            item.setDate(LocalDateTime.parse(result.getString(dateIndex), FORMATTER));
        }
        catch (Exception e) {}
        item.setNote(result.getString(noteIndex));
        return item;
    }
    //Returns the item with the largest ID number, aka the one added most recently
    private Item getLastItem() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s ORDER BY %2$s DESC LIMIT 1",
                context.getString(R.string.table_items), context.getString(R.string.key_id));
        Cursor result = db.rawQuery(query, null);
        if (result.getCount() > 0) {
            result.moveToFirst();
            return getItem(result);
        }
        return null;
    }
    //Returns all the friends as an iterable map
    public IterableMap<Friend> getFriends() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s", context.getString(R.string.table_friends));
        Cursor result = db.rawQuery(query, null);
        IterableMap<Friend> friends = new IterableMap<Friend>();
        while (result.moveToNext()) {
            friends.add(getFriend(result));
        }
        return friends;
    }
    //Returns the friend that has the given id
    public Friend getFriend(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s WHERE %2$s = ?",
                context.getString(R.string.table_friends), context.getString(R.string.key_id));
        Cursor result = db.rawQuery(query, new String[]{Integer.toString(id)});
        if (result.getCount() > 0) {
            result.moveToFirst();
            return getFriend(result);
        }
        return null;
    }
    //Private method used to get one single friend from at the cursor location of a group of friends
    private Friend getFriend(Cursor result) {
        int idIndex = result.getColumnIndex(context.getString(R.string.key_id));
        int nameIndex = result.getColumnIndex(context.getString(R.string.name));
        int usernameIndex = result.getColumnIndex(context.getString(R.string.friend_username));
        Friend friend = new Friend(Integer.parseInt(result.getString(idIndex)), result.getString(nameIndex), result.getString(usernameIndex));
        return friend;
    }
    //Returns the friend with the largest ID number, aka the one added most recently
    private Friend getLastFriend() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %1$s ORDER BY %2$s DESC LIMIT 1",
                context.getString(R.string.table_friends), context.getString(R.string.key_id));
        Cursor result = db.rawQuery(query, null);
        if (result.getCount() > 0) {
            result.moveToFirst();
            return getFriend(result);
        }
        return null;
    }

    //Updates the category in the database with the given ID number, with the given parameters
    public boolean updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(context.getString(R.string.name), category.getName());
        db.update(context.getString(R.string.table_categories), contentValues, "ID = ?", new String[]{Integer.toString(category.getID())});
        return true;
    }
    //Updates the item in the database with the given ID number, with the given parameters
    public boolean updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(context.getString(R.string.name), item.getName());
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        contentValues.put(context.getString(R.string.item_date), formatter.print(item.getDate()));
        contentValues.put(context.getString(R.string.item_note), item.getNote());
        //contentValues.put(ITEM_CATEGORY_KEY_ID, categoryID);
        db.update(context.getString(R.string.table_items), contentValues, "ID = ?", new String[]{Integer.toString(item.getID())});
        return true;
    }
    //Updates the friend in the database with the given ID number, with the given parameters
    public boolean updateFriend(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(context.getString(R.string.name), friend.getName());
        contentValues.put(context.getString(R.string.friend_username), friend.getUsername());
        db.update(context.getString(R.string.table_friends), contentValues, "ID = ?", new String[]{Integer.toString(friend.getID())});
        return true;
    }

    //Deletes the category in the database with the given ID number
    public Integer deleteCategory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(context.getString(R.string.table_categories), "ID = ?", new String[] {Integer.toString(id)});
    }
    //Deletes the item in the database with the given ID number
    public Integer deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(context.getString(R.string.table_items), "ID = ?", new String[] {Integer.toString(id)});
    }
    //Deletes the friend in the database with the given ID number
    public Integer deleteFriend(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(context.getString(R.string.table_friends), "ID = ?", new String[] {Integer.toString(id)});
    }
}
