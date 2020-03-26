package pt.ulisboa.tecnico.sise.seproject.insure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewClaimActivity extends AppCompatActivity {
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
        Log.d(InternalProtocol.LOG, "Create claim in process...");

        buttonSubmit = findViewById(R.id.new_claim_btn_submit);
        buttonCancel = findViewById(R.id.new_claim_btn_cancel);
        editTextTitle = findViewById(R.id.new_claim_claim_title);
        editTextPlateNumber = findViewById(R.id.new_claim_plate_number);
        editTextOccurrenceDate = findViewById(R.id.new_claim_occurrence_date);
        editTextDesc = findViewById(R.id.new_claim_description);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(InternalProtocol.LOG, editTextTitle.getText().toString());
                Log.d(InternalProtocol.LOG, editTextPlateNumber.getText().toString());
                Log.d(InternalProtocol.LOG, editTextOccurrenceDate.getText().toString());
                Log.d(InternalProtocol.LOG, editTextDesc.getText().toString());
                String claimTitle = editTextTitle.getText().toString();
                String claimPlateNumber = editTextPlateNumber.getText().toString();
                String claimOccurDate = editTextOccurrenceDate.getText().toString();
                String claimDesc = editTextDesc.getText().toString();

                if (claimTitle.equals("")) {
                    Toast.makeText(view.getContext(), "Write a claim title", Toast.LENGTH_LONG).show();
                    return;
                }

                //return an intent containing the title and body of the new note
                Intent resultIntent = new Intent();
                startActivityForResult(resultIntent, InternalProtocol.NEW_ClAIM_REQUEST);

                resultIntent.putExtra(InternalProtocol.KEY_NEW_CLAIM_TITLE, claimTitle);
                resultIntent.putExtra(InternalProtocol.KEY_NEW_CLAIM_PLATE_NUMBER, claimPlateNumber);
                resultIntent.putExtra(InternalProtocol.KEY_NEW_CLAIM_OCCUR_DATE, claimOccurDate);
                resultIntent.putExtra(InternalProtocol.KEY_NEW_CLAIM_DESCRIPTION, claimDesc);

                setResult(Activity.RESULT_OK, resultIntent);

                // write a toast message
                Toast.makeText(view.getContext(), "Claim submitted", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // return the return code only; no intent message is required
                setResult(Activity.RESULT_CANCELED);

                Intent intent = new Intent(NewClaimActivity.this, MainPageActivity.class);
                startActivityForResult(intent, InternalProtocol.NEW_ClAIM_REQUEST);
                // write  a toast message
                Toast.makeText(view.getContext(), "Claim submission cancelled", Toast.LENGTH_LONG).show();
                // finish activity
                finish();
            }
        });
    }
}
