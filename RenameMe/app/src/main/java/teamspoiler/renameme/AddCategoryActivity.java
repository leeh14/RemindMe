package teamspoiler.renameme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddCategoryActivity extends AppCompatActivity {
    DatabaseHelperClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        db = DatabaseHelperClass.getInstance(this);
    }


    //Add category by simply calling db.addCategory(new Category(NAME));
}
