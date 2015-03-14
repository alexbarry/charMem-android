package net.alexbarry.charmem;

import java.io.Serializable;
import java.util.ArrayList;

public class CharEntryGroup extends ArrayList<CharEntry> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3929285841506392082L;
	
	private String name, description;
	//private ArrayList<CharEntry> charEntries = new ArrayList<CharEntry>();
	
	public CharEntryGroup(String name, String sectionDescription) {
		this.name = name;
		this.description = sectionDescription;
	}
	
//	public void add(CharEntry ce) { 
//		this.add(ce);
//	}

	//public int size() { return this.charEntries.size(); }
	//public CharEntry get( int i ) { return this.get(i); }
	
	public String getName() { return this.name; }
	public String getDescription() { return this.description; }

}
