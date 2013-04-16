package semanticMarkup.ling.learn;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
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

//	@Test
//	public void testPopulateUnknownWordsTable() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testUpdateTable() {
		// Method updateTable
		assertEquals("updateTable - empty word", 0,
				tester.updateTable("", "", "", "", 0));
		assertEquals("updateTable - forbidden word", 0,
				tester.updateTable("to", "", "", "", 0));
	}

	@Test
	public void testMarkKnown() {
		// Method markKnown
		assertEquals("markKnown - forbidden word", 0,
				tester.markKnown("and", "", "", "", 0));
		assertEquals("markKnown - stop word", 0,
				tester.markKnown("page", "", "", "", 0));
	}

//	@Test
//	public void testProcessNewWord() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testSingularPluralVariations() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testUpdateUnknownWords() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testUpdatePOS() {
//		// Method updatePOS
//		// assertEquals ("getPluralRuleHelper - ves plural", 0,
//		// tester.updatePOS("", "", "", 0));
//	}

//	@Test
//	public void testChangePOS() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testMergeRole() {
		// Method mergeRole
		assertEquals("mergeRole - case 1", "new", tester.mergeRole("*", "new"));
		assertEquals("mergeRole - case 2", "old", tester.mergeRole("old", "*"));
		assertEquals("mergeRole - case 3", "new", tester.mergeRole("", "new"));
		assertEquals("mergeRole - case 4", "old", tester.mergeRole("old", ""));
		assertEquals("mergeRole - case 5", "+", tester.mergeRole("old", "new"));
		assertEquals("mergeRole - case 0", "same",
				tester.mergeRole("same", "same"));
	}

//	@Test
//	public void testDiscount() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testGetParentSentenceTag() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testTagSentWithMT() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetMTFromParentTag() {
		// Method getMTFromParentTag
		List<String> pair = new ArrayList<String>();
		pair.add("");
		pair.add("");
		assertEquals("getMTFromParentTag - case 0: fail", pair,
				tester.getMTFromParentTag("[modifier_ta"));
		pair.remove(1);
		pair.remove(0);
		pair.add("modifier");
		pair.add("tag");
		assertEquals("getMTFromParentTag - case 1: with []", pair,
				tester.getMTFromParentTag("[modifier tag]"));
		assertEquals("getMTFromParentTag - case 2: without []", pair,
				tester.getMTFromParentTag("modifier tag"));
	}

//	@Test
//	public void testAddHeuristicsNouns() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddDescriptors() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddNouns() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testGetHeuristicsNouns() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetHeuristicsNounsHelper() {
		HashSet<String> words = new HashSet<String>();
		words.add("septa");
		words.add("word1");
		words.add("septum");
		assertEquals("getHeuristicsNouns - handleSpecialCase 1", "septa[p]",
				tester.getHeuristicsNounsHelper("septa[s]", words));
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
		assertEquals(
				"getPresentAbsentNouns - end with none ss",
				"computers[p]",
				tester.getPresentAbsentNouns("only one pair of computers absent"));
		assertEquals("getPresentAbsentNouns - teeth", "teeth[p]",
				tester.getPresentAbsentNouns("only one pair of teeth present"));
		assertEquals("getPresentAbsentNouns - not SENDINGS", "serum[s]",
				tester.getPresentAbsentNouns("only one pair of serum absent"));
		assertEquals(
				"getPresentAbsentNouns - SENDINGS",
				"computer[s]",
				tester.getPresentAbsentNouns("only one pair of computer absent"));

	}

