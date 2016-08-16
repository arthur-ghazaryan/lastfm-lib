//Copyright 2016 Arthur Ghazaryan

//        Licensed under the Apache License, Version 2.0 (the "License");
//        you may not use this file except in compliance with the License.
//        You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//        Unless required by applicable law or agreed to in writing, software
//        distributed under the License is distributed on an "AS IS" BASIS,
//        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//        See the License for the specific language governing permissions and
//        limitations under the License.


package com.ag.lfm;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ag.lfm.api.httpClient.AuthOperation;

/**
 * Class for initialization
 */
@SuppressWarnings("unused,unchecked")
public class Lfm {

    private static final String SESSION_KEY = "session_key";
    private static final String SESSION_USERNAME = "username";
    private static final String SESSION_SUBSCRIBER = "subscriber";

    /**
     * API key.
     */
    private static String api_key;

    /**
     * API account secret.
     */
    private static String secret;

    /**
     * Application context.
     */
    private static Context context;


    public enum LoginState {
        LoggedOut,
        LoggedIn
    }

    /**
     * Method for API key initialization.
     */
    public static void initialize(Context context) {
        Lfm.context = context;
        if (!(context instanceof Application)) {
            if (context == null) {
                throw new NullPointerException("Application context cannot be null");
            } else {
                throw new RuntimeException("Lfm.initialize(Context) must be call from Application#onCreate()");
            }
        } else {
            Lfm.api_key = getStringResByName(context, "api_key");
            if (api_key == null) {
                throw new RuntimeException("String <string name=\\\"api_key\\\">your_api_key</string> did not find in your resources.xml");
            }
            if (getStringFromPref(context, SESSION_KEY, null) != null) {
                Session.sessionkey = getStringFromPref(context, SESSION_KEY, null);
                Session.username = getStringFromPref(context, SESSION_USERNAME, null);
                Session.subscriber = getIntFromPref(context,SESSION_SUBSCRIBER,0);
            }
        }

    }

    private static String getStringResByName(Context ctx, String aString) {
        int resId = ctx.getResources().getIdentifier(aString, "string", ctx.getPackageName());
        try {
            return ctx.getString(resId);
        } catch (Exception e) {
            return null;
        }
    }

    public static void wakeUpSession(LfmCallback<LoginState> callback) {
        if (getStringFromPref(context, SESSION_KEY, null) == null) {
            callback.onResult(LoginState.LoggedOut);
        } else {
            callback.onResult(LoginState.LoggedIn);
        }
    }

    public static void initializeWithSecret(Context context) {
        initialize(context);
        Lfm.secret = getStringResByName(context, "secret");
        if (secret == null) {
            throw new RuntimeException("String <string name=\\\"secret\\\">your_api_secret</string> did not find in your resources.xml");
        }
    }

    public static String getApiKey() {
        return api_key;
    }

    public static String getSecret() {
        return secret;
    }

    /**
     * Starts authorization process.
     *
     * @param username Username.
     * @param password User password.
     * @param callback Authorization callback.
     */

    public static void login(String username, String password, LfmCallback<Session> callback) {
        LfmParameters params = new LfmParameters();
        params.put("username", username);
        params.put("password", password);
        params.put("api_sig", genMethodSignature(username, password));
        new AuthOperation(params).execute(callback);

    }

    private static void storeStringToPref(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static void storeIntToPref(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private static String getStringFromPref(Context context, String key, String def) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, def);
    }

    private static int getIntFromPref(Context context, String key, int def) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, def);
    }

    private static String genMethodSignature(String username, String password) {
        StringBuilder builder = new StringBuilder();
        builder
                .append("api_key")
                .append(Lfm.api_key)
                .append("methodauth.getMobileSessionpassword")
                .append(password).append("username")
                .append(username)
                .append(secret);
        Log.e("MobileSession", com.ag.lfm.util.LfmUtil.md5Custom(builder.toString()));
        return com.ag.lfm.util.LfmUtil.md5Custom(builder.toString());
    }

    public static void saveSession() {
        storeStringToPref(context, SESSION_KEY, Session.sessionkey);
        storeStringToPref(context, SESSION_USERNAME, Session.username);
        storeIntToPref(context,SESSION_SUBSCRIBER,Session.subscriber);
    }

    /**
     * Authorization callback.
     */
    public static abstract class LfmCallback<T> {

        /**
         * Called if user successfully logged in.
         */
        public abstract void onResult(T result);

        /**
         * Called immediately if there was authorization error, or  if there was an HTTP error.
         *
         * @param error error for LfmCallback
         */
        public abstract void onError(LfmError error);

    }

}
