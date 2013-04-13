package semanticMarkup.ling.learn;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import semanticMarkup.core.Treatment;

public class LearnerTest {
	

	private Learner tester;

	@Before
	public void initialize() {
		this.tester = new Learner("plain", "res/WordNet/WordNet-3.0/dict");
	}

	@Test
	public void testLearn() {

		String str = "C:/Users/Dongye/Dropbox/ATEST/target/descriptions";
		FileLoader fileLoader = new FileLoader(str);
		fileLoader.load();

		List<Treatment> tms = fileLoader.getTreatmentList();

		DataHolder results = new DataHolder();

		Map<String, String> myHeuristicNounTable = results
				.getHeuristicNounTable();
		myHeuristicNounTable.put("word1", "type1");

		List<Sentence> mySentenceTable = results.getSentenceTable();
		mySentenceTable.add(new Sentence("source1", "sentence1",
				"originalSentence", "lead1", "status1", "tag1", "modifier1",
				"type1"));

		// Learner tester = new Learner("plain","res/WordNet/WordNet-3.0/dict");

		// assertEquals ("learner", results, tester.Learn(tms));

		// results = tester.Learn(tms);

		// assertEquals ("learner", results, tester.Learn(tms));
	}

	@Test
	public void testHandleText() {
		// handleTest (Fully finished - Dongye 01/08)
		// null
		assertEquals("Result", null, tester.handleText(null));
		// ""
		assertEquals("Result", "", tester.handleText(""));
		// remove " and '
		assertEquals("Result", "words word", tester.handleText("word's wo\"rd"));
		// plano - to
		assertEquals("Result", "word to word",
				tester.handleText("word -to word"));
		//
		assertEquals("Result", "word -shaped",
				tester.handleText("word ______shaped"));
		// unhide <i>
		assertEquals("Result", "word <i> word.",
				tester.handleText("word &lt;i&gt; word."));
		// unhide </i>
		assertEquals("Result", "word </i> word.",
				tester.handleText("word &lt;/i&gt; word."));
		// remove 2a. (key marks)
		assertEquals("Result", "word", tester.handleText("7b. word"));
		// remove HTML entities
		assertEquals("Result", "word   word",
				tester.handleText("word &amp; word"));
		// " & " => " and "
		assertEquals("Result", "word and word.",
				tester.handleText("word & word."));
		// "_" => "-"
		assertEquals("Result", "word-word.", tester.handleText("word_word."));
		// absent ; => absent;
		assertEquals("Result", "word; word; word.",
				tester.handleText("word ;word ;word."));
		// absent;blade => absent; blade
		assertEquals("Result", "word; word; word.",
				tester.handleText("word;word;word."));
		assertEquals("Result", "word: word. word.",
				tester.handleText("word:word.word."));
		// 1 . 5 => 1.5
		assertEquals("Result", "word 1.5 word 384739.84 word.",
				tester.handleText("word 1 . 5 word 384739 . 84 word."));
		// #diam . =>diam.
		assertEquals("Result", "word diam. word diam. word.",
				tester.handleText("word diam . word diam . word."));
		// ca . =>ca.
		assertEquals("Result", "word ca. word ca. word.",
				tester.handleText("word ca . word ca . word."));
		// cm|mm|dm|m
		assertEquals("Result", "word 12 cm[DOT] word 376 mm[DOT] word.",
				tester.handleText("word 12 cm . word 376 mm. word."));

	}

	@Test
	public void testHandleSentence() {
		// handleString
		// null
		assertEquals("Result", null, tester.handleSentence(null));
		// ""
		assertEquals("Result", "", tester.handleSentence(""));
		// remove (.a.)
		assertEquals("Result", "word word word word .",
				tester.handleSentence("word (.a.) word (a) word ( a ) word."));
		// remove [.a.]
		assertEquals("Result", "word word word word .",
				tester.handleSentence("word [.a.] word [a] word [ a ] word."));
		// remove {.a.}
		assertEquals("Result", "word word word word .",
				tester.handleSentence("word {.a.} word {a} word { a } word."));
		// to fix basi- and hypobranchial
		assertEquals(
				"Result",
				"word cup_ shaped word cup_ shaped word cup_ shaped word .",
				tester.handleSentence("word cup --- shaped word cup-shaped word cup ---------        shaped word."));

		// multiple spaces => 1 space
		assertEquals("Result", "word word word .",
				tester.handleSentence("word  word	 word."));
		// remove multipe spaces at the beginning
		assertEquals("Result", "word word .",
				tester.handleSentence("  	word word."));
		// remove multipe spaces at the rear
		assertEquals("Result", "word word .",
				tester.handleSentence("word word.    "));
	}


	@Test
	public void testPopulateUnknownWordsTable() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateTable() {
		fail("Not yet implemented");
	}

	@Test
	public void testMarkKnown() {
		fail("Not yet implemented");
	}

	@Test
	public void testInSingularPluralPair() {
		fail("Not yet implemented");
	}

	@Test
	public void testProcessNewWord() {
		fail("Not yet implemented");
	}

	@Test
	public void testSingularPluralVariations() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUnknownWords() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdatePOS() {
		fail("Not yet implemented");
	}

	@Test
	public void testChangePOS() {
		fail("Not yet implemented");
	}

	@Test
	public void testMergeRole() {
		fail("Not yet implemented");
	}

