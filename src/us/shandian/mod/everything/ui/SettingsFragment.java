package us.shandian.mod.everything.ui;

import android.preference.Preference;

import us.shandian.mod.everything.R;

public class SettingsFragment extends BaseFragment
{

	@Override
	protected void initPreferences() {
		// Add all
		addPreferencesFromResource(R.xml.settings);
	}

	@Override
	protected void attach() {
		setTitleResource(R.string.app_name);
	}
	
}
