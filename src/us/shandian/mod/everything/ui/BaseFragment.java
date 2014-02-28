package us.shandian.mod.everything.ui;

import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference;
import android.view.MenuItem;
import android.os.Bundle;
import android.app.Activity;

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
	public boolean onPreferenceChange(Preference preferencr, Object newValue) {
		return false;
	}
	
	protected void enableReturn() {
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	protected void setTitleResource(int id) {
		getActivity().getActionBar().setTitle(getActivity().getResources().getString(id));
	}
	
	protected void switchTo(int fragment) {
		((EverythingSettings) getActivity()).switchTo(fragment);
	}
	
	protected void initPreferences() {
		
	}
	
	protected void attach() {
		
	}
}
