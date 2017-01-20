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

package com.movesol.jparsec.examples.java.parser;

import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import com.movesol.jparsec.examples.java.ast.declaration.AnnotationDef;
import com.movesol.jparsec.examples.java.ast.declaration.ClassDef;
import com.movesol.jparsec.examples.java.ast.declaration.ConstructorDef;
import com.movesol.jparsec.examples.java.ast.declaration.Declaration;
import com.movesol.jparsec.examples.java.ast.declaration.FieldDef;
import com.movesol.jparsec.examples.java.ast.declaration.InterfaceDef;
import com.movesol.jparsec.examples.java.ast.declaration.Member;
import com.movesol.jparsec.examples.java.ast.declaration.MethodDef;
import com.movesol.jparsec.examples.java.ast.declaration.Program;
import com.movesol.jparsec.examples.java.ast.expression.Expression;
import com.movesol.jparsec.examples.java.ast.expression.NewArrayExpression;
import com.movesol.jparsec.examples.java.ast.expression.NewExpression;
import com.movesol.jparsec.examples.java.ast.expression.Operator;
import com.movesol.jparsec.examples.java.ast.expression.QualifiedExpression;
import com.movesol.jparsec.examples.java.ast.statement.Annotation;
import com.movesol.jparsec.examples.java.ast.statement.Modifier;
import com.movesol.jparsec.examples.java.parser.DeclarationParser;
import com.movesol.jparsec.examples.java.parser.ExpressionParser;
import com.movesol.jparsec.examples.java.parser.JavaLexer;
import com.movesol.jparsec.examples.java.parser.StatementParser;
import com.movesol.jparsec.examples.java.parser.TerminalParser;
import com.movesol.jparsec.examples.java.parser.TypeLiteralParser;
import com.movesol.jparsec.examples.sql.ast.FunctionExpression;

/**
 * Integration test for the entire java parser.
 * 
 * @author benyu
 */
public class JavaParserIntegrationTest {

  @Test
  public void testParse() throws Exception {
    parseJavaSourceFiles(
        DeclarationParser.class, StatementParser.class, ExpressionParser.class,
        TerminalParser.class, JavaLexer.class, TypeLiteralParser.class,
        Program.class, Declaration.class, Member.class, Modifier.class,
        FieldDef.class, MethodDef.class, ConstructorDef.class,
        AnnotationDef.class, InterfaceDef.class, ClassDef.class,
        Expression.class, NewArrayExpression.class, QualifiedExpression.class,
        Operator.class, NewExpression.class, FunctionExpression.class, Annotation.class);
  }

  private static void parseJavaSourceFiles(Class<?>...classes) throws IOException {
    for (Class<?> cls : classes) {
      parseJavaSourceFile(cls);
    }
  }
  private static void parseJavaSourceFile(Class<?> cls) throws IOException {
    DeclarationParser.parse(toSourceUrl(cls));
  }
  
  private static URL toSourceUrl(Class<?> cls) {
    URL url = cls.getResource(cls.getSimpleName() + ".java");
    if (url == null) {
      throw new IllegalArgumentException("Cannot find source file for " + cls.getName());
    }
    return url;
  }
}
