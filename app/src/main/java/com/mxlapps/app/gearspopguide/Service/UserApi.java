package com.mxlapps.app.gearspopguide.Service;

import com.mxlapps.app.gearspopguide.Request.DataMaster;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {
    String USER_CREATE = "user/create_fb";
    String USER_SHOW = "user/show_fb";


//    @GET(Constante.URL_GIRO_EMPRESA)
//    Call<DataMaster> giro_empresas(
//            @Query("data[categoria]") String catalogo,
//            @Query("data[codigo_confirmacion]") String codigo_confirmacion
//    );
//
//
    @POST(USER_CREATE)
    Call<DataMaster> createUser(
            @Query("token") String token,
            @Body DataMaster dataMaster);
//
//
//    // Mostra solo 1 producto
    @GET(USER_SHOW)
    Call<DataMaster> showUser(
            @Query("token") String token,
            @Query("user[token]") String user_token);

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
//Obtener Clave Sat
////    @GET(Constante.URL_PRODUCTOS_CLAVE_SAT + "/{claveSat}")
////    Call<DataMaster> claveSat(
////            @Path("claveSat") String claveSat,
////            @Query("meta[empresa_rfc]") String empresa_rfc,
////            @Query("meta[usuario_id]") int usuario_id,
////            @Query("meta[ambiente]") String ambiente,
////            @Query("meta[sesion_token]") String sesion_token,
////            @Query("meta[objeto]") String sucursales);
////
////    // Agregar nuevo producto
////    @POST(Constante.URL_PRODUCTOS_NUEVO )
////    Call<DataMaster> nuevoProducto(@Body DataMaster dataMaster);
//    //

}
