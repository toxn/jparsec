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
package com.movesol.jparsec.functors;

import com.movesol.jparsec.internal.util.Objects;

/**
 * Immutable data holder for 7 values.
 * 
 * @author Ben Yu
 */
public class Tuple7<A, B, C, D, E, F, G> extends Tuple6<A, B, C, D, E, F>{
  
  public final G g;
  
  public Tuple7(A a, B b, C c, D d, E e, F f, G g) {
    super(a, b, c, d, e, f);
    this.g = g;
  }
  
  boolean equals(Tuple7<?, ?, ?, ?, ?, ?, ?> other) {
    return super.equals(other) && Objects.equals(g, other.g);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Tuple7<?, ?, ?, ?, ?, ?, ?>) {
      return equals((Tuple7<?, ?, ?, ?, ?, ?, ?>) obj);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    return super.hashCode() * 31 + Objects.hashCode(g);
  }
  
  @Override
  public String toString() {
    return "(" + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ", " + g + ")";
  }
  
	public <T> T map(Map7<A, B, C, D, E, F, G, T> map) {
		return map.map(a, b, c, d, e, f, g);
	}

}
