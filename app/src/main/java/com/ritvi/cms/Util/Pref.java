package com.ritvi.cms.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ritvi.cms.pojo.user.UserProfilePOJO;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sunil on 26-05-2017.
 */

public class Pref {

    private static final String PrefDB = "momyt";


    public static final String FCM_REGISTRATION_TOKEN = "fcm_registration_token";


    public static void SetStringPref(Context context, String KEY, String Value) {
        try {
            SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(KEY, Value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String GetStringPref(Context context, String KEY, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getString(KEY, defValue);
    }

    public static void SetBooleanPref(Context context, String KEY, boolean Value) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY, Value);
        editor.commit();
    }

    public static int GetIntPref(Context context, String KEY, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getInt(KEY, defValue);
    }

    public static void SetIntPref(Context context, String KEY, int Value) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY, Value);
        editor.commit();
    }

    public static boolean GetBooleanPref(Context context, String KEY, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getBoolean(KEY, defValue);
    }

    public static void clearSharedPreference(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public static void SaveDeviceToken(Context context, String Value) {
        SharedPreferences sp = context.getSharedPreferences("momytdevicetoken.txt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("devicetoken", Value);
        editor.commit();
    }

    public static String GetDeviceToken(Context context, String defValue) {
        SharedPreferences sp = context.getSharedPreferences("momytdevicetoken.txt", MODE_PRIVATE);
        return sp.getString("devicetoken", defValue);
    }

    public static void SaveUserProfile(Context context, UserProfilePOJO userProfilePOJO) {
        if (userProfilePOJO != null) {
            SetStringPref(context, StringUtils.USER_ID, userProfilePOJO.getUserId());
            SetStringPref(context, StringUtils.USER_PROFILE_ID, userProfilePOJO.getUserProfileId());
            SetStringPref(context, StringUtils.USER_NAME, userProfilePOJO.getUserName());
            SetStringPref(context, StringUtils.USER_FULL_NAME, userProfilePOJO.getUserFullName());
            SetStringPref(context, StringUtils.USER_IMAGE, userProfilePOJO.getUserImage());
            SetStringPref(context, StringUtils.USER_EMAIL, userProfilePOJO.getUserEmail());
            SetStringPref(context, StringUtils.USER_PHONE_COUNTRY, userProfilePOJO.getUserPhonecountry());
            SetStringPref(context, StringUtils.USER_MOBILE, userProfilePOJO.getUserMobile());
            SetStringPref(context, StringUtils.USER_CREATED_ON, userProfilePOJO.getUserCreatedon());
            SetStringPref(context, StringUtils.USER_ADDRESS, userProfilePOJO.getUserAddress());
            SetStringPref(context, StringUtils.USER_CITY, userProfilePOJO.getUserCity());
            SetStringPref(context, StringUtils.USER_STATE, userProfilePOJO.getUserState());
            SetStringPref(context, StringUtils.USER_DEVICE_TOKEN, userProfilePOJO.getUserDeviceToken());
            SetStringPref(context, StringUtils.USER_DATE_OF_BIRTH, userProfilePOJO.getUserDateOfBirth());
            SetStringPref(context, StringUtils.USER_GENDER, userProfilePOJO.getUserGender());
            SetStringPref(context, StringUtils.USER_LOGIN_STATUS, userProfilePOJO.getUserLoginStatus());
        }
    }

    public static UserProfilePOJO GetUserProfile(Context context) {

        UserProfilePOJO userProfilePOJO = new UserProfilePOJO(
                GetStringPref(context, StringUtils.USER_ID, ""),
                GetStringPref(context, StringUtils.USER_PROFILE_ID, ""),
                GetStringPref(context, StringUtils.USER_NAME, ""),
                GetStringPref(context, StringUtils.USER_FULL_NAME, ""),
                GetStringPref(context, StringUtils.USER_IMAGE, ""),
                GetStringPref(context, StringUtils.USER_EMAIL, ""),
                GetStringPref(context, StringUtils.USER_PHONE_COUNTRY, ""),
                GetStringPref(context, StringUtils.USER_MOBILE, ""),
                GetStringPref(context, StringUtils.USER_CREATED_ON, ""),
                GetStringPref(context, StringUtils.USER_ADDRESS, ""),
                GetStringPref(context, StringUtils.USER_CITY, ""),
                GetStringPref(context, StringUtils.USER_STATE, ""),
                GetStringPref(context, StringUtils.USER_DEVICE_TOKEN, ""),
                GetStringPref(context, StringUtils.USER_DATE_OF_BIRTH, ""),
                GetStringPref(context, StringUtils.USER_GENDER, ""),
                GetStringPref(context, StringUtils.USER_LOGIN_STATUS, "")
        );

        return userProfilePOJO;
    }

}