	@Test
	public void testDiscount() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetParentSentenceTag() {
		fail("Not yet implemented");
	}

	@Test
	public void testTagSentWithMT() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMTFromParentTag() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddHeuristicsNouns() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsMatchedWords() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddDescriptors() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNouns() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHeuristicsNouns() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHeuristicsNounsHelper() {
		HashSet<String> words = new HashSet<String>();		
		words.add("septa");
		words.add("word1");
		words.add("septum");
		assertEquals("getHeuristicsNouns - handleSpecialCase 1", "septa[p]", tester.getHeuristicsNounsHelper("septa[s]", words));
		
		assertEquals("isMatchedWords", true, tester.isMatchedWords("and", Constant.FORBIDDEN));
		assertEquals("isMatchedWords", false, tester.isMatchedWords("kahgds", Constant.FORBIDDEN));
	}

	@Test
	public void testGetPresentAbsentNouns() {
		// Method getPresentAbsentNouns
		assertEquals("getPresentAbsentNouns - no present/absent", "",
				tester.getPresentAbsentNouns("only one pair of abcly presen"));
		assertEquals("getPresentAbsentNouns - and|or|to", "",
				tester.getPresentAbsentNouns("only one pair of and present"));
		assertEquals("getPresentAbsentNouns - STOP words", "",
				tester.getPresentAbsentNouns("only one pair of without absent"));
		assertEquals(
				"getPresentAbsentNoun - always|often|seldom|sometimes|[a-z]+lys",
				"",
				tester.getPresentAbsentNouns("only one pair of abcly present"));
		assertEquals("getPresentAbsentNouns - PENDINGS", "circuli[p]",
				tester.getPresentAbsentNouns("only one pair of circuli absent"));
		assertEquals("getPresentAbsentNouns - end with ss", "glass[s]",
				tester.getPresentAbsentNouns("only one pair of glass absent"));
		assertEquals("getPresentAbsentNouns - end with none ss", "computers[p]",
				tester.getPresentAbsentNouns("only one pair of computers absent"));
		assertEquals("getPresentAbsentNouns - teeth", "teeth[p]",
				tester.getPresentAbsentNouns("only one pair of teeth present"));
		assertEquals("getPresentAbsentNouns - not SENDINGS", "serum[s]",
				tester.getPresentAbsentNouns("only one pair of serum absent"));
		assertEquals("getPresentAbsentNouns - SENDINGS", "computer[s]",
				tester.getPresentAbsentNouns("only one pair of computer absent"));
	
	}

	@Test
	public void testCharacterHeuristics() {
		fail("Not yet implemented");
	}

	@Test
	public void testAdd2HeuristicNounTable() {
		fail("Not yet implemented");
	}

	@Test
	public void testFilterOutDescriptors() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTaxonNameNouns() {

		// Nouns rule 0: Taxon name nouns
		Set<String> taxonNames = new HashSet<String>();
		// Method getTaxonNameNouns
		assertEquals("getTaxonNameNouns - not match", taxonNames, tester.getTaxonNameNouns("word word word"));
		assertEquals("getTaxonNameNouns - empty taxon name", taxonNames, tester.getTaxonNameNouns("< i >< / i >"));
		taxonNames.add("word1 word2	word3");
		taxonNames.add("word1");
		taxonNames.add("word2");
		taxonNames.add("word3");
		taxonNames.add("word4 word5");
		taxonNames.add("word4");
		taxonNames.add("word5");
		assertEquals("getTaxonNameNouns - match", taxonNames, tester.getTaxonNameNouns("< i	>word1 word2	word3< /	i>, < i >word4 word5<	/i>"));
	}

	@Test
	public void testGetNounsMecklesCartilage() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNounsRule1() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNounsRule2() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNounsRule3Helper() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNounsRule4() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDescriptorsRule1() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDescriptorsRule2() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsDescriptor() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsMatched() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddStopWords() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddCharacters() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNumbers() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddClusterstrings() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddProperNouns() {
		fail("Not yet implemented");
	}

	@Test
	public void testPosBySuffix() {
		fail("Not yet implemented");
	}

	@Test
	public void testContainSuffix() {
		// test method containSuffix
		assertEquals("containSuffix less", true, tester.containSuffix("less", "", "less"));
		assertEquals("containSuffix ly", true, tester.containSuffix("slightly", "slight", "ly"));	
		assertEquals("containSuffix er", true, tester.containSuffix("fewer", "few", "er"));
		assertEquals("containSuffix est", true, tester.containSuffix("fastest", "fast", "est"));		
		assertEquals("containSuffix base is in WN", true, tester.containSuffix("platform", "plat", "form"));
		assertEquals("containSuffix sole adj", true, tester.containSuffix("scalelike", "scale", "like"));
	
	}

	@Test
	public void testMarkupByPattern() {
		fail("Not yet implemented");
	}

	@Test
	public void testMarkupIgnore() {
		fail("Not yet implemented");
	}

	@Test
	public void testDiscover() {
		fail("Not yet implemented");
	}

	@Test
	public void testRuleBasedLearn() {
		fail("Not yet implemented");
	}

	@Test
	public void testDoIt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPOSptn() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckPOSInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testTagIt() {
		fail("Not yet implemented");
	}

	@Test
	public void testMatchPattern() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuildPattern() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCheckedWords() {
		fail("Not yet implemented");
	}

}
