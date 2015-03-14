package net.alexbarry.charmem.android;

import net.alexbarry.charmem.CharEntry;
import net.alexbarry.charmem.R;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class CharReviewElementWrapper {

	private View view;
	
	private TextView
				keywordText,
				charText,
				//pinyinEdit,
				subtitleText,
				charIdText;
				//hintEdit;

	public CharReviewElementWrapper(ViewGroup	parent, CharEntry charEntry) {
		Context context = parent.getContext();
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		boolean attachToRoot = false;
		this.view = layoutInflater.inflate(R.layout.char_review_entry, parent, attachToRoot );
		
		this.keywordText	= (TextView) this.view.findViewById(R.id.textKeyword);
		this.charText 		= (TextView) this.view.findViewById(R.id.textChar);
		//this.pinyinEdit		= (EditText) this.view.findViewById(R.id.);
		this.subtitleText	= (TextView) this.view.findViewById(R.id.textSubtitle);
		//this.hintEdit		= (TextView) this.view.findViewById(R.id.text);
		this.charIdText		= (TextView) this.view.findViewById(R.id.textCharacterId);
		
		String test = charEntry.getKeyword();
		
		this.keywordText.setText(		charEntry.getKeyword()		);
		this.charText.setText(			charEntry.getCharacter()	);
		//this.pinyinEdit.setText(		charEntry.getPinyin()		);
		this.subtitleText.setText(		charEntry.getSubtitle()		);
		int charId = charEntry.getCharacterId();
		String charIdText;
		if( charId != -1 ) {
			charIdText = Integer.toString( charId );
		} else {
			charIdText = "p";
		}
		
		this.charIdText.setText( charIdText );

		//this.hintEdit.setText(			charEntry.getHint()			);
	}
	
	public View getView() { return this.view; }

}
