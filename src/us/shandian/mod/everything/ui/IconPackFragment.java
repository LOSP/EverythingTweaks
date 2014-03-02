package us.shandian.mod.everything.ui;

import android.app.AlertDialog;
import android.preference.Preference;
import android.preference.ListPreference;

import java.util.ArrayList;

import us.shandian.mod.everything.R;
import us.shandian.mod.everything.helper.IconPackHelper;
import us.shandian.mod.everything.provider.SettingsProvider;

public class IconPackFragment extends BaseFragment
{
	private static final String ICONPACK_INTRODUCTION = "iconpack_introduction";
	
	private ListPreference mPackage;
	private Preference mIntroduction;
	
	private ArrayList<IconPackHelper.IconPackInfo> mAppList;
	
	@Override
	protected void initPreferences() {
		// Add all
		addPreferencesFromResource(R.xml.settings_iconpack);
		
		// Get the preferences
		mPackage = (ListPreference) findPreference(SettingsProvider.ICONPACK_PACKAGE);
		mIntroduction = findPreference(ICONPACK_INTRODUCTION);
		
		// Register
		registerPreferences(new Preference[] {
			mPackage,
			mIntroduction
		});
		
		// Initialize AppList
		mAppList = new ArrayList<IconPackHelper.IconPackInfo>(
						IconPackHelper.getSupportedPackages(getActivity()).values());
		mAppList.add(0, new IconPackHelper.IconPackInfo(
						getActivity().getResources().getString(R.string.iconpack_default), null, ""));
		String current = SettingsProvider.getString(getActivity(), SettingsProvider.ICONPACK_PACKAGE, "");
		int currentId = 0;
		ArrayList<CharSequence> entries = new ArrayList<CharSequence>();
		ArrayList<CharSequence> entryValues = new ArrayList<CharSequence>();
		for (IconPackHelper.IconPackInfo info : mAppList) {
			int index = mAppList.indexOf(info);
			entries.add(info.label);
			entryValues.add(String.valueOf(index));
			
			if (current.equals(info.packageName)) {
				currentId = index;
			}
		}
		
		CharSequence[] entriesArray = new CharSequence[entries.size()];
		mPackage.setEntries(entries.toArray(entriesArray));
		CharSequence[] entryValuesArray = new CharSequence[entryValues.size()];
		mPackage.setEntryValues(entryValues.toArray(entryValuesArray));
		mPackage.setValueIndex(currentId);
		mPackage.setSummary(entriesArray[currentId]);
		
	}

	@Override
	protected void attach() {
		enableReturn();
		setTitleResource(R.string.iconpack_title);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (preference == mPackage) {
			// Set the value
			int index = Integer.valueOf(newValue.toString());
			mPackage.setSummary(mAppList.get(index).label);
			if (index == 0) {
				SettingsProvider.remove(getActivity(), SettingsProvider.ICONPACK_PACKAGE);
			} else {
				SettingsProvider.putString(getActivity(), 
						SettingsProvider.ICONPACK_PACKAGE, mAppList.get(index).packageName);
			}
			
			needsRestart();
			
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference == mIntroduction) {
			new  AlertDialog.Builder(getActivity())
							.setTitle(R.string.iconpack_introduction)
							.setMessage(R.string.iconpack_introduction_text)
							.create()
							.show();
			return true;
		} else {
			return false;
		}
	}
	
}
