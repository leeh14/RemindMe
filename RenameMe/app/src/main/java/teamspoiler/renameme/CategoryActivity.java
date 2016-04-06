package teamspoiler.renameme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    private void initialize() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getInt("Category_ID");
        }
        db = DatabaseHelperClass.getInstance(this);
        category = db.getCategory(id);
        TextView categoryTitle = (TextView)findViewById(R.id.Cate_Title);
        categoryTitle.setText(category.getName());
    }

    //To get items
    //getItems(category.getID());
}
