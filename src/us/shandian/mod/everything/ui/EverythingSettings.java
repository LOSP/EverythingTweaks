package us.shandian.mod.everything.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.view.MenuItem;
import android.os.Bundle;

import java.util.ArrayList;

import us.shandian.mod.everything.R;

public class EverythingSettings extends Activity
{
	// Constants
	public static final int FRAGMENT_SETTINGS = 0;
	public static final int FRAGMENT_INTERFACE = 1;
	public static final int FRAGMENT_ICONPACK = 2;
	
	private FragmentManager mManager;
	
	private ArrayList<BaseFragment> mFragments = new ArrayList<BaseFragment>();
	
	private int mCurrent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Initialize the view
		setContentView(R.layout.settings);
		
		mManager = getFragmentManager();
		
		// Create all fragments
		mFragments.add(FRAGMENT_SETTINGS, new SettingsFragment());
		mFragments.add(FRAGMENT_INTERFACE, new InterfaceFragment());
		mFragments.add(FRAGMENT_ICONPACK, new IconPackFragment());
		
		// Switch to default
		switchTo(FRAGMENT_SETTINGS);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return mFragments.get(mCurrent).onOptionsItemSelected(item);
	}
	
	public void switchTo(int fragment) {
		mManager.beginTransaction()
				.replace(R.id.container, mFragments.get(fragment))
				.commit();
		
		mCurrent = fragment;
	}
	
}
