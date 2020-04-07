package pt.ulisboa.tecnico.sise.seproject.insure.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.InternalProtocol;
import pt.ulisboa.tecnico.sise.seproject.insure.R;

public class ReadClaimActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SISE - ReadClaims";
    TextView claimIDView;
    TextView claimTitleView;
    TextView claimPlateNumberView;
    TextView claimOccurDateView;
    TextView claimDescriptionView;
    TextView claimStatusView;
    private Button buttonBack;
    private GlobalState _globalState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_claim);

        //obtain a reference to the claim's data structure
        _globalState = (GlobalState) getApplicationContext();
        //ClaimRecord claim = _globalState.getCustomer().getClaimRecordList().get(index);
        //int sessionId = _globalState.getCustomer().getSessionId();

        // update the UI
        claimIDView = findViewById(R.id.read_claim_id);
        claimTitleView = findViewById(R.id.read_claim_title);
        claimPlateNumberView = findViewById(R.id.read_claim_plate_number);
        claimOccurDateView = findViewById(R.id.read_claim_date);
        claimDescriptionView = findViewById(R.id.read_claim_description);
        claimStatusView = findViewById(R.id.read_claim_status);

        buttonBack = (Button) findViewById(R.id.read_claim_back_button);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "The button Back from ReadClaimsActivity is working!");
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.d(InternalProtocol.LOG, "Internal error: Index cannot be null.");
            finish();
            return;
        }
        int index = extras.getInt(InternalProtocol.READ_CLAIM_INDEX);
        Log.d(InternalProtocol.LOG, "Index: " + index);

        String title = extras.getString(InternalProtocol.READ_CLAIM_TITLE);
        String plateNumber = extras.getString(InternalProtocol.READ_CLAIM_PLATE_NUMBER);
        String occurrenceDate = extras.getString(InternalProtocol.READ_CLAIM_OCCUR_DATE);
        String id = extras.getString(InternalProtocol.READ_CLAIM_ID);
        String description = extras.getString(InternalProtocol.READ_CLAIM_DESCRIPTION);
        String status = extras.getString(InternalProtocol.READ_CLAIM_STATUS);

        claimIDView.setText(id);
        claimTitleView.setText(title);
        claimPlateNumberView.setText(plateNumber);
        claimOccurDateView.setText(occurrenceDate);
        claimDescriptionView.setText(description);
        
        claimStatusView.setText(status);
        if(status.equals("denied")) {
            claimStatusView.setTextColor(Color.RED);
        } else if(status.equals("accepted")) {
            claimStatusView.setTextColor(Color.GREEN);
        } else {
            claimStatusView.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

}
