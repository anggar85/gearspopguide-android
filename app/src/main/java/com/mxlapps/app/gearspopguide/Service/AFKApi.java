package com.mxlapps.app.gearspopguide.Service;

import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Utils.Constante;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AFKApi {

    @GET(Constante.HERO_LIST)
    Call<DataMaster> getHeroList(
            @Query("token") String token,
            @Query("gameLevel") String gameLevel,
            @Query("section") String section,
            @Query("rarity") String rarity,
            @Query("classe") String classe,
            @Query("race_name") String race_name
    );
    @GET(Constante.HERO_DETAIL + "/{hero_id}")
    Call<DataMaster> getHeroDetail(
            @Path("hero_id") Integer hero_id,
            @Query("token") String token
    );

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
