package mx.capitalsocial.capitalsocial.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.facebook.login.LoginManager;

/**
 * Created by miguegar
 */

public class Utils {

    /**
     * COMPROBAR CONEXIÓN
     *
     */
    public static boolean comprobarConexion(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    /**
     * GUARDAR TOKEN LOGIN
     *
     */
    @SuppressLint("NewApi")
    public static void guardarTokenUsuario(Context context, String nombre, String token)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("token_usuario", token);
        editor.putString("nombre_usuario", nombre);
        editor.apply();
    }

    /**
     * CONSULTAR TOKEN LOGIN
     *
     */
    public static String getTokenUsuario(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String token = preferences.getString("token_usuario", "");
        String nombre = preferences.getString("nombre_usuario", "");

        if (token != null || token.compareTo("") == 0) {
            return nombre;
        }
        return "";
    }

    /**
     * LOGOUT
     *
     */
    public static void logOut(Context context)
    {
        LoginManager.getInstance().logOut();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove("token_usuario").commit();
        preferences.edit().remove("nombre_usuario").commit();
    }
}
