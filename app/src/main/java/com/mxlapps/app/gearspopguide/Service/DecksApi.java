package com.mxlapps.app.gearspopguide.Service;

import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Utils.Constante;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DecksApi {

    String create_deck = "deck/create_deck";
    String getDecks = "deck/decks_list";
    String show_deck = "deck/show_deck";

    @GET(Constante.URL_PIN_LIST + "/{race}/{role}/{type}/{cover}")
    Call<DataMaster> getPinList(
            @Path("race") String race,
            @Path("role") String role,
            @Path("type") String type,
            @Path("cover") String cover);

    @GET(show_deck + "/{deck_id}")
    Call<DataMaster> show_deck(
            @Path("deck_id") int deck_id);

    @GET(getDecks + "/{columna}/{orderBy}")
    Call<DataMaster> getDecks(
            @Path("orderBy") String orderBy,
            @Path("columna") String columna);


    @POST(create_deck )
    Call<DataMaster> createDeck(@Body DataMaster dataMaster);





}
