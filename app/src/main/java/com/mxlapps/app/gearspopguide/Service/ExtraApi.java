package com.mxlapps.app.gearspopguide.Service;

import com.mxlapps.app.gearspopguide.Request.DataMaster;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ExtraApi {
    String CONTRIBUTORS_LIST = "extras/contributors";
    String NEWS_LIST = "extras/news";

    // Secciones
    String ITEMS_LIST = "extras/items_list";
    String ROL_DEFINITIONS_LIST = "extras/rol_definitions";
    String FAQ_LIST = "extras/faq";
    String ABOUT_US = "extras/about_us";

    String ADD_SUGGESTION = "extras/add_suggestion";


    @GET(CONTRIBUTORS_LIST)
    Call<DataMaster> getContributors(@Query("token") String user_token);

    @GET(NEWS_LIST)
    Call<DataMaster> getNews(@Query("token") String user_token);


    // Secciones DRAWER

    @GET(ITEMS_LIST)
    Call<DataMaster> getItems(@Query("token") String user_token);

    @GET(ROL_DEFINITIONS_LIST)
    Call<DataMaster> getRoleDefinitions(@Query("token") String user_token);


    @GET(FAQ_LIST)
    Call<DataMaster> getFaq(@Query("token") String user_token);

    @GET(ABOUT_US)
    Call<DataMaster> getAboutUs(@Query("token") String user_token);

    @POST(ADD_SUGGESTION)
    Call<DataMaster> add_suggestion(
            @Query("token") String token,
            @Body DataMaster dataMaster);


}
