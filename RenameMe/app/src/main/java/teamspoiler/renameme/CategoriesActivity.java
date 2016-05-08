package teamspoiler.renameme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import teamspoiler.renameme.DataElements.*;

public class CategoriesActivity extends AppCompatActivity {

    private DatabaseHelperClass db;                     // reference to database helper class
    private ServerAPI Servera;                          //reference to server database helper class
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
        Servera = ServerAPI.getInstance(this);
        pullSharedCategories();
        categories = db.getCategories();
        // populate the category list
        populateCategoryList();
        // set action for category list button
        registerClickCallBack();
    }

    //Add categeories on the server that were shared with you by other users
    private void pullSharedCategories() {
        //adding to categories from the share
        Pair<Boolean, List<String>> check = Servera.CheckShareCategory();
        if(check.first == true){
            ArrayList<Pair<String, String>> new_cat = new ArrayList<Pair<String, String>>();
            String delims = "\\|";
            Pair<String, String> tempcat = new Pair<String, String>("","");
            for (Category i : db.getCategories())
                //second for loop to iterate through all categories
                for(String category : check.second) {
                    //split the string into 2 parts
                    String[] categories = category.split(delims);
                    if (i.getName().equals(categories[1])) {
                        //exist so reset tempcat to nothing and break
                        tempcat = new Pair<String, String>("","");
                        break;

                    }else {
                        tempcat = new Pair<String, String>(categories[0],categories[1]);
                    }
                }
                //make sure it has a valid value before adding to it
                if(!tempcat.first.equals("")){
                    new_cat.add(tempcat);
                }
            //if database is empty still have to add the categories found
            if(db.getCategories().toList().isEmpty()){
                for(String category : check.second) {
                    //split the string into 2 parts
                    String[] categories = category.split(delims);
                    new_cat.add(new Pair<String, String>(categories[0],categories[1]));
                }
            }
            //if new_cat holds all the new categories that need to be built
            if(!new_cat.isEmpty()){
                //iterate through categories
                for(Pair<String ,String> cat: new_cat){
                    Category c = new Category(cat.second);
                    c.setID(Integer.parseInt(cat.first));

                    db.addCategory(c);
                    List<String> new_item = Servera.AddShare(Integer.parseInt(cat.first));
                    if (new_item != null) {
                        for (String s: new_item
                                ) {
                            //String delims = "|";
                            String[] item_values= s.split(delims);
                            Integer cat_num= c.getID();
                            Item item = new Item(item_values[0],cat_num );
                            LocalDateTime dateTime = LocalDateTime.parse(item_values[1]);
                            item.setDate(dateTime);
                            item.setNote(item_values[2]);
                            db.addItem(item);
                        }
                    }
                }
            }
        }
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

            //ShareIntent.putExtra("Category_ID", cid);
        }
    }
}
