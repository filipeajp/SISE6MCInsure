package pt.ulisboa.tecnico.sise.seproject.insure.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import pt.ulisboa.tecnico.sise.seproject.insure.R;

public class MyInformationActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SISE - MyInformation";
    private Button  buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);

        buttonBack = (Button) findViewById(R.id.my_information_back_button);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "My information back button is working");
                Intent intentMyInformation = new Intent(MyInformationActivity.this, MainPageActivity.class);
                startActivity(intentMyInformation);
                finish();
            }
        });
    }
}
