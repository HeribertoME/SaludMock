package com.hmelizarraraz.saludmock.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.hmelizarraraz.saludmock.data.api.model.Affiliate;

/**
 * Created by heriberto on 26/05/17.
 */

public class SessionsPrefs {
    public static final String PREFS_NAME = "SALUDMOCK_PREFS";
    public static final String PREF_AFFILIATE_ID = "PREF_USER_ID";
    public static final String PREF_AFFILIATE_NAME = "PREF_USER_NAME";
    public static final String PREF_AFFILIATE_ADDRESS = "PREF_USER_ADDRESS";
    public static final String PREF_AFFILIATE_GENDER = "PREF_USER_GENDER";
    public static final String PREF_AFFILIATE_TOKEN = "PREF_USER_TOKEN";

    private final SharedPreferences mPrefs;
    private boolean mIsLoggedIn = false;

    private static SessionsPrefs INSTANCE;

    public static SessionsPrefs get(Context context){
        if (INSTANCE == null) {
            INSTANCE = new SessionsPrefs(context);
        }
        return INSTANCE;
    }

    private SessionsPrefs(Context context) {
        mPrefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        mIsLoggedIn = !TextUtils.isEmpty(mPrefs.getString(PREF_AFFILIATE_TOKEN, null));
    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public void saveAffiliate(Affiliate affiliate){
        if (affiliate != null) {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(PREF_AFFILIATE_ID, affiliate.getId());
            editor.putString(PREF_AFFILIATE_NAME, affiliate.getName());
            editor.putString(PREF_AFFILIATE_ADDRESS, affiliate.getAddress());
            editor.putString(PREF_AFFILIATE_GENDER, affiliate.getGender());
            editor.putString(PREF_AFFILIATE_TOKEN, affiliate.getToken());
            editor.apply();

            mIsLoggedIn = true;
        }
    }

    public void logOut() {
        mIsLoggedIn = false;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_AFFILIATE_ID, null);
        editor.putString(PREF_AFFILIATE_NAME, null);
        editor.putString(PREF_AFFILIATE_ADDRESS, null);
        editor.putString(PREF_AFFILIATE_GENDER, null);
        editor.putString(PREF_AFFILIATE_TOKEN, null);
        editor.apply();
    }
}
