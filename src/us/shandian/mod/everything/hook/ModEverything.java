package us.shandian.mod.everything.hook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XposedBridge;

import android.app.Activity;
import android.content.Context;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.os.Bundle;
import android.os.Build;

import java.lang.reflect.Method;
import java.util.HashMap;

import us.shandian.mod.everything.provider.SettingsProvider;
import us.shandian.mod.everything.helper.IconPackHelper;

public class ModEverything implements IXposedHookLoadPackage, IXposedHookZygoteInit
{
	private static String EVERYTHINGME_PACKAGE_NAME = "me.everything.launcher";
	private static String EVERYTHINGME_LAUNCHER = "me.everything.base.Launcher";
	private static String EVERYTHINGME_ICON_CACHE = "me.everything.base.IconCache";

	@Override
	public void initZygote(IXposedHookZygoteInit.StartupParam param) throws Throwable
	{
		// Initialize global preferences
		SettingsProvider.initZygote();
	}
	
	@Override
	public void handleLoadPackage(XC_LoadPackage.LoadPackageParam param) throws Throwable
	{
		// If we are in EverythingMe
		if (EVERYTHINGME_PACKAGE_NAME.equals(param.packageName)) {
			// Hook the create event
			XposedHelpers.findAndHookMethod(EVERYTHINGME_LAUNCHER, param.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
				@Override
				public void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Activity activity = (Activity) param.thisObject;
					View mDragLayer = (View) XposedHelpers.findField(param.thisObject.getClass().getSuperclass().getSuperclass(), "mDragLayer").get(param.thisObject);
					
					// Reload settings first
					SettingsProvider.reload();
					
					// Translucent status on KitKat or above
					if (Build.VERSION.SDK_INT >= 19 && 
						SettingsProvider.getBoolean(SettingsProvider.INTERFACE_GLOBAL_TRANSLUCENT_BARS, true))
					{
						activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
						mDragLayer.setFitsSystemWindows(true);
						mDragLayer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
					}
				}
			});
			
			// Hooks for IconPack
			Class<?> IconCache = XposedHelpers.findClass(EVERYTHINGME_ICON_CACHE, param.classLoader);
			XposedBridge.hookAllConstructors(IconCache, new XC_MethodHook() {
				@Override
				public void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					// Initialize params
					Context context = (Context) param.args[0];
					
					// Load icon pack
					IconPackHelper mHelper = new IconPackHelper(context);
					boolean hasIconPack = loadIconPack(mHelper);
					
					XposedHelpers.setAdditionalInstanceField(param.thisObject, "mHelper", mHelper);
					XposedHelpers.setAdditionalInstanceField(param.thisObject, "hasIconPack", hasIconPack);
				}
			});
			
			XposedHelpers.findAndHookMethod(IconCache, "getFullResIcon", ActivityInfo.class, new XC_MethodHook() {
				@Override
				public void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					// Initialize params
					ActivityInfo info = (ActivityInfo) param.args[0];
					boolean hasIconPack = (Boolean) XposedHelpers.getAdditionalInstanceField(param.thisObject, "hasIconPack");
					
					if (hasIconPack) {
						// Replace icon with icon pack's
						IconPackHelper mHelper = (IconPackHelper) XposedHelpers.getAdditionalInstanceField(param.thisObject, "mHelper");
						int iconId = mHelper.getResourceIdForActivityIcon(info);
						
						// If this icon pack includes icon for this app
						if (iconId != 0) {
							Method method = XposedHelpers.findMethodExact(EVERYTHINGME_ICON_CACHE, 
												param.thisObject.getClass().getClassLoader(), "getFullResIcon", new Class<?>[] {
								Resources.class,
								int.class
							});
							
							Drawable ret = (Drawable) method.invoke(param.thisObject, new Object[] {
								mHelper.getIconPackResources(),
								iconId
							});
							
							param.setResult(ret);
						}
					}
				}
			});
			
			XposedHelpers.findAndHookMethod(IconCache, "cacheLocked", ComponentName.class, ResolveInfo.class, HashMap.class, new XC_MethodHook() {
				@Override
				public void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Method method = XposedHelpers.findMethodExact(EVERYTHINGME_ICON_CACHE, param.thisObject.getClass().getClassLoader(), "flush", new Class<?>[0]);
					method.invoke(param.thisObject, new Object[0]);
				}
			});
		}
	}
	
	private boolean loadIconPack(IconPackHelper helper) {
		SettingsProvider.reload();
		String iconPack = SettingsProvider.getString(SettingsProvider.ICONPACK_PACKAGE, "");
		if (iconPack.equals("")) {
			// No icon pack!
			return false;
		} else {
			helper.loadIconPack(iconPack);
			return true;
		}
	}

}
