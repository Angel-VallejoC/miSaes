package me.angelvc.misaes.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Pair;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.Key;
import java.util.concurrent.atomic.AtomicReference;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import me.angelvc.misaes.R;
import me.angelvc.saes.scraper.SAEScraper;


public class AppPreferences {

    public static boolean getLoginStatus(Context context) {
        return getAppPreferences(context).getBoolean(
                context.getString(R.string.app_login_status), false
        );
    }

    public static void setLoginStatus(Context context, boolean status) {
        getAppPreferences(context)
                .edit().putBoolean(
                context.getString(R.string.app_login_status),
                status
        ).apply();

    }

    public static void saveScraperInstance(Context context, SAEScraper scraper) {
        Gson gson = new
                GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes field) {
                        return field.getDeclaringClass() == SAEScraper.class && field.getName().equals("workingDocument");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        getAppPreferences(context).edit().putString(
                context.getString(R.string.app_scraper_instance), gson.toJson(scraper)
        ).apply();
    }

    public static void deleteScraperInstance(Context context) {
        getAppPreferences(context).edit().remove(
                context.getString(R.string.app_scraper_instance)
        ).apply();
    }

    public static String getScraperJson(Context context) {
        return getAppPreferences(context)
                .getString(context.getString(R.string.app_scraper_instance), null);
    }

    public static SAEScraper getScraperInstance(String json) throws Exception {
        Gson gson = new
                GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes field) {
                        return field.getDeclaringClass() == SAEScraper.class && field.getName().equals("workingDocument");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        SAEScraper scraper = gson.fromJson(json, SAEScraper.class);
        AtomicReference<Pair<Boolean, Exception>> result = new AtomicReference<>();
        Thread reloadScraperInstance = new Thread(() -> {
            try {
                scraper.reload();
                result.set(new Pair<>(true, null));
            } catch (Exception e) {
                result.set(new Pair<>(false, e));
            }
        });
        reloadScraperInstance.start();
        while (reloadScraperInstance.isAlive()) ;

        if (!result.get().first)
            throw result.get().second;

        return scraper;
    }

    public static String getSelectedSchool(Context context) {
        return getAppPreferences(context)
                .getString(context.getString(R.string.login_school_preference), null);
    }

    public static SharedPreferences getAppPreferences(Context context) {
        return context.getSharedPreferences(context.getString(R.string.app_preferences_key),
                Context.MODE_PRIVATE);
    }

    public static void setRememberMeStatus(Context context, boolean status){
        getAppPreferences(context).edit()
                .putBoolean(context.getString(R.string.login_rememberMe_preference), status)
                .apply();
    }

    public static boolean getRememberMeStatus(Context context){
        return getAppPreferences(context).getBoolean(context.getString(R.string.login_rememberMe_preference), false);
    }

    public static void saveUserAndPassword(Context context, String user, String password){
        SharedPreferences preferences = getAppPreferences(context);
        String userEncrypted = encrypt(context.getString(R.string.encryption_key), user);
        String passwordEncrypted = encrypt(context.getString(R.string.encryption_key), password);

        preferences.edit().putString(context.getString(R.string.login_user_preference), userEncrypted)
                .putString(context.getString(R.string.login_password_preference), passwordEncrypted)
                .apply();
    }

    public static void removeUserAndPassword(Context context){
            SharedPreferences preferences = getAppPreferences(context);
            preferences.edit().remove(context.getString(R.string.login_user_preference))
                    .remove(context.getString(R.string.login_password_preference))
                    .apply();
    }

    public static String getUser(Context context){
         String userEncrypted = getAppPreferences(context).getString(context.getString(R.string.login_user_preference), null);
         return userEncrypted == null ? "" : decrypt(context.getString(R.string.encryption_key), userEncrypted);
    }

    public static String getPassword(Context context){
        String passwordEncrypted = getAppPreferences(context).getString(context.getString(R.string.login_password_preference), null);
        return passwordEncrypted == null ? "" : decrypt(context.getString(R.string.encryption_key), passwordEncrypted);
    }

    private static String encrypt(String keyValue, String toEncrypt){
        Key key = new SecretKeySpec(keyValue.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());
            return new String( android.util.Base64.encode(encrypted, Base64.DEFAULT) );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String decrypt(String keyValue, String toDecrypt){
        Key key = new SecretKeySpec(keyValue.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(Base64.decode(toDecrypt, Base64.DEFAULT));
            return new String(decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
