package pt.ulisboa.tecnico.sise.seproject.insure;

import android.app.Application;
import android.util.Log;

import pt.ulisboa.tecnico.sise.seproject.insure.datamodel.Customer;

public class GlobalState extends Application {
    private static int _sessionId = -1;
    private static String _password;
    private Customer _customer;


    public Customer getCustomer() {
        return this._customer;
    }

    public void set_customer(Customer customer) {
        this._customer = customer;
    }

    public int getSessionId() {
        Log.d("My", "getSessionID" + _sessionId);
        return _sessionId;
    }

    public void setSessionId(int sessionId) {
        Log.d("My", "setSessionID-before" + _sessionId);
        _sessionId = sessionId;
        Log.d("My", "setSessionID-after" + _sessionId);
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        _password = password;
    }

    public void clearCustomer() {
        _customer.setSessionId(-1);
        _customer.setName("");
        _customer.setPolicyNumber(-1);
        _customer.setAddress("");
        _customer.setDateOfBirth("");
        _customer.setUsername("");
        _customer.setPassword("");
        _customer.getClaimRecordList().clear();
        _customer.getPlateList().clear();
        _customer = null;
    }
}
