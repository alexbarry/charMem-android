package net.alexbarry.charmem.android;

import java.util.List;

import net.alexbarry.charmem.CharEntry;
import net.alexbarry.charmem.CharEntryGroup;
import net.alexbarry.charmem.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GuessNameFragment extends Fragment {
	


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

		final GuessNameFragment GuessNameFrag = this;
		
		this.enterBtn.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String charInput = GuessNameFrag.charGuessInput.getText().toString().trim();
				String charKeyword = MainActivity.getCharGetter().getCurrentChar().getKeyword();
				String msg;
				boolean isCorrect;
				if( charInput.equals( charKeyword ) ) {
					msg = getString( R.string.charGuessCorrect );
					isCorrect = true;
				} else {
					String frmt = getString( R.string.charGuessIncorrect_frmt );
					String charDisplay = GuessNameFrag.charDisplay.getText().toString();
					msg = String.format( frmt, charDisplay, charKeyword );
					isCorrect = false;
				}
				
				GuessNameFrag.statusText.setText( msg );
				
				MainActivity.getCharGetter().nextChar();
				GuessNameFrag.updateChar();
			}
		});
		
		return rootView;
	}

	private void updateChar() {
		CharEntry charEntry = MainActivity.getCharGetter().getCurrentChar();
		this.charDisplay.setText( charEntry.getCharacter() );
		this.charSubtitle.setText( charEntry.getSubtitle() );		
	}
}