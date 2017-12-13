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
 * Immutable data holder for 6 values.
 * 
 * @author Ben Yu
 */
public class Tuple6<A, B, C, D, E, F> extends Tuple5<A, B, C, D, E>{
  
  public final F f;
  
  public Tuple6(A a, B b, C c, D d, E e, F f) {
    super(a, b, c, d, e);
    this.f = f;
  }
  
  boolean equals(Tuple6<?, ?, ?, ?, ?, ?> other) {
    return super.equals(other) && Objects.equals(f, other.f);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Tuple6<?, ?, ?, ?, ?, ?>) {
      return equals((Tuple6<?, ?, ?, ?, ?, ?>) obj);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    return super.hashCode() * 31 + Objects.hashCode(f);
  }
  
  @Override
  public String toString() {
    return "(" + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ")";
  }
  
	public <T> T map(Map6<A, B, C, D, E, F, T> map) {
		return map.map(a, b, c, d, e, f);
	}

}
