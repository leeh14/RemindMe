package teamspoiler.renameme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import teamspoiler.renameme.DataElements.*;

public class CategoryActivity extends AppCompatActivity {
    private DatabaseHelperClass db;             // reference to database helper class
    static int cid;                             // id of the category
    private Category category;                 // reference to category itself
    private IterableMap<Item> items;           // reference to items inside category
    static final int ADD_ITEM_REQUEST = 1;  // The request code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initialize();
    }

    // initialize
    private void initialize() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            cid = extras.getInt("Category_ID");
        }
        db = DatabaseHelperClass.getInstance(this);
        category = db.getCategory(cid);
        items = db.getItems(cid);

        TextView categoryTitle = (TextView)findViewById(R.id.Cate_Title);
        categoryTitle.setText(category.getName());

        final Button Categories = (Button) findViewById(R.id.Cate_CategoriesButton);
        final Button Friends = (Button) findViewById(R.id.Cate_FriendsButton);
        final Button Settings = (Button) findViewById(R.id.Cate_SettingsButton);
        final Button AddItem = (Button) findViewById(R.id.Cate_AddItemButton);
        final Button Share = (Button) findViewById(R.id. Cate_ShareButton);

        // set action for categories button at top
        Categories.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CategoriesIntent = new Intent(v.getContext(), CategoriesActivity.class);
                startActivity(CategoriesIntent);
            }
        }));

        // set action for friend button at top
        Friends.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FriendsIntent = new Intent(v.getContext(), FriendsActivity.class);
                startActivity(FriendsIntent);
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

        // set action for add item button at bottom
        AddItem.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddItemIntent = new Intent(v.getContext(), AddItemActivity.class);
                AddItemIntent.putExtra("Category_ID", cid);
                startActivityForResult(AddItemIntent, ADD_ITEM_REQUEST);
            }
        }));

        // set action for share button at bottom
        Share.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ShareIntent = new Intent(v.getContext(), ShareCategoryActivity.class);
                startActivity(ShareIntent);
            }
        }));

        populateItemsList();
        registerClickCallBack();
    }

    // populate the category list with category button
    private void populateItemsList() {
        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(
                this,
                R.layout.data_itemslist,
                items.toList());
        ListView list = (ListView) findViewById(R.id.Cate_ItemsList);
        list.setAdapter(adapter);
    }

    // set action for category list button
    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.Cate_ItemsList);
        list.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Item it = (Item) parent.getAdapter().getItem(position);
                Intent i = new Intent(CategoryActivity.this, ItemActivity.class);
                i.putExtra("Category_ID", cid);
                i.putExtra("Item_ID", it.getID());
                startActivity(i);
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // refresh the activity
        if (requestCode == ADD_ITEM_REQUEST) {
            finish();
            startActivity(getIntent());
        }
    }
}
