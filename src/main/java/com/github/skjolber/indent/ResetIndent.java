package com.github.skjolber.indent;

import java.io.IOException;
import java.io.Writer;

/**
 * Intent which resets the intent to zero at a specified level. 
 * 
 */

public class ResetIndent extends Indent {

	public static final int defaultResetLevel = defaultPreparedLevels + 1;

	protected final int resetLevel;
	
	public ResetIndent() {
		this(defaultCharacter, defaultCount, defaultPreparedLevels, defaultResetLevel, defaultLinebreak);
	}
	
	/**
	 * Construct a new instance.
	 * 
	 * @param character whitespace character to use, i.e. usually space or tab
	 * @param count number of characters to each indented level
	 * @param preparedLevels number of prepared (cached) levels
	 * @param resetLevel level at which linebreak is reset to zero
	 * @param linebreak type of linebreak
	 */
	
	public ResetIndent(char character, int count, int preparedLevels, int resetLevel, LinebreakType linebreak) {
		super(character, count, Math.min(preparedLevels, resetLevel - 1), linebreak); // pointless to prepare more than reset level
		
		this.resetLevel = resetLevel;
	}

	public void append(StringBuffer buffer, int level) {
		if(level < resetLevel) {
			super.append(buffer, level);
		} else {
			super.append(buffer, level % resetLevel);
		}
	}

	public void append(StringBuilder buffer, int level) {
		if(level < resetLevel) {
			super.append(buffer, level);
		} else {
			super.append(buffer, level % resetLevel);
		}
	}
	
	public void append(Writer buffer, int level) throws IOException {
		if(level < resetLevel) {
			super.append(buffer, level);
		} else {
			super.append(buffer, level % resetLevel);
		}
	}
	
	public int getResetLevel() {
		return resetLevel;
	}
}