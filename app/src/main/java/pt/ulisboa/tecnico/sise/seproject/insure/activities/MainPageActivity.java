package pt.ulisboa.tecnico.sise.seproject.insure.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    private GlobalState _globalState;
    private Context _context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        // place the claim list in the application domain
        _globalState = (GlobalState) getApplicationContext();

        buttonLogout = findViewById(R.id.main_page_logout_button);

        //Used for checking sessionId
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
                new LogoutTask(_globalState, _context).execute();
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


}
