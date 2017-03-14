package com.movesol.jparsec.parameters;

import com.movesol.jparsec.Token;

public class ParseLevelState {
  private Token first;
  private Token last;
  private Parameters params;
  private String module;
  
  public ParseLevelState(Token first, Token last, String module, Parameters params) {
    this.first = first;
    this.last = last;
    this.module = module;
    this.params = params;
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

}
