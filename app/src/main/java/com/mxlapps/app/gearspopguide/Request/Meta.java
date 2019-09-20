
package com.mxlapps.app.gearspopguide.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("ambiente")
    @Expose
    private String ambiente;

    @SerializedName("sesion_token")
    @Expose
    private String sesion_token;


    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getSesion_token() {
        return sesion_token;
    }

    public void setSesion_token(String sesion_token) {
        this.sesion_token = sesion_token;
    }
}
