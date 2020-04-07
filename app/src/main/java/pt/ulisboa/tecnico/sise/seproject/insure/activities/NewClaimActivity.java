package pt.ulisboa.tecnico.sise.seproject.insure.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.R;
import pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks.GetPlatesTask;
import pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks.NewClaimTask;
import pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks.TaskCallBack;

public class NewClaimActivity extends AppCompatActivity implements TaskCallBack, AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = "SISE - NewClaim";
    private GlobalState _globalState;
    private Button buttonSubmit;
    private Button buttonCancel;
    private EditText editTextTitle;
    private EditText editTextOccurrenceDate;
    private EditText editTextDesc;
    private Spinner plateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_claim);
        Log.d(LOG_TAG, "Create claim in process...");

        _globalState = (GlobalState) getApplicationContext();

        buttonSubmit = (Button) findViewById(R.id.new_claim_btn_submit);
        buttonCancel = (Button) findViewById(R.id.new_claim_btn_cancel);
        editTextTitle = (EditText) findViewById(R.id.new_claim_claim_title);
        editTextOccurrenceDate = (EditText) findViewById(R.id.new_claim_occurrence_date);
        editTextDesc = (EditText) findViewById(R.id.new_claim_description);
        plateSpinner = (Spinner) findViewById(R.id.new_claim_plate_spinner);

        plateSpinner.setOnItemSelectedListener(this);
        plateSpinner.setSelection(0);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, editTextTitle.getText().toString());
                Log.d(LOG_TAG, plateSpinner.getSelectedItem().toString());
                Log.d(LOG_TAG, editTextOccurrenceDate.getText().toString());
                Log.d(LOG_TAG, editTextDesc.getText().toString());
                String claimTitle = editTextTitle.getText().toString();
                String claimPlateNumber = plateSpinner.getSelectedItem().toString();
                String claimOccurDate = editTextOccurrenceDate.getText().toString();
                String claimDesc = editTextDesc.getText().toString();

                new NewClaimTask(view.getContext(), _globalState.getSessionId(), claimTitle, claimOccurDate, claimPlateNumber, claimDesc).execute();
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                Toast.makeText(view.getContext(), "Claim submission cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        _globalState = (GlobalState) getApplicationContext();
        Log.d("My", "NewClaim activity - calling global state session id: " + _globalState.getSessionId());
        int sessionId = _globalState.getSessionId();
        new GetPlatesTask(sessionId, plateSpinner, this).execute();
    }

    @Override
    public void done() {
        finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

