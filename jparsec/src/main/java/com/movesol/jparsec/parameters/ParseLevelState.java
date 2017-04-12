package com.movesol.jparsec.parameters;

import com.movesol.jparsec.Token;

public class ParseLevelState {
  private Token first;
  private int firstIndex;
  private Token last;
  private int lastIndex;
  private Parameters params;
  private String module;
  
  public ParseLevelState(Token first, int firstIndex, Token last, int lastIndex, String module, Parameters params) {
    this.first = first;
    this.last = last;
    this.module = module;
    this.params = params;
    this.lastIndex = lastIndex;
    this.firstIndex = firstIndex;
  }
  
  public String getModule() {
		return module;
	}

	public Token getFirstToken() {
    return first;
  }

  public Token getLastToken() {
    return last;
  }
  
  public Parameters getParams() {
    return params;
  }

	public int getFirstTokenIndex() {
		return firstIndex;
	}

	public int getLastTokenIndex() {
		return lastIndex;
	}

}
