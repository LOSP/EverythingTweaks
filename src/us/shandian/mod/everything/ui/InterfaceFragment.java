package us.shandian.mod.everything.ui;

import android.os.Build;
import android.preference.Preference;
import android.preference.CheckBoxPreference;

import us.shandian.mod.everything.R;
import us.shandian.mod.everything.provider.SettingsProvider;

public class InterfaceFragment extends BaseFragment
{
	private CheckBoxPreference mTransBars;
	
	@Override
	protected void initPreferences() {
		// Add all
		addPreferencesFromResource(R.xml.settings_interface);
		
		// Get the Preferences
		mTransBars = (CheckBoxPreference) findPreference(SettingsProvider.INTERFACE_GLOBAL_TRANSLUCENT_BARS);
		
		// Register
		registerPreferences(new Preference[] {
			mTransBars
		});
		
		// Initialize default value
		boolean transBars = SettingsProvider.getBoolean(getActivity(), 
									SettingsProvider.INTERFACE_GLOBAL_TRANSLUCENT_BARS,
									true);
		mTransBars.setChecked(transBars);
		mTransBars.setEnabled(Build.VERSION.SDK_INT >= 19);
	}

	@Override
	protected void attach() {
		enableReturn();
		setTitleResource(R.string.interface_title);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference == mTransBars) {
			SettingsProvider.putBoolean(getActivity(), 
					SettingsProvider.INTERFACE_GLOBAL_TRANSLUCENT_BARS,
					mTransBars.isChecked());
			needsRestart();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (preference == mTransBars) {
			SettingsProvider.putBoolean(getActivity(), 
					SettingsProvider.INTERFACE_GLOBAL_TRANSLUCENT_BARS,
					(Boolean) newValue);
			needsRestart();
			return true;
		} else {
			return false;
		}
	}
	
}
