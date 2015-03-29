package net.alexbarry.charmem.android;

import net.alexbarry.charmem.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class CharReviewFragment extends CharTabFragment {

	public CharReviewFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.char_review_fragment,
				container, false);

		
		
		ExpandableListView charReviewList = (ExpandableListView) rootView.findViewById(R.id.charReviewExpandableList);
		
		charReviewList.setAdapter( new CharEntryAdapter( MainActivity.getCharEntries() )  );
		
		
			
		return rootView;
	}

	@Override
	public void updateChar() { 	}

	@Override
	public String getTitle() { return "Review Chars"; }

}