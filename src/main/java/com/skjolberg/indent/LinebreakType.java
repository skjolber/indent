package com.skjolberg.indent;

public enum LinebreakType {
	
	NONE(""), CarriageReturnLineFeed("\r\n"), LineFeed("\n");
	
	String characters;

	private LinebreakType(String characters) {
		this.characters = characters;
	}
	
	public String getCharacters() {
		return characters;
	}
	
	public int length() {
		return characters.length();
	}
}