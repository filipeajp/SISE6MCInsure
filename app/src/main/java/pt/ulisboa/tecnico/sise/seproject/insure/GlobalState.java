package pt.ulisboa.tecnico.sise.seproject.insure;

import android.app.Application;

import java.util.ArrayList;

import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.ClaimRecord;

public class GlobalState extends Application {
    private ArrayList<ClaimRecord> _claimList;

    public ArrayList<ClaimRecord> getClaimList() {
        return _claimList;
    }

    public void setClaimList(ArrayList<ClaimRecord> claimList) {
        _claimList = claimList;
    }

}
