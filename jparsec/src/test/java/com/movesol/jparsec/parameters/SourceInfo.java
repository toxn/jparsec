package com.movesol.jparsec.parameters;

public class SourceInfo {
	private int start;
	private int end;
	private int startTokenIndex;
	private int endTokenIndex;
  private String filename;
	
  public SourceInfo(String filename, int start, int startTokenIndex, int end, int endTokenIndex) {
		super();
		this.filename = filename;
		this.start = start;
		this.startTokenIndex = startTokenIndex;
		this.end = end;
		this.endTokenIndex = endTokenIndex;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

  public int getStartTokenIndex() {
		return startTokenIndex;
	}

	public int getEndTokenIndex() {
		return endTokenIndex;
	}

	public String getFilename() {
    return filename;
  }
}
