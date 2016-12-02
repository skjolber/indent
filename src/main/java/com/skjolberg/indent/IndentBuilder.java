package com.skjolberg.indent;

public class IndentBuilder {

	private IndentFactory factory = new IndentFactory();
	
	public IndentBuilder withCharacter(char character) {
		factory.setCharacter(character);
		
		return this;
	}

	public IndentBuilder withSpace(int count) {
		return withCharacter(' ').withCount(count);
	}

	public IndentBuilder withTab() {
		return withCharacter('\t').withCount(1);
	}
	
	public IndentBuilder withCount(int count) {
		factory.setCount(count);
		
		return this;
	}
	
	public IndentBuilder withPreparedLevels(int preparedLevels) {
		factory.setPreparedLevels(preparedLevels);
		
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

	public IndentBuilder withLinebreak(LinebreakType linebreak) {
		factory.setLinebreakType(linebreak);
		
		return this;
	}
	
	/**
	 * 
	 * Set the level at which the indent level is reset to zero. 
	 * 
	 */
	
	public IndentBuilder withResetLevel(int resetLevel) {
		factory.setResetLevel(resetLevel);
		
		return this;
	}

	public Indent build() {
		return factory.build();
	}
		
}