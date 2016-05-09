package teamspoiler.renameme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private ServerAPI serAPI;                       //reference to server api class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
    }

    private void initialize(){
        final EditText username = (EditText) findViewById(R.id.Register_NameInput);
        final EditText password = (EditText) findViewById(R.id.Register_PasswordInput);
        final Button confirm = (Button) findViewById(R.id.Register_ConfirmButton);
        final Button cancel = (Button) findViewById(R.id.Register_CancelButton);
        serAPI = ServerAPI.getInstance(this);
        confirm.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString(); // get username from input box
                String passwordText = password.getText().toString(); // get password from input box

                if (usernameText.trim().equals("") || passwordText.trim().equals("")) {
                    Toast.makeText(RegisterActivity.this,
                            "incorrect username or password",
                            Toast.LENGTH_LONG).show();

                }else{
                    //TO-DO pass data to register function on server
                    if(serAPI.CheckAuthenticate(usernameText.trim(), passwordText.trim())) {
                        Toast.makeText(RegisterActivity.this,
                                "Username and password already taken",
                                Toast.LENGTH_LONG).show();
                    }else {
                        serAPI.AddingUser(usernameText.trim(), passwordText.trim());
                        finish();
                    }
                }
            }
        }));

        cancel.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }));
    }
}
