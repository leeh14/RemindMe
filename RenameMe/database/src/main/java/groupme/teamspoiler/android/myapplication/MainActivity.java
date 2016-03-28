package groupme.teamspoiler.android.myapplication;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.joda.time.LocalDateTime;

//Database test activity
public class MainActivity extends AppCompatActivity {
    private DatabaseHelperClass db;
    private Button btnViewCategories, btnViewItems, btnViewFriends;

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

        db = new DatabaseHelperClass(this);
        btnViewCategories = (Button) findViewById(R.id.btnViewCategories);
        btnViewItems = (Button) findViewById(R.id.btnViewItems);
        btnViewFriends = (Button) findViewById(R.id.btnViewFriends);
        viewData();
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

    public void viewData() {
        btnViewCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Data", resultToString(db.getCategoryData()));
            }
        });
        btnViewItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Data", resultToString(db.getItemData()));
            }
        });
        btnViewFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Data", resultToString(db.getFriendData()));
            }
        });
    }

    public String resultToString(Cursor result) {
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()) {
            for (int i = 0; i < result.getColumnCount(); i++)
                buffer.append(String.format("%1$s: %2$s \n", result.getColumnName(i), result.getString(i)));
            buffer.append("\n");
        }
        buffer.append("\n\n");
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
