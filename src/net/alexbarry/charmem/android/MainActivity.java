package net.alexbarry.charmem.android;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

import net.alexbarry.charmem.CharEntryGroup;
import net.alexbarry.charmem.R;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.alexbarry.charmem.*;

public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private static List<CharEntryGroup> charEntries;
	
	private static CharGetter charGetter;
	
	public static List<CharEntryGroup> getCharEntries() { return charEntries; }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		AssetManager assetManager = getAssets();
		InputStream inputStream;
		try {
			inputStream = assetManager.open("chars.txt"); 
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		String enc = "UTF-8";
		
		List<CharEntryGroup> charEntries;
		try {
			charEntries = CharEntryFileParser.parseFile( inputStream, enc );
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		MainActivity.charEntries = charEntries;
		
		MainActivity.charGetter = new CharGetter(charEntries );


		
		
		
		

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		
		public static final String ARG_SECTION_NUMBER = "section_number";


		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment;
			switch(position) {
			case 0: fragment = new CharSectionSelectFragment(); break;
			case 1: fragment = new GuessNameFragment(); break;
			case 2: fragment = new CharDrawFragment(); break;
			default: fragment = new CharReviewFragment(); break;
			}
			Bundle args = new Bundle();
			args.putInt(SectionsPagerAdapter.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() { return 4; }

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.sec_sectionSel);//.toUpperCase(l);
			case 1:
				return getString(R.string.sec_guessName);//.toUpperCase(l);
			case 2:
				return getString(R.string.sec_guessChar);//.toUpperCase(l);
			case 3:
				return getString(R.string.sec_reviewChars);//.toUpperCase(l);
			}
			return null;
		}
	}

	public static CharGetter getCharGetter() {
		return MainActivity.charGetter;
	}

}
