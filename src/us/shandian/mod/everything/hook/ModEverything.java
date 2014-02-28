package us.shandian.mod.everything.hook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.os.Bundle;
import android.os.Build;

import us.shandian.mod.everything.provider.SettingsProvider;

public class ModEverything implements IXposedHookLoadPackage, IXposedHookZygoteInit
{
	private static String EVERYTHINGME_PACKAGE_NAME = "me.everything.launcher";
	private static String EVERYTHINGME_LAUNCHER = "me.everything.base.Launcher";

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
		}
	}

}
