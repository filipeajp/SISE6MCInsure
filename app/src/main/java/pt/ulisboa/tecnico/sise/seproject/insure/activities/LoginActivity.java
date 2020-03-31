package pt.ulisboa.tecnico.sise.seproject.insure.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.seproject.insure.R;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText editTextUsername;
    private EditText editTextPassword;

    private String username;
    private String password;

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
                username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();

                // password has to be 1234 for now
                if (password.equals("1234")) {
                    Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(view.getContext(), "Wrong password!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
