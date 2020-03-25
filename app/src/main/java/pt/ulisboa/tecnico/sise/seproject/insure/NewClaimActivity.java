package pt.ulisboa.tecnico.sise.seproject.insure;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewClaimActivity extends AppCompatActivity {
    private static final String LOG_TAG = "InsureApp";
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

        buttonSubmit = findViewById(R.id.new_claim_btn_submit);
        buttonCancel = findViewById(R.id.new_claim_btn_cancel);
        editTextTitle = findViewById(R.id.new_claim_claim_title);
        editTextPlateNumber = findViewById(R.id.new_claim_plate_number);
        editTextOccurrenceDate = findViewById(R.id.new_claim_occurrence_date);
        editTextDesc = findViewById(R.id.new_claim_description);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, editTextTitle.getText().toString());
                Log.d(LOG_TAG, editTextPlateNumber.getText().toString());
                Log.d(LOG_TAG, editTextOccurrenceDate.getText().toString());
                Log.d(LOG_TAG, editTextDesc.getText().toString());
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            private int counter = 1;

            @Override
            public void onClick(View view) {
                buttonCancel.setText("Cancel-" + counter++);
                Log.d(LOG_TAG, "Claim creation cancelled!");
            }
        });
    }
}
