package teamspoiler.renameme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.joda.time.LocalDateTime;

import teamspoiler.renameme.DataElements.*;

public class ItemActivity extends AppCompatActivity {
    private DatabaseHelperClass db;      // reference to the database class
    private int iid, cid;               // item id and category id
    private Category category;          // the category object
    private ServerAPI serAPI;                       //reference to server api class
    private Item item;                  // the item object
    final Context context = this;                  // context of this activity

    static final int EDIT_ITEM_REQUEST = 1;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        initialize();
    }

    private void initialize() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            iid = extras.getInt(getString(R.string.extra_item_id));
        }
        db = DatabaseHelperClass.getInstance(this);
        serAPI = ServerAPI.getInstance(this);
        item = db.getItem(iid);
        cid = item.getCategoryID();
        category = db.getCategory(cid);

        ItemNotification.denotify(ItemActivity.this, item);

        final TextView CategoryTitle = (TextView) findViewById(R.id.Item_Category);
        final TextView ItemName = (TextView) findViewById(R.id.Item_Name);
        final TextView ExpDate = (TextView) findViewById(R.id.Item_ExpDate);
        final TextView ExpTime = (TextView) findViewById(R.id.Item_ExpTime);
        final TextView Note = (TextView) findViewById(R.id.Item_Note);
        final Button Edit = (Button) findViewById(R.id.Item_EditButton);
        final Button Delete = (Button) findViewById(R.id.Item_DeleteButton);

        CategoryTitle.setText(category.getName());
        ItemName.setText(item.getName());
        if(item.getDate() != null) {
            LocalDateTime date = item.getDate();
            ExpDate.setText((date.getMonthOfYear()) + "/"
            + date.getDayOfMonth() + "/" + date.getYear());
            ExpTime.setText(date.getHourOfDay() + ":"
            + date.getMinuteOfHour());
        }
        if (item.getNote() != null) {
            Note.setText(item.getNote());
        }

        // set action for add item button at bottom
       Edit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EditItemIntent = new Intent(v.getContext(), EditItemActivity.class);
                EditItemIntent.putExtra(getString(R.string.extra_item_id), iid);
                startActivityForResult(EditItemIntent, EDIT_ITEM_REQUEST);
            }
        }));

        Delete.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title
                String iName = item.getName();
                alertDialogBuilder.setTitle("Delete Item");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to delete " + iName + "?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                db.deleteItem(item.getID());
                                serAPI.DeleteItem(item.getID());
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // close the dialog
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // refresh the activity
        if (requestCode == EDIT_ITEM_REQUEST) {
            finish();
            startActivity(getIntent());
        }
    }
}
