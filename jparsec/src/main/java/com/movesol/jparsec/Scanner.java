package com.movesol.jparsec;

/**
 * An interface to implement custom scanners to be used with method {@ Scanners.from()}
 */
public interface Scanner {
	public boolean scan(CharacterReader cscanner);
}
