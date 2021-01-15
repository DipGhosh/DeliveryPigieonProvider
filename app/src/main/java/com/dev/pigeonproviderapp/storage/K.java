package com.dev.pigeonproviderapp.storage;

import android.util.Log;

public class K {
    public static final String id = "id";
    public static final String isLoggedIn = "isLoggedIn";
    public static final String isMonthOfDay = "isMonthOfDay";
    public static final String email = "email";
    public static final String phone = "phone";
    public static final String UserId = "UserId";


    public static int getid() {
        if (SharedPreferenceUtils.getInt(K.id, 0) != 0) {
            Log.e("id", "" + SharedPreferenceUtils.getInt(K.id, 0));
            return SharedPreferenceUtils.getInt(K.id, 0);
        }
        return -1;
    }

    public static int getUserId() {
        if (SharedPreferenceUtils.getInt(K.UserId, 0) != 0) {
            Log.e("UserId", "" + SharedPreferenceUtils.getInt(K.UserId, 0));
            return SharedPreferenceUtils.getInt(K.UserId, 0);
        }
        return -1;
    }



    public static boolean isLoggedIn() {
        if (SharedPreferenceUtils.getBoolean(K.isLoggedIn, false)) {
            Log.e("isLogin", "" + SharedPreferenceUtils.getBoolean(K.isLoggedIn, false));
            return SharedPreferenceUtils.getBoolean(K.isLoggedIn, false);
        }
        return false;
    }
    public static String getEmail() {
        if (SharedPreferenceUtils.getString(K.email, "") != null) {
            Log.e("email", "" + SharedPreferenceUtils.getString(K.email, ""));
            return SharedPreferenceUtils.getString(K.email, "");
        }
        return "";
    }
    public static String getPhone() {
        if (SharedPreferenceUtils.getString(K.phone, "") != null) {
            Log.e("phone", "" + SharedPreferenceUtils.getString(K.phone, ""));
            return SharedPreferenceUtils.getString(K.phone, "");
        }
        return "";
    }
}