//	@Test
//	public void testCharacterHeuristics() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAdd2HeuristicNounTable() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFilterOutDescriptors() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetTaxonNameNouns() {

		// Nouns rule 0: Taxon name nouns
		Set<String> taxonNames = new HashSet<String>();
		// Method getTaxonNameNouns
		assertEquals("getTaxonNameNouns - not match", taxonNames,
				tester.getTaxonNameNouns("word word word"));
		assertEquals("getTaxonNameNouns - empty taxon name", taxonNames,
				tester.getTaxonNameNouns("< i >< / i >"));
		taxonNames.add("word1 word2	word3");
		taxonNames.add("word1");
		taxonNames.add("word2");
		taxonNames.add("word3");
		taxonNames.add("word4 word5");
		taxonNames.add("word4");
		taxonNames.add("word5");
		assertEquals(
				"getTaxonNameNouns - match",
				taxonNames,
				tester.getTaxonNameNouns("< i	>word1 word2	word3< /	i>, < i >word4 word5<	/i>"));
	}

	@Test
	public void testGetNounsMecklesCartilage() {
		// Nouns rule 0.5: Method getNounsMecklesCartilage
		Set<String> nouns = new HashSet<String>();
		assertEquals("getTaxonNameNouns - not match", nouns,
				tester.getNounsMecklesCartilage("word word word"));
		nouns.add("meckel#s");
		nouns.add("meckels");
		nouns.add("meckel");
		assertEquals("getTaxonNameNouns - match", nouns,
				tester.getNounsMecklesCartilage("word Meckel#s word"));
	}

	@Test
	public void testGetNounsRule1() {
		// Method getNounsRule1
		// Set<String> descriptorMap = new HashSet<String>();
		Set<String> nouns1 = new HashSet<String>();
		nouns1.add("term1");
		assertEquals(
				"getNounsRule1",
				nouns1,
				tester.getNounsRule1(
						"Chang_2004.xml_ ffa60eb1-4320-4e69-b151-75a2615dca4b_29482156-8083-430c-91f4-e80209b50138.txt-0",
						"term1", new HashMap<String, Boolean>()));
	}

	@Test
	public void testGetNounsRule2() {
		// Method getNounsRule2
		Set<String> nouns2 = new HashSet<String>();
		assertEquals("getNounsRule2 - not match", nouns2,
				tester.getNounsRule2("word word 	word soe width nea"));
		nouns2.add("nouna");
		assertEquals("getNounsRule2 - match 1", nouns2,
				tester.getNounsRule2("word word 	word some nouna"));
		nouns2.add("nounb");
		assertEquals(
				"getNounsRule2 - match 2",
				nouns2,
				tester.getNounsRule2("word some nouna near word some width near word third nounb near end"));
		assertEquals(
				"getNounsRule2 - match 2",
				nouns2,
				tester.getNounsRule2("word some nouna near word some width near word third nounb near end nounc abction of end"));
	}

	@Test
	public void testGetNounsRule3Helper() {
		// Method getNounsRule3
		Set<String> nouns3 = new HashSet<String>();
		nouns3.add("II");
		nouns3.add("IX");
		assertEquals(
				"getNounsRule3",
				nouns3,
				tester.getNounsRule3Helper("posterior and dorsal to foramen for nerve II (i.e. a posterior oblique myodome IX)"));
		nouns3.remove("II");
		nouns3.remove("IX");
		nouns3.add("Meckelian");
		assertEquals(
				"getNounsRule3",
				nouns3,
				tester.getNounsRule3Helper("Pronounced dorsal process on Meckelian element"));
	}

	@Test
	public void testGetNounsRule4() {
		// Method getNounsRule4
		Set<String> nouns4 = new HashSet<String>();
		assertEquals("getNounsRule4 - not match", nouns4,
				tester.getNounsRule4("word word 	word noun one"));
		nouns4.add("nouna");
		assertEquals("getNounsRule4 - not match", nouns4,
				tester.getNounsRule4("word word 	word nouna 1"));
		nouns4.remove("nouna");
		nouns4.add("nounb");
		assertEquals(
				"getNounsRule4 - not match",
				nouns4,
				tester.getNounsRule4("word word 	word page 1 word above 2 word NoUnb 2 end"));
	}

	@Test
	public void testGetDescriptorsRule1() {
		// Method getDescriptorsRule1
		Set<String> descriptors1 = new HashSet<String>();
		descriptors1.add("absent");
		assertEquals("getDescriptorsRule1", descriptors1,
				tester.getDescriptorsRule1(
						"Brazeau_2009.xml_states200_state202.txt-0", "absent",
						new HashSet<String>()));
		descriptors1.remove("absent");
		descriptors1.add("present");
		Set<String> nouns = new HashSet<String>();
		nouns.add("present");
		assertEquals("getDescriptorsRule1", new HashSet<String>(),
				tester.getDescriptorsRule1(
						"Brazeau_2009.xml_states200_state203.txt-0", "present",
						nouns));
		assertEquals("getDescriptorsRule1", descriptors1,
				tester.getDescriptorsRule1(
						"Brazeau_2009.xml_states200_state203.txt-0", "present",
						new HashSet<String>()));
	}

//	@Test
//	public void testGetDescriptorsRule2() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testIsDescriptor() {
		// Method filterOutDescriptors
		Set<String> rNouns = new HashSet<String>();
		Set<String> rDescriptors = new HashSet<String>();
		Set<String> results = new HashSet<String>();
		rNouns.add("noun1");
		rNouns.add("descriptor2");
		rNouns.add("noun2");
		rDescriptors.add("descriptor1");
		rDescriptors.add("descriptor2");
		rDescriptors.add("descriptor3");
		results.add("noun1");
		results.add("noun2");
		assertEquals("filterOutDescriptors", results,
				tester.filterOutDescriptors(rNouns, rDescriptors));
	}

	@Test
	public void testIsMatched() {
		// Method isMatched
		Map<String, Boolean> descriptorMap = new HashMap<String, Boolean>();
		descriptorMap.put("term1", false);
		assertEquals("isMatched", false, descriptorMap.get("term1"));
		assertEquals("isMatched", true, tester.isMatched(
				"begin word word was term1 word word end", "term1",
				descriptorMap));
		assertEquals("isMatched", true, descriptorMap.get("term1"));
	}

//	@Test
//	public void testAddStopWords() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddCharacters() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddNumbers() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddClusterstrings() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddProperNouns() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testPosBySuffix() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testContainSuffix() {
		// test method containSuffix
		assertEquals("containSuffix less", true,
				tester.containSuffix("less", "", "less"));
		assertEquals("containSuffix ly", true,
				tester.containSuffix("slightly", "slight", "ly"));
		assertEquals("containSuffix er", true,
				tester.containSuffix("fewer", "few", "er"));
		assertEquals("containSuffix est", true,
				tester.containSuffix("fastest", "fast", "est"));
		assertEquals("containSuffix base is in WN", true,
				tester.containSuffix("platform", "plat", "form"));
		assertEquals("containSuffix sole adj", true,
				tester.containSuffix("scalelike", "scale", "like"));

	}

//	@Test
//	public void testMarkupByPattern() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testMarkupIgnore() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDiscover() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRuleBasedLearn() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDoIt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetPOSptn() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCheckPOSInfo() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testTagIt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testMatchPattern() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testBuildPattern() {
		// Method buildPattern
		assertEquals(
				"buildPattern",
				"(?:^\\b(?:one|two|three)\\b|^\\w+\\s\\b(?:one|two|three)\\b|^\\w+\\s\\w+\\s\\b(?:one|two|three)\\b)",
				tester.buildPattern("one two three".split(" ")));
	}

	@Test
	public void testUpdateCheckedWords() {
		// Method updateCheckedWords
		String checkedWords = ":";
		Set<String> list = new HashSet<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		assertEquals("updateCheckedWords", ":two:one:three:",
				tester.updateCheckedWords(":", checkedWords, list));
	}

}
