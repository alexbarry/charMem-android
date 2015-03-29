package net.alexbarry.charmem.android;

import net.alexbarry.charmem.CharEntry;
import net.alexbarry.charmem.R;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class GuessNameFragment extends CharTabFragment {
	


	private TextView statusText;
	private TextView charDisplay;
	private TextView charSubtitle;
	
	private EditText charGuessInput;
	
	private Button enterBtn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.char_name_guess_fragment,
				container, false);
		
		this.statusText = (TextView) rootView.findViewById( R.id.statusMsg );
		this.charDisplay = (TextView) rootView.findViewById( R.id.charDisplay );
		this.charSubtitle = (TextView) rootView.findViewById( R.id.charInfo );
		
		this.charGuessInput = (EditText) rootView.findViewById( R.id.charNameInput );
		
		this.enterBtn = (Button) rootView.findViewById( R.id.charInputBtn );		
		
		this.updateChar();

		final GuessNameFragment guessNameFrag = this;
		
		this.enterBtn.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				guessNameFrag.checkGuess();
			}
		});
		
//		this.charGuessInput.setKeyListener( new KeyListener() {
//			@Override public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) { return false; }
//			@Override public boolean onKeyOther(View view, Editable text, KeyEvent event) { return false; }
//			@Override public int getInputType() { return 0; }
//			@Override public void clearMetaKeyState(View view, Editable content, int states) { }
//			
//			@Override
//			public boolean onKeyDown(View view, Editable text, int keyCode,
//					KeyEvent event) {
//				// TODO: might not need to do anything here?
//				return false;
//			}
//			
//		});
		
		this.charGuessInput.setOnEditorActionListener( new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				boolean handled = false;
				if( actionId == EditorInfo.IME_ACTION_NEXT ) {
					handled = true;
					guessNameFrag.checkGuess();
				}
				return handled;
			}
		});
		
		return rootView;
	}

	protected boolean checkGuess() {
		String charInput = charGuessInput.getText().toString().trim();
		String charKeyword = MainActivity.getCharGetter().getCurrentChar().getKeyword();
		String msg;
		boolean isCorrect;
		if( charInput.equals( charKeyword ) ) {
			msg = getString( R.string.charGuessCorrect );
			isCorrect = true;
		} else {
			String frmt = getString( R.string.charGuessIncorrect_frmt );
			String charDisplayed = this.charDisplay.getText().toString();
			msg = String.format( frmt, charDisplayed, charKeyword );
			isCorrect = false;
		}
		
		statusText.setText( msg );
		charGuessInput.setText("");
		
		MainActivity.getCharGetter().nextChar();
		updateChar();
		
		return isCorrect;
	}

	public void updateChar() {
		CharEntry charEntry = MainActivity.getCharGetter().getCurrentChar();
		this.charDisplay.setText( charEntry.getCharacter() );
		this.charSubtitle.setText( charEntry.getSubtitle() );
	}

	@Override
	public String getTitle() {
		return "Guess Name";
	}
}