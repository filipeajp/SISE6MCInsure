package pt.ulisboa.tecnico.sise.seproject.insure.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.InternalProtocol;
import pt.ulisboa.tecnico.sise.seproject.insure.R;
import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.ClaimMessage;
import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.ClaimRecord;

public class MainPageActivity extends AppCompatActivity {
    private ArrayList<ClaimRecord> _claimList;
    private Button buttonLogout;
    private TextView textViewHelloMessage;
    private Button buttonMyInformation;
    private Button buttonNewClaim;
    private Button buttonMyClaims;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // place the claim list in the application domain
        _claimList = new ArrayList<>();
        GlobalState globalState = (GlobalState) getApplicationContext();
        globalState.setClaimList(_claimList);

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
            }
        });

        buttonNewClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, NewClaimActivity.class);
                startActivityForResult(intent, InternalProtocol.NEW_ClAIM_REQUEST);
                //finish();
            }
        });

        buttonMyClaims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, MyClaimsActivity.class);
                startActivity(intent);
                //finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case InternalProtocol.NEW_ClAIM_REQUEST:

                if (resultCode == Activity.RESULT_OK) {
                    // retrieve the claim data from the intent
                    String claimTitle = data.getStringExtra(InternalProtocol.KEY_NEW_CLAIM_TITLE);
                    String claimPlateNumber = data.getStringExtra(InternalProtocol.KEY_NEW_CLAIM_PLATE_NUMBER);
                    String claimOccurDate = data.getStringExtra(InternalProtocol.KEY_NEW_CLAIM_OCCUR_DATE);
                    String claimDescription = data.getStringExtra(InternalProtocol.KEY_NEW_CLAIM_DESCRIPTION);
                    Log.d(InternalProtocol.LOG, "New claim: " + claimTitle + ", " + claimPlateNumber + ", " + claimOccurDate + ", " + claimDescription);


                    // update the domain data structure
                    // CORRIGIR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11
                    _claimList.add(new ClaimRecord(1, claimTitle, claimPlateNumber, claimOccurDate, claimDescription, "jfd", "", new ArrayList<ClaimMessage>()));

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Log.d(InternalProtocol.LOG, "Cancel pressed.");
                } else {
                    Log.d(InternalProtocol.LOG, "Internal error: unknown result code.");
                }
                break;
            default:
                Log.d(InternalProtocol.LOG, "Internal error: unknown intent message.");
        }
    }


}
