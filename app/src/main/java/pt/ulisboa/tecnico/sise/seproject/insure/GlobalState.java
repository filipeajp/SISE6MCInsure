package pt.ulisboa.tecnico.sise.seproject.insure;

import android.app.Application;

import java.util.ArrayList;

public class GlobalState extends Application {
    private ArrayList<Claim> _claimList;

    public ArrayList<Claim> getClaimList() {
        return _claimList;
    }

    public void setClaimList(ArrayList<Claim> claimList) {
        _claimList = claimList;
    }

}
