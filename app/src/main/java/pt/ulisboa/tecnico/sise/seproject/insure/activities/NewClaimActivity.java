package pt.ulisboa.tecnico.sise.seproject.insure.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.R;
import pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks.GetPlatesTask;
import pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks.NewClaimTask;
import pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks.TaskCallBack;

public class NewClaimActivity extends AppCompatActivity implements TaskCallBack, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private static final String LOG_TAG = "SISE - NewClaim";
    private GlobalState _globalState;
    private Button buttonSubmit;
    private Button buttonCancel;
    private EditText editTextTitle;
    private TextView viewOccurrenceDate;
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
        viewOccurrenceDate = (TextView) findViewById(R.id.new_claim_occurrence_date);
        editTextDesc = (EditText) findViewById(R.id.new_claim_description);
        plateSpinner = (Spinner) findViewById(R.id.new_claim_plate_spinner);

        plateSpinner.setOnItemSelectedListener(this);
        plateSpinner.setSelection(0);

        viewOccurrenceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens datePicker and sets text equal to the selected date (string format)
                showDatePickerDialog();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, editTextTitle.getText().toString());
                Log.d(LOG_TAG, plateSpinner.getSelectedItem().toString());
                Log.d(LOG_TAG, viewOccurrenceDate.getText().toString());
                Log.d(LOG_TAG, editTextDesc.getText().toString());
                String claimTitle = editTextTitle.getText().toString();
                String claimPlateNumber = plateSpinner.getSelectedItem().toString();
                String claimOccurDate = viewOccurrenceDate.getText().toString();
                String claimDesc = editTextDesc.getText().toString();

                if (claimTitle.isEmpty()) {
                    Toast.makeText(view.getContext(), "Write a claim title.", Toast.LENGTH_LONG).show();
                    return;
                } else if (claimOccurDate.equals("Click here")) {
                    Toast.makeText(view.getContext(), "Select an occurrence date.", Toast.LENGTH_LONG).show();
                } else {
                    new NewClaimTask(_globalState, view.getContext(), _globalState.getSessionId(), claimTitle, claimOccurDate, claimPlateNumber, claimDesc).execute();
                }

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

    private void showDatePickerDialog() {
        DatePickerDialog datePicker = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePicker.getDatePicker().setMaxDate(new Date().getTime());
        datePicker.show();
    }

    @Override
    public void onDateSet(DatePicker dp, int y, int m, int d) {
        String selectedDate = addZero(d) + "-" + addZero(m + 1) + "-" + y;
        viewOccurrenceDate.setText(selectedDate);
    }

    private String addZero(int n) {
        if (n < 10) {
            return "0" + n;
        } else {
            return String.valueOf(n);
        }
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

