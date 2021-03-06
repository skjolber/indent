package com.github.skjolber.indent;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.skjolber.indent.Indent;
import com.github.skjolber.indent.IndentBuilder;
import com.github.skjolber.indent.IndentFactory;

public class IdentTest extends AbstractIndentTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testDefaultsBuilder() throws IOException {
		Indent indent = new IndentBuilder().build();
		
		assertEquals(Indent.defaultCharacter, indent.getCharacter());
		assertEquals(Indent.defaultCount, indent.getCount());
		assertEquals(Indent.defaultPreparedLevels, indent.getPreparedLevels());
		assertEquals(Indent.defaultLinebreak, indent.getLinebreakType());
		assertIndentEquals("  ", indent, 1);
	}
	
	@Test
	public void testDefaultConstructor() throws IOException {
		Indent indent = new Indent();
		
		assertEquals(Indent.defaultCharacter, indent.getCharacter());
		assertEquals(Indent.defaultCount, indent.getCount());
		assertEquals(Indent.defaultPreparedLevels, indent.getPreparedLevels());
		assertEquals(Indent.defaultLinebreak, indent.getLinebreakType());
		assertIndentEquals("  ", indent, 1);
	}
	
	@Test
	public void testDefaultFactory() throws IOException {
		IndentFactory factory = new IndentFactory();
		
		assertEquals(Indent.defaultCharacter, factory.getCharacter());
		assertEquals(Indent.defaultCount, factory.getCount());
		assertEquals(Indent.defaultPreparedLevels, factory.getPreparedLevels());
		assertEquals(Indent.defaultLinebreak, factory.getLinebreakType());
		assertEquals(-1, factory.getResetLevel());
	}	
	
	@Test
	public void testInvalidDefaults1() {
		exception.expect(Exception.class);
		
		new IndentBuilder().withCount(-1).build();
	}

	@Test
	public void testInvalidDefaults2() {
		exception.expect(Exception.class);
		
		new IndentBuilder().withPreparedLevels(-1).build();
	}
	
	@Test
	public void testInvalidDefaults3() {
		exception.expect(Exception.class);
		
		new Indent(Indent.defaultCharacter, Indent.defaultCount, Indent.defaultPreparedLevels, null);
	}

	@Test
	public void testUnixLinebreak() throws IOException {
		Indent indent = new IndentBuilder().withUnixLinebreak().build();

		assertIndentEquals("\n  ", indent, 1);
	}
	
	@Test
	public void testWindowsLinebreak() throws IOException {
		Indent indent = new IndentBuilder().withWindowsLinebreak().build();
		
		assertIndentEquals("\r\n", indent, 0);
	}	
	
	@Test
	public void testTabs() throws IOException {
		Indent indent = new IndentBuilder().withUnixLinebreak().withTab().withCount(1).build();
		
		assertIndentEquals("\n\t", indent, 1);
	}
	
	@Test
	public void testSpaces() throws IOException {
		Indent indent = new IndentBuilder().withUnixLinebreak().withSpace(2).build();
		
		assertIndentEquals("\n  ", indent, 1);
	}

	@Test
	public void testLongerThanPreparedLine1() throws IOException {
		Indent indent = new IndentBuilder().withUnixLinebreak().withCount(1).withPreparedLevels(4).build();
		
		assertLevel(indent, 256);
	}
	
	@Test
	public void testLongerThanPreparedLine2() throws IOException {
		Indent indent = new IndentBuilder().withUnixLinebreak().withCount(1).withPreparedLevels(5).build();
		
		assertLevel(indent, 127);
	}

}
