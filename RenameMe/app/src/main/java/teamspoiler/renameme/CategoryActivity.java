package teamspoiler.renameme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import teamspoiler.renameme.DataElements.*;

public class CategoryActivity extends AppCompatActivity {
    DatabaseHelperClass db;
    static int id;
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
        db = new DatabaseHelperClass(this);
        Category category = db.getCategory(id);
        TextView categoryTitle = (TextView)findViewById(R.id.Cate_Title);
        categoryTitle.setText(category.getName());
    }
}
