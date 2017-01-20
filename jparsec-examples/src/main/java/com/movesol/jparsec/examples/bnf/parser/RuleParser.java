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

import static com.movesol.jparsec.examples.bnf.parser.TerminalParser.INDENTATION;
import static com.movesol.jparsec.examples.bnf.parser.TerminalParser.term;

import java.util.List;

import com.movesol.jparsec.Parser;
import com.movesol.jparsec.Parsers;
import com.movesol.jparsec.Terminals;
import com.movesol.jparsec.examples.bnf.ast.AltRule;
import com.movesol.jparsec.examples.bnf.ast.LiteralRule;
import com.movesol.jparsec.examples.bnf.ast.Rule;
import com.movesol.jparsec.examples.bnf.ast.RuleDef;
import com.movesol.jparsec.examples.bnf.ast.RuleReference;
import com.movesol.jparsec.examples.bnf.ast.SequentialRule;
import com.movesol.jparsec.functors.Map;
import com.movesol.jparsec.misc.Mapper;

/**
 * Parser for bnf rules.
 * 
 * @author benyu
 */
public final class RuleParser {
  
  static final Parser<Rule> LITERAL =
      curry(LiteralRule.class).sequence(Terminals.StringLiteral.PARSER);
  
  static final Parser<Rule> IDENT = curry(RuleReference.class).sequence(
          Terminals.Identifier.PARSER.notFollowedBy(term("::=")));
  
  static Parser<RuleDef> RULE_DEF = Mapper.curry(RuleDef.class)
      .sequence(Terminals.Identifier.PARSER, term("::="), rule());
  
  public static Parser<List<RuleDef>> RULE_DEFS = RULE_DEF.many();
  
  static Parser<Rule> rule() {
    Parser.Reference<Rule> ref = Parser.newReference();
    Parser<Rule> atom = Parsers.or(LITERAL, IDENT, unit(ref.lazy()));
    Parser<Rule> parser = alternative(sequential(atom));
    ref.set(parser);
    return parser;
  }

  static Parser<Rule> unit(Parser<Rule> rule) {
    return Parsers.or(
        rule.between(term("("), term(")")),
        rule.between(INDENTATION.indent(), INDENTATION.outdent()));
  }
  
  static Parser<Rule> sequential(Parser<Rule> rule) {
    return rule.many1().map(new Map<List<Rule>, Rule>() {
      @Override public Rule map(List<Rule> list) {
        return list.size() == 1 ? list.get(0) : new SequentialRule(list);
      }
    });
  }
  
  static Parser<Rule> alternative(Parser<Rule> rule) {
    return rule.sepBy1(term("|")).map(new Map<List<Rule>, Rule>() {
      @Override public Rule map(List<Rule> list) {
        return list.size() == 1 ? list.get(0) : new AltRule(list);
      }
    });
  }
  
  private static Mapper<Rule> curry(Class<? extends Rule> ruleClass, Object... curryArgs) {
    return Mapper.curry(ruleClass, curryArgs);
  }
}
