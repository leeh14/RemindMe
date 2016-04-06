package teamspoiler.renameme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ItemActivity extends AppCompatActivity {
    DatabaseHelperClass db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        initialize();
    }

    private void initialize() {
        db = new DatabaseHelperClass(this);
        //Item item = db.getItem(itemID);
        //etc...
    }


}
