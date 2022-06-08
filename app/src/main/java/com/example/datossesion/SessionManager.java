package com.example.datossesion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "PixelPro";
    private static final String IS_LOGGED = "Identificado";
    public static final String KEY_NAME = "Nombre";
    public static final String KEY_EMAIL = "Email";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String name, String email){
        editor.putBoolean( IS_LOGGED, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public HashMap<String, String> getUserPref(){
        HashMap <String, String> user = new HashMap <String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        return user;
    }

    public void loginStatus(){
        if(!this.isLogged()){
            Intent intent = new Intent(_context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //_context.startActivity(intent);
        }
    }

    public boolean isLogged() {
        return pref.getBoolean(IS_LOGGED, false);
    }

    public void logOut(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(_context, CloseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(intent);
    }
}
