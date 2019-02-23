package org.jetbrains.plugins.ideavim.helper;

import com.maddyhome.idea.vim.helper.SearchHelper;
import junit.framework.TestCase;
import org.jetbrains.plugins.ideavim.VimTestCase;

public class SearchHelperTest extends VimTestCase {
  public void testFindNextWord() {
    String text = "first second";
    int nextWordPosition = SearchHelper.findNextWord(text, 0, text.length(), 1, true, false);

    assertEquals(nextWordPosition, text.indexOf("second"));
  }

  public void testFindSecondNextWord() {
    String text = "first second third";
    int nextWordPosition = SearchHelper.findNextWord(text, 0, text.length(), 2, true, false);

    assertEquals(nextWordPosition, text.indexOf("third"));
  }

  public void testFindAfterLastWord() {
    String text = "first second";
    int nextWordPosition = SearchHelper.findNextWord(text, 0, text.length(), 3, true, false);

    assertEquals(nextWordPosition, text.length());
  }

  public void testFindPreviousWord() {
    String text = "first second";
    int previousWordPosition = SearchHelper.findNextWord(text, text.indexOf("second"), text.length(), -1, true, false);

    assertEquals(previousWordPosition, text.indexOf("first"));
  }

  public void testFindSecondPreviousWord() {
    String text = "first second third";
    int previousWordPosition = SearchHelper.findNextWord(text, text.indexOf("third"), text.length(), -2, true, false);

    assertEquals(previousWordPosition, text.indexOf("first"));
  }

  public void testFindBeforeFirstWord() {
    String text = "first second";
    int previousWordPosition = SearchHelper.findNextWord(text, text.indexOf("second"), text.length(), -3, true, false);

    assertEquals(previousWordPosition, text.indexOf("first"));
  }

  public void testFindPreviousWordWhenCursorOutOfBound() {
    String text = "first second";
    int previousWordPosition = SearchHelper.findNextWord(text, text.length(), text.length(), -1, true, false);

    assertEquals(previousWordPosition, text.indexOf("second"));
  }

  public void testIsInHTMLTag() {
    String text = " text1 <tag1> asdf<tag2 lang='en'>tmpfd <br> <tag3>dafdf </tag3>hello <ignore/> :) </tag2></tag1>";
    String answ = "0000000SSSSSS00000SSSSSSSSSSSSSSSS000000SSSS0SSSSSS000000EEEEEEE0000000000000000000EEEEEEEEEEEEEE";

    for (int i = 0; i < text.length(); i++) {
      boolean isStartTag = SearchHelper.isInHTMLTag(text, i, false);
      boolean isEndTag = SearchHelper.isInHTMLTag(text, i, true);
      switch(answ.charAt(i)) {
        case '0':
          assertFalse(isStartTag);
          assertFalse(isEndTag);
          break;
        case 'E':
          assertFalse(isStartTag);
          assertTrue(isEndTag);
          break;
        case 'S':
          assertTrue(isStartTag);
          assertFalse(isEndTag);
          break;
        default:
          fail();
      }
    }
  }
}
