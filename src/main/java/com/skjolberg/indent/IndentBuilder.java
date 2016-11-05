package com.skjolberg.indent;

public class IndentBuilder {

	private char character = Indent.defaultCharacter;
	private int count = Indent.defaultCount;
	private int preparedLevels = Indent.defaultPreparedLevels;
	private int resetLevel = -1;
	private LinebreakType linebreak = Indent.defaultLinebreak;
	
	public IndentBuilder withCharacter(char character) {
		this.character = character;
		
		return this;
	}

	public IndentBuilder withSpace(int count) {
		return withCharacter(' ').withCount(count);
	}

	public IndentBuilder withTab() {
		return withCharacter('\t').withCount(1);
	}
	
	public IndentBuilder withCount(int count) {
		this.count = count;
		
		return this;
	}
	
	public IndentBuilder withPreparedLevels(int preparedLevels) {
		this.preparedLevels = preparedLevels;
		
		return this;
	}
	
	public IndentBuilder withoutLinebreak() {
		return withLinebreak(LinebreakType.NONE);
	}

	public IndentBuilder withWindowsLinebreak() {
		return withLinebreak(LinebreakType.CarriageReturnLineFeed);
	}

	public IndentBuilder withUnixLinebreak() {
		return withLinebreak(LinebreakType.LineFeed);
	}

	private IndentBuilder withLinebreak(LinebreakType linebreak) {
		this.linebreak = linebreak;
		
		return this;
	}
	
	/**
	 * 
	 * Set the level at which the indent level is reset to zero. 
	 * 
	 */
	
	public IndentBuilder withResetLevel(int resetLevel) {
		this.resetLevel = resetLevel;
		
		return this;
	}

	public Indent build() {
		
		if(resetLevel != -1) {
			if(preparedLevels - 1 >= resetLevel) {
				return new PreparedResetIndent(character, count, resetLevel, linebreak);
			}
			return new ResetIndent(character, count, preparedLevels, resetLevel, linebreak);
		}
		
		return new Indent(character, count, preparedLevels, linebreak);
	}
		
}