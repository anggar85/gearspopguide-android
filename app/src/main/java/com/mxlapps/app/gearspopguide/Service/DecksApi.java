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

    @GET(getDecks)
    Call<DataMaster> getDecks();


    @POST(create_deck )
    Call<DataMaster> createDeck(@Body DataMaster dataMaster);



//    @GET(Constante.URL_GIRO_EMPRESA)
//    Call<DataMaster> giro_empresas(
//            @Query("data[categoria]") String catalogo,
//            @Query("data[codigo_confirmacion]") String codigo_confirmacion
//    );
//
//
//    @POST(Constante.URL_ERRORES_APP)
//
//
//    // Mostra solo 1 producto
//    @GET(Constante.URL_PRODUCTOS_LISTADO + "/{id}")
//    Call<DataMaster> mostrarProducto(
//            @Path("id") Integer id,
//            @QueryMap(encoded = true) Map<String, String> query);
//
//
//    // Eliminar producto
//    @POST(Constante.URL_PRODUCTOS_ELIMINAR)
//    Call<DataMaster> eliminarProductos(@Body DataMaster dataMaster);
//
//
//    // Actualizar producto
//    @PUT(Constante.URL_PRODUCTOS_ACTUALIZAR + "/{id}")
//    Call<DataMaster> actualizarProducto(
//            @Path("id") Integer id,
//            @Body DataMaster dataMaster);
//
//
//    // Obtener Unidad Sat
//    @GET(Constante.URL_PRODUCTOS_UNIDAD_SAT + "/{unidadSat}")
//    Call<DataMaster> unidadSat(
//            @Path("unidadSat") String unidadSat,
//            @QueryMap(encoded = true) Map<String, String> query);
//
//
//    // Obtener Clave Sat
//    @GET(Constante.URL_PRODUCTOS_CLAVE_SAT + "/{claveSat}")
//    Call<DataMaster> claveSat(
//            @Path("claveSat") String claveSat,
//            @Query("meta[empresa_rfc]") String empresa_rfc,
//            @Query("meta[usuario_id]") int usuario_id,
//            @Query("meta[ambiente]") String ambiente,
//            @Query("meta[sesion_token]") String sesion_token,
//            @Query("meta[objeto]") String sucursales);
//
//    // Agregar nuevo producto
//    @POST(Constante.URL_PRODUCTOS_NUEVO )
//    Call<DataMaster> nuevoProducto(@Body DataMaster dataMaster);



}
