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

	private static final int PICKFILE_RESULT_CODE = 0;

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
			//inputStream = assetManager.open("chars.txt"); // TODO should use this
			inputStream = assetManager.open("chars-full.txt"); // TODO should not use this
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		String enc = "UTF-8";
		
		List<CharEntryGroup> charEntries;
		try {
			charEntries = CharEntryFileParser.parseFile( inputStream, enc );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		MainActivity.charEntries = charEntries;


		
		
		
		

		
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
			default: fragment = new CharReviewFragment(); break; // TODO
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

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class GuessNameFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */

		public GuessNameFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.char_name_guess_fragment,
					container, false);
			//TextView dummyTextView = (TextView) rootView
			//		.findViewById(R.id.charDisplay);
			//dummyTextView.setText(Integer.toString(getArguments().getInt(
			//		SectionsPagerAdapter.ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
	
	public static class CharSectionSelectFragment extends Fragment {

		public CharSectionSelectFragment() {
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.char_section_select_fragment,
					container, false);
			
			LinearLayout selLayout = (LinearLayout) rootView.findViewById(R.id.sectionSelLayout);
			
			List<CharEntryGroup> charEntries = MainActivity.getCharEntries();
			for( CharEntryGroup charEntryGroup : charEntries ) {
				CheckBox selectionRow = new CheckBox( getActivity() );
				String text = charEntryGroup.getName();
				if( charEntryGroup.getDescription().length() > 0 ) {
					text += ": " + charEntryGroup.getDescription();
				}
				
				selectionRow.setText( text );
				selLayout.addView( selectionRow );
			}
			
			return rootView;
		}
	}
	
	public static class CharDrawFragment extends Fragment {

		public CharDrawFragment() {
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.char_draw_fragment,
					container, false);
			
			return rootView;
		}
	}
	
	public static class CharReviewFragment extends Fragment {

		private static final int PICKFILE_RESULT_CODE = 0;
		private View rootView;

		public CharReviewFragment() {
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.char_review_fragment,
					container, false);
			
			this.rootView = rootView;
			
			
			ExpandableListView charReviewList = (ExpandableListView) rootView.findViewById(R.id.charReviewExpandableList);
			
			charReviewList.setAdapter( new CharEntryAdapter( MainActivity.getCharEntries() )  );
			
			
				
			return rootView;
		}

	}

}