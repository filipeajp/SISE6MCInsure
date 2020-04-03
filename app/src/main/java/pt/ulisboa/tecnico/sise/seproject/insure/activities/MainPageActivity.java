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
import java.util.List;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.InternalProtocol;
import pt.ulisboa.tecnico.sise.seproject.insure.R;
import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.ClaimRecord;
import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.Customer;
import pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks.LogoutTask;
import pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks.TaskCallBack;

public class MainPageActivity extends AppCompatActivity implements TaskCallBack {
    private List<ClaimRecord> _claimList;
    private Customer _customer;
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
        final GlobalState globalState = (GlobalState) getApplicationContext();
        _customer = globalState.getCustomer();

        buttonLogout = findViewById(R.id.main_page_logout_button);
        //Log.d("Insure", "" + globalState.getCustomer().getSessionId());

        // Hello Message: maybe create asyncTask?
        textViewHelloMessage = findViewById(R.id.main_page_hello_message);
        Bundle extras = getIntent().getExtras();
        String username = extras.getString("USERNAME");
        textViewHelloMessage.setText("Hello " + username + "!");

        buttonMyInformation = findViewById(R.id.main_page_my_information_button);
        buttonNewClaim = findViewById(R.id.main_page_new_claim_button);
        buttonMyClaims = findViewById(R.id.main_page_my_claims_button);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new LogoutTask(globalState, view.getContext()).execute();
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
            }
        });

        buttonMyClaims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, MyClaimsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void done() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case InternalProtocol.NEW_ClAIM_REQUEST:

                if (resultCode == Activity.RESULT_OK) {
//                    // retrieve the claim data from the intent
//                    String claimTitle = data.getStringExtra(InternalProtocol.KEY_NEW_CLAIM_TITLE);
//                    String claimPlateNumber = data.getStringExtra(InternalProtocol.KEY_NEW_CLAIM_PLATE_NUMBER);
//                    String claimOccurDate = data.getStringExtra(InternalProtocol.KEY_NEW_CLAIM_OCCUR_DATE);
//                    String claimDescription = data.getStringExtra(InternalProtocol.KEY_NEW_CLAIM_DESCRIPTION);
//                    Log.d(InternalProtocol.LOG, "New claim: " + claimTitle + ", " + claimPlateNumber + ", " + claimOccurDate + ", " + claimDescription);
//
//
//                    if (_customer.getPlateList().contains(claimPlateNumber))
//                        _customer.addPlate(claimPlateNumber);
//                    // update the domain data structure
//                    // CORRIGIR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    _customer.addClaim(new ClaimRecord(1, claimTitle, claimPlateNumber, claimOccurDate, claimDescription, "jfd", "", new ArrayList<ClaimMessage>()));
                    Log.d(InternalProtocol.LOG, "Claim submitted.");
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
