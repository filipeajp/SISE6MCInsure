package pt.ulisboa.tecnico.sise.seproject.insure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MyClaimsActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SISE - SeeListClaims";
    private ListView _listView;
    private ArrayList<Claim> _claimList;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_claims);

        // place the claim list in the application domain
        _claimList = new ArrayList<>();
        GlobalState globalState = (GlobalState) getApplicationContext();
        globalState.setClaimList(_claimList);

        //assign adapter to list view
        _listView = (ListView) findViewById(R.id.my_claims_list);
        ArrayAdapter<Claim> adpater = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, _claimList);
        _listView.setAdapter(adpater);

        // attach click listener to list view items
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create the read claim activity, passing to it the index position as parameter
                Log.d("position", position + "");
                Intent intent = new Intent(MyClaimsActivity.this, ReadClaimActivity.class);
                intent.putExtra(InternalProtocol.READ_CLAIM_INDEX, position);
                startActivity(intent);

                // if instead of string, we pass a list with notes, we can retrieve the original Claim object this way
                //Note note = (Note)parent.getItemAtPosition(position);
            }
        });


        buttonBack = (Button) findViewById(R.id.my_claims_list_button);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "My Claims Buttons is working!");
                Intent intentMyClaims = new Intent(MyClaimsActivity.this, MainPageActivity.class);
                startActivity(intentMyClaims);
                //finish();
//                Intent intent = new Intent(MyClaimsActivity.this, NewClaimActivity.class);
//                startActivityForResult(intent, InternalProtocol.NEW_ClAIM_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case InternalProtocol.NEW_ClAIM_REQUEST:

                if (resultCode == Activity.RESULT_OK) {
                    // retrieve the claim data from the intent
                    String claimTitle = data.getStringExtra(InternalProtocol.KEY_NEW_CLAIM_TITLE);
                    String claimPlateNumber = data.getStringExtra(InternalProtocol.KEY_NEW_CLAIM_PLATE_NUMBER);
                    String claimOccurDate = data.getStringExtra(InternalProtocol.KEY_NEW_CLAIM_OCCUR_DATE);
                    String claimDescription = data.getStringExtra(InternalProtocol.KEY_NEW_CLAIM_DESCRIPTION);
                    Log.d(InternalProtocol.LOG, "New claim: " + claimTitle + ", " + claimPlateNumber + ", " + claimOccurDate + ", " + claimDescription);

                    // update the domain data structure
                    _claimList.add(new Claim(claimTitle, claimPlateNumber, claimOccurDate, claimDescription));

                    // refresh the list on screen
                    _listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, _claimList));
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Log.d(InternalProtocol.LOG, "Cancel pressed.");
                } else {
                    Log.d(InternalProtocol.LOG, "Internal error: unknown result code.");
                }
                break;
            default:
                Log.d(InternalProtocol.LOG, "Internal error: unknown intent message.");
        }
    }
}
