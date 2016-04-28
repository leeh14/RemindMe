package teamspoiler.renameme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import teamspoiler.renameme.DataElements.*;

public class FriendsActivity extends AppCompatActivity {
    DatabaseHelperClass db;
    IterableMap<Friend> friends;
    static final int ADD_FRIEND_REQUEST = 1;  // The request code
    final Context context = this;                  // context of this activity
    ArrayAdapter<Friend> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        initialize();
    }

    private void initialize() {
        db = DatabaseHelperClass.getInstance(this);
        friends = db.getFriends();

        final Button Categories = (Button) findViewById(R.id.Friends_CategoriesButton);
        final Button Settings = (Button) findViewById(R.id.Friends_SettingsButton);
        final Button AddFriend = (Button) findViewById(R.id.Friends_AddFriendButton);

        // set action for friend button at top
        Categories.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CategoriesIntent = new Intent(v.getContext(), CategoriesActivity.class);
                startActivity(CategoriesIntent);
            }
        }));

        // set action for setting button at top
        Settings.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SettingsIntent = new Intent(v.getContext(), SettingsActivity.class);
                startActivity(SettingsIntent);
            }
        }));

        // set action for setting button at top
        AddFriend.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddFriendIntent = new Intent(v.getContext(), AddFriendActivity.class);
                startActivityForResult(AddFriendIntent, ADD_FRIEND_REQUEST);
            }
        }));
        populateItemsList();
        registerClickCallBack();
    }

    // populate the category list with category button
    private void populateItemsList() {
        adapter = new ArrayAdapter<Friend>(
                this,
                R.layout.data_sharelist,
                friends.toList());
        ListView list = (ListView) findViewById(R.id.Friends_FriendList);
        list.setAdapter(adapter);
    }

    // set action for category list button
    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.Friends_FriendList);
        list.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                final Friend friend = (Friend) parent.getAdapter().getItem(position);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                final String fName = friend.getName();
                final String fuName = friend.getUsername();

                // set title
                alertDialogBuilder.setTitle("Friend");

                // set dialog message
                alertDialogBuilder
                        .setMessage("username: " + fuName +
                                "\nname: " + fName)
                        .setCancelable(false)
                        .setPositiveButton("Delete",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(
                                        context);

                                // set title

                                alertDialogBuilder2.setTitle("Delete friend");

                                // set dialog message
                                alertDialogBuilder2
                                        .setMessage("Do you want to delete " + fName + " ?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                db.deleteFriend(friend.getID());
                                                finish();
                                                startActivity(getIntent());
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // close the dialog
                                                dialog.cancel();
                                            }
                                        });

                                // create alert dialog
                                AlertDialog alertDialog2 = alertDialogBuilder2.create();

                                // show it
                                alertDialog2.show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
        if (requestCode == ADD_FRIEND_REQUEST) {
            finish();
            startActivity(getIntent());
        }
    }
}
