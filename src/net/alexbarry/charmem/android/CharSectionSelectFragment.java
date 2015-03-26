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

public class CharSectionSelectFragment extends Fragment {

	public CharSectionSelectFragment() {
	}
	
	/** Value of the section selection checkboxes initially. */
	public static final boolean sectionEnabledDefaultVal = true;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.char_section_select_fragment,
				container, false);
		
		LinearLayout selLayout = (LinearLayout) rootView.findViewById(R.id.sectionSelLayout);
		
		List<CharEntryGroup> charEntries = MainActivity.getCharEntries();
		for( CharEntryGroup charEntryGroup : charEntries ) {
			CheckBox selectionRow = new CheckBox( getActivity() );
			selectionRow.setChecked( sectionEnabledDefaultVal );
			
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