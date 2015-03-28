package net.alexbarry.charmem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.alexbarry.charmem.android.CharSectionSelectFragment;

public class CharGetter {
	
	/** Value returned if no sections are enabled or defined */
	public static CharEntry defaultChar = null;
	
	static {
		CharGetter.defaultChar = new CharEntry("?", "no characters defined!");
		CharGetter.defaultChar.setCharacterSubtitle("no characters defined!");
	}
	
	private List<CharEntryGroup> charEntrySections;
	private List<Boolean> charSectionEnabled;
	
	private int charIndex = 0;
	
	private List<CharEntry> chars;

	private int laps = 0;

	public CharGetter( List<CharEntryGroup> charEntrySections ) {
		this.charEntrySections = charEntrySections;
		
		this.chars = new ArrayList<CharEntry>();
		this.charSectionEnabled = new ArrayList<Boolean>();
		
		for(int i = 0; i<charEntrySections.size(); i++ ) {
			this.charSectionEnabled.add( CharSectionSelectFragment.sectionEnabledDefaultVal );
		}
		
		this.updateAndShuffleChars();
	}
	
	public void setSectionEnabledStatus( int sectionNum, boolean isEnabled ) {
		this.charSectionEnabled.set(sectionNum, isEnabled);
		
		//this.shuffleChars();
	}

	/**
	 * Shuffles characters, and updates them if the enabled sections have changed. 
	 * Also resets current character.
	 */
	public void updateAndShuffleChars() {
		this.charIndex = 0;
		
		this.chars.clear();
		
		for(int sectionNum = 0; sectionNum<this.charEntrySections.size(); sectionNum++ ) {
			if( this.isSectionEnabled(sectionNum) == false ) { continue; }
			for( CharEntry charEntry : this.charEntrySections.get(sectionNum) ) {
				this.chars.add(charEntry);
			}
		}
		
		this.shuffleChars();
	}
	
	private void shuffleChars() {
		Collections.shuffle(this.chars);
	}

	private boolean isSectionEnabled(int sectionNum) {
		return this.charSectionEnabled.get(sectionNum);
	}
	
	
	public CharEntry getCurrentChar() {
		if( this.chars.size() == 0 ) { return CharGetter.defaultChar; }
		return this.chars.get( this.charIndex );
	}
	
	public CharEntry nextChar() {
		this.charIndex += 1;
		if( this.charIndex >= this.chars.size() ) { 
			this.charIndex = 0;
			this.laps  += 1;
			this.shuffleChars();
		}
		
		return this.getCurrentChar();
	}
	

}
