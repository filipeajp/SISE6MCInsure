package pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.WSHelper;
import pt.ulisboa.tecnico.sise.seproject.insure.activities.MainPageActivity;

public class LoginTask extends AsyncTask<Void, Integer, Integer> {
    public final static String TAG = "LoginCallTask";
    GlobalState _globalState;
    private String _username;
    private String _password;
    private Context context;
    private int _sessionId = -1;

    public LoginTask(String username, String password, GlobalState globalState, Context context) {
        this._username = username;
        this._password = password;
        this._globalState = globalState;
        this.context = context;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        int sessionId = -1;

        try {
            sessionId = WSHelper.login(_username, _password);        // username doesn't exist
            Log.d(TAG, "Login result => " + sessionId);
            publishProgress(sessionId);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress(sessionId);

        }
        return sessionId;

    }

    @Override
    protected void onProgressUpdate(Integer... value) {
        //_sessionId = value[0];
    }

    protected void onPostExecute(Integer result) {
        Log.d(TAG, "result => " + result);
        _globalState.setSessionId((int) result);
        if (result <= 0) {
            Toast.makeText(context, "Login failed! Username or password incorrect.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this.context, MainPageActivity.class);
            intent.putExtra("USERNAME", _username);
            intent.putExtra("SESSIONID", result);
            context.startActivity(intent);
            ((Activity) context).finish();
            //super.onPostExecute(result);
        }
    }
}

