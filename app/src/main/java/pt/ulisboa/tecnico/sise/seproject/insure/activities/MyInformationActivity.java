package pt.ulisboa.tecnico.sise.seproject.insure.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.R;
import pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks.MyInformationTask;

public class MyInformationActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SISE - MyInformation";

    private GlobalState _globalState;
    private TextView textViewName;
    private TextView textViewDateOfBirth;
    private TextView textViewAddress;
    private TextView textViewNif;
    private TextView textViewPolicyNr;

    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);

        textViewName = findViewById(R.id.my_information_name);
        textViewDateOfBirth = findViewById(R.id.my_information_birth);
        textViewAddress = findViewById(R.id.my_information_address);
        textViewNif = findViewById(R.id.my_information_nif);
        textViewPolicyNr = findViewById(R.id.my_information_policy_nr);


        buttonBack = findViewById(R.id.my_information_back_button);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "My information back button is working");
                //Intent intentMyInformation = new Intent(MyInformationActivity.this, MainPageActivity.class);
                //startActivity(intentMyInformation);
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        _globalState = (GlobalState) getApplicationContext();
        Log.d("My", "Myinfo activity - calling global state session id: " + _globalState.getSessionId());
        int sessionId = _globalState.getSessionId();
        new MyInformationTask(sessionId, textViewName, textViewDateOfBirth, textViewAddress, textViewNif, textViewPolicyNr).execute();
    }

}
