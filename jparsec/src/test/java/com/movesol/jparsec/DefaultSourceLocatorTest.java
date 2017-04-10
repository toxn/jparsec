package com.movesol.jparsec;

import org.junit.Test;

import com.movesol.jparsec.CharSequenceSourceLocator;
import com.movesol.jparsec.error.Location;
import com.movesol.jparsec.internal.util.IntList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Unit test for {@link CharSequenceSourceLocator}.
 * 
 * @author Ben Yu
 */
public class DefaultSourceLocatorTest {

  @Test
  public void testLocate_onlyOneLineBreakCharacter() {
    SourceLocator locator = new CharSequenceSourceLocator("\n");
    Location location = locator.locate(0);
    assertEquals(new Location(1, 1), location);
    assertEquals(location, locator.locate(0));
    assertEquals(new Location(2, 1), locator.locate(1));
  }

  @Test
  public void testLocate_emptySource() {
    SourceLocator locator = new CharSequenceSourceLocator("");
    Location location = locator.locate(0);
    assertEquals(new Location(1, 1), location);
    assertEquals(location, locator.locate(0));
  }

  @Test
  public void testBinarySearch_firstElementIsEqual() {
    assertEquals(0, CharSequenceSourceLocator.binarySearch(intList(1, 2, 3), 1));
  }

  @Test
  public void testBinarySearch_firstElementIsBigger() {
    assertEquals(0, CharSequenceSourceLocator.binarySearch(intList(1, 2, 3), 0));
  }

  @Test
  public void testBinarySearch_secondElementIsEqual() {
    assertEquals(1, CharSequenceSourceLocator.binarySearch(intList(1, 2, 3), 2));
  }

  @Test
  public void testBinarySearch_secondElementIsBigger() {
    assertEquals(1, CharSequenceSourceLocator.binarySearch(intList(1, 3, 5), 2));
  }

  @Test
  public void testBinarySearch_lastElementIsEqual() {
    assertEquals(2, CharSequenceSourceLocator.binarySearch(intList(1, 3, 5), 5));
  }

  @Test
  public void testBinarySearch_lastElementIsBigger() {
    assertEquals(2, CharSequenceSourceLocator.binarySearch(intList(1, 3, 5), 4));
  }

  @Test
  public void testBinarySearch_allSmaller() {
    assertEquals(3, CharSequenceSourceLocator.binarySearch(intList(1, 3, 5), 10));
  }

  @Test
  public void testBinarySearch_oneEqualElement() {
    assertEquals(0, CharSequenceSourceLocator.binarySearch(intList(1), 1));
  }

  @Test
  public void testBinarySearch_oneBiggerElement() {
    assertEquals(0, CharSequenceSourceLocator.binarySearch(intList(2), 1));
  }

  @Test
  public void testBinarySearch_oneSmallerElement() {
    assertEquals(1, CharSequenceSourceLocator.binarySearch(intList(0), 1));
  }

  @Test
  public void testBinarySearch_noElement() {
    assertEquals(0, CharSequenceSourceLocator.binarySearch(intList(), 1));
  }

  @Test
  public void testLookup_noLineBreaksScanned() {
    CharSequenceSourceLocator locator = new CharSequenceSourceLocator("whatever", 2, 3);
    assertEquals(new Location(2, 4), locator.lookup(1));
  }

  @Test
  public void testLookup_inFirstLine() {
    CharSequenceSourceLocator locator = new CharSequenceSourceLocator("whatever", 2, 3);
    addLineBreaks(locator, 3, 5, 7);
    assertEquals(new Location(2, 4), locator.lookup(1));
  }

  @Test
  public void testLookup_firstLineBreak() {
    CharSequenceSourceLocator locator = new CharSequenceSourceLocator("whatever", 2, 3);
    addLineBreaks(locator, 3, 5, 7);
    assertEquals(new Location(2, 6), locator.lookup(3));
  }

  @Test
  public void testLookup_firstCharInSecondLine() {
    CharSequenceSourceLocator locator = new CharSequenceSourceLocator("whatever", 2, 3);
    addLineBreaks(locator, 3, 5, 7);
    assertEquals(new Location(3, 1), locator.lookup(4));
  }

  @Test
  public void testLookup_lastCharInSecondLine() {
    CharSequenceSourceLocator locator = new CharSequenceSourceLocator("whatever", 2, 3);
    addLineBreaks(locator, 3, 5, 7);
    assertEquals(new Location(3, 2), locator.lookup(5));
  }

  @Test
  public void testLookup_firstCharInThirdLine() {
    CharSequenceSourceLocator locator = new CharSequenceSourceLocator("whatever", 2, 3);
    addLineBreaks(locator, 3, 5, 7);
    assertEquals(new Location(4, 1), locator.lookup(6));
  }

  @Test
  public void testLookup_lastCharInThirdLine() {
    CharSequenceSourceLocator locator = new CharSequenceSourceLocator("whatever", 2, 3);
    addLineBreaks(locator, 3, 5, 7);
    assertEquals(new Location(4, 2), locator.lookup(7));
  }

  @Test
  public void testLookup_firstCharInLastLine() {
    CharSequenceSourceLocator locator = new CharSequenceSourceLocator("whatever", 2, 3);
    addLineBreaks(locator, 3, 5, 7);
    assertEquals(new Location(5, 1), locator.lookup(8));
  }

  @Test
  public void testLookup_secondCharInLastLine() {
    CharSequenceSourceLocator locator = new CharSequenceSourceLocator("whatever", 2, 3);
    addLineBreaks(locator, 3, 5, 7);
    assertEquals(new Location(5, 2), locator.lookup(9));
  }

  @Test
  public void testScanTo_indexOutOfBounds() {
    CharSequenceSourceLocator locator = new CharSequenceSourceLocator("whatever", 2, 3);
    try {
      locator.scanTo(100);
      fail();
    } catch (StringIndexOutOfBoundsException e) {}
  }

  @Test
  public void testScanTo_indexOnEof() {
    CharSequenceSourceLocator locator = new CharSequenceSourceLocator("foo", 2, 3);
    assertEquals(new Location(2, 6), locator.scanTo(3));
    assertEquals(3, locator.nextIndex);
    assertEquals(3, locator.nextColumnIndex);
  }

  @Test
  public void testScanTo_spansLines() {
    CharSequenceSourceLocator locator = new CharSequenceSourceLocator("foo\nbar\n", 2, 3);
    assertEquals(new Location(3, 1), locator.scanTo(4));
    assertEquals(5, locator.nextIndex);
    assertEquals(1, locator.nextColumnIndex);
  }

  @Test
  public void testScanTo_lastCharOfLine() {
    CharSequenceSourceLocator locator = new CharSequenceSourceLocator("foo\nbar\n", 2, 3);
    assertEquals(new Location(3, 4), locator.scanTo(7));
    assertEquals(8, locator.nextIndex);
    assertEquals(0, locator.nextColumnIndex);
  }

  @Test
  public void testLocate() {
    SourceLocator locator = new CharSequenceSourceLocator("foo\nbar\n", 2, 3);
    assertEquals(new Location(3, 4), locator.locate(7));
    assertEquals(new Location(2, 5), locator.locate(2)); // this will call lookup()
  }
  
  private static void addLineBreaks(CharSequenceSourceLocator locator, int... indices) {
    for (int i : indices) {
      locator.lineBreakIndices.add(i);
    }
  }
  
  private static IntList intList(int... ints) {
    IntList intList = new IntList();
    for (int i : ints) {
      intList.add(i);
    }
    return intList;
  }
}
