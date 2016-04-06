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

import teamspoiler.renameme.DataElements.Category;
import teamspoiler.renameme.DataElements.*;

public class CategoriesActivity extends AppCompatActivity {
    IterableMap<Category>  categories;
    DatabaseHelperClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        initialize();
    }

    // initialize
    private void initialize() {
        final Button Friends = (Button) findViewById(R.id.Cates_FriendsButton);
        final Button Settings = (Button) findViewById(R.id.Cates_SettingButton);
        final Button AddCate = (Button) findViewById(R.id.Cates_AddCategoryButton);

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

        // set action for adding category button
        AddCate.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddCategory = new Intent(CategoriesActivity.this,
                                                AddCategoryActivity.class);
                startActivity(AddCategory);
            }
        }));

        db = DatabaseHelperClass.getInstance(this);
        categories = db.getCategories();
        // populate the category list
        populateCategoryList();
        // set action for category list button
        registerClickCallBack();
    }

    // populate the category list with category button
    private void populateCategoryList() {
        /*String[] categoryNames = new String[categories.size()];
        int count = 0;
        for (Category c : categories) {
            categoryNames[count] = c.getName();
            count++;
        }
        categories.add(new Category(1, "name"));*/

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
                TextView textView = (TextView) viewClicked;
                Intent i = new Intent(CategoriesActivity.this, CategoryActivity.class);
                Category c = (Category)parent.getAdapter().getItem(position);
                i.putExtra("Category_ID", c.getID());
                //i.putExtra("Category_Name", textView.getText().toString());
                startActivity(i);
            }
        }));
    }

}
