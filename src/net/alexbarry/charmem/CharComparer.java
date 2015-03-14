package net.alexbarry.charmem;

import java.util.Locale;

public class CharComparer {

	public static boolean guessesMatch(String s1, String s2) {
		s1 = cleanseStr(s1);
		s2 = cleanseStr(s2);
		return s1.equals(s2);
	}
	
	public static String cleanseStr(String s) {
		return s.trim().toLowerCase(Locale.US);
	}
}
