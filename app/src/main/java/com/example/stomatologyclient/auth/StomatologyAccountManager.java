package com.example.stomatologyclient.auth;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.stomatologyclient.activity.ActivityLogin;
import com.example.stomatologyclient.activity.RegisterActivity;

import retrofit2.Response;

/**
 * Менеджер аккаунтов
 */
public class StomatologyAccountManager
{
    //Activity mContext;

    //Типы ролей
    public final static int ROLE_PATIENT = 1;
    public final static int ROLE_DOCTOR = 2;
    public final static int ROLE_TECHNICAN = 3;
    public final static int ROLE_ADMIN = 4;

    //Для Preferences
    private final static String TOKEN = "token";
    private final static String ROLE = "role";

    /*public StomatologyAccountManager(Activity context) {
        mContext = context;
    }*/


    /**
     * Запускает активити регистрации
     * @param role
     */
    public static Intent getRegistrationIntetn(Context mContext, int role)
    {

        final Intent intent = new Intent(mContext, RegisterActivity.class);
        intent.putExtra(RegisterActivity.EXTRA_TOKEN_TYPE, role);
        //mContext.startActivity(intent);

        return intent;
    }

    /**
     * Сохраняет токен
     * @param token
     * @param role
     */
    public static void saveToken(Activity mContext, String token, String role)
    {
        SharedPreferences token_pr =  mContext.getSharedPreferences(TOKEN,Context.MODE_PRIVATE);
        SharedPreferences role_pr =  mContext.getSharedPreferences(ROLE,Context.MODE_PRIVATE);

        SharedPreferences shpr =  mContext.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =shpr.edit();
        editor.putString(TOKEN, token);

        if(role.equals("admin"))
            editor.putInt(ROLE,ROLE_ADMIN);
        else if(role.equals("patient"))
            editor.putInt(ROLE,ROLE_PATIENT);
        else if(role.equals("doctor"))
            editor.putInt(ROLE,ROLE_DOCTOR);
        else if(role.equals("dental_technican"))
            editor.putInt(ROLE,ROLE_TECHNICAN);

        editor.commit();
    }

    /**
     * Получаем токен, если нет - открывем активити для логининга
     * @return
     */
    public static String getAuthToken(Activity mContext)
    {
        SharedPreferences shpr =  mContext.getPreferences(Context.MODE_PRIVATE);
        String token = shpr.getString(TOKEN, null);

        if(token==null)
            showLoginActivity(mContext);

        return  token;
    }

    /**
     * Показывает активити регистрации
     */
    public static void showLoginActivity(Context mContext)
    {
        Intent intent = new Intent(mContext, ActivityLogin.class);
        mContext.startActivity(intent);
    }

    /**
     * Возвращает роль текущего пользователя или -1
     * если не зарегестрирован
     * @return
     */
    public static int GetAccountType(Activity mContext)
    {
        SharedPreferences shpr =  mContext.getPreferences(Context.MODE_PRIVATE);
        return shpr.getInt(TOKEN,-1);
    }

}
