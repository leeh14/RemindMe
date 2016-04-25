package teamspoiler.renameme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import teamspoiler.renameme.DataElements.Category;
import teamspoiler.renameme.DataElements.Friend;
import teamspoiler.renameme.DataElements.Item;

public class ReceiveActivity extends AppCompatActivity {

    private DatabaseHelperClass db;      // reference to the database class
    private Category category;          // the category object
    private Item item;                  // the item object
    private Friend friend;              // the friend object
    final Context context = this;      // context of this activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        initialize();
    }

    public void initialize(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            //TO-DO get SerializableExtra data
        }
        db = DatabaseHelperClass.getInstance(this);

        final TextView PackageName = (TextView) findViewById(R.id.Receive_PackageName);
        final TextView FriendName = (TextView) findViewById(R.id.Receive_FriendName);
        final Button Confirm = (Button) findViewById(R.id.Receive_ConfirmButton);
        final Button Cancel = (Button) findViewById(R.id.Receive_CancelButton);

        if(item != null){
            PackageName.setText(item.getName() + " (item)");
        }else{
            PackageName.setText(category.getName() + " (category)");
        }
        FriendName.setText(friend.getName());

        Confirm.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Select Category to add to");

                    ListView modeList = new ListView(context);
                    ArrayAdapter<Category> modeAdapter = new ArrayAdapter<Category>(
                            context,
                            android.R.layout.simple_list_item_1,
                            android.R.id.text1,
                            db.getCategories().toList());
                    modeList.setAdapter(modeAdapter);

                    builder.setView(modeList);
                    final Dialog dialog = builder.create();
                    dialog.show();

                    db.addItem(item);
                } else {
                    db.addCategory(category);
                }
            }
        }));

        Cancel.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }));
    }
}
