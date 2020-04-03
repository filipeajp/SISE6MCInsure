package pt.ulisboa.tecnico.sise.seproject.insure.activities;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.R;
import pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks.LoginTask;
import pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks.TaskCallBack;

public class LoginActivity extends AppCompatActivity implements TaskCallBack {

    private Button buttonLogin;
    private EditText editTextUsername;
    private EditText editTextPassword;

    private String _username;
    private String _password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = findViewById(R.id.login_button);
        editTextUsername = findViewById(R.id.login_user_name);
        editTextPassword = findViewById(R.id.login_user_password);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _username = editTextUsername.getText().toString();
                _password = editTextPassword.getText().toString();

                Log.d("sise", _username + " " + _password);
                new LoginTask(_username, _password, (GlobalState) getApplicationContext(), view.getContext()).execute();

            }
        });

    }

    @Override
    public void done() {
        finish();
    }
}
