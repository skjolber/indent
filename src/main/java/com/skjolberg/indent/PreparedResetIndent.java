package com.skjolberg.indent;

import java.io.IOException;
import java.io.Writer;

/**
 * Intent which resets the intent to zero at a specified level. <br/><br/>
 * 
 * All levels are prepared upon constructions.
 */

public class PreparedResetIndent extends Indent {

	public static final int defaultResetLevel = defaultPreparedLevels + 1;

	protected final int resetLevel;
	
	public PreparedResetIndent() {
		this(defaultCharacter, defaultCount, defaultResetLevel, defaultLinebreak);
	}
	
	/**
	 * Construct a new instance.
	 * 
	 * @param character whitespace character to use, i.e. usually space or tab
	 * @param count number of characters to each indented level
	 * @param resetLevel level at which linebreak is reset to zero
	 * @param linebreak type of linebreak
	 */
	
	public PreparedResetIndent(char character, int count, int resetLevel, LinebreakType linebreak) {
		super(character, count, resetLevel - 1, linebreak); // pointless to prepare more than reset level
		
		this.resetLevel = resetLevel;
	}

	public void append(StringBuffer buffer, int level) {
		buffer.append(indentations[level % resetLevel]);
	}

	public void append(StringBuilder buffer, int level) {
		buffer.append(indentations[level % resetLevel]);
	}
	
	public void append(Writer buffer, int level) throws IOException {
		buffer.write(indentations[level % resetLevel]);
	}
	
	public int getResetLevel() {
		return resetLevel;
	}
}