/*****************************************************************************
 * Copyright (C) jparsec.org                                                *
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
package com.movesol.jparsec;

import com.movesol.jparsec.internal.util.Objects;

/**
 * Represents any token with a token value and the 0-based index in the source.
 * 
 * @author Ben Yu
 */
public class Token {
  
  private final int ind;
  private final int len;
  private final Object value;
  private final String module;
  
  /**
   * @param index the starting index.
   * @param length the length of the token.
   * @param value the token value.
   */
  public Token(int index, int length, Object value) {
  	this(index, length, value, null);
  }

  /**
   * @param index the starting index.
   * @param length the length of the token.
   * @param value the token value.
   * @param module the token module.
   */
  public Token(int index, int length, Object value, String module) {
    this.ind = index;
    this.len = length;
    this.value = value;
    this.module = module;
  }
  
  /** Returns the length of the token. */
  public int length() {
    return len;
  }
  
  /** Returns the index of the token in the original source. */
  public int index() {
    return ind;
  }
  
  /** Returns the token value. */
  public Object value() {
    return value;
  }
  
  /** Returns the token module. */
  public String module() {
  	return module;
  }
  
  /** Returns the string representation of the token value. */
  @Override public String toString() {
    return String.valueOf(value);
  }
  
  @Override public int hashCode() {
    return (ind * 31 + len) * 31 + Objects.hashCode(value);
  }
  
  @Override public boolean equals(Object obj) {
    if (obj instanceof Token) {
      return equalToken((Token) obj);
    }
    return false;
  }
  
  private boolean equalToken(Token that) {
    return ind == that.ind && len == that.len && Objects.equals(value, that.value);
  }
}
