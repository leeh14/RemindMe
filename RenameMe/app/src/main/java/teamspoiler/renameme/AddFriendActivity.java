package teamspoiler.renameme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import teamspoiler.renameme.DataElements.*;

public class AddFriendActivity extends AppCompatActivity {

    DatabaseHelperClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        initialize();
    }

    private void initialize(){
        db = DatabaseHelperClass.getInstance(this);
        final Button Submit = (Button) findViewById(R.id.AddFriend_SubmitButton);
        final Button Cancel = (Button) findViewById(R.id.AddCate_CancelButton);

        // set action for friend button at top
        Submit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView username = (TextView) findViewById(R.id.AddFriend_UsernameInput);
                TextView name = (TextView) findViewById(R.id.AddFriend_NameInput);
                Friend nf = new Friend(name.getText().toString(),username.getText().toString());
                db.addFriend(nf);
                finish();
            }
        }));
    }
}
