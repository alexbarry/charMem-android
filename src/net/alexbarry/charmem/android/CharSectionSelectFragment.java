package net.alexbarry.charmem.android;

import java.util.ArrayList;
import java.util.List;

import net.alexbarry.charmem.CharEntryGroup;
import net.alexbarry.charmem.CharGetter;
import net.alexbarry.charmem.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class CharSectionSelectFragment extends Fragment {

	private LinearLayout selLayout;
	private Button sel_btn_confirm;
	private Button btnSelectAll;
	private Button btnClearAll;

	public CharSectionSelectFragment() {
	}
	
	/** Value of the section selection checkboxes initially. */
	public static final boolean sectionEnabledDefaultVal = true;
	
	
	private List<CheckBox> checkBoxes = new ArrayList<CheckBox>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.char_section_select_fragment,
				container, false);
		
		this.selLayout = (LinearLayout) rootView.findViewById(R.id.sectionSelLayout);
		
		List<CharEntryGroup> charEntries = MainActivity.getCharEntries();
		for( CharEntryGroup charEntryGroup : charEntries ) {
			CheckBox selectionRow = new CheckBox( getActivity() );
			this.checkBoxes.add( selectionRow );
			selectionRow.setChecked( sectionEnabledDefaultVal );
			
			String text = charEntryGroup.getName();
			if( charEntryGroup.getDescription().length() > 0 ) {
				text += ": " + charEntryGroup.getDescription();
			}
			
			selectionRow.setText( text );
			selLayout.addView( selectionRow );
		}
		
		this.sel_btn_confirm = (Button) rootView.findViewById( R.id.sel_btn_confirm );
		this.btnSelectAll = (Button) rootView.findViewById( R.id.sel_selectAllBtn );
		this.btnClearAll = (Button) rootView.findViewById( R.id.sel_clearAllBtn );
		
		final CharSectionSelectFragment sectionSelectView = this;
		
		this.sel_btn_confirm.setOnClickListener( new OnClickListener() {
			@Override public void onClick(View v) {
				sectionSelectView.updateCharGetter();
			}
		});
		
		this.btnSelectAll.setOnClickListener( new OnClickListener() {
			@Override public void onClick(View v) {
				sectionSelectView.setAllCheckboxes(true);
			}
		});
		
		this.btnClearAll.setOnClickListener( new OnClickListener() {
			@Override public void onClick(View v) {
				sectionSelectView.setAllCheckboxes(false);
			}
		});
		
		
		return rootView;
	}

	protected void setAllCheckboxes( boolean isChecked ) {
		for( CheckBox checkBox : this.checkBoxes ) {
			checkBox.setChecked( isChecked );
		}
		
	}

	private void updateCharGetter() {
		
		CharGetter charGetter = MainActivity.getCharGetter();
		for( int i=0; i<this.checkBoxes.size(); i++ ) {
			CheckBox checkBox = this.checkBoxes.get(i);
			charGetter.setSectionEnabledStatus(i, checkBox.isChecked() );
		}
		
		charGetter.updateAndShuffleChars();
		
	}
}