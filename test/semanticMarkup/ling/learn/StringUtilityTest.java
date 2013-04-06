package semanticMarkup.ling.learn;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilityTest {

	@Test
	public void testStrip() {
		// Method strip
		assertEquals("strip", "word1 word2",
				StringUtility.strip("word1 <abc> word2"));
		assertEquals("strip", "word1 word2",
				StringUtility.strip("word1 <?abc?> word2"));
		assertEquals("strip", "word1 word2",
				StringUtility.strip("word1 &nbsp; word2"));
	}

	@Test
	public void testRemovePunctuation() {
		// Method removePunctuation
		assertEquals("removePunctuation", "word word word wo-rd cant Id end", 
				StringUtility.removePunctuation("word word, word&$% wo-rd can't I'd end.","-"));
	}

	@Test
	public void testTrimString() {
		// Method trimString
		assertEquals("trimString head", "word", StringUtility.trimString("	 	word"));
		assertEquals("trimString tail", "word",
				StringUtility.trimString("word   		 	"));
		assertEquals("trimString head and tail", "word",
				StringUtility.trimString("	 	word	 	 		  "));
	}

	@Test
	public void testProcessWord() {
		// Method processWord
		String word = "<word>word <\\iword>word word</word2>";
		assertEquals("processWord", "word word word",
				StringUtility.processWord(word));
		assertEquals("processWord", "word word word",
				StringUtility.processWord(" 	 word word word"));
		assertEquals("processWord", "word word word",
				StringUtility.processWord("word word word 	 "));
	}

	@Test
	public void testRemoveAll() {
		// Method removeAll
		assertEquals("removeAll - begin", "word word ",
				StringUtility.removeAll("   word word ", "^\\s+"));
		assertEquals("removeAll - end", "word|word", 
				StringUtility.removeAll("word|word|", "\\|+$"));
		assertEquals("removeAll - all", "wordword", 
				StringUtility.removeAll("|word|word|", "\\|"));
		assertEquals("removeAll - remove beginning", "word", 
				StringUtility.removeAll("above word","^("+Constant.STOP+"|"+Constant.FORBIDDEN+")\\b\\s*"));
		assertEquals("removeAll - remove ending 1", "word1 word2", 
				StringUtility.removeAll("word1 word2 or","\\s*\\b("+Constant.STOP+"|"+Constant.FORBIDDEN+"|\\w+ly)$"));
		assertEquals("removeAll - remove ending 2", "word1 word2", 
				StringUtility.removeAll("word1 word2 usually","\\s*\\b("+Constant.STOP+"|"+Constant.FORBIDDEN+"|\\w+ly)$"));
		assertEquals("removeAll - remove middle pronouns", "word1  word2", 
				StringUtility.removeAll("word1 each word2","\\b("+Constant.PRONOUN+")\\b"));
		assertEquals("removeAll - remove beginning and ending", "word", 
				StringUtility.removeAll(" 	word	 	","(^\\s*|\\s*$)"));
	}

}
