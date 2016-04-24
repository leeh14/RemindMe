package teamspoiler.renameme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    // initialize
    public void initialize() {


        final Button submit = (Button) findViewById(R.id.login_SubmitButton);
        final EditText username = (EditText) findViewById(R.id.login_UserNameInput);
        final EditText password = (EditText) findViewById(R.id.login_PasswordInput);

        // the submit button function
        submit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString(); // get username from input box
                String passwordText = password.getText().toString(); // get password from input box

                // check if username is empty
                if (usernameText.trim().equals("")) {
                    Toast.makeText(LoginActivity.this, "empty username", Toast.LENGTH_LONG).show();
                }

                // check if password is empty
                if (passwordText.trim().equals("")) {
                    Toast.makeText(LoginActivity.this, "empty password", Toast.LENGTH_LONG).show();
                }

                // check if user name and password is valid
                //if(checkValidity(usernameText, passwordText)){
                //accessing the server api singleton
                ServerAPI Servera = ServerAPI.getInstance(LoginActivity.this);
                if(Servera.CheckAuthenticate(usernameText, passwordText)){
                    // move to CategoriesActivity
                    Intent i = new Intent(LoginActivity.this, CategoriesActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(LoginActivity.this, "incorrect username / password", Toast.LENGTH_LONG).show();
                }
            }
        }));
    }

    // check for username and password validity
    public boolean checkValidity(String un, String pw) {
        String unAns = "sdd";   // user name answer -> get from server
        String pwAns = "sdd";   // user name password -> get from server
        if(unAns.equals(un) && pwAns.equals(pw)) {
            return true;
        }
        return  false;
    }
}
