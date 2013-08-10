package semanticMarkup.ling.learn;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import semanticMarkup.know.lib.WordNetPOSKnowledgeBase;
import semanticMarkup.ling.Token;
import semanticMarkup.ling.transform.ITokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPSentencesTokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPTokenizer;


public class LearnerUtilityTest {
	
	private LearnerUtility tester;

	@Before
	public void initialize() {
		Configuration myConfiguration = new Configuration();
		ITokenizer sentenceDetector = new OpenNLPSentencesTokenizer(
				myConfiguration.getOpenNLPSentenceDetectorDir());
		ITokenizer tokenizer = new OpenNLPTokenizer(myConfiguration.getOpenNLPTokenizerDir());
		
		WordNetPOSKnowledgeBase wordNetPOSKnowledgeBase = null;
		try {
			wordNetPOSKnowledgeBase = new WordNetPOSKnowledgeBase(myConfiguration.getWordNetDictDir(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		this.tester = new LearnerUtility(sentenceDetector, tokenizer, wordNetPOSKnowledgeBase);
	}
	
	// populate sentence utilities
	@Test
	public void testGetType() {
		assertEquals("PopulateSent Helper - getType: character", 1,
				tester.getType("Brazeau_2009.xml_states737.txt"));
		assertEquals("PopulateSent Helper - getType: description", 2,
				tester.getType("Brazeau_2009.xml_states737_state739.txt"));
		assertEquals("PopulateSent Helper - getType: otherwise", 0,
				tester.getType("saf_saiflkds)dsljf_fls.txt"));
	}

	@Test
	public void testHideMarksInBrackets() {
		assertEquals("Result", null, tester.hideMarksInBrackets(null));
		assertEquals("Result", "", tester.hideMarksInBrackets(""));
		assertEquals("Result", "before (word[DOT]  word) after",
				tester.hideMarksInBrackets("before (word. word) after"));
		assertEquals("Result", "before (word[QST]  word) after",
				tester.hideMarksInBrackets("before (word? word) after"));
		assertEquals("Result", "before (word[SQL]  word) after",
				tester.hideMarksInBrackets("before (word; word) after"));
		assertEquals("Result", "before (word[QLN]  word) after",
				tester.hideMarksInBrackets("before (word: word) after"));
		assertEquals("Result", "before (word[EXM]  word) after",
				tester.hideMarksInBrackets("before (word! word) after"));
	}

	@Test
	public void testRestoreMarksInBrackets() {
		assertEquals("Result", null, tester.restoreMarksInBrackets(null));
		assertEquals("Result", "", tester.restoreMarksInBrackets(""));
		assertEquals("Result", "before (word.  word) after",
				tester.restoreMarksInBrackets("before (word[DOT]  word) after"));
		assertEquals("Result", "before (word?  word) after",
				tester.restoreMarksInBrackets("before (word[QST]  word) after"));
		assertEquals("Result", "before (word;  word) after",
				tester.restoreMarksInBrackets("before (word[SQL]  word) after"));
		assertEquals("Result", "before (word:  word) after",
				tester.restoreMarksInBrackets("before (word[QLN]  word) after"));
		assertEquals("Result", "before (word!  word) after",
				tester.restoreMarksInBrackets("before (word[EXM]  word) after"));
	}

	@Test
	public void testAddSpace() {
		// null
		assertEquals("Result", null, tester.addSpace(null, null));
		// ""
		assertEquals("Result", "", tester.addSpace("", ""));
		assertEquals("Result", "word , word ; word : word ! word ? word . ",
				tester.addSpace("word,word;word:word!word?word.", "\\W"));
	}
	
	@Test
	public void testGetSentenceHead() {
		assertEquals("getSentenceHead - case 0.1 - null input", null, tester.getSentenceHead(null));
		assertEquals("getSentenceHead - case 0.2 - empty string input", "", tester.getSentenceHead(""));
		
		assertEquals("getSentenceHead - case 1", "word1 word2", tester.getSentenceHead("word1 word2 , word3"));
		assertEquals("getSentenceHead - case 1", "word1 word2", tester.getSentenceHead("word1 word2 : word3"));
		assertEquals("getSentenceHead - case 1", "word1 word2", tester.getSentenceHead("word1 word2 ; word3"));
		assertEquals("getSentenceHead - case 1", "word1 word2", tester.getSentenceHead("word1 word2 . word3"));
		assertEquals("getSentenceHead - case 1", "word1 word2", tester.getSentenceHead("word1 word2 [ word3"));
		assertEquals("getSentenceHead - case 1", "word1 word2", tester.getSentenceHead("word1 word2 ( word3"));
		
		assertEquals("getSentenceHead - case 2", "lepidotrichia", tester.getSentenceHead("lepidotrichia , of fin webs"));
		assertEquals("getSentenceHead - case 2", "bases of", tester.getSentenceHead("bases of tooth whorls"));
		
		assertEquals("getSentenceHead - case n", "word1 word2 word3", tester.getSentenceHead("word1 word2 word3"));
	}

	@Test
	public void testHideAbbreviations(){
		assertEquals("hideAbbreviations", "Word1 jr[DOT] name word2.", tester.hideAbbreviations("Word1 jr. name word2."));
		assertEquals("hideAbbreviations", "Word1 Gen[DOT] name word2.", tester.hideAbbreviations("Word1 Gen. name word2."));
		assertEquals("hideAbbreviations", "Word1 uNiV[DOT] name word2.", tester.hideAbbreviations("Word1 uNiV. name word2."));
		assertEquals("hideAbbreviations", "Word1 blvd[DOT] name coRp[DOT] word 3 name word2.", tester.hideAbbreviations("Word1 blvd. name coRp. word 3 name word2."));
		assertEquals("hideAbbreviations", "Word1 bld[DOT] name coRp[DOT] word 3 name word2.", tester.hideAbbreviations("Word1 bld. name coRp. word 3 name word2."));		
		assertEquals("hideAbbreviations", "Word1 uNiV[DOT] name coRp[DOT] word 3 name word2.", tester.hideAbbreviations("Word1 uNiV. name coRp. word 3 name word2."));	
	}
	
	@Test
	public void testRestoreAbbreviations(){
		assertEquals("hideAbbreviations", "Word1 jr. name word2.", tester.restoreAbbreviations("Word1 jr[DOT] name word2."));
		assertEquals("hideAbbreviations", "Word1 Gen. name word2.", tester.restoreAbbreviations("Word1 Gen[DOT] name word2."));
		assertEquals("hideAbbreviations", "Word1 uNiV. name word2.", tester.restoreAbbreviations("Word1 uNiV[DOT] name word2."));
		assertEquals("hideAbbreviations", "Word1 uNiV. name coRp. word 3 name word2.", tester.restoreAbbreviations("Word1 uNiV[DOT] name coRp[DOT] word 3 name word2."));
		assertEquals("hideAbbreviations", "Word1 uNiV. name pde. word 3 name word2.", tester.restoreAbbreviations("Word1 uNiV[DOT] name pde[DOT] word 3 name word2."));
		assertEquals("hideAbbreviations", "Word1 uNiV. name pd. word 3 name word2.", tester.restoreAbbreviations("Word1 uNiV[DOT] name pd[DOT] word 3 name word2."));
	}
	
	@Test
	public void testSegmentSentence(){
		assertEquals("segmentSentence - handle abbreviations", new Token("This is jr. Gates."), tester.segmentSentence("This is jr. Gates. This is second sentence.").get(0));
		assertEquals("segmentSentence - handle abbreviations", new Token("This is second sentence."), tester.segmentSentence("This is jr. Gates. This is second sentence.").get(1));
		assertEquals("segmentSentence - handle abbreviations", new Token("The Energy DEPT. is holding a work shop now."), tester.segmentSentence("The Energy DEPT. is holding a work shop now. This is second sentence. ").get(0));
		assertEquals("segmentSentence - handle abbreviations", new Token("This is second sentence."), tester.segmentSentence("The Energy DEPT. is holding a work shop now. This is second sentence. ").get(1));
		assertEquals("segmentSentence - handle abbreviations", new Token("Mr. Gates from the Energy DEPT. is holding a work shop now."), tester.segmentSentence("Mr. Gates from the Energy DEPT. is holding a work shop now. This is second sentence. ").get(0));
		
	}
	
	@Test
	public void testCollection2Pattern(){
		assertEquals("collection2Pattern - null", "", LearnerUtility.Collection2Pattern(null));
		assertEquals("collection2Pattern - empty input", "", LearnerUtility.Collection2Pattern(new LinkedList<String>()));
		
		Set<String> input = new HashSet<String>();
		input.add("word1");
		input.add("word2");
		input.add("word3");
		assertEquals("collection2Pattern - set input", "word1|word2|word3", LearnerUtility.Collection2Pattern(input));
	}
	
	@Test
	public void testPattern2Set(){
		assertEquals("pattern2Set - null input", new HashSet<String>(),
				LearnerUtility.Pattern2Set(null));
		assertEquals("pattern2Set - empty input", new HashSet<String>(),
				LearnerUtility.Pattern2Set(""));
		
		Set<String> result = new HashSet<String>();
		result.addAll(Arrays.asList("word1|word2|word3".split("|")));
		assertEquals("pattern2Set - normal input", result,
				LearnerUtility.Pattern2Set("word2|word3|word1"));
		
	}
}
