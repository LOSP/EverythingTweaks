package us.shandian.mod.everything.ui;

import android.preference.Preference;

import us.shandian.mod.everything.R;

public class SettingsFragment extends BaseFragment
{
	private static final String INTERFACE = "interface";
	private static final String ICONPACK = "iconpack";
	private static final String ABOUT = "about";
	
	private Preference mInterface;
	private Preference mIconPack;
	private Preference mAbout;
	
	@Override
	protected void initPreferences() {
		// Add all
		addPreferencesFromResource(R.xml.settings);
		
		// Get all the prefs
		mInterface = findPreference(INTERFACE);
		mIconPack = findPreference(ICONPACK);
		mAbout = findPreference(ABOUT);
		
		// Register
		registerPreferences(new Preference[] {
			mInterface,
			mIconPack,
			mAbout
		});
	}

	@Override
	protected void attach() {
		disableReturn();
		setTitleResource(R.string.app_name);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference == mInterface) {
			switchTo(EverythingSettings.FRAGMENT_INTERFACE);
			return true;
		} else if (preference == mIconPack) {
			switchTo(EverythingSettings.FRAGMENT_ICONPACK);
			return true;
		} else if (preference == mAbout) {
			switchTo(EverythingSettings.FRAGMENT_ABOUT);
			return true;
		} else {
			return false;
		}
	}
	
}
