package pt.ulisboa.tecnico.sise.seproject.insure.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.InternalProtocol;
import pt.ulisboa.tecnico.sise.seproject.insure.R;
import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.ClaimRecord;
import pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks.MyClaimsTask;

public class MyClaimsActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SISE - ListClaims";
    private ListView _listView;
    private List<ClaimRecord> _claimList;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_claims);

        // place the claim list in the application domain
        _claimList = new ArrayList<>();
        GlobalState globalState = (GlobalState) getApplicationContext();
        int session_id = globalState.getCustomer().getSessionId();

        //assign adapter to list view
        _listView = (ListView) findViewById(R.id.my_claims_list);

        new MyClaimsTask(session_id, _listView, this.getApplicationContext()).execute();

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
                finish();
            }
        });
    }
}
