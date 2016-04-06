package teamspoiler.renameme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCategoryActivity extends AppCompatActivity {
    DatabaseHelperClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
<<<<<<< HEAD
        initialize();
    }

    // initialize
    private void initialize() {
        final Button Save = (Button) findViewById(R.id.AddCate_SaveButton);
        final Button Cancel = (Button) findViewById(R.id.AddCate_CancelButton);
        final EditText InputName = (EditText) findViewById(R.id.AddCate_InputName);

        // save
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = InputName.getText().toString(); // get username from input box
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
=======
        db = DatabaseHelperClass.getInstance(this);
>>>>>>> e18cb68e069a9a3565eae2258e6ebb56fa926c21
    }


    //Add category by simply calling db.addCategory(new Category(NAME));
}
