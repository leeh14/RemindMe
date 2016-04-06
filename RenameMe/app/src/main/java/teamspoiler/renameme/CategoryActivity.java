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
    DatabaseHelperClass db;
    static int id;
    Category category;
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
            id = extras.getInt("Category_ID");
        }
        db = DatabaseHelperClass.getInstance(this);
        category = db.getCategory(id);

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

        AddItem.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddItemIntent = new Intent(v.getContext(), AddItemActivity.class);
                startActivity(AddItemIntent);
            }
        }));

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
        String[] itemsList = {"milk", "tea", "ham", "spaghetti"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.data_itemslist,
                itemsList);
        ListView list = (ListView) findViewById(R.id.Cate_ItemsList);
        list.setAdapter(adapter);
    }

    // set action for category list button
    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.Cate_ItemsList);
        list.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                Intent i = new Intent(CategoryActivity.this, ItemActivity.class);
                i.putExtra("Item_Name", textView.getText().toString());
                startActivity(i);
            }
        }));
    }

    //To get items
    //getItems(category.getID());
}
