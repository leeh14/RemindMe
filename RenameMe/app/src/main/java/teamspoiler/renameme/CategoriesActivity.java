package teamspoiler.renameme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        initialize();
    }

    public void initialize() {
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

        AddCate.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent AddCategory = new Intent(v.getContext(), CategoryActivity.class);
                //startActivity(AddCategory);
            }
        }));

        populateCategoryList();
    }

    // populate the category list with category button
    public void populateCategoryList() {

    }

}
