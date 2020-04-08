package pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.activities.LoginActivity;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.WSHelper;

public class LogoutTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "LogoutCallTask";

    private GlobalState _globalState;
    private Context _context;
    private boolean _exception_caught = false;

    public LogoutTask(GlobalState globalState, Context context) {
        this._globalState = globalState;
        this._context = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Integer sessionId = _globalState.getCustomer().getSessionId();
        boolean res = false;
        try {
            res = WSHelper.logout(sessionId);
            Log.d(TAG, "result" + res);
            if (!res) {
                try {
                    sessionId = WSHelper.login(_globalState.getCustomer().getUsername(), _globalState.getPassword());
                    Log.d(TAG, "Login result => " + sessionId);
                    _globalState.setSessionId(sessionId);
                    _globalState.getCustomer().setSessionId(sessionId);
                    res = WSHelper.logout(sessionId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return res;
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            _exception_caught = true;

            Log.d(TAG, "result" + res);
            return res;
        }
    }

    @Override
    protected void onPostExecute(Boolean b) {
        if (b) {
            //reset session id
            _context.deleteFile("customer.json");
            _context.deleteFile("claimList.json");
            _globalState.clearCustomer();
            _globalState.setSessionId(-1);
            _globalState.setPassword("");

            //open login activity
            Intent intent = new Intent(this._context, LoginActivity.class);
            _context.startActivity(intent);

            ((TaskCallBack) _context).done();

        } else if (_exception_caught) {
            //open login activity
            Intent intent = new Intent(this._context, LoginActivity.class);
            _context.startActivity(intent);

            ((TaskCallBack) _context).done();
        } else {
            Toast.makeText(_context, "Unable to logout...", Toast.LENGTH_SHORT).show();
        }
    }
}
