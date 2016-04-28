package teamspoiler.renameme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initialize();
    }

    private void initialize(){
        final Button Categories = (Button) findViewById(R.id.Setting_CategoriesButton);
        final Button Friends = (Button) findViewById(R.id.Setting_FriendsButton);

        // set action for categories button at top
        Categories.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CategoriesIntent = new Intent(v.getContext(), CategoriesActivity.class);
                startActivity(CategoriesIntent);
            }
        }));

        // set action for friend button at top
        Friends.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FriendsIntent = new Intent(v.getContext(), FriendsActivity.class);
                startActivity(FriendsIntent);
            }
        }));
    }
}
