package pt.ulisboa.tecnico.sise.seproject.insure;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ClaimSubmittedActivity extends AppCompatActivity {
    private static final String LOG_TAG = "InsureApp";
    private Button buttonMenu;
    private Button buttonMyClaims;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_submitted);
        Log.d(LOG_TAG, "Claim submitted!");

        buttonMenu = findViewById(R.id.claim_submitted_btn_menu);
        buttonMyClaims = findViewById(R.id.claim_submitted_btn_my_claims);

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Menu button clicked.");
            }
        });

        buttonMyClaims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "MyClaims button clicked!");
            }
        });

    }
}
