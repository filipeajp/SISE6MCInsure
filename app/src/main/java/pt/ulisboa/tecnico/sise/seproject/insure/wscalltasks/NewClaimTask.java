package pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.seproject.insure.helpers.WSHelper;

public class NewClaimTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "Insure";
    private Boolean res;
    private Context _context;
    private int _sessionID = 0;
    private String _title;
    private String _occurDate;
    private String _plateNumber;
    private String _description;

    public NewClaimTask(Context context, int sessionID, String title, String occurDate, String plateNumber, String description) {
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

        } catch (Exception e) {
            Log.d(TAG, "new claim failed!");
        }
        return res;
    }

    @Override
    protected void onProgressUpdate(Void... params) {

    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            if (_title.equals("")) {
                Toast.makeText(_context, "Write a claim title", Toast.LENGTH_LONG).show();
                return;
            }

            //return an intent containing the title and body of the new note
            //Intent resultIntent = new Intent(_context, MainPageActivity.class);
            Toast.makeText(_context, "Claim submitted", Toast.LENGTH_LONG).show();
            //_context.startActivity(resultIntent);

            ((TaskCallBack) _context).done();
        } else {
            Toast.makeText(_context, "Submission error. Please review information submitted or press Cancel to go back to Main Page", Toast.LENGTH_LONG).show();
        }
    }
}

