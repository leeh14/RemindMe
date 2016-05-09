package groupme.teamspoiler.android.myapplication;

import groupme.teamspoiler.android.myapplication.DataElements.*;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.joda.time.LocalDateTime;

//Database test activity
public class MainActivity extends AppCompatActivity {
    private DatabaseHelperClass db;
    private Button btnViewCategories, btnViewItems, btnViewFriends, btnAdd, delCatButton, delItemButton, delFriendButton;
    private EditText nameView, usernameView, notesView, idView, categoryIDView;
    private CheckBox viewAllCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        db = DatabaseHelperClass.getInstance(this);
        btnViewCategories = (Button) findViewById(R.id.btnViewCategories);
        btnViewItems = (Button) findViewById(R.id.btnViewItems);
        btnViewFriends = (Button) findViewById(R.id.btnViewFriends);
        delCatButton = (Button) findViewById(R.id.delCatButton);
        delItemButton = (Button) findViewById(R.id.delItemButton);
        delFriendButton = (Button) findViewById(R.id.delFriendButton);
        btnAdd = (Button) findViewById(R.id.addButton);
        nameView = (EditText)findViewById(R.id.nameView);
        usernameView = (EditText)findViewById(R.id.usernameView);
        notesView = (EditText)findViewById(R.id.notesView);
        idView = (EditText)findViewById(R.id.idView);
        categoryIDView = (EditText)findViewById(R.id.categoryIDView);
        viewAllCheckBox = (CheckBox)findViewById(R.id.viewAllCheckBox);

        initializeButtons();
        testMethod();
    }

    //Test method, gives example of usage
    public void testMethod() {

        /*db.addCategory("Groceries");
        db.addItem("Milk", "1", new LocalDateTime(2016, 4, 11, 10, 23), "2% Reduced Fat");
        db.addFriend("Joe", "joep1987");
        db.updateCategory("1", "GROCERIES");
        db.updateItem("1", "MILK", "2", new LocalDateTime(2016, 4, 11, 10, 23), "2% Reduced Fat");
        db.updateFriend("1", "JOE", "joep1987");*/
        /*db.deleteCategory("1");
        db.deleteItem("1");db.deleteItem("2");db.deleteItem("3");db.deleteItem("4");
        db.deleteFriend("1");*/
    }

    public void initializeButtons() {
        btnViewCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Data", (viewAllCheckBox.isChecked() ?
                        resultToStringCategory(db.getCategories()) :
                        resultToString(db.getCategory(Integer.parseInt(idView.getText().toString())))));
            }
        });
        btnViewItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Data", (viewAllCheckBox.isChecked() ?
                        resultToStringItem(db.getItems()) :
                        resultToString(db.getItem(Integer.parseInt(idView.getText().toString())))));
            }
        });
        btnViewFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Data", (viewAllCheckBox.isChecked() ?
                        resultToStringFriend(db.getFriends()) :
                        resultToString(db.getFriend(Integer.parseInt(idView.getText().toString())))));
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Category category = new Category(nameView.getText().toString());
                category.setID(Integer.parseInt(idView.getText().toString()));
                Item item = null;
                if (!categoryIDView.getText().toString().isEmpty()) {
                    item = new Item(nameView.getText().toString(), Integer.parseInt(categoryIDView.getText().toString()));
                    item.setID(Integer.parseInt(idView.getText().toString()));
                    item.setDate(LocalDateTime.now());
                    item.setNote(notesView.getText().toString());
                }
                Friend friend = new Friend(nameView.getText().toString(), usernameView.getText().toString());
                friend.setID(Integer.parseInt(idView.getText().toString()));

                if (!idView.getText().toString().isEmpty()) {
                    if (!usernameView.getText().toString().isEmpty())
                        db.addFriend(friend);
                    else if (!categoryIDView.getText().toString().isEmpty())
                        db.addItem(item);
                    else
                        db.addCategory(category);
                }
                /*else {
                    category.setID(Integer.parseInt(idView.getText().toString()));
                    if(item != null)
                        item.setID(Integer.parseInt(idView.getText().toString()));
                    friend.setID(Integer.parseInt(idView.getText().toString()));
                    if (!usernameView.getText().toString().isEmpty())
                        db.updateFriend(friend);
                    else if (!categoryIDView.getText().toString().isEmpty())
                        db.updateItem(item);
                    else
                        db.updateCategory(category);
                }*/
            }
        });
        delCatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.deleteCategory(Integer.parseInt(idView.getText().toString()));
            }
        });
        delItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.deleteItem(Integer.parseInt(idView.getText().toString()));
            }
        });
        delFriendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.deleteFriend(Integer.parseInt(idView.getText().toString()));
            }
        });
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("RemindMe")
                        .setContentText("test");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,mBuilder.build());
    }

    public String resultToStringCategory(IterableMap<Category> map) {
        StringBuffer buffer = new StringBuffer();
        for (Category category : map) {
            buffer.append(resultToString(category));
        }
        return buffer.toString();
    }
    public String resultToStringItem(IterableMap<Item> map) {
        StringBuffer buffer = new StringBuffer();
        for (Item item : map) {
            buffer.append(resultToString(item));
        }
        return buffer.toString();
    }
    public String resultToStringFriend(IterableMap<Friend> map) {
        StringBuffer buffer = new StringBuffer();
        for (Friend friend : map) {
            buffer.append(resultToString(friend));
        }
        return buffer.toString();
    }
    public String resultToString(Category category) {
        if(category == null) return "";
        StringBuffer buffer = new StringBuffer();
        buffer.append(String.format("ID: %1$s \n", category.getID()));
        buffer.append(String.format("Name: %1$s \n", category.getName()));
        for (Item item: db.getItems(category.getID())) {
            buffer.append(resultToString(item));
        }
        buffer.append("\n");
        return buffer.toString();
    }
    public String resultToString(Item item) {
        if(item == null) return "";
        StringBuffer buffer = new StringBuffer();
        buffer.append(String.format("ID: %1$s \n", item.getID()));
        buffer.append(String.format("Name: %1$s \n", item.getName()));
        if (item.getDate() != null)
            buffer.append(String.format("Date: %1$s \n", item.getDate().toString()));
        buffer.append(String.format("Note: %1$s \n", item.getNote().toString()));
        buffer.append("\n");
        return buffer.toString();
    }
    public String resultToString(Friend friend) {
        if(friend == null) return "";
        StringBuffer buffer = new StringBuffer();
        buffer.append(String.format("ID: %1$s \n", friend.getID()));
        buffer.append(String.format("Name: %1$s \n", friend.getName()));
        buffer.append(String.format("Username: %1$s \n", friend.getUsername()));
        buffer.append("\n");
        return buffer.toString();
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
