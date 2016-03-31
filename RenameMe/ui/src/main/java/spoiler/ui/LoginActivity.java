package spoiler.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
    }

    public void initialize() {
        Button submit = (Button) findViewById(R.id.login_SubmitButton);
        final EditText username = (EditText) findViewById(R.id.login_UserNameInput);
        final EditText password = (EditText) findViewById(R.id.login_PasswordInput);

        submit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                if(!usernameText.trim().equals("")) {
                    Toast.makeText(LoginActivity.this, usernameText, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivity.this, "empty username", Toast.LENGTH_LONG).show();
                }

                if(!passwordText.trim().equals("")) {
                    Toast.makeText(LoginActivity.this, passwordText, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivity.this, "empty password", Toast.LENGTH_LONG).show();
                }
            }
        }));
    }
}
