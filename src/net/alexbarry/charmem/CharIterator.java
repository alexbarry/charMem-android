package net.alexbarry.charmem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CharIterator extends ArrayList<CharEntry> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6437848800916251487L;
	private int laps = 0;
	private int index = 0;
	
	public CharIterator() {	}
	
	public void addChars(CharEntryGroup charEntryGroup) {
		this.addAll(charEntryGroup);
	}
	
	public CharEntry nextCharEntry() {
		if( this.size() == 0 ) { return CharEntry.UNDEFINED; }
		CharEntry toReturn = this.get(index);
		
		this.index += 1;
		if( index >= this.size() ) { 
			this.index = 0;
			this.laps += 1;
		}
		
		return toReturn;	
		
	}
	
	public CharEntry getCurrentCharEntry() {
		if( this.size() == 0 ) {  return CharEntry.UNDEFINED; }
		return this.get(this.index);
	}
	
	public int getLapCount() { return this.laps; }
	
	public void shuffle() {
		Collections.shuffle(this);
	}
	

}
