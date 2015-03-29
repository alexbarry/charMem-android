package net.alexbarry.charmem.android;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.alexbarry.charmem.CharEntryGroup;
import net.alexbarry.charmem.R;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.SparseArray;
import android.view.Menu;
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
	
	//private Map<Integer, CharTabFragment> charTabs = new HashMap<Integer, CharTabFragment>();
	private SparseArray<CharTabFragment> charTabs = new SparseArray<CharTabFragment>();
	
	
	
	public MainActivity() {
		for( int position=0; position<4; position++) {
			CharTabFragment fragment;
			switch(position) {
			case 0: fragment = new CharSectionSelectFragment(); break;
			case 1: fragment = new GuessNameFragment(); break;
			case 2: fragment = new CharDrawFragment(); break;
			case 3: fragment = new CharReviewFragment(); break;
			default: throw new RuntimeException();
			}
			
			this.charTabs.put(position, fragment);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
			getSupportFragmentManager(),
			this
		);

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

		final MainActivity mainActivity = this;

		mViewPager.setOnPageChangeListener( new OnPageChangeListener() {
			@Override
			public void onPageSelected(int pageNum) {
				CharTabFragment charTab = mainActivity.charTabs.get(pageNum);
				if( charTab != null && charTab.getView() != null ) { charTab.updateChar(); }
			}
			@Override public void onPageScrollStateChanged(int arg0) { }
			@Override public void onPageScrolled(int arg0, float arg1, int arg2) { }
		});
		
		
		

		
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
		private MainActivity mainActivity;


		public SectionsPagerAdapter(FragmentManager fm, MainActivity mainActivity) {
			super(fm);
			this.mainActivity = mainActivity;
		}

		@Override
		public Fragment getItem(int position) {

			Fragment fragment =  mainActivity.charTabs.get(position);
			
			Bundle args = new Bundle();
			args.putInt(SectionsPagerAdapter.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() { return 4; }

		@Override
		public CharSequence getPageTitle(int position) {
			CharTabFragment charTab = this.mainActivity.charTabs.get(position);
			
			if( charTab == null ) { return null; }
			
			return charTab.getTitle();

		}
	}

	public static CharGetter getCharGetter() {
		return MainActivity.charGetter;
	}

}
