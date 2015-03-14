package net.alexbarry.charmem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CharEntryFileParser {
	
	public static final String 
		PINYIN_FLAG			= "pinyin:",
		NOTE_FLAG 			= "note:",
		PRIMITIVE_MARKER1	= "note:primitive",
		PRIMITIVE_MARKER2	= "note: primitive";
	
	public static final String
		SECTION_START		= "# Lesson ",
		COMMENT				= "#";
	
	static {
		if( SECTION_START.startsWith(COMMENT) == false ) {
			throw new RuntimeException("SECTION_START needs to be prefixed with COMMENT");
		}
	}
	
	public static ArrayList<CharEntryGroup> parseFile(String fpath, String enc) throws Exception {
		File f = new File(fpath);
		return parseFile(f, enc);		
	}
	
	public static ArrayList<CharEntryGroup> parseFile(File f, String enc) throws Exception {
		FileInputStream fis = new FileInputStream(f);
		return parseFile( fis, enc);
	}
	
	public static ArrayList<CharEntryGroup> parseFile(InputStream is, String enc) throws IOException {
		InputStreamReader isr = new InputStreamReader( is, enc );
		BufferedReader br = new BufferedReader(isr);
		
		ArrayList<CharEntryGroup> charEntryGroups = new ArrayList<CharEntryGroup>();
		
		br.skip(1);
		int charId = 1;
		
		try {
			while(true) {
				String line = br.readLine();
				if( line == null ) break;
				
				line = line.trim();
				if( line.length() == 0 ) { continue; }
				
				if( line.startsWith(SECTION_START) ) {
					line = line.substring( COMMENT.length() );
					line = line.trim();
					
					String[] lineParts = line.split(":", 2);
					
					String sectionName = lineParts[0];
					String sectionDescription = "";
					if( lineParts.length > 1) { sectionDescription = lineParts[1]; }
					
					charEntryGroups.add( new CharEntryGroup(sectionName, sectionDescription) );
					
				} else if( line.startsWith(COMMENT) ) { 
					continue;
				} else {
					if( charEntryGroups.size() == 0 ) { throw new RuntimeException("Invalid input file: need to define section first"); }
					CharEntryGroup lastSection = charEntryGroups.get( charEntryGroups.size() - 1 );
					
					CharEntry charEntry = parseLine(line);
					if( charEntry.isPrimitive == false ) {
						charEntry.setCharacterId(charId);
						charId += 1;
					}
					lastSection.add(charEntry);
				}
				
				
			}
		} catch( IOException e ) {
			throw e;
		} finally {
			br.close();
		}

		return charEntryGroups;
	}
	
	private static CharEntry parseLine(String line) {
		String[] parts = line.split("\t");
		
		String keyword = null, character, subtitle;
		CharEntry charEntry = null;
		int i = 0;
		for( String part : parts ) {
			part = part.trim();
			if( part.length() == 0 ) { continue; }
			if( i == 0 ) { keyword = part; }
			else if ( i == 1 ) { 
				character = part;
				CharacterAndSubtitle charAndSub = separateSubtitle(character);
				character = charAndSub.character;
				subtitle  = charAndSub.subtitle;
				charEntry = new CharEntry(character, keyword );
				charEntry.setCharacterSubtitle(subtitle);
			} else if ( i >= 2 ) { 
				if( handlePinyin(charEntry, part) ) { }
				else if ( handlePrimitive(charEntry, part) ) { }
				else if ( handleNote(charEntry, part) ) { }
				else { charEntry.setHint(part); }
			}
			
			i += 1;
		}
		
		return charEntry;
		
	}

	private static boolean handleNote(CharEntry charEntry, String part) {
		if( part.startsWith(NOTE_FLAG) ) {
			part = part.substring( NOTE_FLAG.length() );
			charEntry.addNote( part.trim() );
			return true;
		} else {
			return false;
		}
	}

	private static boolean handlePrimitive(CharEntry charEntry, String part) {
		if( part.equals(PRIMITIVE_MARKER1) || part.equals(PRIMITIVE_MARKER2) ) { 
			charEntry.isPrimitive = true;
			return true;
		} else {
			return false;
		}
	}

	private static boolean handlePinyin(CharEntry charEntry, String part) {
		if( part.startsWith(PINYIN_FLAG) ) {
			part = part.substring( PINYIN_FLAG.length() );
			charEntry.setPinyin( part.trim() );
			return true;
		} else {
			return false;
		}
	}
	
	private static CharacterAndSubtitle separateSubtitle(String characterLine) {
		String 
			character = characterLine, 
			subtitle = "";
		String delim = ",";
		if( characterLine.contains(delim) ) {
			String[] parts = characterLine.split(delim, 2);
			character = parts[0].trim();
			subtitle  = parts[1].trim();
		}
		
		return new CharacterAndSubtitle(character, subtitle);
	}
	
	private static class CharacterAndSubtitle {
		public String character, subtitle;
		
		public CharacterAndSubtitle(String character, String subtitle) {
			this.character = character;
			this.subtitle  = subtitle;
		}
	}

}