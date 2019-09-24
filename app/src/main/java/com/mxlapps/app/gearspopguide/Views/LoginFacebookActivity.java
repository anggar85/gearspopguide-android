package com.mxlapps.app.gearspopguide.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.snackbar.Snackbar;
import com.mxlapps.app.gearspopguide.Model.UserModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Request.Data;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Utils.AppPreferences;
import com.mxlapps.app.gearspopguide.Utils.Util;
import com.mxlapps.app.gearspopguide.ViewModel.UserViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginFacebookActivity extends AppCompatActivity {

    private static final String TAG = "afkArenaMainActivity";
    private View rootView;
    // FACEBOOK
    LoginButton loginButton;
    ConstraintLayout layout_informacion_usuario;
    CallbackManager callbackManager;
    TextView txtName;
    UserModel userModel = new UserModel();
    UserViewModel userViewModel;
    private static final int VALIDATE_USER = 3;
    private static final int CREATE_USER = 5;
    Button button_back_to_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_facebook);

        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        userViewModel = ViewModelProviders.of(LoginFacebookActivity.this).get(UserViewModel.class);

        button_back_to_app = findViewById(R.id.button_back_to_app);

        button_back_to_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

        initFacebookEvents();
    }


    private void initFacebookEvents() {
        loginButton = findViewById(R.id.login_button);

        boolean loggedOut = AccessToken.getCurrentAccessToken() == null;

        if (!loggedOut) {
            //Using Graph API
            getUserProfile(AccessToken.getCurrentAccessToken());
        }

        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //loginResult.getAccessToken();
                Log.d("iuofihj", "onSuccess: aquiii");
                getUserProfile(AccessToken.getCurrentAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("API123", "onCancel: ");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("API123", "onError: " + exception.getMessage());
            }
        });


        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                Log.d("uhy", "onCurrentAccessTokenChanged()");
                if (accessToken == null) {
                    ///////
                } else if (accessToken2 == null) {
                    AppPreferences.cerrarSesion();
                    Log.d("ijuhj", "onCurrentAccessTokenChanged: se supone que ya borro todo");
                    setResult(RESULT_OK);
                    finish();
                }
            }
        };

        accessTokenTracker.startTracking();
    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email"); // todo se hara algo con el correo?
                            String id = object.getString("id");
                            /// Se setetan variables en objeto userModel
                            userModel.setToken(id);
                            userModel.setEmail(email);
                            userModel.setName(first_name + " " + last_name);
                            userModel.setFacebook_name(first_name + " " + last_name);
                            userModel.setGame_name(first_name + " " + last_name);
                            // Se revisa si el usuario existe en la base de datos
                            userViewModel.showUser(id).observe(LoginFacebookActivity.this, new Observer<Resource<DataMaster>>() {
                                @Override
                                public void onChanged(Resource<DataMaster> dataMasterResource) {
                                    procesaRespuesta(dataMasterResource, VALIDATE_USER);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }


    private void procesaRespuesta(Resource<DataMaster> dataMasterResource, int opcion) {
        /* Este opcion va a poder procesar la respuesta del server y ejecutar el callback dependiendo del parametro @opcion*/
        switch (dataMasterResource.status) {
            case ERROR:
                Util.stopLoading(rootView);
                Util.alertaMensajeCtx(dataMasterResource.message, LoginFacebookActivity.this);
                break;
            case LOADING:
                Util.startLoading(rootView);
                break;
            case SUCCESS:
                Util.stopLoading(rootView);
                // Crea copia de listado de heroes
                switch (opcion) {
                    case CREATE_USER:
                        responseCreateUser(dataMasterResource);
                        break;
                    case VALIDATE_USER:
                        responseRevisarUsuario(dataMasterResource);
                        break;
                }
                break;
            default:
                break;
        }
    }

    //region responses request
    private void responseRevisarUsuario(Resource<DataMaster> dataMasterResource) {
        assert dataMasterResource.data != null;
        if (dataMasterResource.data.getData().isError()) {

            // Se crea el usuario
            DataMaster dataMaster = new DataMaster();
            Data data = new Data();
            data.setUser(userModel);
            dataMaster.setData(data);

            userViewModel.createUser(dataMaster).observe(LoginFacebookActivity.this, new Observer<Resource<DataMaster>>() {
                @Override
                public void onChanged(Resource<DataMaster> dataMasterResource) {
                    procesaRespuesta(dataMasterResource, CREATE_USER);
                }
            });

        } else {
            // El usuario ya existe en la base de datos, solo se actualiza el objeo user con lo que se tenga enla DB
            initInfoUSer(dataMasterResource);
        }
    }


    private void responseCreateUser(Resource<DataMaster> dataMasterResource) {
        initInfoUSer(dataMasterResource);
    }


    //endregion

    private void initInfoUSer(Resource<DataMaster> dataMasterResource) {
        assert dataMasterResource.data != null;
        userModel = dataMasterResource.data.getData().getUser();

        Snackbar.make(findViewById(android.R.id.content), "Welcome back " + userModel.getEmail(), Snackbar.LENGTH_LONG).show();

        // Seteta el id de usuario en las sharedpreferences
        AppPreferences.getInstance(this).setUserId(userModel.getToken());
        AppPreferences.getInstance(this).setName(userModel.getName());
        AppPreferences.getInstance(this).setEmail(userModel.getEmail());

        Log.d(TAG, "initInfoUSer1: " + AppPreferences.getInstance(this).getUserId());
        Log.d(TAG, "initInfoUSer1: " + AppPreferences.getInstance(this).getName());

        String token = AppPreferences.getInstance(this).getUserId();

        if (token.compareToIgnoreCase("") == 0){
            Log.d(TAG, "initInfoUSer: ");
        }else{
            button_back_to_app.setVisibility(View.VISIBLE);
//            setResult(RESULT_OK);
//            finish();
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " );
        Util.stopLoading(rootView);

    }
}
