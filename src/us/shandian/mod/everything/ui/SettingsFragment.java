package us.shandian.mod.everything.ui;

import android.preference.Preference;

import us.shandian.mod.everything.R;

public class SettingsFragment extends BaseFragment
{
	private static final String INTERFACE = "interface";
	
	private Preference mInterface;
	
	@Override
	protected void initPreferences() {
		// Add all
		addPreferencesFromResource(R.xml.settings);
		
		// Get all the prefs
		mInterface = findPreference(INTERFACE);
		
		// Register
		registerPreferences(new Preference[] {
			mInterface
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
		} else {
			return false;
		}
	}
	
}
