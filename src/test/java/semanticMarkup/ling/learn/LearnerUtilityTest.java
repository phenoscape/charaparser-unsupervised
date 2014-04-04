package semanticMarkup.ling.learn;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import semanticMarkup.know.lib.WordNetPOSKnowledgeBase;
import semanticMarkup.ling.Token;
import semanticMarkup.ling.learn.auxiliary.GetNounsAfterPtnReturnValue;
import semanticMarkup.ling.learn.auxiliary.StringAndInt;
import semanticMarkup.ling.learn.dataholder.DataHolder;
import semanticMarkup.ling.learn.dataholder.WordPOSKey;
import semanticMarkup.ling.learn.utility.LearnerUtility;
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
	public void testGetFirstNWords() {
		List<String> nWords = new ArrayList<String>();
		assertEquals("PopulateSent Helper - getFirstNWords: none", nWords,
				tester.getFirstNWords(null, -1));
		assertEquals("PopulateSent Helper - getFirstNWords: none", nWords,
				tester.getFirstNWords("", -1));
		assertEquals("PopulateSent Helper - getFirstNWords: none", nWords,
				tester.getFirstNWords(null, 1));
		assertEquals("PopulateSent Helper - getFirstNWords: none", nWords,
				tester.getFirstNWords("", 1));
		nWords.add("word1");
		nWords.add("word2");
		assertEquals("PopulateSent Helper - getFirstNWords: none", nWords,
				tester.getFirstNWords("word1 word2 word3 word4", 2));
		assertEquals("PopulateSent Helper - getFirstNWords: none", nWords,
				tester.getFirstNWords("word1 word2", 3));
	}
	
	@Test
	public void testGetAllWords() {
		Map<String, Integer> wordsBefore = new HashMap<String, Integer>();
		wordsBefore.put("word1", 1);
		wordsBefore.put("word2", 2);
		Map<String, Integer> wordsAfter = new HashMap<String, Integer>();
		wordsAfter.put("word1", 2);
		wordsAfter.put("word2", 4);
		wordsAfter.put("word3", 2);
		wordsAfter.put("word4", 1);
		wordsAfter.put("word5", 1);
		assertEquals("PopulateSent Helper - getAllWords", wordsAfter,
				tester.getAllWords("word1 word2 word3 word2 word3 word4 word5",
						wordsBefore));
	}
	
	@Test
	public void testGetSentencePtns(){
		Learner myTester = learnerFactory();		
		Set<String> token = new HashSet<String>();
		token.addAll(Arrays.asList("and or nor".split(" ")));
		token.add("/");
		token.add("and / or");
		
		List<String> words = new ArrayList<String>();
		words.addAll(Arrays
				.asList("distinct crown and <N>base</N> demarcated <B>by</B> <B>a</B> <N>constriction</N> <B>(</B> neck"
						.split(" ")));
		String target = "qq&nqbbnbq";
		
		assertEquals("getSentencePtns", target, myTester.getLearnerUtility().getSentencePtn(myTester.getDataHolder(), token, 80, words));
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
	public void testIterable2Pattern(){
		assertEquals("Iterable2Pattern - null", "", tester.Iterable2Pattern(null));
		assertEquals("Iterable2Pattern - empty input", "", tester.Iterable2Pattern(new LinkedList<String>()));
		
		Set<String> input = new HashSet<String>();
		input.add("word1");
		input.add("word2");
		input.add("word3");
		input.add("(");
		assertEquals("Iterable2Pattern - set input", "word1|word2|word3|\\(", tester.Iterable2Pattern(input));
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
	
	 @Test
	    public void testGetPSWord(){
			Learner myTester = learnerFactory();
			
	    	myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"acrodin", "s", "role", "0", "0", null, null}));
	    	myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"areas", "p", "role", "0", "0", null, null}));
	    	myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"(", "p", "role", "0", "0", null, null}));
	    	
	    	
	    	Set<String> target = new HashSet<String>();
	    	target.add("acrodin");
	    	target.add("areas");
	    	
	    	assertEquals("getPSWords", target, myTester.getLearnerUtility().getPSWords(myTester.getDataHolder()));
	    }
	    
	    @Test
	    public void testGetO() {
			Learner myTester = learnerFactory();

	    	myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent", "osent","lead","status","ignore","m","type"}));
	    	myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent", "osent","lead","status",null,"m","type"}));
	    	myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent", "osent","lead","status","taga tagb","m","type"}));
	    	myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent", "osent","lead","status","taga[tagb]","m","type"}));
	    	myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent", "osent","lead","status","tag1","m","type"}));
	    	myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent", "osent","lead","status","tag2","m","type"}));
	    	
	    	Set<String> target = new HashSet<String>();
	    	target.add("tag1");
	    	target.add("tag2");
	    	
	    	assertEquals("getOs", target, myTester.getLearnerUtility().getOrgans(myTester.getDataHolder()));
	    }
	    
	    @Test
	    public void testGetModifiers(){
			Learner myTester = learnerFactory();
			
			myTester.getDataHolder().add2Holder(DataHolder.MODIFIER, Arrays.asList(new String[] {"basal", "1", "false"}));
			myTester.getDataHolder().add2Holder(DataHolder.MODIFIER, Arrays.asList(new String[] {"endoskeletal", "1", "false"}));
			myTester.getDataHolder().add2Holder(DataHolder.MODIFIER, Arrays.asList(new String[] {"\\", "1", "false"}));
			myTester.getDataHolder().add2Holder(DataHolder.MODIFIER, Arrays.asList(new String[] {null, "1", "false"}));
	    	
	    	Set<String> target = new HashSet<String>();
	    	target.add("basal");
	    	target.add("endoskeletal");
	    	
	    	assertEquals("getModifiers", target, myTester.getLearnerUtility().getModifiers(myTester.getDataHolder()));
	    }
	    
	    @Test
	    public void testGetBoundaries(){
			Learner myTester = learnerFactory();

			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"\\", "b", "role", "0", "0", null, null}));
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {")", "b", "role", "0", "0", null, null}));
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"[", "b", "role", "0", "0", null, null}));
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"}", "b", "role", "0", "0", null, null}));
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {".", "b", "role", "0", "0", null, null}));
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"|", "b", "role", "0", "0", null, null}));
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"+", "b", "role", "0", "0", null, null}));
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"*", "b", "role", "0", "0", null, null}));
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"?", "b", "role", "0", "0", null, null}));
			
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {",", "b", "role", "0", "0", null, null}));
			
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"about", "b", "role", "0", "0", null, null}));
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"along", "b", "role", "0", "0", null, null}));
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"acrodin", "s", "role", "0", "0", null, null}));
			
			Set<String> targetWords = new HashSet<String>();
			targetWords.addAll(Arrays.asList("about along".split(" ")));
			
			Set<String> targetMarks = new HashSet<String>();
			targetMarks.addAll(Arrays.asList(") \\ [ } . | * + ?".split(" ")));
			
			List<Set<String>> target = new LinkedList<Set<String>>();
			target.add(targetWords);
			target.add(targetMarks);
			
			assertEquals("getBoundaries", target, myTester.getLearnerUtility().getBoundaries(myTester.getDataHolder()));
	    }
	    
	    @Test
	    public void testGetProperNouns(){
			Learner myTester = learnerFactory();
			
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"propernoun1", "z", "*", "0", "0", "", null}));
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"acrodin", "s", "role", "0", "0", null, null}));
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"propernoun2", "z", "role", "0", "0", null, null}));
			myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"(", "z", "role", "0", "0", null, null}));
			
			
			Set<String> target = new HashSet<String>();
			target.add("propernoun1");
			target.add("propernoun2");
			
			assertEquals("getProperNouns", target, myTester.getLearnerUtility().getProperNouns(myTester.getDataHolder()));
	    }
	    
	private Learner learnerFactory(){
		Learner myTester;

		Configuration myConfiguration = new Configuration();
		ITokenizer tokenizer = new OpenNLPTokenizer(
				myConfiguration.getOpenNLPTokenizerDir());
		ITokenizer sentenceDetector = new OpenNLPSentencesTokenizer(
				myConfiguration.getOpenNLPSentenceDetectorDir());
		WordNetPOSKnowledgeBase wordNetPOSKnowledgeBase = null;
		try {
			wordNetPOSKnowledgeBase = new WordNetPOSKnowledgeBase(myConfiguration.getWordNetDictDir(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		LearnerUtility myLearnerUtility = new LearnerUtility(sentenceDetector,
				tokenizer, wordNetPOSKnowledgeBase);
		myTester = new Learner(myConfiguration, tokenizer, myLearnerUtility);

		return myTester;
	}
	
	@Test
	public void testTagAllSentence(){
		assertEquals("tagAllSentenceHelper", "word1 word2", tester.tagAllSentencesHelper("word1 <tag> word2"));
		assertEquals("tagAllSentenceHelper", "3_nerved , cup_shaped , 3 - 5 ( - 7 ) _nerved", tester.tagAllSentencesHelper(" 	 3  - nerved, cup- shaped, 3-5 (-7) -nerved		 "));
	}
	

	@Test
	public void testAnnotateSentence(){
		// Test Case 1: See testUnknownWordBootstrapping - Postprocessing
		
		// Test Case 2:
		String input = "stems usually erect , sometimes prostrate to ascending ( underground stems sometimes woody caudices or rhizomes , sometimes fleshy ) .";
		String expected1 = 
				"stems usually erect , sometimes prostrate to ascending <B>(</B> underground stems sometimes woody caudices or rhizomes , sometimes fleshy <B>)</B> .";
		String expected2 = 
				"stems <B>usually</B> <B>erect</B> , sometimes prostrate to ascending <B>(</B> underground stems sometimes woody caudices or rhizomes , sometimes fleshy <B>)</B> .";
		Set<String> boundaryWords = new HashSet<String>();
		Set<String> boundaryMarks = new HashSet<String>();
		boundaryMarks.addAll(Arrays.asList("( ) [ ] { }".split(" ")));
		boundaryWords.addAll(Arrays.asList("under up upward usually erect villous was weakly".split(" ")));
		
		assertEquals("annotateSentenceHelper1", expected1, tester.annotateSentenceHelper(input, boundaryMarks, "B", false));
		assertEquals("annotateSentenceHelper1", expected2, tester.annotateSentenceHelper(expected1, boundaryWords, "B", true));
		
		assertEquals("annotateSentenceHelper2", " word ", tester.annotateSentenceHelper2("<B> 	 </B> word <N> 	 </N>"));
		assertEquals("annotateSentenceHelper2", "<B> 	 </C> word ", tester.annotateSentenceHelper2("<B> 	 </C> word <B> 	 </B>"));
		assertEquals("annotateSentenceHelper2", "and", tester.annotateSentenceHelper2("<B>and</B>"));
		assertEquals("annotateSentenceHelper2", "and</B>", tester.annotateSentenceHelper2("and</B>"));
	}
	
//	@Test
//	public void testDoItCaseHandle(){
//		// case x: boundary case
//		Learner myTesterBoundary = learnerFactory();
//		assertEquals("CaseHandle - boundary case", null, myTesterBoundary.doItCaseHandle(null, null));   
//		assertEquals("CaseHandle - boundary case", new StringAndInt("",0), myTesterBoundary.doItCaseHandle("", ""));   
//		
//        // case 1
//		Learner myTester1 = learnerFactory();
//		myTester1.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"submandibular", "s", "", "0", "0", null, null}));
//		myTester1.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"submandibulars", "p", "", "0", "0", null, null}));
//		
//		assertEquals("CaseHandle - case 1", new StringAndInt("submandibulars",0), myTester1.doItCaseHandle("submandibulars", "submandibulars"));
//		
//		// case 2
//		Learner myTester2 = learnerFactory();
//		myTester2.getDataHolder().add2Holder(DataHolder.SENTENCE, 
//				Arrays.asList(new String[] {"src", 
//						"<N>stems</N> <B>usually</B> erect , sometimes prostrate to ascending <B>(</B> underground <N>stems</N> sometimes woody <O>caudices</O> or rhizomes , sometimes fleshy <B>)</B> . ", 
//						"Stems usually erect, sometimes prostrate to ascending (underground stems sometimes woody caudices or rhizomes, sometimes fleshy ).",
//						"lead","status",null,"m","type"}));
//		myTester2.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"stems", "p", "", "0", "0", null, null}));
//		myTester2.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"stem", "s", "", "0", "0", null, null}));		
//		myTester2.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"usually", "s", "", "0", "0", null, null}));
//		String sentence = 
//				"stems usually erect , sometimes prostrate to ascending ( underground stems sometimes woody caudices or rhizomes , sometimes fleshy ) .";
//		String lead = "stems usually erect";
//		assertEquals("CaseHandle - case 2", new StringAndInt("stems",1), myTester2.doItCaseHandle(sentence, lead));
//		assertEquals("CaseHandle - case 2, updatePOS - case 2.1, resolveConfict, changePOS - case 2", true, myTester2.getDataHolder().getWordPOSHolder().containsKey(new WordPOSKey("usually", "b")));
//		assertEquals("CaseHandle - case 2, discountPOS - all", false, myTester2.getDataHolder().getWordPOSHolder().containsKey(new WordPOSKey("usually", "s")));
//        
//        // case 3.2
//		// This also tests method markKnown() - case 1.1
//		Learner myTester32 = learnerFactory();
//		myTester32.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"teeth", "p", "role", "1", "1", "", ""}));
//		myTester32.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"with", "b", "role", "1", "1", "", ""}));
//		
//		myTester32.getDataHolder().add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList(new String[] {"bicuspid", "unknown"}));
//		myTester32.getDataHolder().add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList(new String[] {"multicuspid", "unknown"}));
//		myTester32.getDataHolder().add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList(new String[] {"tricuspid", "unknown"}));
//		
//		myTester32.getDataHolder().add2Holder(DataHolder.SINGULAR_PLURAL, Arrays.asList(new String[] {"tooth", "teeth"}));
//		
//        assertEquals("CaseHandle - case 3.2", new StringAndInt("teeth",4), 
//            myTester32.doItCaseHandle("teeth unicuspid with crowns posteriorly curved along the main axis of the mandible , organized into a long series of equally_ sized teeth", 
//                "teeth unicuspid with"));   
//        
//        // case 4
//        // case 4.2	
//		Learner myTester42 = learnerFactory();
//		// test case 1
//		myTester42.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"teeth", "p", "role", "1", "1", "", ""}));
//		myTester42.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"variously", "b", "role", "0", "0", "", ""}));
//		
//		myTester42.getDataHolder().add2Holder(DataHolder.SINGULAR_PLURAL, Arrays.asList(new String[] {"tooth", "teeth"}));
//		myTester42.getDataHolder().add2Holder(DataHolder.SINGULAR_PLURAL, Arrays.asList(new String[] {"base", "bases"}));
//
//        assertEquals("CaseHandle - case 4.2", new StringAndInt("teeth",0), 
//                myTester42.doItCaseHandle("teeth variously arranged , but never very numerous , equally_ sized and regularly curved posteriorly along main axis of mandible", 
//                    "teeth variously arranged")); 
//        
//        //case 4.2 - test case 2
//		myTester42.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"muscle", "s", "role", "0", "0", "", ""}));
//		myTester42.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"with", "b", "role", "0", "0", "", ""}));
//        assertEquals("CaseHandle - case 4.2", new StringAndInt("hyohyoidei muscle",1), 
//                myTester42.doItCaseHandle("hyohyoidei muscle with a broad origin across the entire ventral surface and lateral margins of the ventrolateral wings of the urohyal",  
//                    "hyohyoidei muscle with")); 
//        
//        //case 4.2 - test case 2
//		myTester42.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"bases", "p", "role", "0", "0", "", ""}));
//		myTester42.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"of", "b", "role", "2", "2", "", ""}));
//        assertEquals("CaseHandle - case 4.2", new StringAndInt("bases",0), 
//                myTester42.doItCaseHandle("bases of tooth whorls", "bases of")); 
//      
//		
//		// case 5.1.3 and case x
//		Learner myTester513x = learnerFactory();
//		myTester513x.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"styles", "p", "role", "1", "1", "", ""}));
//		myTester513x.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"style", "s", "role", "1", "1", "", ""}));
//		myTester513x.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"branches", "p", "role", "23", "23", "", ""}));
//		myTester513x.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"branch", "s", "role", "23", "23", "", ""}));
//		StringAndInt result513x = myTester513x.doItCaseHandle("styles branches :", "styles branches");
//		StringAndInt target513x = new StringAndInt("branches",1);
//		assertEquals("CaseHandle - case 5.1.3 and case x", result513x, target513x); 
//		
//		Learner myTester52 = learnerFactory();
//		myTester52.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"basal", "b", "role", "30", "30", "", ""}));
//		myTester52.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"leaf", "s", "role", "0", "0", "", ""}));
//		myTester52.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"blades", "p", "role", "63", "63", "", ""}));
//		myTester52.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"linear_lanceolate", "b", "role", "2", "2", "", ""}));
//		myTester52.getDataHolder().add2Holder(DataHolder.MODIFIER, 
//				Arrays.asList(new String[] {"basal", "1", "false"}));
//		StringAndInt result52 = myTester52.doItCaseHandle(
//					"basal leaf blades linear_lanceolate , 3 ?10 cm , margins entire or with remote linear lobes , apices acute ;", 
//					"basal leaf blades");
//		StringAndInt target52 = new StringAndInt("basal leaf blades", 0);
//		assertEquals("CaseHandle - case 5.2", result52, target52);
//
//		
//		// case 6.2
//		Learner myTester62 = learnerFactory();
//		
//		myTester62.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"cauline", "b", "role", "1", "1", "", ""}));
//		myTester62.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"much", "s", "role", "1", "1", "", ""}));
//		
//		myTester62.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"reduced", "b", "role", "11", "11", "", ""}));
//		myTester62.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"distally", "b", "role", "2", "2", "", ""}));
//		
//		StringAndInt returnedValue62 = myTester62.doItCaseHandle(
//				"principal cauline much reduced distally , sessile , bases decurrent or not , as spiny wings ;", 
//				"principal cauline much");
//		assertEquals("CaseHandle - case 6.2", "principal cauline much", returnedValue62.getString());
//		
//		
////		assertEquals(myTester7.doItCase7Helper("^s(\\?)$", "s?");
//		
////		// case 7
////		Learner myTester7 = new Learner(myConfiguration, myUtility);
////		assertEquals(myTester7.doItCase7Helper("^s(\\?)$", "s?");
//		
//		// case 9
//		Learner myTester9 = learnerFactory();
//		myTester9.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"basal", "b", "role", "24", "24", "", ""}));
//		myTester9.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"leaves", "p", "role", "112", "112", "", ""}));
//		myTester9.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"leaf", "s", "role", "112", "112", "", ""}));
//		assertEquals("CaseHandle - case 9", new StringAndInt("basal leaves",0), 
//				myTester9.doItCaseHandle("basal leaves :", "basal leaves")); 		
//    
//        // case 10
//		// case 10.1.1
//		Learner myTester10_1_1 = learnerFactory();
//        myTester10_1_1.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"of", "b", "role", "0", "0", "", ""}));
//        assertEquals("CaseHandle - case 10.1.1", new StringAndInt("teeth",2), 
//                myTester10_1_1.doItCaseHandle("teeth of dentary", 
//                    "teeth of")); 
//        
//        myTester10_1_1.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"on", "b", "role", "4", "4", "", ""}));
//        assertEquals("CaseHandle - case 10.1.1", new StringAndInt("foramina",2), 
//                myTester10_1_1.doItCaseHandle("foramina on external surface of lower jaw", 
//                    "foramina on"));
//        // case 10.1.2
//		Learner myTester10_1_2 = learnerFactory();
//		myTester10_1_2.finiteSetsLoader.run(myTester10_1_2.getDataHolder());
//		
//        assertEquals("CaseHandle - case 10.1.1", new StringAndInt("stems",2), 
//                myTester10_1_2.doItCaseHandle("stems 1 ?several , erect or ascending , densely gray_tomentose ", 
//                    "stems NUM several")); 
//        
//		
//        // case 10.2
//        Learner myTester10_2 = learnerFactory();
//        myTester10_2.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"between", "b", "role", "0", "0", "", ""}));
//        myTester10_2.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"scales", "p", "role", "0", "0", "", ""}));
//		myTester10_2.getDataHolder().add2Holder(DataHolder.SINGULAR_PLURAL, Arrays.asList(new String[] {"scale", "scales"}));
//        
//        assertEquals("CaseHandle - case 10.2", new StringAndInt("",0), 
//                myTester10_2.doItCaseHandle("passes between scales", 
//                    "passes between")); 
//                    
//        // case 0
//        Learner myTester0 = learnerFactory();
//        
//		myTester0.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"does", "b", "role", "0", "0", "", ""}));
//		myTester0.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"not", "b", "role", "0", "0", "", ""}));
//
//        assertEquals("CaseHandle - case 0", new StringAndInt("",0), 
//                myTester0.doItCaseHandle("does not cross over the anterodorsal corner of opercular bone", 
//                    "does not cross"));                     
//        
//	}
	
//	@Test
//	public void testDoItMarkup() {
//		Learner myTester = learnerFactory();
//		
//		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {
//				"src", "sent nor", "osent","lead","status",null,"m","type"}));
//		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {
//				"src", "sent and", "osent","lead","status","","m","type"}));
//		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {
//				"src", "sent", "osent","lead","status","unknown","m","type"}));
////		assertEquals("doItMarkup - case 1", 0, myTester.doItMarkup());
//		
//		assertEquals("doItMarkup - Helper - true", true, myTester.doItMarkupHelper(null));
//		assertEquals("doItMarkup - Helper - true", true, myTester.doItMarkupHelper(""));
//		assertEquals("doItMarkup - Helper - true", true, myTester.doItMarkupHelper("unknown"));
//		assertEquals("doItMarkup - Helper - false", false, myTester.doItMarkupHelper("abc"));
//		
//		assertEquals("doItMarkup - case 1 - true", true, myTester.doItMarkupCase1Helper("postcleithra 2 and 3 fused into a single ossification"));
//		assertEquals("doItMarkup - case 1 - false", false, myTester.doItMarkupCase1Helper("postcleithra 2  3 fused into a single ossification"));
//		
//		assertEquals("doItMarkup - case 2 - true", true, myTester.doItMarkupCase2Helper("ossified as autogenous units"));
//		assertEquals("doItMarkup - case 2 - false", false, myTester.doItMarkupCase2Helper("ossified autogenous units"));
//		
//	}
	
//	@Test
//	public void testGetNounsAfterPtn() {
//		Learner myTester = learnerFactory();
//		
//		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"margins", "p", "role", "0", "0", null, null}));
//		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"often", "b", "role", "0", "0", null, null}));
//		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"??", "b", "role", "0", "0", null, null}));
//		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"deeply", "b", "role", "0", "0", null, null}));
//		
//		List<String> nouns = new ArrayList<String>();
//		nouns.add("margins");
//		List<String> nounPtn = new ArrayList<String>();
//		nounPtn.add("p");
//		String bWord = "often";
//		GetNounsAfterPtnReturnValue target = new GetNounsAfterPtnReturnValue(nouns, nounPtn, bWord);
//		
//		assertEquals("getNounsAfterPtn", target, myTester.getNounsAfterPtn("proximal blade margins often ?? deeply lobed , ( spiny in c . benedicta ) , distal ?smaller , often entire , faces glabrous or ?tomentose , sometimes also villous , strigose , or puberulent , often glandular_punctate .", 2));
//		
//	}
	
//	@Test
//	public void testGetPOSptn(){
//		Learner myTester = learnerFactory();
//		
//		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"teeth", "p", "role", "1", "1", "", ""}));
//		
//		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"unicuspid", "p", "role", "1", "3", "", ""}));
//		
//		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, 
//				Arrays.asList(new String[] {"with", "b", "role", "1", "1", "", ""}));
//		
//		assertEquals("getPOSptn", "p?b", myTester.getPOSptn(Arrays.asList("teeth unicuspid with".split(" "))));
//	}
	
//	@Test
//	public void testIsFollowedByNoun() {
//		Learner myTester = learnerFactory();
//		
//		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"rhombic", "b", "role", "0", "0", null, null}));
//		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"bones", "p", "role", "0", "0", null, null}));
//		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"radial", "s", "role", "0", "0", null, null}));
//		
//		assertEquals("isFollowedByNoun - null case", false, myTester.isFollowedByNoun(null, null));
//		assertEquals("isFollowedByNoun - empty case", false, myTester.isFollowedByNoun("", ""));
//		assertEquals("isFollowedByNoun", true, myTester.isFollowedByNoun("foramina on dermal cheek bones", "foramina on"));
//		assertEquals("isFollowedByNoun", true, myTester.isFollowedByNoun("foramina on bones", "foramina on"));
//		assertEquals("isFollowedByNoun", false, myTester.isFollowedByNoun("teeth of dentary", "teeth of"));
//	}
	
//	@Test
//	public void testTagSentence() {		
//		Learner myTester = learnerFactory();
//		myTester.getConfiguration().setMaxTagLength(10);
//		
//		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent", "osent","lead","status","tag","m","type"}));
//
//		assertEquals("tagIt - case 1", false, myTester.tagSentence(0, ""));
//		assertEquals("tagIt - case 2", false, myTester.tagSentence(0, "page"));
//		assertEquals("tagIt - case 3", true, myTester.tagSentence(0, "teeth"));
//		assertEquals("tagIt - max tag length", "teeth", myTester.getDataHolder().getSentenceHolder().get(0).getTag());
//		
//		assertEquals("tagIt - case 3", true, myTester.tagSentence(0, "abcdefghijkl"));
//		//myTester.tagSentence(0, "abcdefghijkl");
//		assertEquals("tagIt - max tag length", "abcdefghij", myTester.getDataHolder().getSentenceHolder().get(0).getTag());
//	}
	
	public LearnerUtility learnerUtilityFactory() {
		Configuration myConfiguration = new Configuration();
		ITokenizer tokenizer = new OpenNLPTokenizer(
				myConfiguration.getOpenNLPTokenizerDir());
		ITokenizer sentenceDetector = new OpenNLPSentencesTokenizer(
				myConfiguration.getOpenNLPSentenceDetectorDir());
		WordNetPOSKnowledgeBase wordNetPOSKnowledgeBase = null;
		try {
			wordNetPOSKnowledgeBase = new WordNetPOSKnowledgeBase(myConfiguration.getWordNetDictDir(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		LearnerUtility myLearnerUtility = new LearnerUtility(sentenceDetector,
				tokenizer, wordNetPOSKnowledgeBase);
		return myLearnerUtility;
	}
	
}
