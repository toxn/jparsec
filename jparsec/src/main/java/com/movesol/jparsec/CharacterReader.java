package com.movesol.jparsec;

public interface CharacterReader {
	
	/**
	 * Character returned when the end of file is reached
	 */
	public static final int EOF = -1;
	
	/**
	 * Return the next character or EOF.
	 * @return the next character or EOF.
	 */
	public int read();
	
	/**
	 * Rewind the reader back to n characters
	 * @param n
	 */
	public void rewind(int n);

	/**
	 * Rewind the reader back to 1 character
	 * @param n
	 */
	public void rewind();

}
