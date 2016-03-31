package spoiler.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        initialize();
    }

    public void initialize() {
        final Button Categories = (Button) findViewById(R.id.Cates_CategoriesButton);
        final Button Friends = (Button) findViewById(R.id.Cates_FriendsButton);
        final Button Settings = (Button) findViewById(R.id.Cates_SettingButton);

       Friends.setOnClickListener((new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent FriendsIntent = new Intent(v.getContext(), FriendsActivity.class);
               startActivity(FriendsIntent);
           }
       }));

       Settings.setOnClickListener((new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               Intent SettingsIntent = new Intent(v.getContext(), SettingsActivity.class);
               startActivity(SettingsIntent);
           }
       }));
    }
}
