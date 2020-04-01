package pt.ulisboa.tecnico.sise.seproject.insure.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.R;
import pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks.LoginTask;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText editTextUsername;
    private EditText editTextPassword;

    private String _username;
    private String _password;
    private int _sessionId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final GlobalState globalState = (GlobalState) getApplicationContext();

        buttonLogin = findViewById(R.id.login_button);
        editTextUsername = findViewById(R.id.login_user_name);
        editTextPassword = findViewById(R.id.login_user_password);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _username = editTextUsername.getText().toString();
                _password = editTextPassword.getText().toString();

                Log.d("sise", _username + " " + _password);
                new LoginTask(_username, _password, globalState, view.getContext()).execute();

//                if (globalState.getSessionId() <= 0) {
//                    Log.d("Call", "" + globalState.getSessionId());
//                    Toast.makeText(view.getContext(), "Login failed! Username or password incorrect.", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
//                    intent.putExtra("USERNAME", _username);
//                    startActivity(intent);
//                    finish();
//                }
            }
        });

    }
}
