package teamspoiler.renameme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddItemActivity extends AppCompatActivity {
    DatabaseHelperClass db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        initialize();
    }

    private void initialize() {
        db = new DatabaseHelperClass(this);
    }

    //Item item = new Item(ITEMNAME); item.setDate(new LocalDateTime(int year, int month, int day, int hour, int minute));
    //item.setNote(NOTE);
    //Add item by calling db.addItem(item);
}
