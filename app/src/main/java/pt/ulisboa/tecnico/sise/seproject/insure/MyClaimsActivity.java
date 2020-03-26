package pt.ulisboa.tecnico.sise.seproject.insure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MyClaimsActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SISE - SeeListClaims";
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_claims);

        buttonBack = (Button) findViewById(R.id.my_claims_list_button);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "My Claims Buttons is working!");
                Intent intentMyClaims = new Intent(MyClaimsActivity.this, MainPageActivity.class);
                startActivity(intentMyClaims);
            }
        });
    }
}
