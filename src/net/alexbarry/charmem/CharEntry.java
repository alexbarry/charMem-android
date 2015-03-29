package net.alexbarry.charmem;

import java.io.Serializable;
import java.util.ArrayList;

public class CharEntry implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6764461020023112189L;

	public static final CharEntry UNDEFINED = new CharEntry("?", "no characters defined!" );

	//private static final String PRIMITIVE_ID = "p";
	
	String character;
	String keyword;
	String character_subtitle = "";
	String hint = null, pinyin = null;
	boolean isPrimitive = false;
	int char_id;
	
	ArrayList<String> notes = new ArrayList<String>();

	private int charId = -1;
	
	public CharEntry(String character, String keyword ) {
		this.character = character;
		this.keyword = keyword;
	}
	
	public void addNote(String note) { this.notes.add(note); }

	public void setCharacterSubtitle(String subtitle) {
		this.character_subtitle = subtitle;
	}
	
	public void setHint(String hint) {
		if( this.hint != null ) {
			throw new RuntimeException( "Tried to define hint twice for keyword '" + this.keyword +
					"': originally '" + this.hint + "', now '" + hint + "'." );
		}
		
		this.hint = hint;
	}
	
	public void setPinyin(String pinyin) {
		if( this.pinyin != null ) {
			throw new RuntimeException( "Tried to define pinyin twice for keyword '" + this.keyword +
					"': originally '" + this.pinyin + "', now '" + pinyin + "'." );
		}
		
		this.pinyin = pinyin;
	}

	public String getKeyword() { return this.keyword; }

	public String getCharacter() { return this.character; }

	public String getSubtitle() { return this.character_subtitle; }

	public void setCharacterId(int charId) {
		this.charId  = charId;		
	}
	
	public int getCharacterId() { 
		if(this.isPrimitive) { return -1; }
		else { return this.charId; }
	}
	
	public boolean isPrimitive() { return this.isPrimitive; }

	public void setPrimitive() { this.isPrimitive = true; }

	public String getPinyin() { return this.pinyin; }

	public String getHint() { return this.hint; }
	
	
	

}
