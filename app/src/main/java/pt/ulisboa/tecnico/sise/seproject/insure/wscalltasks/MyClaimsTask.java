package pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.ClaimItem;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.WSHelper;

public class MyClaimsTask extends AsyncTask<Void, Void, List<ClaimItem>> {
    private static final String TAG = "Insure";
    private int _sessionId = 0;
    private ListView _listView;
    private Context _context;
    private List<ClaimItem> _claimItemList;

    public MyClaimsTask(int sessionId, ListView listView, Context context) {
        this._sessionId = sessionId;
        this._listView = listView;
        this._context = context;
    }

    @Override
    protected List<ClaimItem> doInBackground(Void... params) {
        try {
            _claimItemList = WSHelper.listClaims(_sessionId);
            if (_claimItemList != null) {
                String m = _claimItemList.size() > 0 ? "" : "empty array";
                for (ClaimItem claimItem : _claimItemList) {
                    m += " (" + claimItem.toString() + ")";
                }
                Log.d(TAG, "List claim item result => " + m);
            } else {
                Log.d(TAG, "List claim item result => null.");
            }

        } catch (Exception e) {
            Log.d(TAG, e.toString());

        }
        return _claimItemList;
    }

    @Override
    protected void onProgressUpdate(Void... paramas) {

    }

    @Override
    protected void onPostExecute(List<ClaimItem> claimList) {

        ArrayAdapter<ClaimItem> adpater = new ArrayAdapter<>(_context, android.R.layout.simple_list_item_1, android.R.id.text1, claimList);
        _listView.setAdapter(adpater);
    }


}
