package teamspoiler.renameme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import teamspoiler.renameme.DataElements.Friend;
import teamspoiler.renameme.DataElements.IterableMap;

public class FriendsActivity extends AppCompatActivity {
    DatabaseHelperClass db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        initialize();
    }

    private void initialize() {
        db = new DatabaseHelperClass(this);
        IterableMap<Friend> friends = db.getFriends();
        //Implement in a similar manner to categories
    }


}
