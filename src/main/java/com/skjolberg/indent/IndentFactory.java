package com.skjolberg.indent;

public class IndentFactory {

	private char character = Indent.defaultCharacter;
	private int count = Indent.defaultCount;
	private int preparedLevels = Indent.defaultPreparedLevels;
	private int resetLevel = -1;
	private LinebreakType linebreakType = Indent.defaultLinebreak;

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPreparedLevels() {
		return preparedLevels;
	}

	public void setPreparedLevels(int preparedLevels) {
		this.preparedLevels = preparedLevels;
	}

	public int getResetLevel() {
		return resetLevel;
	}

	public void setResetLevel(int resetLevel) {
		this.resetLevel = resetLevel;
	}

	public LinebreakType getLinebreakType() {
		return linebreakType;
	}

	public void setLinebreakType(LinebreakType linebreakType) {
		this.linebreakType = linebreakType;
	}
		
	public Indent build() {
		
		if(resetLevel != -1) {
			if(preparedLevels - 1 >= resetLevel) {
				return new PreparedResetIndent(character, count, resetLevel, linebreakType);
			}
			return new ResetIndent(character, count, preparedLevels, resetLevel, linebreakType);
		}
		
		return new Indent(character, count, preparedLevels, linebreakType);
	}
	
}