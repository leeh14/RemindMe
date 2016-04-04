package teamspoiler.renameme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CategoryActivity extends AppCompatActivity {

    static String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initialize();
    }

    private void initialize() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            name = extras.getString("Category_Name");
        }

        TextView categoryTitle = (TextView)findViewById(R.id.Cate_Title);
        categoryTitle.setText(name);
    }
}
