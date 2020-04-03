package pt.ulisboa.tecnico.sise.seproject.insure.wscalltasks;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import pt.ulisboa.tecnico.sise.seproject.insure.InternalProtocol;
import pt.ulisboa.tecnico.sise.seproject.insure.R;
import pt.ulisboa.tecnico.sise.seproject.insure.WSHelper;
import pt.ulisboa.tecnico.sise.seproject.insure.activities.MyClaimsActivity;
import pt.ulisboa.tecnico.sise.seproject.insure.activities.ReadClaimActivity;
import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.ClaimRecord;

public class ReadClaimTask extends AsyncTask<Void, Void, ClaimRecord> {
    private static final String TAG = "ReadClaimTask";
    private Context _context;
    private int _sessionId;
    private int _claimId;
    private TextView _claimTitleView;
    private TextView _claimOccurDateView;
    private TextView _claimPlateNumberView;
    private TextView _claimDescriptionView;
    private TextView _claimStatusView;
    private ClaimRecord _claim;

//    public ReadClaimTask(int sessionId, int index, TextView claimTitleView, TextView claimOccurDateView, TextView claimPlateNumberView, TextView claimDescriptionView, TextView claimStatusView) {
//
//        this._sessionId = sessionId;
//        this._index = index;
//        this._claimTitleView = claimTitleView;
//        this._claimOccurDateView = claimOccurDateView;
//        this._claimPlateNumberView = claimPlateNumberView;
//        this._claimDescriptionView = claimDescriptionView;
//        this._claimStatusView = claimStatusView;
//    }

    public ReadClaimTask(int sessionId, int claimId, Context context){
        this._sessionId = sessionId;
        this._claimId = claimId;
        this._context = context;
    }

    @Override
    protected ClaimRecord doInBackground(Void... voids) {
        try {
            _claim = WSHelper.getClaimInfo(_sessionId, _claimId);
            if (_claim != null) {
                Log.d(TAG, "Get Claim Info result claimId " + _claimId + " => " + _claim.toString());
            } else {
                Log.d(TAG, "Get Claim Info result claimId " + _claimId + " => null.");
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return _claim;
    }

    @Override
    protected void onProgressUpdate(Void... params){
    }

    @Override
    protected void onPostExecute(ClaimRecord claim) {
        Intent intent = new Intent(_context, ReadClaimActivity.class);
        intent.putExtra(InternalProtocol.READ_CLAIM_INDEX, claim.getTitle());
        intent.putExtra(InternalProtocol.READ_CLAIM_INDEX, claim.getOccurrenceDate());
        intent.putExtra(InternalProtocol.READ_CLAIM_INDEX, claim.getPlate());
        intent.putExtra(InternalProtocol.READ_CLAIM_INDEX, claim.getStatus());
        _context.startActivity(intent);
    }
}


