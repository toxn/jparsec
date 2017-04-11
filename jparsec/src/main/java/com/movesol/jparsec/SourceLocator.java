package com.movesol.jparsec;

import com.movesol.jparsec.error.Location;

public interface SourceLocator {

	public Location locate(int index, String module);

}