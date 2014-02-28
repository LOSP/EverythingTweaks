package us.shandian.mod.everything.ui;

import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference;
import android.widget.Toast;
import android.view.MenuItem;
import android.os.Bundle;
import android.app.Activity;

import us.shandian.mod.everything.R;

public class BaseFragment extends PreferenceFragment implements OnPreferenceClickListener, OnPreferenceChangeListener
{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Initialize preferences
		initPreferences();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		getActivity().getActionBar().setDisplayUseLogoEnabled(false);
		getActivity().getActionBar().setDisplayShowHomeEnabled(false);
		attach();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			// Switch to root
			switchTo(EverythingSettings.FRAGMENT_SETTINGS);
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean onPreferenceClick(Preference preference) {
		return false;
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		return false;
	}
	
	protected void enableReturn() {
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	protected void disableReturn() {
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
	}
	
	protected void setTitleResource(int id) {
		getActivity().getActionBar().setTitle(getActivity().getResources().getString(id));
	}
	
	protected void registerPreferences(Preference[] prefs) {
		// Register listener to all Preferences
		for (Preference pref : prefs) {
			pref.setOnPreferenceChangeListener(this);
			pref.setOnPreferenceClickListener(this);
		}
	}
	
	protected void needsRestart() {
		// Show a message that says launcher needs restart
		Toast.makeText(getActivity(), R.string.msg_restart, 2000).show();
	}
	
	protected void switchTo(int fragment) {
		((EverythingSettings) getActivity()).switchTo(fragment);
	}
	
	protected void initPreferences() {
		
	}
	
	protected void attach() {
		
	}
}
