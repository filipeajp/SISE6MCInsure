package pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.ClaimItem;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.JsonCodec;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.JsonFileManager;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.WSHelper;

public class MyClaimsTask extends AsyncTask<Void, Void, List<ClaimItem>> {
    private static final String TAG = "Insure";
    private static final String CLAIM_LIST_FILE_NAME = "claimList.json";

    private int _sessionId = 0;
    private GlobalState _globalState;
    private ListView _listView;
    private Context _context;
    private List<ClaimItem> _claimItemList;

    public MyClaimsTask(GlobalState globalState, int sessionId, ListView listView, Context context) {
        this._globalState = globalState;
        this._sessionId = sessionId;
        this._listView = listView;
        this._context = context;
    }

    @Override
    protected List<ClaimItem> doInBackground(Void... params) {
        try {
            String claimListJson = JsonFileManager.jsonReadFromFile(_context, CLAIM_LIST_FILE_NAME);
            Log.d(TAG, "claimList: read from - " + CLAIM_LIST_FILE_NAME);
            if (JsonCodec.decodeClaimList(claimListJson) == null) {
                _claimItemList = WSHelper.listClaims(_sessionId);
            } else {
                Log.d(TAG, "ClaimList: - " + JsonCodec.decodeClaimList(claimListJson));
                _claimItemList = JsonCodec.decodeClaimList(claimListJson);
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        if (_claimItemList != null) {
            String m = _claimItemList.size() > 0 ? "" : "empty array";
            for (ClaimItem claimItem : _claimItemList) {
                m += " (" + claimItem.toString() + ")";
            }
            Log.d(TAG, "List claim item result => " + m);
        } else {
            Log.d(TAG, "List claim item result => null.");
        }
        return _claimItemList;
    }

    @Override
    protected void onProgressUpdate(Void... paramas) {

    }

    @Override
    protected void onPostExecute(List<ClaimItem> claimList) {
        if (claimList == null) {
            Toast.makeText(_context, "Server error and no claims in cache.\nPleas try again later.", Toast.LENGTH_LONG).show();
        } else if (claimList.isEmpty()) {
            Toast.makeText(_context, "No claims in your history.", Toast.LENGTH_LONG).show();
        } else {
            try {
                //_globalState.setCustomerClaimList(claimList);

                ArrayAdapter<ClaimItem> adpater = new ArrayAdapter<>(_context, android.R.layout.simple_list_item_1, android.R.id.text1, claimList);
                _listView.setAdapter(adpater);

                String claimListJson = JsonCodec.encodeClaimList(_claimItemList);
                Log.d(TAG, "claimList: customerJson - " + claimListJson);
                JsonFileManager.jsonWriteToFile(_context, CLAIM_LIST_FILE_NAME, claimListJson);
                Log.d(TAG, "claimList: written to - " + CLAIM_LIST_FILE_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


}
