package pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.WSHelper;

public class NewClaimTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "NewClaimTask";

    private Boolean res = false;
    private Boolean _exceptionCaught = false;
    private GlobalState _globalState;
    private Context _context;
    private int _sessionID = 0;
    private String _title;
    private String _occurDate;
    private String _plateNumber;
    private String _description;

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
            //quando o servidor volta
            if (!res) {
                _sessionID = WSHelper.login(_globalState.getCustomer().getUsername(), _globalState.getPassword());
                Log.d(TAG, "Login result => " + _sessionID);
                _globalState.setSessionId(_sessionID);
                _globalState.getCustomer().setSessionId(_sessionID);
                res = WSHelper.submitNewClaim(_sessionID, _title, _occurDate, _plateNumber, _description);
            }
            return res;
        } catch (Exception e) {
            _exceptionCaught = true;
            Log.d(TAG, "exception caught: " + _exceptionCaught);
        }

        Log.d(TAG, "result: " + res);
        return res;
    }

    @Override
    protected void onProgressUpdate(Void... params) {

    }

    @Override
    protected void onPostExecute(Boolean result) {

        if (result) {
            Toast.makeText(_context, "Claim submitted", Toast.LENGTH_LONG).show();
            ((TaskCallBack) _context).done();
        } else if (_exceptionCaught) {
            Toast.makeText(_context, "Submission failed! We're having server problems.\nPlease try again later.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(_context, "Submission error. Please review information submitted or press Cancel to go back to Main Page", Toast.LENGTH_LONG).show();
        }
    }
}

