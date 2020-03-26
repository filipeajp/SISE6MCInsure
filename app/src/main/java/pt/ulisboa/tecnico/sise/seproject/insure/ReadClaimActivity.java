package pt.ulisboa.tecnico.sise.seproject.insure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
            Intent intentReadClaims =  new Intent(ReadClaimActivity.this, MyClaimsActivity.class);
            startActivity(intentReadClaims);
        }
    });

    }
}
