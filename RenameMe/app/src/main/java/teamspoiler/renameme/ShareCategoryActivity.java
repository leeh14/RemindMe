package teamspoiler.renameme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import teamspoiler.renameme.DataElements.*;

public class ShareCategoryActivity extends AppCompatActivity {

    private DatabaseHelperClass db;
    private IterableMap<Friend> friends;
    public static boolean[] selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_category);
        initialize();
    }

    private void initialize(){
        db = DatabaseHelperClass.getInstance(this);
        friends = db.getFriends();
        final Button Save = (Button) findViewById(R.id.ShareCate_ShareButton);
        final Button Cancel = (Button) findViewById(R.id.ShareCate_CancelButton);

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
        selected = new boolean[friends.toList().size()];
        ArrayAdapter<Friend> adapter = new ArrayAdapter<Friend>(
                this,
                R.layout.data_sharelist,
                friends.toList());
        ListView list = (ListView) findViewById(R.id.ShareCate_ShareList);
        list.setAdapter(adapter);
    }

    // set action for share category list button
    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.ShareCate_ShareList);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setItemsCanFocus(false);
        list.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                viewClicked.setSelected(true);
                selected[position] = !selected[position];
            }
        }));
    }
}

