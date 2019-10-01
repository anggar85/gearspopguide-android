/*
 * Copyright 2019 DocDigitales. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mxlapps.app.gearspopguide.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppPreferences {

    // PREFERENCIAS
    private static final String SHAREDPREFERENCE_NAME = "AFKARENAGUIDEPREF";
    private static final String PREFERENCES_USER_ID = "PREFERENCES_USER_ID";

    private static final String TAG = AppPreferences.class.getName();
    Context mContext;

    private static AppPreferences instance;
    public static final String PREF_NAME = SHAREDPREFERENCE_NAME;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public static AppPreferences getInstance(Context context) {
        if(instance == null){
            synchronized (AppPreferences.class){
                if(instance == null)
                    instance = new AppPreferences(context.getApplicationContext());
            }

        }

        return instance;
    }

    private AppPreferences(Context mContext) {

        preferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();

        this.mContext = mContext;
        Util.setContext(mContext);
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
            SharedPreferences settings = instance.mContext.getSharedPreferences(pname, 0);
            boolean res = settings.getBoolean(key, defaultValue);
            Log.d(TAG, "getPreferenceBoolean(" + key + ", " + defaultValue + " " + res + ", " + pname + ")");
            return res;
        } catch (Exception ex) {
            Log.d(TAG, "getPreferenceBoolean(" + key + ", " + defaultValue + " " + false + ", " + pname + ")");
            return false;
        }
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
        SharedPreferences settings = instance.mContext.getSharedPreferences(pname, 0);
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
        SharedPreferences settings = instance.mContext.getSharedPreferences(pname, 0);
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
        SharedPreferences settings = instance.mContext.getSharedPreferences(pname, 0);
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
        SharedPreferences settings = instance.mContext.getSharedPreferences(pname, 0);
        int res = settings.getInt(key, defaultValue);
        Log.d(TAG, "getPreferenceInt(" + key + ", " + defaultValue + " " + res + ", " + pname + ")");
        Log.d(TAG, "getPreferenceInt(" + key + ", " + res + ", " + pname + ")");
        return res;
    }


    public  String getUserId(){
        return  getPreferenceString(PREFERENCES_USER_ID,SHAREDPREFERENCE_NAME);
    }

    public void setUserId(String token){
        setPreference(PREFERENCES_USER_ID, token,SHAREDPREFERENCE_NAME);
    }

    public void setName(String name){
        setPreference("name", name, SHAREDPREFERENCE_NAME);
    }

    public String getName(){
        return getPreferenceString("name", SHAREDPREFERENCE_NAME);
    }

    public void setEmail(String email){
        setPreference("email", email, SHAREDPREFERENCE_NAME);
    }

    public String getEmail(){
        return getPreferenceString("email", SHAREDPREFERENCE_NAME);
    }

    public void setTapTarget(Boolean name){
        setPreference("tap_target_facebook", name, SHAREDPREFERENCE_NAME);
    }

    public Boolean getTapTarget(){
        return getPreferenceBoolean("tap_target_facebook", false , SHAREDPREFERENCE_NAME);
    }


    public void setUsuario_id(Integer name){
        setPreference("id_usuario", name, SHAREDPREFERENCE_NAME);
    }

    public Integer getUsuario_id(){
        return getPreferenceInt("id_usuario",  0, SHAREDPREFERENCE_NAME);
    }



    public static void cerrarSesion() {
        SharedPreferences preferences = instance.mContext.getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor spreferencesEditor = preferences.edit();
        spreferencesEditor.clear();
        spreferencesEditor.commit();
        Log.d(TAG, "cerrarSesion: SE CIERRA SESION DESTRUYENDO TODO");
    }

    //endregion

}
