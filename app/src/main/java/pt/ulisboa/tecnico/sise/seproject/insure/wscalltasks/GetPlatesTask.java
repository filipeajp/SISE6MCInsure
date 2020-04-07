package pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import pt.ulisboa.tecnico.sise.seproject.insure.helpers.WSHelper;

public class GetPlatesTask extends AsyncTask<Void, Void, List<String>> {

    private static final String TAG = "Insure";
    private int _sessionId;
    private Spinner _platesSpinner;
    private Context _context;
    private List<String> _plateList;

    public GetPlatesTask(int _sessionId, Spinner _platesSpinner, Context _context) {
        this._sessionId = _sessionId;
        this._platesSpinner = _platesSpinner;
        this._context = _context;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        try {
            _plateList = WSHelper.listPlates(_sessionId);
        } catch (Exception e) {
            Log.d(TAG, e.toString());

        }
        return _plateList;
    }

    @Override
    protected void onPostExecute(List<String> plateList) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(_context, android.R.layout.simple_spinner_item, plateList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _platesSpinner.setAdapter(dataAdapter);
    }
}
