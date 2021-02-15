package com.movesol.jparsec;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.movesol.jparsec.error.ParserException;

public class RecoverTest {
	static final Terminals TERMS = Terminals.operators().words(Scanners.IDENTIFIER).keywords("foo", "bar", "baz").build();

	static final Parser<Token> PHRASE = TERMS.token("foo").followedBy(TERMS.token("bar")).followedBy(TERMS.token("baz"));

	static final Parser<Boolean> SYNTAX = PHRASE.map(x -> true);

	static final Parser<String> SYNTAX2 = PHRASE.source()
	    .recover(ped -> String.format("%s at %d, encountered %s, unexpected %s, expected %s", ped.getFailureMessage(),
	        ped.getIndex(), ped.getEncountered(), ped.getUnexpected(), ped.getExpected()), Parsers.never())
	    .followedBy(Parsers.ANY_TOKEN.skipMany());

	static final Parser<String> SYNTAX3 = PHRASE.source().recover(
	    ped -> String.format("%s at %d, encountered %s, unexpected %s, expected %s", ped.getFailureMessage(),
	        ped.getIndex(), ped.getEncountered(), ped.getUnexpected(), ped.getExpected()),
	    Parsers.never(), Parsers.EOF.cast());

	@Test
	public void test1() {
		Boolean res = SYNTAX.from(TERMS.tokenizer(), Scanners.WHITESPACES.optional()).parse("foo bar baz");
		assertTrue(res);
	}

	@Test
	public void test2() {
		String res1 = SYNTAX2.from(TERMS.tokenizer(), Scanners.WHITESPACES.optional()).parse("foo bar baz");
		String res2 = SYNTAX2.from(TERMS.tokenizer(), Scanners.WHITESPACES.optional()).parse("foo bar qux");

		assertEquals("correct syntax failed", "foo bar baz", res1);
		assertEquals("incorrect syntax failed", "null at 8, encountered qux, unexpected null, expected [baz]", res2);
	}

	@Test
	public void test3() {
		String res1 = SYNTAX3.from(TERMS.tokenizer(), Scanners.WHITESPACES.optional()).parse("foo bar baz");
		try {
			String res2 = SYNTAX3.from(TERMS.tokenizer(), Scanners.WHITESPACES.optional()).parse("foo bar qux");
			assertEquals("incorrect syntax failed", "null at 8, encountered qux, unexpected null, expected [baz]", res2);
		} catch (ParserException ex) {
			assertEquals(8, ex.getErrorDetails().getIndex());
			assertEquals("qux", ex.getErrorDetails().getEncountered());
			assertEquals(asList("baz", "EOF"), ex.getErrorDetails().getExpected());
		}
		String res3 = SYNTAX3.from(TERMS.tokenizer(), Scanners.WHITESPACES.optional())
				.parse("foo bar qux quux corge grault garply waldo fred plugh xyzzy thud baz");

		assertEquals("correct syntax failed", "foo bar baz", res1);
		assertEquals("incorrect syntax failed", "null at 8, encountered qux, unexpected null, expected [baz]", res3);
	}
}
