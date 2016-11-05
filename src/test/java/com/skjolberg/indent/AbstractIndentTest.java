package com.skjolberg.indent;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

public abstract class AbstractIndentTest {

	public void assertIndentEquals(String expected, Indent indent, int level) throws IOException {
		assertEquals(escape(expected), escape(appendStringWriter(indent, level)));
		assertEquals(escape(expected), escape(appendStringBuffer(indent, level)));
		assertEquals(escape(expected), escape(appendStringBuilder(indent, level)));
	}
	
	public String appendStringWriter(Indent indent, int level) throws IOException {
		StringWriter builder = new StringWriter();
		indent.append(builder, level);
		return builder.toString();
	}

	public String appendStringBuffer(Indent indent, int level) throws IOException {
		StringBuffer builder = new StringBuffer();
		indent.append(builder, level);
		return builder.toString();
	}

	public String appendStringBuilder(Indent indent, int level) throws IOException {
		StringBuilder builder = new StringBuilder();
		indent.append(builder, level);
		return builder.toString();
	}
	
	public String escape(String str) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < str.length(); i++) {
			switch(str.charAt(i)) {
				case '\n' : {
					builder.append("n");
					break;
				}
				case '\r' : {
					builder.append("r");
					break;
				}
				case '\t' : {
					builder.append("t");
					break;
				}
				case ' ' : {
					builder.append("s");
					break;
				}
				default : {
					throw new IllegalArgumentException(str);
				}
			}
		}
		return builder.toString();
	}

	public String lineFeedSpaces(int count) {
		StringBuilder builder = new StringBuilder();
		builder.append('\n');
		builder.append(spaces(count));
		return builder.toString();
	}

	public String spaces(int count) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < count; i++) {
			builder.append(' ');
		}
		return builder.toString();
	}
	
	public void assertResetLevel(Indent indent, int resetLevel) throws IOException {

		for(int k = 0; k < 10; k++) {
			for(int i = resetLevel * k; i < resetLevel * (1 + k); i++) {
				assertIndentEquals(lineFeedSpaces(i - k * resetLevel), indent, i);
			}
		}
	}
	
	public void assertLevel(Indent indent, int level) throws IOException {

		for(int i = 0; i <= level; i++) {
			assertIndentEquals(lineFeedSpaces(i), indent, i);
		}
	}
	
	
	
}
