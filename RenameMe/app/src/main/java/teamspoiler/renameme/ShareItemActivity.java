package teamspoiler.renameme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import teamspoiler.renameme.DataElements.Category;
import teamspoiler.renameme.DataElements.Friend;
import teamspoiler.renameme.DataElements.Item;
import teamspoiler.renameme.DataElements.IterableMap;

public class ShareItemActivity extends AppCompatActivity {

    private DatabaseHelperClass db;                 // reference to database
    static int iid;                                 // id of the category
    private Item item;                             // reference to item itself
    private IterableMap<Friend> friends;           // reference to friend list
    final Context context = this;                  // context of this activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_item);
        initialize();
    }

    private void initialize(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            iid = extras.getInt("Item_ID");
        }
        db = DatabaseHelperClass.getInstance(this);
        item = db.getItem(iid);
        friends = db.getFriends();
        final Button Save = (Button) findViewById(R.id.ShareItem_ShareButton);
        final Button Cancel = (Button) findViewById(R.id.ShareItem_CancelButton);

        // set action for friend button at top
        Save.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }));

        // set action for setting button at top
        Cancel.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }));

        // populate the category list
        populateShareList();
        // set action for category list button
        registerClickCallBack();
    }

    // populate the category list with category button
    private void populateShareList() {
        ArrayAdapter<Friend> adapter = new ArrayAdapter<Friend>(
                this,
                R.layout.data_sharelist,
                friends.toList());
        ListView list = (ListView) findViewById(R.id.ShareItem_ShareList);
        list.setAdapter(adapter);
    }

    // set action for share category list button
    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.ShareItem_ShareList);
        list.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                final Friend friend = (Friend) parent.getAdapter().getItem(position);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title
                String iName = item.getName();
                String fName = friend.getName();
                alertDialogBuilder.setTitle("Share with friend");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to share " + iName + " with " + fName + "?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                shareItem(friend);
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

    // send request to server of sharing a category with a friend
    private void shareItem(Friend friend){
        // target item is = static variable above named item
        // target friend is = Friend argument named friend
        // TO-DO send request to server
    }
}
