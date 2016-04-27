package teamspoiler.renameme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import teamspoiler.renameme.DataElements.Category;
import teamspoiler.renameme.DataElements.*;

public class AddCategoryActivity extends AppCompatActivity {

    private DatabaseHelperClass db;                 // reference to database helper class
    private IterableMap<Category>  categories;    // categories data class
    private ServerAPI serAPI;                       //reference to server api class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        initialize();
    }

    // initialize
    private void initialize() {

        // get reference
        db = DatabaseHelperClass.getInstance(this);
        categories = db.getCategories();
        serAPI = ServerAPI.getInstance(this);
        // set buttons' function
        final Button Save = (Button) findViewById(R.id.AddCate_SaveButton);
        final Button Cancel = (Button) findViewById(R.id.AddCate_CancelButton);
        final EditText InputName = (EditText) findViewById(R.id.AddCate_InputName);

        // save
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = InputName.getText().toString(); // get username from input box
                Category nc = new Category(categoryName);
                db.addCategory(nc);

                //testing adding the category to the server
                //String s = serAPI.AddingCat(categoryName);
                //adding category to server
                serAPI.AddingCat(nc);

                //testing the updating category
//                IterableMap<Item> k = db.getItems();
//                for (Item i : k)
//                    if(i.getName().equals("Guth"))
//                    {
//                        i.setName("Not Guth");
//                        i.setNote("ther is something");
//                        serAPI.UpdateItem(i);
//                    }
                    //serAPI.UpdatingCat(i.getName());
                //testing the updating of categories
                finish();
            }
        });

        // cancel
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
