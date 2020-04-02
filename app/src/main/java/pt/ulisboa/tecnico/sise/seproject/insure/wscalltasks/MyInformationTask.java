package pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import pt.ulisboa.tecnico.sise.seproject.insure.WSHelper;
import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.Customer;

public class MyInformationTask extends AsyncTask<Void, Void, Customer> {

    private static final String TAG = "MyInformationCallTask";

    private int _sessionId;
    private TextView _textViewName;
    private TextView _textViewDateOfBirth;
    private TextView _textViewAddress;
    private TextView _textViewNif;
    private TextView _textViewPolicyNr;

    public MyInformationTask(Integer sessionId, TextView textViewName, TextView textViewDateOfBirth,
                             TextView textViewAddress, TextView textViewNif, TextView textViewPolicyNr) {

        this._sessionId = sessionId;
        this._textViewName = textViewName;
        this._textViewDateOfBirth = textViewDateOfBirth;
        this._textViewAddress = textViewAddress;
        this._textViewNif = textViewNif;
        this._textViewPolicyNr = textViewPolicyNr;
    }

    @Override
    protected Customer doInBackground(Void... voids) {

        try {
            return WSHelper.getCustomerInfo(_sessionId);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Customer c) {
        this._textViewName.setText(c.getName());
        this._textViewDateOfBirth.setText(String.valueOf(c.getDateOfBirth()));
        this._textViewAddress.setText(c.getAddress());
        this._textViewNif.setText(String.valueOf(c.getFiscalNumber()));
        this._textViewPolicyNr.setText(String.valueOf(c.getPolicyNumber()));
    }
}
