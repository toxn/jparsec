package com.movesol.jparsec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
	    Parsers.never(), Parsers.ANY_TOKEN.skipMany());

	@Test
	public void test1() {
		Object res = SYNTAX.from(TERMS.tokenizer(), Scanners.WHITESPACES.optional()).parse("foo bar baz");

		assertTrue(res == Boolean.TRUE);
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
		String res2 = SYNTAX3.from(TERMS.tokenizer(), Scanners.WHITESPACES.optional()).parse("foo bar qux");

		assertEquals("correct syntax failed", "foo bar baz", res1);
		assertEquals("incorrect syntax failed", "null at 8, encountered qux, unexpected null, expected [baz]", res2);
	}
}
