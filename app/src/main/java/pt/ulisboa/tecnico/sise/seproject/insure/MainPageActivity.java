package pt.ulisboa.tecnico.sise.seproject.insure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainPageActivity extends AppCompatActivity {

    private Button buttonLogout;

    private TextView textViewHelloMessage;

    private Button buttonMyInformation;
    private Button buttonNewClaim;
    private Button buttonMyClaims;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        buttonLogout = findViewById(R.id.main_page_logout_button);

        // change hello message
        textViewHelloMessage = findViewById(R.id.main_page_hello_message);
        Bundle extras = getIntent().getExtras();
        String username = extras.getString("USERNAME");
        textViewHelloMessage.setText("Hello " + username + " !");

        buttonMyInformation = findViewById(R.id.main_page_my_information_button);
        buttonNewClaim = findViewById(R.id.main_page_new_claim_button);
        buttonMyClaims = findViewById(R.id.main_page_my_claims_button);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonMyInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, MyInformationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonNewClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, NewClaimActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonMyClaims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, MyClaimsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
