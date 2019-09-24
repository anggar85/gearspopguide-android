
package com.mxlapps.app.gearspopguide.Request;

import com.mxlapps.app.gearspopguide.Model.PinModel;
import com.mxlapps.app.gearspopguide.Model.UserModel;

import java.util.ArrayList;

public class Data {

    private PinModel pin;
    private ArrayList<PinModel> pins;
    private String msg;
    private boolean error;
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ArrayList<PinModel> getPins() {
        return pins;
    }

    public void setPins(ArrayList<PinModel> pins) {
        this.pins = pins;
    }

    public PinModel getPin() {
        return pin;
    }

    public void setPin(PinModel pin) {
        this.pin = pin;
    }


}
