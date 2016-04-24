package teamspoiler.renameme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;//will remove
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;//will remove

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

                //ac.execute(categoryName).get();
                //try {
                String s = serAPI.AddingCat(categoryName);
                //String s = vali.Authentication(uname,upass);
                //works everywhere except debug mode
                String b = "sdf";
//                }
//                catch (InterruptedException e )
//                {
//                    String s = e.getMessage();
//                }catch (ExecutionException b ) {
//                    String sdf = b.getMessage();
//                }

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
