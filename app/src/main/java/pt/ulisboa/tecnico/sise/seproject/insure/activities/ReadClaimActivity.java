package pt.ulisboa.tecnico.sise.seproject.insure.activities;

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
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_claim);

        buttonBack = (Button) findViewById(R.id.read_claim_back_button);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "The button Back from ReadClaimsActivity is working!");
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.d(InternalProtocol.LOG, "Internal error: Index cannot be null.");
            finish();
            return;
        }
        int index = extras.getInt(InternalProtocol.READ_CLAIM_INDEX);
        Log.d(InternalProtocol.LOG, "Index: " + index);

        //obtain a reference to the claim's data structure
        GlobalState context = (GlobalState) getApplicationContext();
        Claim claim = context.getClaimList().get(index);

        // update the UI
        TextView claimTitleView = findViewById(R.id.read_claim_title);
        claimTitleView.setText(claim.getTitle());
        TextView claimPlateNumberView = findViewById(R.id.read_claim_plate_number);
        claimPlateNumberView.setText(claim.getPlateNumber());
        TextView claimOccurDateView = findViewById(R.id.read_claim_date);
        claimOccurDateView.setText(claim.getOccurrenceDate());
        TextView claimDescriptionView = findViewById(R.id.read_claim_description);
        claimDescriptionView.setText(claim.getDescription());
    }
}
