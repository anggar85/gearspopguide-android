package com.mxlapps.app.gearspopguide.Utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mxlapps.app.gearspopguide.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final String TAG = Util.class.getName();
    private static Context currentContext = null;
    private int previousPosition;

    private Util() {
        throw new UnsupportedOperationException();
    }
    /**
     * Set the current and save it into currentContext
     *
     * @param context the context from the application
     */
    public static void setContext(Context context) {
        Log.d(TAG, "setContext: ");
        currentContext = context;
    }

    public static Drawable findHeroImage(String hero_name){

//        switch (hero_name){
//            case Constante.LIGHTBEARERS:
//                return getContext().getDrawable(R.drawable.humen_i);
//            case Constante.MAULERS:
//                return getContext().getDrawable(R.drawable.orc_i);
//            case Constante.WILDERS:
//                return getContext().getDrawable(R.drawable.elf_i);
//            case Constante.GRAVEBORN:
//                return getContext().getDrawable(R.drawable.undead_i);
//        }

        return null;

    }

    public static String CurrencyChange(String precio_decimal){
        Double precio_listo = Double.parseDouble(precio_decimal);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("$#,###.##",symbols);

        String precio_final;
        precio_final = decimalFormat.format(precio_listo);


        return precio_final;

    }


    public static class AnimationUtil {
        public static void animate(RecyclerView.ViewHolder holder , boolean goesDown){


            AnimatorSet animatorSet = new AnimatorSet();

            ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown==true ? 200 : -200, 0);
            animatorTranslateY.setDuration(500);


//        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(holder.itemView,"translationX",-50,50,-30,30,-20,20,-5,5,0);
//        animatorTranslateX.setDuration(1000);

            animatorSet.playTogether(animatorTranslateY);

            //animatorSet.playTogether(animatorTranslateY);
            animatorSet.start();

        }
    }

    public static void release() {
        currentContext = null;
    }

    /**
     * Get the current context
     *
     * @return an Context object
     */
    public static Context getContext() {
        if (currentContext == null) {
            Log.d(TAG, "getContext: getContext currentContext NOT PREVIOUSLY SET!!!");
            //currentContext = .getInstance().getBaseContext();;
        }
        return currentContext;
    }

    private JSONObject inside;

    public JSONObject extrae_jsnon(String response, String key) {
        JSONObject mainObject;
        try {
            mainObject = new JSONObject(response);
            inside = mainObject.getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return inside;
    }



    public static boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }


    /**
     * Set a Shared Preference from the application
     *
     * @param key   the key in a String representation
     * @param value the value to store in an Object instance
     * @param pname the preference name
     */
    public static void setPreference(String key, Object value, String pname) {
        Log.d(TAG, "setPreference:(" + key + ", " + value + ", " + pname + ")");
        SharedPreferences settings = Util.getContext().getSharedPreferences(pname, 0);
        SharedPreferences.Editor editor = settings.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        editor.commit();
    }

    /**
     * To read a string from the preferences file.
     *
     * @param key   The key that identifies the preferences file.
     * @param pname the preference name
     */
    public static String getPreferenceString(String key, String pname) {
        SharedPreferences settings = Util.getContext().getSharedPreferences(pname, 0);
        String res = settings.getString(key, "");
        Log.d(TAG, "getPreferenceString(" + key + ", " + res + ", " + pname + ")");
        return res;

    }

    /**
     * To read a string from the preferences file.
     *
     * @param key          The key that identifies the preferences file.
     * @param defaultValue The default value to be returned if the preference does not exist.
     * @param pname        the preference name
     */
    public static String getPreferenceString(String key, String defaultValue, String pname) {
        SharedPreferences settings = Util.getContext().getSharedPreferences(pname, 0);
        String res = settings.getString(key, defaultValue);
        Log.d(TAG, "getPreferenceString(" + key + ", " + defaultValue + " " + res + ", " + pname + ")");
        return res;

    }

    /**
     * To read an int from the preferences file.
     *
     * @param key          The key that identifies the preferences file.
     * @param defaultValue The default value to be returned if the preference does not exist.
     * @param pname        the preference name
     */
    public static int getPreferenceInt(String key, int defaultValue, String pname) {
        SharedPreferences settings = Util.getContext().getSharedPreferences(pname, 0);
        int res = settings.getInt(key, defaultValue);
        Log.d(TAG, "getPreferenceInt(" + key + ", " + defaultValue + " " + res + ", " + pname + ")");
        Log.d(TAG, "getPreferenceInt(" + key + ", " + res + ", " + pname + ")");
        return res;
    }

    /**
     * To read a long from the preferences file.
     *
     * @param key          The key that identifies the preferences file.
     * @param defaultValue The default value to be returned if the preference does not exist.
     * @param pname        the preference name
     */
    public static boolean getPreferenceBoolean(String key, boolean defaultValue, String pname) {
        try {
            SharedPreferences settings = Util.getContext().getSharedPreferences(pname, 0);
            boolean res = settings.getBoolean(key, defaultValue);
            Log.d(TAG, "getPreferenceBoolean(" + key + ", " + defaultValue + " " + res + ", " + pname + ")");
            return res;
        } catch (Exception ex) {
            Log.d(TAG, "getPreferenceBoolean(" + key + ", " + defaultValue + " " + false + ", " + pname + ")");
            return false;
        }
    }

    /**
     * To read a long from the preferences file.
     *
     * @param key          The key that identifies the preferences file.
     * @param defaultValue The default value to be returned if the preference does not exist.
     * @param pname        the preference name
     */
    public static long getPreferenceLong(String key, long defaultValue, String pname) {
        SharedPreferences settings = Util.getContext().getSharedPreferences(pname, 0);
        Long res = settings.getLong(key, defaultValue);

        Log.d(TAG, "getPreferenceLong(" + key + ", " + defaultValue + " " + res + ", " + pname + ")");
        return res;
    }

    /**
     * To remove a preference from the preferences file.
     *
     * @param key   The key that identifies the preferences file.
     * @param pname the preference name
     */
    public static void removePreference(String key, String pname) {
        Log.d(TAG, "removePreference(" + key + ", " + pname + ")");
        SharedPreferences settings = Util.getContext().getSharedPreferences(pname, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }


    public  static String getNameVersion(){
        String version = "";
        try {
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }



    // Loding Animation
    public static void stopLoading(View v){
        // Activa everything
        ConstraintLayout loading_layout = v.findViewById(R.id.loading_layout);
        loading_layout.setVisibility(View.GONE);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    public static void startLoading(View v){
        // Bloquea everything
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ConstraintLayout loading_layout = v.findViewById(R.id.loading_layout);
        loading_layout.setVisibility(View.VISIBLE);
    }

    public static void alertaMensajeCtx(String msg, Context ctx) {
        new AlertDialog.Builder(ctx, R.style.Theme_AppCompat_Light_Dialog_MinWidth)
                .setTitle(R.string.app_name)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }



}
