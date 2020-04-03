package pt.ulisboa.tecnico.sise.seproject.insure;

import android.app.Application;

import java.util.ArrayList;

import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.ClaimRecord;
import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.Customer;

public class GlobalState extends Application {
    private ArrayList<ClaimRecord> _claimList;
    private int _sessionId;
    private Customer _customer;

    public Customer getCustomer() {
        return this._customer;
    }

    public void set_customer(Customer customer) {
        this._customer = customer;
    }

//    public ArrayList<ClaimRecord> getClaimList() {
//        return _claimList;
//    }
//
//    public void setClaimList(ArrayList<ClaimRecord> claimList) {
//        _claimList = claimList;
//    }
//
//    public int getSessionId() {
//        return _sessionId;
//    }
//
//    public void setSessionId(int sessionId) {
//        _sessionId = sessionId;
//    }
}
