package com.mxlapps.app.gearspopguide.Service;

import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Utils.Constante;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PinsApi {

    @GET(Constante.URL_PIN_LIST + "/{race}/{role}/{type}/{cover}/en")
    Call<DataMaster> getPinList(
            @Path("race") String race,
            @Path("role") String role,
            @Path("type") String type,
            @Path("cover") String cover);


    @GET(Constante.HERO_DETAIL + "/{hero_id}")
    Call<DataMaster> getHeroDetail(
            @Path("hero_id") Integer hero_id,
            @Query("token") String token
    );


}
