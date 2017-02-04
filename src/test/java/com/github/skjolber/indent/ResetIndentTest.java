package com.github.skjolber.indent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.skjolber.indent.Indent;
import com.github.skjolber.indent.IndentBuilder;
import com.github.skjolber.indent.PreparedResetIndent;
import com.github.skjolber.indent.ResetIndent;

public class ResetIndentTest extends AbstractIndentTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testDefaultsBuilder() throws IOException {
		Indent indent = new IndentBuilder().withResetLevel(ResetIndent.defaultResetLevel).build();
		
		assertEquals(Indent.defaultCharacter, indent.getCharacter());
		assertEquals(Indent.defaultCount, indent.getCount());
		assertEquals(Indent.defaultPreparedLevels, indent.getPreparedLevels());
		assertEquals(Indent.defaultLinebreak, indent.getLinebreakType());
		assertIndentEquals("  ", indent, 1);
	}
	
	@Test
	public void testDefaultConstructor() throws IOException {
		Indent indent = new ResetIndent();
		
		assertEquals(Indent.defaultCharacter, indent.getCharacter());
		assertEquals(Indent.defaultCount, indent.getCount());
		assertEquals(Indent.defaultPreparedLevels, indent.getPreparedLevels());
		assertEquals(Indent.defaultLinebreak, indent.getLinebreakType());
		assertIndentEquals("  ", indent, 1);
	}
	
	@Test
	public void testInvalidDefaults1() {
		exception.expect(Exception.class);
		
		new IndentBuilder().withResetLevel(ResetIndent.defaultResetLevel).withCount(-1).build();
	}

	@Test
	public void testInvalidDefaults2() {
		exception.expect(Exception.class);
		
		new IndentBuilder().withResetLevel(ResetIndent.defaultResetLevel).withResetLevel(-2).build();
	}	
	
	@Test
	public void testInvalidDefaults3() {
		exception.expect(Exception.class);
		
		new ResetIndent(Indent.defaultCharacter, Indent.defaultCount, ResetIndent.defaultPreparedLevels, ResetIndent.defaultResetLevel, null);
	}

	@Test
	public void testInvalidDefaults4() {
		exception.expect(Exception.class);
		
		new IndentBuilder().withResetLevel(-2).build();
	}

	
	@Test
	public void testUnixLinebreak() throws IOException {
		Indent indent = new IndentBuilder().withResetLevel(ResetIndent.defaultResetLevel).withUnixLinebreak().build();

		assertIndentEquals("\n  ", indent, 1);
	}
	
	@Test
	public void testWindowsLinebreak() throws IOException {
		Indent indent = new IndentBuilder().withResetLevel(ResetIndent.defaultResetLevel).withWindowsLinebreak().build();
		
		assertIndentEquals("\r\n", indent, 0);
	}	
	
	@Test
	public void testTabs() throws IOException {
		Indent indent = new IndentBuilder().withResetLevel(ResetIndent.defaultResetLevel).withUnixLinebreak().withTab().withCount(1).build();
		
		assertIndentEquals("\n\t", indent, 1);
	}
	
	@Test
	public void testSpaces() throws IOException {
		Indent indent = new IndentBuilder().withUnixLinebreak().withSpace(2).withResetLevel(ResetIndent.defaultResetLevel).build();
		
		assertIndentEquals("\n  ", indent, 1);
	}
	
	
	@Test
	public void testResetLinebreak() throws IOException {
		Indent indent = new IndentBuilder().withUnixLinebreak().withCount(1).withResetLevel(32).build();
		
		assertIndentEquals("\n",  indent, 32);
		assertIndentEquals("\n ",  indent, 33);
	}

	@Test
	public void testReset1() throws IOException {
		Indent indent = new IndentBuilder().withUnixLinebreak().withCount(1).withResetLevel(6).build();		
		assertTrue(indent instanceof PreparedResetIndent); // optimized instance
		
		assertResetLevel(indent, 6);
	}
	

	
}
