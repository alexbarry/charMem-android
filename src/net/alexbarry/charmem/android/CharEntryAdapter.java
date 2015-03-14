package net.alexbarry.charmem.android;

import java.util.List;

import net.alexbarry.charmem.CharEntry;
import net.alexbarry.charmem.CharEntryGroup;
import android.database.DataSetObserver;
import android.os.DropBoxManager.Entry;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class CharEntryAdapter extends BaseExpandableListAdapter {
	
	private List<CharEntryGroup> charEntries;
	
	public CharEntryAdapter( List<CharEntryGroup> charEntries ) {
		this.charEntries = charEntries;
	}


	
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView groupView = new TextView( parent.getContext() );
		List<CharEntryGroup> charEntries = this.charEntries;
		groupView.setText( charEntries.get(groupPosition).getName() );
		return groupView;
	}
	
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}
	
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this.charEntries.size();
	}
	
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public long getCombinedGroupId(long groupId) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public long getCombinedChildId(long groupId, long childId) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return this.charEntries.get(groupPosition).size();
	}
	
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		CharEntry charEntry =  this.charEntries.get(groupPosition).get(childPosition);
		//CharReviewEntryFragment charViewFrag = CharReviewEntryFragment.newInstance(charEntry);
		CharReviewElementWrapper charReviewElement = new CharReviewElementWrapper(parent, charEntry);
		return charReviewElement.getView();
	}



	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	


}
