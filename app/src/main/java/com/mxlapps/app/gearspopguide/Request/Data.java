
package com.mxlapps.app.gearspopguide.Request;

import com.mxlapps.app.gearspopguide.Model.PinModel;

import java.util.ArrayList;

public class Data {

    private PinModel pin;
    private ArrayList<PinModel> pins;

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
