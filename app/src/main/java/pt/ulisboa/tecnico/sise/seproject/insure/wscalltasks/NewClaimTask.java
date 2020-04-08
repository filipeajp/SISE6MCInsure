package pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.ClaimItem;
import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.ClaimRecord;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.JsonCodec;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.JsonFileManager;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.WSHelper;

public class NewClaimTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "NewClaimTask";
    private static final String CLAIM_LIST_FILE_NAME = "claimList.json";

    private Boolean res;
    private Boolean _exceptionCaught = false;
    private GlobalState _globalState;
    private Context _context;
    private int _sessionID = 0;
    private String _title;
    private String _occurDate;
    private String _plateNumber;
    private String _description;
    private List<ClaimItem> _claimItemList;

    public NewClaimTask(GlobalState globalState, Context context, int sessionID, String title, String occurDate, String plateNumber, String description) {
        this._globalState = globalState;
        this._context = context;
        this._sessionID = sessionID;
        this._title = title;
        this._occurDate = occurDate;
        this._plateNumber = plateNumber;
        this._description = description;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Log.d(TAG, "SessionID:" + _sessionID);
            res = WSHelper.submitNewClaim(_sessionID, _title, _occurDate, _plateNumber, _description);
            Log.d(TAG, "Submit new claim result => " + res);

            if (!res) {
                try {
                    _sessionID = WSHelper.login(_globalState.getCustomer().getUsername(), _globalState.getPassword());
                    Log.d(TAG, "Login result => " + _sessionID);
                    _globalState.setSessionId(_sessionID);
                    _globalState.getCustomer().setSessionId(_sessionID);

                    List<ClaimItem> claimItemsList = WSHelper.listClaims(_sessionID);

                    String claimListJson = JsonFileManager.jsonReadFromFile(_context, CLAIM_LIST_FILE_NAME);
                    Log.d(TAG, "claimList: read from - " + CLAIM_LIST_FILE_NAME);
                    _claimItemList = JsonCodec.decodeClaimList(claimListJson);
                    Log.d(TAG, "ClaimList: - " + JsonCodec.decodeClaimList(claimListJson));

                    if (_claimItemList != null && (_claimItemList.size() > claimItemsList.size())) {
                        int dif = _globalState.getCustomer().getClaimRecordList().size() - claimItemsList.size();
                        for (int i = 0; i < dif; i++) {
                            int index = _globalState.getCustomer().getClaimRecordList().size() - dif;
                            ClaimRecord claim = _globalState.getCustomer().getClaimRecord(index);
                            res = WSHelper.submitNewClaim(_sessionID, claim.getTitle(), claim.getOccurrenceDate(), claim.getPlate(), claim.getDescription());
                            Log.d(TAG, "resubmission: " + res);
                        }
                    }
                } catch (Exception ex) {
                    _exceptionCaught = true;
                }
            }
            return res;
        } catch (Exception e) {
            _exceptionCaught = true;
            Log.d(TAG, "exception caught: " + _exceptionCaught);
//            try {
//                _sessionID = WSHelper.login(_globalState.getCustomer().getUsername(), _globalState.getCustomer().getPassword());
//                Log.d(TAG, "Login result => " + _sessionID);
//                _globalState.setSessionId(_sessionID);
//                _globalState.getCustomer().setSessionId(_sessionID);
//                Log.d(TAG, "new claim failed!");
//                _exceptionCaught = true;
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }

        }
        Log.d(TAG, "result: " + res);
        return false;
    }

    @Override
    protected void onProgressUpdate(Void... params) {

    }

    @Override
    protected void onPostExecute(Boolean result) {

        if (result || _exceptionCaught) {
            String claimListJson = JsonFileManager.jsonReadFromFile(_context, CLAIM_LIST_FILE_NAME);
            Log.d(TAG, "claimList: read from - " + CLAIM_LIST_FILE_NAME);
            _claimItemList = JsonCodec.decodeClaimList(claimListJson);
            Log.d(TAG, "ClaimList: - " + JsonCodec.decodeClaimList(claimListJson));

            int claimId = _claimItemList.size() + 1;
            _claimItemList.add(new ClaimItem(claimId, _title));
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            _globalState.getCustomer().addClaim(new ClaimRecord(claimId, _title, dateFormat.format(new Date()), _occurDate, _plateNumber, _description, "pending", null));

            try {
                claimListJson = JsonCodec.encodeClaimList(_claimItemList);
                Log.d(TAG, "claimList: customerJson - " + claimListJson);
                JsonFileManager.jsonWriteToFile(_context, CLAIM_LIST_FILE_NAME, claimListJson);
                Log.d(TAG, "claimList: written to - " + CLAIM_LIST_FILE_NAME);

                //return an intent containing the title and body of the new note
                //Intent resultIntent = new Intent(_context, MainPageActivity.class);
                Toast.makeText(_context, "Claim submitted", Toast.LENGTH_LONG).show();
                //_context.startActivity(resultIntent);

                ((TaskCallBack) _context).done();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(_context, "Submission error. Please review information submitted or press Cancel to go back to Main Page", Toast.LENGTH_LONG).show();
        }
    }
}

