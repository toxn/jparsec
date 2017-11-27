package com.movesol.jparsec.parameters;

import java.util.function.Predicate;

import com.movesol.jparsec.Parser.Mode;
import com.movesol.jparsec.Token;

/**
 * Runtime parameters applied to a parse execution.
 * @author Sylvain Colomer
 */
public class Parameters {
	private MapListener mapListener;
	private Mode mode = Mode.PRODUCTION;
	private Predicate<Token> parserStateFilter;
	
	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public MapListener getMapListener() {
		return mapListener;
	}

	public void setMapListener(MapListener mapListener) {
		this.mapListener = mapListener;
	}

	public Predicate<Token> getParserStateFilter() {
		return parserStateFilter;
	}

	public void setParserStateFilter(Predicate<Token> parserStateFilter) {
		this.parserStateFilter = parserStateFilter;
	}
	
}
