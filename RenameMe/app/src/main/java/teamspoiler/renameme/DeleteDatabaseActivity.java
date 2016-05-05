package teamspoiler.renameme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by hirats on 5/4/2016.
 */
//Deletes the local database on the phone
public class DeleteDatabaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        deleteDatabase(getString(R.string.database_name));
        super.onCreate(savedInstanceState);
    }
}
