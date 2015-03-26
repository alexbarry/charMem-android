package net.alexbarry.charmem.android;

import java.util.List;

import net.alexbarry.charmem.CharEntryGroup;
import net.alexbarry.charmem.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class GuessNameFragment extends Fragment {

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