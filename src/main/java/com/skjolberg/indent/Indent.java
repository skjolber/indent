package com.skjolberg.indent;

import java.io.IOException;
import java.io.Writer;

/**
 * Append whitespace to character outputs in levels.<br/><br/>
 * 
 * Keeps a set of prepared indent strings for improved performance.
 *
 */

public class Indent {

	public static final char defaultCharacter = ' ';
	public static final int defaultCount = 2;
	
	public static final int defaultPreparedLevels = 32;
	public static final LinebreakType defaultLinebreak = LinebreakType.NONE;

	protected final char[][] indentations;
	protected final char character;
	protected final int count;
	protected final int preparedLevels;
	
	protected final LinebreakType linebreakType;
	
	public Indent() {
		this(defaultCharacter, defaultCount, defaultPreparedLevels, defaultLinebreak);
	}
	
	/**
	 * Construct a new instance
	 * 
	 * @param character whitespace character to use, i.e. usually space or tab
	 * @param count number of characters to each indented level
	 * @param preparedLevels number of prepared (cached) levels
	 * @param linebreak type of linebreak
	 */

	public Indent(char character, int count, int preparedLevels, LinebreakType linebreak) {
		if(count < 0) {
			throw new IllegalArgumentException("Expected non-negative indent count");
		}
		if(preparedLevels < 0) {
			throw new IllegalArgumentException("Expected non-negative prepared level");
		}
		if(linebreak == null) {
			throw new IllegalArgumentException("Expected non-null linebreak parameter");
		}
		this.character = character;
		this.count = count;
		this.preparedLevels = preparedLevels;
		this.linebreakType = linebreak;
		
		this.indentations = prepare(character, count, linebreak, preparedLevels);
	}

	protected static char[][] prepare(char character, int count, LinebreakType linebreak, int levels) {
		char[][] indentations = new char[levels + 1][]; // count zero as a level

		indentations[0] = linebreak.characters.toCharArray();
		StringBuilder increment = new StringBuilder(count);
		for(int k = 0; k < count; k++) {
			increment.append(character);
		}
		
		for(int i = 1; i < indentations.length; i++) {
			StringBuilder builder = new StringBuilder(i * count + linebreak.length());
			
			builder.append(indentations[i - 1]);
			builder.append(increment);
			
			builder.getChars(0, builder.length(), indentations[i] = new char[builder.length()], 0);
		}
		return indentations;
	}

	public void append(StringBuffer buffer, int level) {
		if(level < indentations.length) {
			buffer.append(indentations[level]);
		} else {
			// do not ensure capacity here, leave that up to the caller
			// append a longer intent than we have prepared
			char[] next = indentations[indentations.length - 1];
			
			buffer.append(next);
			level -= preparedLevels;
			
			while(level >= preparedLevels) {
				buffer.append(next, linebreakType.length(), next.length - linebreakType.length());
				
				level -= preparedLevels;
			}
			
			next = indentations[level % indentations.length];
			buffer.append(next, linebreakType.length(), next.length - linebreakType.length());			
		}
	}

	public void append(StringBuilder buffer, int level) {
		if(level < indentations.length) {
			buffer.append(indentations[level]);
		} else {
			// do not ensure capacity here, leave that up to the caller
			// append a longer intent than we have prepared
			char[] next = indentations[indentations.length - 1];
			
			buffer.append(next);
			level -= preparedLevels;
			
			while(level >= preparedLevels) {
				buffer.append(next, linebreakType.length(), next.length - linebreakType.length());
				
				level -= preparedLevels;
			}
			
			next = indentations[level % indentations.length];
			buffer.append(next, linebreakType.length(), next.length - linebreakType.length());			
		}
	}
	
	public void append(Writer buffer, int level) throws IOException {
		if(level < indentations.length) {
			buffer.write(indentations[level]);
		} else {
			// append a longer intent than we have prepared
			char[] next = indentations[indentations.length - 1];
			
			buffer.write(next);
			level -= preparedLevels;
			
			while(level >= preparedLevels) {
				buffer.write(next, linebreakType.length(), next.length - linebreakType.length());
				
				level -= preparedLevels;
			}
			
			next = indentations[level % indentations.length];
			buffer.write(next, linebreakType.length(), next.length - linebreakType.length());			
		}
	}		
	
	public char getCharacter() {
		return character;
	}
	
	public int getCount() {
		return count;
	}
	
	public LinebreakType getLinebreakType() {
		return linebreakType;
	}
	
	public int getPreparedLevels() {
		return preparedLevels;
	}
}
