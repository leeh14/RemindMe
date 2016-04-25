package teamspoiler.renameme;

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
    private Item item;                  // the item object

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
            cid = extras.getInt("Category_ID");
            iid = extras.getInt("Item_ID");
        }
        db = DatabaseHelperClass.getInstance(this);
        category = db.getCategory(cid);
        item = db.getItem(iid);

        final TextView CategoryTitle = (TextView) findViewById(R.id.Item_Category);
        final TextView ItemName = (TextView) findViewById(R.id.Item_Name);
        final TextView ExpDate = (TextView) findViewById(R.id.Item_ExpDate);
        final TextView ExpTime = (TextView) findViewById(R.id.Item_ExpTime);
        final TextView Note = (TextView) findViewById(R.id.Item_Note);
        final Button Edit = (Button) findViewById(R.id.Item_EditButton);
        final Button Share = (Button) findViewById(R.id.Item_ShareButton);

        CategoryTitle.setText(category.getName());
        ItemName.setText(item.getName());
        if(item.getDate() != null) {
            LocalDateTime date = item.getDate();
            ExpDate.setText((date.getMonthOfYear()+1) + "/"
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
                EditItemIntent.putExtra("Item_ID", iid);
                startActivityForResult(EditItemIntent, EDIT_ITEM_REQUEST);
            }
        }));

        // set action for add item button at bottom
        Share.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // TO-DO implement the share button
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
