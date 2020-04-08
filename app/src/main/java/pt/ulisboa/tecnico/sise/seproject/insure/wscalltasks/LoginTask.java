package pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.activities.MainPageActivity;
import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.Customer;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.WSHelper;

public class LoginTask extends AsyncTask<Void, Void, Integer> {
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
        try {
            _sessionId = WSHelper.login(_username, _password);
            Log.d(TAG, "Login result => " + _sessionId);

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return _sessionId;
    }

    @Override
    protected void onProgressUpdate(Void... params) {
    }

    protected void onPostExecute(Integer result) {
        Log.d(TAG, "result => " + result);
        _globalState.setSessionId(result);

        if (result == 0) {
            Toast.makeText(context, "Login failed! Username or password incorrect.", Toast.LENGTH_SHORT).show();
        } else if (result == -1) {
            if (_globalState.getCustomer() != null) {
                if (!_username.equals(_globalState.getCustomer().getUsername())) {
                    Toast.makeText(context, "Login failed!\nSorry, we are having server problems and Username is not stored in cache.", Toast.LENGTH_LONG).show();
                } else if (!_password.equals(_globalState.getPassword())) {
                    Toast.makeText(context, "Login failed!\nSorry, we are having server problems and password is incorrect.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(this.context, MainPageActivity.class);
                    intent.putExtra("USERNAME", _globalState.getCustomer().getUsername());
                    context.startActivity(intent);
                    ((TaskCallBack) context).done();
                }
            } else {
                Toast.makeText(context, "Sorry, we are having server problems.\nTry again later.", Toast.LENGTH_LONG).show();
            }

        } else {
            _globalState.set_customer(new Customer(_sessionId, _username));
            _globalState.setPassword(_password);

            Intent intent = new Intent(this.context, MainPageActivity.class);
            intent.putExtra("USERNAME", _globalState.getCustomer().getUsername());
            context.startActivity(intent);
            ((TaskCallBack) context).done();

        }
    }
}

