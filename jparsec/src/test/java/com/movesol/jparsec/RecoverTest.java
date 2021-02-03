package com.movesol.jparsec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
    		.recover(ped -> String.format("%s at %d, encountered %s, unexpected %s, expected %s", ped.getFailureMessage(), ped.getIndex(), ped.getEncountered(), ped.getUnexpected(), ped.getExpected()), Parsers.never())
    		.followedBy(Parsers.ANY_TOKEN.skipMany());

    @Test
    public void test2() {
        String res1 = syntax2.from(terms.tokenizer(), Scanners.WHITESPACES.optional()).parse("foo bar baz");
        String res2 = syntax2.from(terms.tokenizer(), Scanners.WHITESPACES.optional()).parse("foo bar qux");

        assertEquals("correct syntax failed", "foo bar baz", res1);
        assertEquals("incorrect syntax failed", "null at 8, encountered qux, unexpected null, expected [baz]", res2);
    }
}
