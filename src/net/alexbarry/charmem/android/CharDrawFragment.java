package net.alexbarry.charmem.android;

import net.alexbarry.charmem.CharEntry;
import net.alexbarry.charmem.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class CharDrawFragment extends CharTabFragment {

	private TextView statusMsg;
	private TextView keywordDisplay;
	private TextView charDisplay;
	private TextView charSubtitle;
	
	private Button btnShow;
	private Button btnCorrect;
	private Button btnIncorrect;
	private CharEntry charEntry;

	public CharDrawFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.char_draw_fragment,
				container, false);
		
		this.statusMsg 			= (TextView) rootView.findViewById( R.id.statusMsg );
		this.keywordDisplay		= (TextView) rootView.findViewById( R.id.keywordDisplay );
		this.charDisplay		= (TextView) rootView.findViewById( R.id.charDisplay );
		this.charSubtitle		= (TextView) rootView.findViewById( R.id.charSubtitle );
		
		this.btnShow			= (Button) rootView.findViewById( R.id.btnShow );
		this.btnCorrect			= (Button) rootView.findViewById( R.id.btnCorrect );
		this.btnIncorrect		= (Button) rootView.findViewById( R.id.btnIncorrect );
		
		final CharDrawFragment charDrawFrame = this;
		
		this.btnShow.setOnClickListener( new OnClickListener() {
			@Override public void onClick(View v) {
				charDrawFrame.showChar();
				
			}
		});
		
		this.btnCorrect.setOnClickListener( new OnClickListener() {
			@Override public void onClick(View v) {
				charDrawFrame.nextChar(true);
			}
		});
		
		this.btnIncorrect.setOnClickListener( new OnClickListener() {
			@Override public void onClick(View v) {
				charDrawFrame.nextChar(false);
			}
		});
		
		
		
		
		this.updateChar();
	
		return rootView;

	}

	public void updateChar() {
		this.charEntry = MainActivity.getCharGetter().getCurrentChar();
		
		this.keywordDisplay.setText( charEntry.getKeyword() );
		this.charDisplay.setText( "" );
		this.charSubtitle.setText( "" );
		
		this.btnShow.setEnabled(true);
		
		this.btnCorrect.setEnabled(false);
		this.btnIncorrect.setEnabled(false);
	}
	
	private void showChar() {
		
		this.charDisplay.setText( charEntry.getCharacter() );
		this.charSubtitle.setText( charEntry.getSubtitle() );
		
		this.btnShow.setEnabled(false);
		
		this.btnCorrect.setEnabled(true);
		this.btnIncorrect.setEnabled(true);
	}
	
	private void nextChar(boolean wasCorrect ) {
		// TODO: record whether or not it was correct
		MainActivity.getCharGetter().nextChar();
		this.updateChar();
	}

	@Override
	public String getTitle() {
		return "Draw Char";
	}
}
