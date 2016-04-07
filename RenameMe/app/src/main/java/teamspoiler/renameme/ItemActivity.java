package teamspoiler.renameme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import teamspoiler.renameme.DataElements.*;

public class ItemActivity extends AppCompatActivity {
    private DatabaseHelperClass db;
    private int iid, cid;
    private Category category;
    private Item item;

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
        if(category == null){
            Log.i("TAGTAG", "here");
        }
        if(item == null){
            Log.i("TAGTAG", "here2");
        }

        final TextView CategoryTitle = (TextView) findViewById(R.id.Item_CategoryTitle);
        final TextView ItemName = (TextView) findViewById(R.id.Item_Name);
        final TextView ExpDate = (TextView) findViewById(R.id.Item_ExpDate);
        final TextView Note = (TextView) findViewById(R.id.Item_Note);

        CategoryTitle.setText(category.getName());
        ItemName.setText(item.getName());
        ExpDate.setText(item.getDate().toString());
        Note.setText(item.getNote());

        //Item item = db.getItem(itemID);
        //etc...
    }


}
