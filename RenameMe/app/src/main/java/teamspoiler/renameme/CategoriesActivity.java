package teamspoiler.renameme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import teamspoiler.renameme.DataElements.*;

public class CategoriesActivity extends AppCompatActivity {

    private DatabaseHelperClass db;                     // reference to database helper class
    private IterableMap<Category>  categories;         // categories data class
    static final int ADD_CATEGORY_REQUEST = 1;      // The request code
    static final int MERGE_CATEGORY_REQUEST = 2;    // Th request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        initialize();
    }

    // initialize
    private void initialize() {
        final Button Friends = (Button) findViewById(R.id.Cates_FriendsButton);
        final Button AddCate = (Button) findViewById(R.id.Cates_AddCategoryButton);

        // set action for friend button at top
       Friends.setOnClickListener((new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent FriendsIntent = new Intent(v.getContext(), FriendsActivity.class);
               startActivity(FriendsIntent);
           }
       }));

        // set action for adding category button
        AddCate.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddCategory = new Intent(CategoriesActivity.this,
                                                AddCategoryActivity.class);
                startActivityForResult(AddCategory, ADD_CATEGORY_REQUEST);
            }
        }));

        // get reference
        db = DatabaseHelperClass.getInstance(this);
        categories = db.getCategories();
        // populate the category list
        populateCategoryList();
        // set action for category list button
        registerClickCallBack();
    }

    // populate the category list with category button
    private void populateCategoryList() {
        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(
                this,
                R.layout.data_categorieslist,
                categories.toList());
        ListView list = (ListView) findViewById(R.id.Cates_CategoriesList);
        list.setAdapter(adapter);
    }

    // set action for category list button
    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.Cates_CategoriesList);
        list.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Category c = (Category) parent.getAdapter().getItem(position);
                Intent i = new Intent(CategoriesActivity.this, CategoryActivity.class);
                i.putExtra(getString(R.string.extra_category_id), c.getID());
                startActivityForResult(i, MERGE_CATEGORY_REQUEST);
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // refresh the activity
        if (requestCode == ADD_CATEGORY_REQUEST || requestCode == MERGE_CATEGORY_REQUEST) {
            finish();
            startActivity(getIntent());
        }
    }
}
