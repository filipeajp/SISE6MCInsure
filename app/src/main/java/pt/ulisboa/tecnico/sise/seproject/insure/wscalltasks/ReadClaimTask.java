package pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.InternalProtocol;
import pt.ulisboa.tecnico.sise.seproject.insure.activities.ReadClaimActivity;
import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.ClaimRecord;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.JsonCodec;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.JsonFileManager;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.WSHelper;

public class ReadClaimTask extends AsyncTask<Void, Void, ClaimRecord> {
    private static final String TAG = "ReadClaimTask";
    private static final String CLAIM_LIST_FILE_NAME = "claimRecordList.json";

    private Context _context;
    private GlobalState _globalState;
    private int _sessionId;
    private int _claimId;
    private ClaimRecord _claim;
    private boolean _exception_caught = false;


    public ReadClaimTask(GlobalState globalState, int sessionId, int claimId, Context context) {
        this._globalState = globalState;
        this._sessionId = sessionId;
        this._claimId = claimId;
        this._context = context;
    }

    @Override
    protected ClaimRecord doInBackground(Void... voids) {
        Log.d(TAG, "log -------------------");
        try {
            _claim = WSHelper.getClaimInfo(_sessionId, _claimId);
            if (_claim == null) {
                _sessionId = WSHelper.login(_globalState.getCustomer().getUsername(), _globalState.getPassword());
                Log.d(TAG, "Login result => " + _sessionId);
                _globalState.setSessionId(_sessionId);
                _globalState.getCustomer().setSessionId(_sessionId);
                _claim = WSHelper.getClaimInfo(_sessionId, _claimId);
            }


        } catch (Exception e) {
            Log.d(TAG, e.toString());
            String claimListJson = JsonFileManager.jsonReadFromFile(_context, CLAIM_LIST_FILE_NAME);
            Log.d(TAG, "claimlist: read from - " + CLAIM_LIST_FILE_NAME);

            List<ClaimRecord> claimList = JsonCodec.decodeClaimRecordList(claimListJson);
            for (ClaimRecord cr : claimList) {
                if (cr.getId() == _claimId) {
                    return cr;
                }
            }
            _exception_caught = true;
        }
        if (_claim != null) {
            Log.d(TAG, "Get Claim Info result claimId " + _claimId + " => " + _claim.toString());
        } else {
            Log.d(TAG, "Get Claim Info result claimId " + _claimId + " => null.");
        }
        return _claim;
    }

    @Override
    protected void onProgressUpdate(Void... params) {
    }

    @Override
    protected void onPostExecute(ClaimRecord claim) {
        if (claim == null) {
            Toast.makeText(_context, "Server Error and Claim Record not stored in cache.\nNot able to advance.\nPlease try again later.", Toast.LENGTH_LONG).show();
        } else if (_exception_caught) {

            Intent intent = new Intent(_context, ReadClaimActivity.class);
            intent.putExtra(InternalProtocol.READ_CLAIM_TITLE, claim.getTitle());
            intent.putExtra(InternalProtocol.READ_CLAIM_ID, "" + claim.getId());
            intent.putExtra(InternalProtocol.READ_CLAIM_OCCUR_DATE, claim.getOccurrenceDate());
            intent.putExtra(InternalProtocol.READ_CLAIM_PLATE_NUMBER, claim.getPlate());
            intent.putExtra(InternalProtocol.READ_CLAIM_STATUS, claim.getStatus());
            intent.putExtra(InternalProtocol.READ_CLAIM_DESCRIPTION, claim.getDescription());
            _context.startActivity(intent);

        } else {
            try {
                String claimListJson = JsonFileManager.jsonReadFromFile(_context, CLAIM_LIST_FILE_NAME);

                List<ClaimRecord> claimList = JsonCodec.decodeClaimRecordList(claimListJson);
                claimList.add(claim);
                claimListJson = JsonCodec.encodeClaimRecordList(claimList);

                JsonFileManager.jsonWriteToFile(_context, CLAIM_LIST_FILE_NAME, claimListJson);

                Intent intent = new Intent(_context, ReadClaimActivity.class);
                intent.putExtra(InternalProtocol.READ_CLAIM_TITLE, claim.getTitle());
                intent.putExtra(InternalProtocol.READ_CLAIM_ID, "" + claim.getId());
                intent.putExtra(InternalProtocol.READ_CLAIM_OCCUR_DATE, claim.getOccurrenceDate());
                intent.putExtra(InternalProtocol.READ_CLAIM_PLATE_NUMBER, claim.getPlate());
                intent.putExtra(InternalProtocol.READ_CLAIM_STATUS, claim.getStatus());
                intent.putExtra(InternalProtocol.READ_CLAIM_DESCRIPTION, claim.getDescription());
                _context.startActivity(intent);
            } catch (Exception e) {
                Log.d(TAG, "Error");
            }

        }
    }
}


