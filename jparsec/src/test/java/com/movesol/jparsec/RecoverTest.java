package com.movesol.jparsec;

import static com.movesol.jparsec.Parsers.or;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.movesol.jparsec.error.ParseErrorDetails;

public class RecoverTest {
    static final Terminals terms = Terminals.operators().words(Scanners.IDENTIFIER).keywords("foo", "bar", "baz")
            .build();

    static final Parser<Boolean> syntax = terms.token("foo")
    		.followedBy(terms.token("bar"))
    		.followedBy(terms.token("baz"))
    		//.next(Parsers.EOF)
            .map(x -> true);

    @Test
    public void test1() {
        Object res = syntax.from(terms.tokenizer(), Scanners.WHITESPACES.optional()).parse("foo bar baz");

        assertTrue(res == Boolean.TRUE);
    }

    static final Parser<String> syntax2 = terms.token("foo")
    		.followedBy(terms.token("bar"))
            .followedBy(terms.token("baz"))
    		.next(Parsers.EOF)
    		.source()
    		.recover(ParseErrorDetails::getFailureMessage, Parsers.ANY_TOKEN.skipMany().followedBy(Parsers.EOF));

    @Test
    public void test2() {
        String res1 = syntax2.from(terms.tokenizer(), Scanners.WHITESPACES.optional()).parse("foo bar baz");
        String res2 = syntax2.from(terms.tokenizer(), Scanners.WHITESPACES.optional()).parse("foo bar qux");

        assertEquals("", "", res1);
        assertEquals("", "", res2);
    }
}
