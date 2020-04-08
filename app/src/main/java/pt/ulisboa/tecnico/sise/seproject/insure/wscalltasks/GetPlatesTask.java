package pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import pt.ulisboa.tecnico.sise.seproject.insure.helpers.JsonCodec;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.JsonFileManager;
import pt.ulisboa.tecnico.sise.seproject.insure.helpers.WSHelper;

public class GetPlatesTask extends AsyncTask<Void, Void, List<String>> {

    private static final String TAG = "Insure";
    private static final String PLATE_LIST_FILE_NAME = "plateList.json";
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
            String plateListJson = JsonFileManager.jsonReadFromFile(_context, PLATE_LIST_FILE_NAME);
            Log.d(TAG, "claimList: read from - " + PLATE_LIST_FILE_NAME);
            if (JsonCodec.decodePlateList(plateListJson) == null) {
                _plateList = WSHelper.listPlates(_sessionId);
            } else {
                Log.d(TAG, "ClaimList: - " + JsonCodec.decodeClaimList(plateListJson));
                _plateList = JsonCodec.decodePlateList(plateListJson);
            }

        } catch (Exception e) {
            Log.d(TAG, e.toString());

        }
        return _plateList;
    }

    @Override
    protected void onPostExecute(List<String> plateList) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(_context, android.R.layout.simple_spinner_item, plateList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _platesSpinner.setAdapter(dataAdapter);
        try {
            String plateListJson = JsonCodec.encodePlateList(_plateList);
            Log.d(TAG, "claimList: customerJson - " + plateListJson);
            JsonFileManager.jsonWriteToFile(_context, PLATE_LIST_FILE_NAME, plateListJson);
            Log.d(TAG, "claimList: written to - " + PLATE_LIST_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
