package pt.ulisboa.tecnico.sise.seproject.insure.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.R;
import pt.ulisboa.tecnico.sise.seproject.insure.TaskCallBack;
import pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks.NewClaimTask;

public class NewClaimActivity extends AppCompatActivity implements TaskCallBack {
    private static final String LOG_TAG = "SISE - NewClaim";
    private Button buttonSubmit;
    private Button buttonCancel;
    private EditText editTextTitle;
    private EditText editTextPlateNumber;
    private EditText editTextOccurrenceDate;
    private EditText editTextDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_claim);
        Log.d(LOG_TAG, "Create claim in process...");

        final GlobalState globalState = (GlobalState) getApplicationContext();

        buttonSubmit = (Button) findViewById(R.id.new_claim_btn_submit);
        buttonCancel = (Button) findViewById(R.id.new_claim_btn_cancel);
        editTextTitle = (EditText) findViewById(R.id.new_claim_claim_title);
        editTextPlateNumber = (EditText) findViewById(R.id.new_claim_plate_number);
        editTextOccurrenceDate = (EditText) findViewById(R.id.new_claim_occurrence_date);
        editTextDesc = (EditText) findViewById(R.id.new_claim_description);


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, editTextTitle.getText().toString());
                Log.d(LOG_TAG, editTextPlateNumber.getText().toString());
                Log.d(LOG_TAG, editTextOccurrenceDate.getText().toString());
                Log.d(LOG_TAG, editTextDesc.getText().toString());
                String claimTitle = editTextTitle.getText().toString();
                String claimPlateNumber = editTextPlateNumber.getText().toString();
                String claimOccurDate = editTextOccurrenceDate.getText().toString();
                String claimDesc = editTextDesc.getText().toString();

                new NewClaimTask(view.getContext(), globalState.getSessionId(), claimTitle, claimOccurDate, claimPlateNumber, claimDesc).execute();
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // return the return code only; no intent message is required
                setResult(Activity.RESULT_CANCELED);

                Toast.makeText(view.getContext(), "Claim submission cancelled", Toast.LENGTH_SHORT).show();
                // finish activity
                finish();
            }
        });
    }

    @Override
    public void done() {
        finish();
    }


}

