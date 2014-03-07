package us.shandian.mod.everything.ui;

import android.app.Activity;
import android.net.Uri;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.preference.Preference;

import us.shandian.mod.everything.R;

public class AboutFragment extends BaseFragment
{
	private static final String ABOUT_VERSION = "about_version";
	private static final String ABOUT_WEBSITE = "about_website";
	
	private Preference mVersion;
	private Preference mWebsite;
	
	@Override
	protected void initPreferences() {
		// Add all
		addPreferencesFromResource(R.xml.settings_about);
		
		// Find them
		mVersion = findPreference(ABOUT_VERSION);
		mWebsite = findPreference(ABOUT_WEBSITE);
		
		// Register
		registerPreferences(new Preference[] {
			mVersion,
			mWebsite
		});
		
		// Show my version
		String version = "0.0.0";
		Activity activity = getActivity();
		String pkgName = activity.getApplication()
							.getApplicationInfo().packageName;
		PackageManager pm = activity.getPackageManager();
		
		try {
			version = pm.getPackageInfo(pkgName, 0).versionName;
		} catch (Exception e) {
			// So?
		}
		
		mVersion.setSummary(version);
	}
	
	@Override
	protected void attach() {
		enableReturn();
		setTitleResource(R.string.about_title);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference == mWebsite) {
			Intent i = new Intent();
			i.setAction(Intent.ACTION_VIEW);
			i.setData(Uri.parse(mWebsite.getSummary().toString()));
			startActivity(i);
			return true;
		} else {
			return false;
		}
	}
}
