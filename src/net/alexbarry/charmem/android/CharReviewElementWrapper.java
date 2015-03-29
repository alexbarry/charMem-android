package net.alexbarry.charmem.android;

import net.alexbarry.charmem.CharEntry;
import net.alexbarry.charmem.R;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class CharReviewElementWrapper {

	private View view;
	
	private TextView
				keywordText,
				charText,
				subtitleText,
				charIdText;

	public CharReviewElementWrapper(ViewGroup	parent, final CharEntry charEntry) {
		// TODO: in another function (that happens on click?) remove this view, inflate a new bigger one
		Context context = parent.getContext();
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		boolean attachToRoot = false;
		this.view = layoutInflater.inflate(R.layout.char_review_entry, parent, attachToRoot );
		
		this.keywordText	= (TextView) this.view.findViewById(R.id.textKeyword);
		this.charText 		= (TextView) this.view.findViewById(R.id.textChar);
		this.subtitleText	= (TextView) this.view.findViewById(R.id.textSubtitle);
		this.charIdText		= (TextView) this.view.findViewById(R.id.textCharacterId);
				
		this.keywordText.setText(		charEntry.getKeyword()		);
		this.charText.setText(			charEntry.getCharacter()	);
		this.subtitleText.setText(		charEntry.getSubtitle()		);
		int charId = charEntry.getCharacterId();
		String charIdText;
		if( charId != -1 ) {
			charIdText = Integer.toString( charId );
		} else {
			charIdText = "p";
		}
		
		this.charIdText.setText( charIdText );
		
		this.view.setClickable(true);
		this.view.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog dialog = new Dialog( v.getContext() );
				dialog.setTitle( charEntry.getKeyword() );
				dialog.show();
			}
		});
	}
	
	public View getView() { return this.view; }

}
