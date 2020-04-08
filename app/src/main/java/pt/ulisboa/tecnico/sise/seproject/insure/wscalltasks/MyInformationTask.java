package pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.seproject.insure.GlobalState;
import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.Customer;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.JsonCodec;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.JsonFileManager;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.WSHelper;

public class MyInformationTask extends AsyncTask<Void, Void, Customer> {

    private static final String TAG = "MyInformationCallTask";
    private static final String CUSTOMER_FILE_NAME = "customer.json";
    private int _sessionId;
    private Context _context;
    private GlobalState _globalState;
    private TextView _textViewName;
    private TextView _textViewDateOfBirth;
    private TextView _textViewAddress;
    private TextView _textViewNif;
    private TextView _textViewPolicyNr;

    public MyInformationTask(GlobalState globalState, Context context, Integer sessionId, TextView textViewName, TextView textViewDateOfBirth,
                             TextView textViewAddress, TextView textViewNif, TextView textViewPolicyNr) {
        this._context = context;
        this._globalState = globalState;
        this._sessionId = sessionId;
        this._textViewName = textViewName;
        this._textViewDateOfBirth = textViewDateOfBirth;
        this._textViewAddress = textViewAddress;
        this._textViewNif = textViewNif;
        this._textViewPolicyNr = textViewPolicyNr;
    }

    @Override
    protected Customer doInBackground(Void... voids) {
        Log.d(TAG, "SessionID:" + _sessionId);
        Log.d(TAG, "read file: customer info " + JsonFileManager.jsonReadFromFile(_context, CUSTOMER_FILE_NAME));
        try {
            String customerJson = JsonFileManager.jsonReadFromFile(_context, CUSTOMER_FILE_NAME);
            Log.d(TAG, "customerInfo: read from - " + CUSTOMER_FILE_NAME);

            if (JsonCodec.decodeCustomerInfo(customerJson) == null) {
                Log.d(TAG, "online");
                Customer c = WSHelper.getCustomerInfo(_sessionId);
                if (c == null) {
                    _sessionId = WSHelper.login(_globalState.getCustomer().getUsername(), _globalState.getPassword());
                    Log.d(TAG, "Login result => " + _sessionId);
                    _globalState.setSessionId(_sessionId);
                    _globalState.getCustomer().setSessionId(_sessionId);

                    return WSHelper.getCustomerInfo(_sessionId);
                }

                return c;

            } else {
                Log.d(TAG, "customerInfo: jsonCustomer - " + JsonCodec.decodeCustomerInfo(customerJson));
                return JsonCodec.decodeCustomerInfo(customerJson);
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Customer c) {
        if (c == null) {
            Toast.makeText(_context, "Server Error and no user information in cache.\nPlease try again later.", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "claimList" + c.getClaimRecordList());
            _globalState.set_customer(c);
            try {
                String customerJson = JsonCodec.encodeCustomerInfo(c);
                Log.d(TAG, "customerInfo: customerJson - " + customerJson);
                JsonFileManager.jsonWriteToFile(_context, CUSTOMER_FILE_NAME, customerJson);
                Log.d(TAG, "customerInfo: written to - " + CUSTOMER_FILE_NAME);

                this._textViewName.setText(c.getName());
                this._textViewDateOfBirth.setText(String.valueOf(c.getDateOfBirth()));
                this._textViewAddress.setText(c.getAddress());
                this._textViewNif.setText(String.valueOf(c.getFiscalNumber()));
                this._textViewPolicyNr.setText(String.valueOf(c.getPolicyNumber()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
