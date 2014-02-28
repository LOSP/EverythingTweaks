package us.shandian.mod.everything.provider;

import android.content.Context;
import android.content.SharedPreferences;

import de.robv.android.xposed.XSharedPreferences;

public class SettingsProvider
{
	public static final String PACKAGE_NAME = "us.shandian.mod.everything";
	
	public static final String PREFS = "preferences";
	
	public static final String INTERFACE_GLOBAL_TRANSLUCENT_BARS = "interface_global_translucent_bars";
	
	private static XSharedPreferences mPrefs;
	
	public static void initZygote() {
		mPrefs = new XSharedPreferences(PACKAGE_NAME, PREFS);
		mPrefs.makeWorldReadable();
	}
	
	public static int getInt(Context context, String key, int defValue) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_WORLD_READABLE);
		return prefs.getInt(key, defValue);
	}
	
	public static int getInt(String key, int defValue) {
		return mPrefs.getInt(key, defValue);
	}
	
	public static float getFloat(Context context, String key, float defValue) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_WORLD_READABLE);
		return prefs.getFloat(key, defValue);
	}

	public static float getFloat(String key, float defValue) {
		return mPrefs.getFloat(key, defValue);
	}
	
	public static boolean getBoolean(Context context, String key, boolean defValue) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_WORLD_READABLE);
		return prefs.getBoolean(key, defValue);
	}

	public static boolean getBoolean(String key, boolean defValue) {
		return mPrefs.getBoolean(key, defValue);
	}
	
	public static String getString(Context context, String key, String defValue) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_WORLD_READABLE);
		return prefs.getString(key, defValue);
	}

	public static String getString(String key, String defValue) {
		return mPrefs.getString(key, defValue);
	}
	
	public static void putInt(Context context, String key, int value) {
		context.getSharedPreferences(PREFS, Context.MODE_WORLD_READABLE).edit().putInt(key, value).commit();
	}
	
	public static void putFloat(Context context, String key, float value) {
		context.getSharedPreferences(PREFS, Context.MODE_WORLD_READABLE).edit().putFloat(key, value).commit();
	}
	
	public static void putBoolean(Context context, String key, boolean value) {
		context.getSharedPreferences(PREFS, Context.MODE_WORLD_READABLE).edit().putBoolean(key, value).commit();
	}
	
	public static void putString(Context context, String key, String value) {
		context.getSharedPreferences(PREFS, Context.MODE_WORLD_READABLE).edit().putString(key, value).commit();
	}
	
	public static void remove(Context context, String key) {
		context.getSharedPreferences(PREFS, Context.MODE_WORLD_READABLE).edit().remove(key).commit();
	}
	
	public static void reload() {
		mPrefs.reload();
	}
}
