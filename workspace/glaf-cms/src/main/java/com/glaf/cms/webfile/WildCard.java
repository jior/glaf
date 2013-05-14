package com.glaf.cms.webfile;

public class WildCard {

	private String card = null;

	public WildCard(String s) {
		card = s.toLowerCase();
	}

	private boolean endsWith(String s, String suffix) {
		if (suffix.equals(""))
			return true;
		if (suffix.length() > s.length())
			return false;
		int pos = s.length() - 1;
		for (int i = suffix.length() - 1; i >= 0; i--) {
			char currentSuffixChar = suffix.charAt(i);
			char currentStringChar = s.charAt(pos--);
			if (currentSuffixChar != '?'
					&& currentSuffixChar != currentStringChar)
				return false;
		}

		return true;
	}

	private boolean equalsIgnoreCase(String s1, String s2) {
		if (s1.length() != s2.length())
			return false;
		for (int i = 0; i < s1.length(); i++) {
			char currentChar1 = Character.toLowerCase(s1.charAt(i));
			char currentChar2 = Character.toLowerCase(s2.charAt(i));
			if (currentChar1 != currentChar2 && currentChar1 != '?'
					&& currentChar2 != '?')
				return false;
		}

		return true;
	}

	private String Format(String s) {
		int pos1 = card.indexOf(42);
		int pos2 = card.lastIndexOf(42);
		String temp = s.toLowerCase();
		String prefix;
		String body;
		String suffix;
		if (pos1 < 0) {
			prefix = "";
			suffix = card;
			body = "";
		} else {
			if (card.equals("*")) {
				prefix = "";
				suffix = "";
			} else {
				prefix = card.substring(0, pos1);
				suffix = card.substring(pos2 + 1);
			}
			if (startsWith(s, prefix))
				body = temp.substring(prefix.length());
			else
				body = temp;
			if (endsWith(body, suffix))
				body = body.substring(0, body.length() - suffix.length());
		}
		if (pos1 < pos2 && indexOf(body, card.substring(pos1 + 1, pos2)) == -1)
			body = body + temp;
		return String.valueOf((new StringBuffer(String.valueOf(prefix)))
				.append(body).append(suffix));
	}

	private int indexOf(String s, String sub) {
		for (int i = 0; i < s.length(); i++)
			if (startsWith(s.substring(i), sub))
				return i;

		return -1;
	}

	public boolean Match(String s) {
		String formatedString = Format(s);
		return equalsIgnoreCase(s, formatedString);
	}

	private boolean startsWith(String s, String prefix) {
		if (prefix.equals(""))
			return true;
		if (prefix.length() > s.length())
			return false;
		for (int i = 0; i < prefix.length(); i++) {
			char currentChar = prefix.charAt(i);
			if (currentChar != '?' && currentChar != s.charAt(i))
				return false;
		}

		return true;
	}
}
