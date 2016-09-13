package org.codehaus.jparsec;

import static org.codehaus.jparsec.Parsers.or;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class PositionsTest {
	@Test
	public void position1() {
		Parser<?> ID = Scanners.IDENTIFIER.position(2).map(a -> Tokens.identifier(a.toString()));
		Parser<?> TOKENIZER = or(ID, Scanners.IDENTIFIER);
		Parser<List<Token>> LEXER = TOKENIZER.lexer(Scanners.WHITESPACES.many());
		
		List<Token> tkns = (List<Token>) LEXER.parse("  ABCD EFGH");

		assertEquals(Tokens.identifier("ABCD"), tkns.get(0).value());
		assertEquals("EFGH", tkns.get(1).value());
	}

	@Test
	public void position2() {
		Parser<?> ID = Scanners.IDENTIFIER.position(2).or(Scanners.IDENTIFIER.position(7)).map(a -> Tokens.identifier(a.toString()));
		Parser<?> TOKENIZER = or(ID, Scanners.IDENTIFIER);
		Parser<List<Token>> LEXER = TOKENIZER.lexer(Scanners.WHITESPACES.many());
		
		List<Token> tkns = (List<Token>) LEXER.parse("  ABCD EFGH");

		assertEquals(Tokens.identifier("ABCD"), tkns.get(0).value());
		assertEquals(Tokens.identifier("EFGH"), tkns.get(1).value());
	}

}