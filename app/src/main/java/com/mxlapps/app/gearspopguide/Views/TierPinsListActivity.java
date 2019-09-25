package com.mxlapps.app.gearspopguide.Views;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import com.facebook.CallbackManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.mxlapps.app.gearspopguide.BuildConfig;
import com.mxlapps.app.gearspopguide.Model.UserModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Utils.AppPreferences;
import com.mxlapps.app.gearspopguide.Utils.Util;
import com.mxlapps.app.gearspopguide.ViewModel.UserViewModel;
import com.mxlapps.app.gearspopguide.Views.Fragment.ListPinsFragment;
import com.mxlapps.app.gearspopguide.Views.Fragment.MyDecksFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class TierPinsListActivity extends AppCompatActivity  {

    //region variables
    private static final String TAG = "PinsMainActivity";
    private View rootView;
    UserViewModel userViewModel;


    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;

    // FACEBOOK


    public static String FACEBOOK_URL = "https://www.facebook.com/AFK-Arena-Guide-449688835791805";
    public static String APP_URL = "https://play.google.com/store/apps/details?id=com.mxlapps.app.afk_arenaguide";
    public static String FACEBOOK_PAGE_ID = "449688835791805";
    private InterstitialAd interstitial;
    private int AD_INTERSTITIAL = 0;

    private DrawerLayout dl;


    //endregion

    //region override

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tier_hero_list_toolbar, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tier_hero_list);


        MobileAds.initialize(this, getString(R.string.admob_app_id));
        AdRequest adIRequest = new AdRequest.Builder().build();
        // Prepare the Interstitial Ad Activity
        interstitial = new InterstitialAd(TierPinsListActivity.this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId(BuildConfig.ADS_INTERSTITIAL);
        // Interstitial Ad load Request
        interstitial.loadAd(adIRequest);
        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
            // Call displayInterstitial() function when the Ad loads
                if (AD_INTERSTITIAL == 2)
                    displayInterstitial();
                }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_tier_list);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        userViewModel = ViewModelProviders.of(TierPinsListActivity.this).get(UserViewModel.class);


        eventsLeftDrawer();
        // Lanza fragmento de heroes
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyDecksFragment()).commit();


        MobileAds.initialize(this, BuildConfig.AD_LIST);
        AdView mAdView = findViewById(R.id.adViewListado);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        initFacebookEvents();

        obtenerHashKey();


    }

    private void obtenerHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.mxlapps.app.gearspopguide", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.i("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }


    private void eventsLeftDrawer() {
        // Eventos en drawer izquierdo
        dl = findViewById(R.id.drawer_layout_tierList);
        ActionBarDrawerToggle t = new ActionBarDrawerToggle(this, dl, R.string.navigation_draw_open, R.string.navigation_draw_close);

        dl.addDrawerListener(t);
        t.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        NavigationView nv = findViewById(R.id.nav_view_left);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_my_decks:
                        Toast.makeText(TierPinsListActivity.this, "nav_my_decks", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyDecksFragment()).commit();
                        break;
                    case R.id.nav_heroes:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListPinsFragment()).commit();
                        break;
                    case R.id.nav_about_us:
                        startActivity(new Intent(TierPinsListActivity.this, AboutUsActivity.class));

                        break;
                }
                dl.closeDrawer(GravityCompat.START);
                return true;

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dl.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_contributors:
//                startActivity(new Intent(TierPinsListActivity.this, ContributorsActivity.class));
                break;
//            case R.id.menu_bubble:
//                bubbleView();
//                break;
            case R.id.menu_about_us:
                startActivity(new Intent(TierPinsListActivity.this, AboutUsActivity.class));
                break;
            case R.id.menu_facebook:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + FACEBOOK_PAGE_ID));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL)));
                }

//                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
//                String facebookUrl = getFacebookPageURL(this);
//                facebookIntent.setData(Uri.parse(facebookUrl));
//                startActivity(facebookIntent);
                break;
            case R.id.menu_rateus:
                Intent rateUsIntent = new Intent(Intent.ACTION_VIEW);
                rateUsIntent.setData(Uri.parse(APP_URL));
                startActivity(rateUsIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + AD_INTERSTITIAL);
        Util.stopLoading(rootView);
        AD_INTERSTITIAL++;
        if (AD_INTERSTITIAL == 2) {
            displayInterstitial();
            AD_INTERSTITIAL = 0;
        }
        
        if (requestCode == 1001){
            Log.d(TAG, "onActivityResult: tyfuhijhgjhgjhkjgtjhjgyhjhkj");
        }
        llenarInfoUser();

    }

    public void llenarInfoUser(){

        NavigationView navigationView = findViewById(R.id.nav_view_left);
        ConstraintLayout layout_informacion_usuario = navigationView.getHeaderView(0).findViewById(R.id.layout_informacion_usuarioT);
        TextView txtEmail = navigationView.getHeaderView(0).findViewById(R.id.txtEmail);
        Button boton_comenzar_login = navigationView.getHeaderView(0).findViewById(R.id.boton_comenzar_login);

        //Log.i(TAG, "llenarInfoUser: ");

        if (AppPreferences.getInstance(this).getName().compareToIgnoreCase("") != 0){
            //Log.i(TAG, "llenarInfoUser: aquii");
            layout_informacion_usuario.setVisibility(View.VISIBLE);
            txtEmail.setText(AppPreferences.getInstance(this).getName());
            boton_comenzar_login.setText("Logout");
        }else{
            //Log.i(TAG, "llenarInfoUser: acaaaaa");
            layout_informacion_usuario.setVisibility(View.GONE);
            boton_comenzar_login.setText("Login with Facebook");
            txtEmail.setText("");
        }


    }

    //endregion

    //region facebook

    private void initFacebookEvents() {

        NavigationView navigationView = findViewById(R.id.nav_view_left);
        Button loginButton = navigationView.getHeaderView(0).findViewById(R.id.boton_comenzar_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TierPinsListActivity.this, LoginFacebookActivity.class);
                startActivityForResult(intent, 1001);
            }
        });

        llenarInfoUser();
    }


    //endregion



    private void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    public void displayInterstitial() {
        // If Interstitial Ads are loaded then show else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;

            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }


}
