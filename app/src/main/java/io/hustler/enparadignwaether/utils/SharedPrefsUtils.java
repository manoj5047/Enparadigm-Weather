package io.hustler.enparadignwaether.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.hustler.enparadignwaether.di.qualifiers.ApplicationContextQualifier;
import io.hustler.enparadignwaether.di.scopes.ApplicationScope;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;




@ApplicationScope
public class SharedPrefsUtils {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor
            editor;
    private static SharedPrefsUtils sharedPrefsUtils;

    @Inject
    public SharedPrefsUtils(@ApplicationContextQualifier Context context) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }

    public static SharedPrefsUtils getSharedPrefsUtils() {
        return sharedPrefsUtils;
    }

    public static void setSharedPrefsUtils(SharedPrefsUtils sharedPrefsUtils) {
        SharedPrefsUtils.sharedPrefsUtils = sharedPrefsUtils;
    }

    public static SharedPrefsUtils getInstance(Context context) {
        if (null == sharedPrefsUtils) {
            sharedPrefsUtils = new SharedPrefsUtils(context);
        }
        return sharedPrefsUtils;
    }

    public boolean putString(String key, String val) {
        return editor.putString(key, val).commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public boolean putBoolean(String sharedPrefsIsDriverOnline, boolean b) {
        return editor.putBoolean(sharedPrefsIsDriverOnline, b).commit();
    }

    public boolean putInt(String key, int val) {
        return editor.putInt(key, val).commit();
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public void clearAll() {
        editor.clear().commit();
    }

    public void putLong(@NotNull String key, Long val) {
        editor.putLong(key, val).commit();
    }

    public long getLong(@NotNull String key) {
        return sharedPreferences.getLong(key, -1);
    }
}


