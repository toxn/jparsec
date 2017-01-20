/*****************************************************************************
 * Copyright (C) Codehaus.org                                                *
 * ------------------------------------------------------------------------- *
 * Licensed under the Apache License, Version 2.0 (the "License");           *
 * you may not use this file except in compliance with the License.          *
 * You may obtain a copy of the License at                                   *
 *                                                                           *
 * http://www.apache.org/licenses/LICENSE-2.0                                *
 *                                                                           *
 * Unless required by applicable law or agreed to in writing, software       *
 * distributed under the License is distributed on an "AS IS" BASIS,         *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
 * See the License for the specific language governing permissions and       *
 * limitations under the License.                                            *
 *****************************************************************************/

package com.movesol.jparsec.examples.bnf.parser;

import com.movesol.jparsec.Indentation;
import com.movesol.jparsec.Parser;
import com.movesol.jparsec.Parsers;
import com.movesol.jparsec.Scanners;
import com.movesol.jparsec.Terminals;
import com.movesol.jparsec.misc.Mapper;

/**
 * Parses terminals in a bnf.
 * 
 * @author benyu
 */
public final class TerminalParser {
  
  private static final String[] OPERATORS = {"*", "+", "?", "|", "::=", "(", ")"};
  private static final Terminals TERMS = Terminals.operators(OPERATORS);
  private static final Parser<Void> COMMENT = Scanners.lineComment("#");
  private static final Parser<String> LITERAL = Parsers.or(
      Terminals.StringLiteral.DOUBLE_QUOTE_TOKENIZER,
      Terminals.StringLiteral.SINGLE_QUOTE_TOKENIZER);
  private static final Parser<?> IDENT = Terminals.Identifier.TOKENIZER;
  static final Parser<?> TOKENIZER = Parsers.<Object>or(TERMS.tokenizer(), LITERAL, IDENT);
  static final Indentation INDENTATION = new Indentation();
  
  static Parser<?> term(String name) {
    return Mapper.skip(TERMS.token(name));
  }
  
  static <T> T parse(Parser<T> parser, String source) {
    return parser.from(INDENTATION.lexer(TOKENIZER, Indentation.WHITESPACES.or(COMMENT).many()))
        .parse(source);
  }
}
