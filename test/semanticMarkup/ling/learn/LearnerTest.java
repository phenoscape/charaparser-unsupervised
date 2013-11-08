package semanticMarkup.ling.learn;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.AbstractCollection;
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
import semanticMarkup.ling.learn.auxiliary.GetNounsAfterPtnReturnValue;
import semanticMarkup.ling.learn.auxiliary.StringAndInt;
import semanticMarkup.ling.learn.dataholder.DataHolder;
import semanticMarkup.ling.learn.dataholder.SentenceStructure;
import semanticMarkup.ling.learn.dataholder.WordPOSKey;
import semanticMarkup.ling.learn.knowledge.Constant;
import semanticMarkup.ling.learn.utility.LearnerUtility;
import semanticMarkup.ling.transform.ITokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPSentencesTokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPTokenizer;

public class LearnerTest {

	private Learner tester;

	@Before
	public void initialize() {
		this.tester = learnerFactory();
	}

//	@Test
//	public void testLearn() {
//		Configuration myConfiguration = new Configuration();
//		Utility myUtility = new Utility(myConfiguration);
//		DataHolder results = new DataHolder(myConfiguration, myUtility);
//
//		Map<String, String> myHeuristicNounTable = results
//				.getHeuristicNounTable();
//		myHeuristicNounTable.put("word1", "type1");
//
//		List<Sentence> mySentenceTable = results.getSentenceHolder();
//		mySentenceTable.add(new Sentence(0, "source1", "sentence1",
//				"originalSentence", "lead1", "status1", "tag1", "modifier1",
//				"type1"));
//
//		// Learner tester = new Learner("plain","res/WordNet/WordNet-3.0/dict");
//
//		// assertEquals ("learner", results, tester.Learn(tms));
//
//		// results = tester.Learn(tms);
//
//		// assertEquals ("learner", results, tester.Learn(tms));
//	}





//	@Test
//	public void testPopulateUnknownWordsTable() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testDiscountPOS() {
		// case "all"
		// see doItCaseHandle case 2
	}
	
	@Test
	public void testResolveConfict() {
		// see doItCaseHandle case 2
	}

	@Test
	public void testChangePOS(){
		// see doItCaseHandle case 2
	}
	
	@Test
	public void testUpdatePOS(){
		// see doItCaseHandle case 2
	}
	
//	@Test
//	public void testGetParentSentenceTag() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testTagSentWithMT() {
//		fail("Not yet implemented");
//	}

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
	
	@Test
	public void testIsIsAndOrSentence(){
		String sentencePtn = null;
		String ptn1 = null;
		String ptn2 = null;
		List<String> words = new ArrayList<String>();
		
//		// case 1
//		words.clear();
//		words.addAll(Arrays.asList("posterior and <M>dorsal</M> to foramen <B>for</B> nerve <N>ii</N>".split(" ")));
//		sentencePtn = "q&mqqbqn";
//		ptn1="^(?:[mbq,]{0,10}[onp]+(?:,|(?=&)))+&(?:[mbq,]{0,10}[onp]+)"; // n,n,n&n
//		ptn2="^(?:[mbq,]{0,10}(?:,|(?=&)))+&(?:[mbq,]{0,10})[onp]+"; // m,m,&mn
//		
//		assertEquals("isIsAndOrSentence case 1", false,
//				tester.isIsAndOrSentenceHelper(words, sentencePtn, ptn1, ptn2));
		
		// case 2
		words.clear();
		words.addAll(Arrays.asList("elongate and <O>passes</O> <B>anterolaterally</B> through orbital <B>?</B> oor".split(" ")));
		sentencePtn = "q&obqqbq";
		ptn1="^(?:[mbq,]{0,10}[onp]+(?:,|(?=&)))+&(?:[mbq,]{0,10}[onp]+)"; // n,n,n&n
		ptn2="^(?:[mbq,]{0,10}(?:,|(?=&)))+&(?:[mbq,]{0,10})[onp]+"; // m,m,&mn
		
		assertEquals("isIsAndOrSentence case 2", true,
				tester.isIsAndOrSentenceHelper(words, sentencePtn, ptn1, ptn2));
		
		// case 3
		words.clear();
		words.addAll(Arrays.asList("<O>divides</O> <B>within</B> otic <N>capsule</N> <B>at</B> <B>the</B> <N>level</N> <B>of</B> <B>the</B> postorbital process".split(" ")));
		sentencePtn = "q,obqnbbnbbqq";
		ptn1="^(?:[mbq,]{0,10}[onp]+(?:,|(?=&)))+&(?:[mbq,]{0,10}[onp]+)"; // n,n,n&n
		ptn2="^(?:[mbq,]{0,10}(?:,|(?=&)))+&(?:[mbq,]{0,10})[onp]+"; // m,m,&mn
		
		assertEquals("isIsAndOrSentence case 3", false,
				tester.isIsAndOrSentenceHelper(words, sentencePtn, ptn1, ptn2));
		
		
		
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
	@Test
	public void testPosBySuffix() {
		// Pattern 1: ^[a-z_]+(er|est|fid|form|ish|less|like|ly|merous|most|shaped)$
		// Pattern 2: ^[._.][a-z]+
		
		tester.posBySuffix();
	}
	
	@Test
	public void testPosBySuffixCase1Helper(){
		assertEquals("posBySuffix Case1 - match", true, tester.posBySuffixCase1Helper("approximately"));
		assertEquals("posBySuffix Case1 - not match", false, tester.posBySuffixCase1Helper("bigger"));
		assertEquals("posBySuffix Case1 - match", true, tester.posBySuffixCase1Helper("bifid"));
		assertEquals("posBySuffix Case1 - not match", false, tester.posBySuffixCase1Helper("per"));
	}
	
	@Test
	public void testPosBySuffixCase2Helper(){
		assertEquals("posBySuffix Case2 - match", true, tester.posBySuffixCase2Helper("_nerved"));
		assertEquals("posBySuffix Case2 - not match", false, tester.posBySuffixCase2Helper("nerved"));
	}

	@Test
	public void testContainSuffix() {
		// test method containSuffix
		assertEquals("containSuffix less", true,
				tester.containSuffix("less", "", "less"));
		assertEquals("containSuffix ly", true,
				tester.containSuffix("slightly", "slight", "ly"));
		assertEquals("containSuffix er", false,
				tester.containSuffix("fewer", "few", "er"));
		assertEquals("containSuffix est", true,
				tester.containSuffix("fastest", "fast", "est"));
		assertEquals("containSuffix base is in WN", true,
				tester.containSuffix("platform", "plat", "form"));
		assertEquals("containSuffix sole adj", true,
				tester.containSuffix("scalelike", "scale", "like"));
		
		// case 3.1.2 and case 3.3.3 not tested
		assertEquals("containSuffix 111", false,
				tester.containSuffix("anterolaterally", "anterolateral", "ly")); // 111
		assertEquals("containSuffix 121", false,
				tester.containSuffix("mesially", "mesial", "ly")); // 121
		assertEquals("containSuffix 122", false,
				tester.containSuffix("per", "p", "er")); // 122
		assertEquals("containSuffix 212", false,
				tester.containSuffix("border", "bord", "er")); // 212
		assertEquals("containSuffix 212", false,
				tester.containSuffix("bigger", "bigg", "er")); // 212
		assertEquals("containSuffix 221", true,
				tester.containSuffix("anteriorly", "anterior", "ly")); // 221
		assertEquals("containSuffix 222", false,
				tester.containSuffix("corner", "corn", "er")); // 222
		assertEquals("containSuffix 222", true,
				tester.containSuffix("lower", "low", "er")); // 222
		assertEquals("containSuffix 223", true,
				tester.containSuffix("bifid", "bi", "fid")); // 223

	}

	@Test
	public void testMarkupByPattern() {
		Learner myTester = learnerFactory();

		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, 
				Arrays.asList(new String[] {"source1", "sentence1", "x=word word word", "lead1", "status1", "tag1", "modifier1", "type1"}));
		myTester.markupByPattern();
		
		List<SentenceStructure> targetSentenceHolder = new LinkedList<SentenceStructure>();
		targetSentenceHolder.add(new SentenceStructure(0, "source1", "sentence1", "x=word word word", "lead1", "status1", "chromosome", "", "type1"));
		
		assertEquals("markupByPattern", targetSentenceHolder, myTester.getDataHolder().getSentenceHolder());
	}
	
	@Test
	public void testMarkupByPatternHelper(){
		// case 1
		SentenceStructure mySentence1 = new SentenceStructure(0, "source1", "sentence1", "x=word word word", "lead1", "status1", "tag1", "modifier1", "type1");
		SentenceStructure target1 = new SentenceStructure(0, "source1", "sentence1", "x=word word word", "lead1", "status1", "chromosome", "", "type1");
		tester.markupByPatternHelper(mySentence1);
		assertEquals("markupByPatternHelper - case 1", target1,mySentence1);
		
		// case 2
		SentenceStructure mySentence2 = new SentenceStructure(1, "source2", "sentence2", "2n=abc...", "lead2", "status2", "tag2", "modifier2", null);
		SentenceStructure target2 = new SentenceStructure(1, "source2", "sentence2", "2n=abc...", "lead2", "status2", "chromosome", "", null);
		tester.markupByPatternHelper(mySentence2);
		assertEquals("markupByPatternHelper - case 2", target2,mySentence2);
		
		// case 3
		SentenceStructure mySentence3 = new SentenceStructure(2, "source", "sentence", "x word word", "lead", "status", "tag", "modifier", null);
		SentenceStructure target3 = new SentenceStructure(2, "source", "sentence", "x word word", "lead", "status", "chromosome", "", null);
		tester.markupByPatternHelper(mySentence3);
		assertEquals("markupByPatternHelper - case 3", target3, mySentence3);
		
		// case 4
		SentenceStructure mySentence4 = new SentenceStructure(3, "source", "sentence", "2n word word", "lead",null, "tag", "modifier", null);
		SentenceStructure target4 = new SentenceStructure(3, "source", "sentence", "2n word word", "lead", null, "chromosome", "", null);
		tester.markupByPatternHelper(mySentence4);
		assertEquals("markupByPatternHelper - case 4", target4, mySentence4);
		
		// case 5
		SentenceStructure mySentence5 = new SentenceStructure(4, "source", "sentence", "2 nword word", "lead", "status", "tag", "modifier", "");
		SentenceStructure target5 = new SentenceStructure(4, "source", "sentence", "2 nword word", "lead", "status", "chromosome", "", "");
		tester.markupByPatternHelper(mySentence5);
		assertEquals("markupByPatternHelper - case 5", target5, mySentence5);
		
		// case 6
		SentenceStructure mySentence6 = new SentenceStructure(5, "source", "sentence", "fl. word word", "lead", "status", null, null, "");
		SentenceStructure target6 = new SentenceStructure(5, "source", "sentence", "fl. word word", "lead", "status", "flowerTime", "", "");
		tester.markupByPatternHelper(mySentence6);
		assertEquals("markupByPatternHelper - case 6", target6, mySentence6);
		
		// case 7
		SentenceStructure mySentence7 = new SentenceStructure(6, "source", "sentence", "fr.word word", "lead", "status", null, "", "");
		SentenceStructure target7 = new SentenceStructure(6, "source", "sentence", "fr.word word", "lead", "status", "fruitTime", "", "");
		tester.markupByPatternHelper(mySentence7);
		assertEquals("markupByPatternHelper - case 7", target7, mySentence7);
	}

	@Test
	public void testMarkupIgnore() {
		Learner myTester = learnerFactory();

		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, 
				Arrays.asList(new String[] {"source1", "sentence1", "IGNOREPTN", "lead1", "status1", "tag1", "modifier1", "type1"}));
		myTester.markupIgnore();
		
		List<SentenceStructure> targetSentenceHolder = new LinkedList<SentenceStructure>();
		targetSentenceHolder.add(new SentenceStructure(0, "source1", "sentence1", "IGNOREPTN", "lead1", "status1", "ignore", "", "type1"));
		
		assertEquals("markupIgnore", targetSentenceHolder, myTester.getDataHolder().getSentenceHolder());

	}

	@Test
	public void testMarkupIgnoreHelper() {
		SentenceStructure mySentence1 = new SentenceStructure(0, "source", "sentence", "IGNOREPTN", "lead", "status", null, "", "");
		SentenceStructure target1 = new SentenceStructure(0, "source", "sentence", "IGNOREPTN", "lead", "status", "ignore", "", "");
		tester.markupIgnoreHelper(mySentence1);
		assertEquals("markupIgnoreHelper", target1, mySentence1);
		
		SentenceStructure mySentence2 = new SentenceStructure(1, "source", "sentence", " IGNOREPTN", "lead", "status", null, "", "");
		SentenceStructure target2 = new SentenceStructure(1, "source", "sentence", " IGNOREPTN", "lead", "status", "ignore", "", "");
		tester.markupIgnoreHelper(mySentence2);
		assertEquals("markupIgnoreHelper", target2, mySentence2);
	}
	
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
		Learner myTester = learnerFactory();

		// Method buildPattern
//		assertEquals(
//				"buildPattern",
//				"(?:^\\b(?:one|two|three)\\b|^\\w+\\s\\b(?:one|two|three)\\b|^\\w+\\s\\w+\\s\\b(?:one|two|three)\\b)",
//				tester.buildPattern("one two three".split(" ")));
		
		HashSet<String> wordSet= new HashSet<String>();
		wordSet.add("teeth");
		wordSet.add("unicuspid");
		wordSet.add("with");
		myTester.setCheckedWordSet(wordSet);
		
		assertEquals("buildPattern", null,
				myTester.buildPattern("teeth ; 9".split(" ")));
		
		assertEquals("buildPattern", 
				"(?:^\\b(?:variously|arranged)\\b|^\\w+\\s\\b(?:variously|arranged)\\b|^\\w+\\s\\w+\\s\\b(?:variously|arranged)\\b).*$",
				myTester.buildPattern("teeth variously arranged".split(" ")));
		
		wordSet.add("circuli");
		wordSet.add("present");
		wordSet.add("on");
		wordSet.add("hyohyoidei");
		wordSet.add("muscle");
		
		assertEquals("buildPattern", 
				"(?:^\\b(?:does|not|cross)\\b|^\\w+\\s\\b(?:does|not|cross)\\b|^\\w+\\s\\w+\\s\\b(?:does|not|cross)\\b).*$",
				myTester.buildPattern("does not cross".split(" ")));
		
		wordSet.addAll(Arrays.asList("lepidotrichia:of:passes:between:bases".split(":")));
		
		assertEquals("buildPattern", 
				"(?:^\\b(?:ankylosed|to)\\b|^\\w+\\s\\b(?:ankylosed|to)\\b|^\\w+\\s\\w+\\s\\b(?:ankylosed|to)\\b).*$",
				myTester.buildPattern("teeth ankylosed to".split(" ")));		
		
	}
	
	@Test
	public void testGetPOSptn(){
		Learner myTester = learnerFactory();
		
		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"teeth", "p", "role", "1", "1", "", ""}));
		
		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"unicuspid", "p", "role", "1", "3", "", ""}));
		
		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"with", "b", "role", "1", "1", "", ""}));
		
		assertEquals("getPOSptn", "p?b", myTester.getPOSptn(Arrays.asList("teeth unicuspid with".split(" "))));
	}
	
	@Test
	public void testDoItCaseHandle(){
		// case x: boundary case
		Learner myTesterBoundary = learnerFactory();
		assertEquals("CaseHandle - boundary case", null, myTesterBoundary.doItCaseHandle(null, null));   
		assertEquals("CaseHandle - boundary case", new StringAndInt("",0), myTesterBoundary.doItCaseHandle("", ""));   
		
        // case 1
		Learner myTester1 = learnerFactory();
		myTester1.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"submandibular", "s", "", "0", "0", null, null}));
		myTester1.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"submandibulars", "p", "", "0", "0", null, null}));
		
		assertEquals("CaseHandle - case 1", new StringAndInt("submandibulars",0), myTester1.doItCaseHandle("submandibulars", "submandibulars"));
		
		// case 2
		Learner myTester2 = learnerFactory();
		myTester2.getDataHolder().add2Holder(DataHolder.SENTENCE, 
				Arrays.asList(new String[] {"src", 
						"<N>stems</N> <B>usually</B> erect , sometimes prostrate to ascending <B>(</B> underground <N>stems</N> sometimes woody <O>caudices</O> or rhizomes , sometimes fleshy <B>)</B> . ", 
						"Stems usually erect, sometimes prostrate to ascending (underground stems sometimes woody caudices or rhizomes, sometimes fleshy ).",
						"lead","status",null,"m","type"}));
		myTester2.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"stems", "p", "", "0", "0", null, null}));
		myTester2.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"stem", "s", "", "0", "0", null, null}));		
		myTester2.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"usually", "s", "", "0", "0", null, null}));
		String sentence = 
				"stems usually erect , sometimes prostrate to ascending ( underground stems sometimes woody caudices or rhizomes , sometimes fleshy ) .";
		String lead = "stems usually erect";
		assertEquals("CaseHandle - case 2", new StringAndInt("stems",1), myTester2.doItCaseHandle(sentence, lead));
		assertEquals("CaseHandle - case 2, updatePOS - case 2.1, resolveConfict, changePOS - case 2", true, myTester2.getDataHolder().getWordPOSHolder().containsKey(new WordPOSKey("usually", "b")));
		assertEquals("CaseHandle - case 2, discountPOS - all", false, myTester2.getDataHolder().getWordPOSHolder().containsKey(new WordPOSKey("usually", "s")));
        
        // case 3.2
		// This also tests method markKnown() - case 1.1
		Learner myTester32 = learnerFactory();
		myTester32.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"teeth", "p", "role", "1", "1", "", ""}));
		myTester32.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"with", "b", "role", "1", "1", "", ""}));
		
		myTester32.getDataHolder().add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList(new String[] {"bicuspid", "unknown"}));
		myTester32.getDataHolder().add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList(new String[] {"multicuspid", "unknown"}));
		myTester32.getDataHolder().add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList(new String[] {"tricuspid", "unknown"}));
		
		myTester32.getDataHolder().add2Holder(DataHolder.SINGULAR_PLURAL, Arrays.asList(new String[] {"tooth", "teeth"}));
		
        assertEquals("CaseHandle - case 3.2", new StringAndInt("teeth",4), 
            myTester32.doItCaseHandle("teeth unicuspid with crowns posteriorly curved along the main axis of the mandible , organized into a long series of equally_ sized teeth", 
                "teeth unicuspid with"));   
        
        // case 4
        // case 4.2	
		Learner myTester42 = learnerFactory();
		// test case 1
		myTester42.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"teeth", "p", "role", "1", "1", "", ""}));
		myTester42.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"variously", "b", "role", "0", "0", "", ""}));
		
		myTester42.getDataHolder().add2Holder(DataHolder.SINGULAR_PLURAL, Arrays.asList(new String[] {"tooth", "teeth"}));
		myTester42.getDataHolder().add2Holder(DataHolder.SINGULAR_PLURAL, Arrays.asList(new String[] {"base", "bases"}));

        assertEquals("CaseHandle - case 4.2", new StringAndInt("teeth",0), 
                myTester42.doItCaseHandle("teeth variously arranged , but never very numerous , equally_ sized and regularly curved posteriorly along main axis of mandible", 
                    "teeth variously arranged")); 
        
        //case 4.2 - test case 2
		myTester42.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"muscle", "s", "role", "0", "0", "", ""}));
		myTester42.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"with", "b", "role", "0", "0", "", ""}));
        assertEquals("CaseHandle - case 4.2", new StringAndInt("hyohyoidei muscle",1), 
                myTester42.doItCaseHandle("hyohyoidei muscle with a broad origin across the entire ventral surface and lateral margins of the ventrolateral wings of the urohyal",  
                    "hyohyoidei muscle with")); 
        
        //case 4.2 - test case 2
		myTester42.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"bases", "p", "role", "0", "0", "", ""}));
		myTester42.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"of", "b", "role", "2", "2", "", ""}));
        assertEquals("CaseHandle - case 4.2", new StringAndInt("bases",0), 
                myTester42.doItCaseHandle("bases of tooth whorls", "bases of")); 
      
		
		// case 5.1.3 and case x
		Learner myTester513x = learnerFactory();
		myTester513x.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"styles", "p", "role", "1", "1", "", ""}));
		myTester513x.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"style", "s", "role", "1", "1", "", ""}));
		myTester513x.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"branches", "p", "role", "23", "23", "", ""}));
		myTester513x.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"branch", "s", "role", "23", "23", "", ""}));
		StringAndInt result513x = myTester513x.doItCaseHandle("styles branches :", "styles branches");
		StringAndInt target513x = new StringAndInt("branches",1);
		assertEquals("CaseHandle - case 5.1.3 and case x", result513x, target513x); 
		
		Learner myTester52 = learnerFactory();
		myTester52.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"basal", "b", "role", "30", "30", "", ""}));
		myTester52.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"leaf", "s", "role", "0", "0", "", ""}));
		myTester52.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"blades", "p", "role", "63", "63", "", ""}));
		myTester52.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"linear_lanceolate", "b", "role", "2", "2", "", ""}));
		myTester52.getDataHolder().add2Holder(DataHolder.MODIFIER, 
				Arrays.asList(new String[] {"basal", "1", "false"}));
		StringAndInt result52 = myTester52.doItCaseHandle(
					"basal leaf blades linear_lanceolate , 3 ?10 cm , margins entire or with remote linear lobes , apices acute ;", 
					"basal leaf blades");
		StringAndInt target52 = new StringAndInt("basal leaf blades", 0);
		assertEquals("CaseHandle - case 5.2", result52, target52);

		
		// case 6.2
		Learner myTester62 = learnerFactory();
		
		myTester62.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"cauline", "b", "role", "1", "1", "", ""}));
		myTester62.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"much", "s", "role", "1", "1", "", ""}));
		
		myTester62.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"reduced", "b", "role", "11", "11", "", ""}));
		myTester62.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"distally", "b", "role", "2", "2", "", ""}));
		
		StringAndInt returnedValue62 = myTester62.doItCaseHandle(
				"principal cauline much reduced distally , sessile , bases decurrent or not , as spiny wings ;", 
				"principal cauline much");
		assertEquals("CaseHandle - case 6.2", "principal cauline much", returnedValue62.getString());
		
		
//		assertEquals(myTester7.doItCase7Helper("^s(\\?)$", "s?");
		
//		// case 7
//		Learner myTester7 = new Learner(myConfiguration, myUtility);
//		assertEquals(myTester7.doItCase7Helper("^s(\\?)$", "s?");
		
		// case 9
		Learner myTester9 = learnerFactory();
		myTester9.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"basal", "b", "role", "24", "24", "", ""}));
		myTester9.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"leaves", "p", "role", "112", "112", "", ""}));
		myTester9.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"leaf", "s", "role", "112", "112", "", ""}));
		assertEquals("CaseHandle - case 9", new StringAndInt("basal leaves",0), 
				myTester9.doItCaseHandle("basal leaves :", "basal leaves")); 		
    
        // case 10
		// case 10.1.1
		Learner myTester10_1_1 = learnerFactory();
        myTester10_1_1.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"of", "b", "role", "0", "0", "", ""}));
        assertEquals("CaseHandle - case 10.1.1", new StringAndInt("teeth",2), 
                myTester10_1_1.doItCaseHandle("teeth of dentary", 
                    "teeth of")); 
        
        myTester10_1_1.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"on", "b", "role", "4", "4", "", ""}));
        assertEquals("CaseHandle - case 10.1.1", new StringAndInt("foramina",2), 
                myTester10_1_1.doItCaseHandle("foramina on external surface of lower jaw", 
                    "foramina on"));
        // case 10.1.2
		Learner myTester10_1_2 = learnerFactory();
		myTester10_1_2.addStopWords();
		
        assertEquals("CaseHandle - case 10.1.1", new StringAndInt("stems",2), 
                myTester10_1_2.doItCaseHandle("stems 1 ?several , erect or ascending , densely gray_tomentose ", 
                    "stems NUM several")); 
        
		
        // case 10.2
        Learner myTester10_2 = learnerFactory();
        myTester10_2.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"between", "b", "role", "0", "0", "", ""}));
        myTester10_2.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"scales", "p", "role", "0", "0", "", ""}));
		myTester10_2.getDataHolder().add2Holder(DataHolder.SINGULAR_PLURAL, Arrays.asList(new String[] {"scale", "scales"}));
        
        assertEquals("CaseHandle - case 10.2", new StringAndInt("",0), 
                myTester10_2.doItCaseHandle("passes between scales", 
                    "passes between")); 
                    
        // case 0
        Learner myTester0 = learnerFactory();
        
		myTester0.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"does", "b", "role", "0", "0", "", ""}));
		myTester0.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"not", "b", "role", "0", "0", "", ""}));

        assertEquals("CaseHandle - case 0", new StringAndInt("",0), 
                myTester0.doItCaseHandle("does not cross over the anterodorsal corner of opercular bone", 
                    "does not cross"));                     
        
	}
	
	@Test
	public void testIsFollowedByNoun() {
		Learner myTester = learnerFactory();
		
		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"rhombic", "b", "role", "0", "0", null, null}));
		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"bones", "p", "role", "0", "0", null, null}));
		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"radial", "s", "role", "0", "0", null, null}));
		
		assertEquals("isFollowedByNoun - null case", false, myTester.isFollowedByNoun(null, null));
		assertEquals("isFollowedByNoun - empty case", false, myTester.isFollowedByNoun("", ""));
		assertEquals("isFollowedByNoun", true, myTester.isFollowedByNoun("foramina on dermal cheek bones", "foramina on"));
		assertEquals("isFollowedByNoun", true, myTester.isFollowedByNoun("foramina on bones", "foramina on"));
		assertEquals("isFollowedByNoun", false, myTester.isFollowedByNoun("teeth of dentary", "teeth of"));
	}
	
	@Test
	public void testGetNounsAfterPtn() {
		Learner myTester = learnerFactory();
		
		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"margins", "p", "role", "0", "0", null, null}));
		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"often", "b", "role", "0", "0", null, null}));
		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"��", "b", "role", "0", "0", null, null}));
		myTester.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"deeply", "b", "role", "0", "0", null, null}));
		
		List<String> nouns = new ArrayList<String>();
		nouns.add("margins");
		List<String> nounPtn = new ArrayList<String>();
		nounPtn.add("p");
		String bWord = "often";
		GetNounsAfterPtnReturnValue target = new GetNounsAfterPtnReturnValue(nouns, nounPtn, bWord);
		
		assertEquals("getNounsAfterPtn", target, myTester.getNounsAfterPtn("proximal blade margins often �� deeply lobed , ( spiny in c . benedicta ) , distal ?smaller , often entire , faces glabrous or ?tomentose , sometimes also villous , strigose , or puberulent , often glandular_punctate .", 2));
		
	}
	
	@Test
	public void testTagSentence() {		
		Learner myTester = learnerFactory();
		myTester.getConfiguration().setMaxTagLength(10);
		
		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent", "osent","lead","status","tag","m","type"}));

		assertEquals("tagIt - case 1", false, myTester.tagSentence(0, ""));
		assertEquals("tagIt - case 2", false, myTester.tagSentence(0, "page"));
		assertEquals("tagIt - case 3", true, myTester.tagSentence(0, "teeth"));
		assertEquals("tagIt - max tag length", "teeth", myTester.getDataHolder().getSentenceHolder().get(0).getTag());
		
		assertEquals("tagIt - case 3", true, myTester.tagSentence(0, "abcdefghijkl"));
		//myTester.tagSentence(0, "abcdefghijkl");
		assertEquals("tagIt - max tag length", "abcdefghij", myTester.getDataHolder().getSentenceHolder().get(0).getTag());
	}
	
	@Test
	public void testDoItMarkup() {
		Learner myTester = learnerFactory();
		
		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {
				"src", "sent nor", "osent","lead","status",null,"m","type"}));
		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {
				"src", "sent and", "osent","lead","status","","m","type"}));
		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {
				"src", "sent", "osent","lead","status","unknown","m","type"}));
//		assertEquals("doItMarkup - case 1", 0, myTester.doItMarkup());
		
		assertEquals("doItMarkup - Helper - true", true, myTester.doItMarkupHelper(null));
		assertEquals("doItMarkup - Helper - true", true, myTester.doItMarkupHelper(""));
		assertEquals("doItMarkup - Helper - true", true, myTester.doItMarkupHelper("unknown"));
		assertEquals("doItMarkup - Helper - false", false, myTester.doItMarkupHelper("abc"));
		
		assertEquals("doItMarkup - case 1 - true", true, myTester.doItMarkupCase1Helper("postcleithra 2 and 3 fused into a single ossification"));
		assertEquals("doItMarkup - case 1 - false", false, myTester.doItMarkupCase1Helper("postcleithra 2  3 fused into a single ossification"));
		
		assertEquals("doItMarkup - case 2 - true", true, myTester.doItMarkupCase2Helper("ossified as autogenous units"));
		assertEquals("doItMarkup - case 2 - false", false, myTester.doItMarkupCase2Helper("ossified autogenous units"));
		
	}

    @Test
    public void testHasHead(){
        assertEquals("hasHead - null", false, 
        		tester.hasHead(	null, 
        						Arrays.asList("passing through most".split(" "))));
        assertEquals("hasHead - not has", false, 
        		tester.hasHead(	Arrays.asList("passing through".split(" ")), 
        						Arrays.asList("passing throug most".split(" "))));
        assertEquals("hasHead - empty head", true, 
        		tester.hasHead(	new ArrayList<String>(), 
        						Arrays.asList("passing through most".split(" "))));
        assertEquals("hasHead - has", true, 
        		tester.hasHead(	Arrays.asList("passing through".split(" ")), 
        						Arrays.asList("passing through most".split(" "))));
        assertEquals("hasHead - head same as list", true, 
        		tester.hasHead(	Arrays.asList("passing through most".split(" ")), 
        						Arrays.asList("passing through most".split(" "))));
    }
    
   
    @Test
    public void testWrapupMarkup() {		
//		// case 1
//		Learner myTester1 = learnerFactory();
//		
//		myTester1.getDataHolder().getSentenceHolder().add(new SentenceStructure(7, "src", "sent", "osent","sensory line not null","status","notnull","modifer","type"));
//		myTester1.getDataHolder().getSentenceHolder().add(new SentenceStructure(192, "src", "sent", "osent","sensory line ignore","status","ignore","modifer","type"));
//		myTester1.getDataHolder().getSentenceHolder().add(new SentenceStructure(193, "src", "sent", "osent","sensory line canal","status",null,"modifer","type"));
//		myTester1.getDataHolder().getSentenceHolder().add(new SentenceStructure(267, "src", "sent", "osent","sensory line canals","status",null,"modifer","type"));
//		myTester1.getDataHolder().getSentenceHolder().add(new SentenceStructure(269, "src", "sent", "osent","opening via tubular","status",null,"modifer","type"));
//		
//		myTester1.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"line", "s", "*", "1", "1", "", null}));
//		myTester1.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"canals", "p", "*", "1", "1", "", null}));
//		
//		myTester1.wrapupMarkup();
//		
//		assertEquals("wrapupmarkup - case 1 - tag sentence", "sensory line canal", myTester1.getDataHolder().getSentence(193).getTag());
//		assertEquals("wrapupmarkup - case 1 - tag sentence", "sensory line", myTester1.getDataHolder().getSentence(267).getTag());
//		
//		// case 2
//		Learner myTester2 = learnerFactory();
//		
//		myTester2.getDataHolder().getSentenceHolder().add(new SentenceStructure(115, "src", "sent", "osent","midsagittal fontanel absent","status",null,"modifer","type"));
//		myTester2.getDataHolder().getSentenceHolder().add(new SentenceStructure(116, "src", "sent", "osent","midsagittal fontanel present","status",null,"modifer","type"));
//		
//		myTester2.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"fontanel", "s", "*", "1", "1", "", null}));
//		myTester2.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"absent", "b", "*", "1", "1", "", null}));
//		myTester2.getDataHolder().add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"present", "b", "*", "1", "1", "", null}));
//		
//		myTester2.wrapupMarkup();
//		
//		assertEquals("wrapupmarkup - case 2 - tag sentence", "midsagittal fontanel", myTester2.getDataHolder().getSentence(115).getTag());
//		assertEquals("wrapupmarkup - case 2 - tag sentence", "midsagittal fontanel", myTester2.getDataHolder().getSentence(116).getTag());
    }
    
    @Test
    public void testOneLeadMarkup(){
		Learner myTester = learnerFactory();
		
		myTester.getDataHolder().getSentenceHolder().add(new SentenceStructure(0, "src", "sent", "osent","lead1 lead2","status","tag tag","modifer","type"));
		myTester.getDataHolder().getSentenceHolder().add(new SentenceStructure(1, "src", "sent", "osent","midsagittal fontanel present","status",null,"modifer","type"));
		myTester.getDataHolder().getSentenceHolder().add(new SentenceStructure(2, "src", "sent", "osent","midsagittal fontanel present","status","tag1","modifer","type"));
		myTester.getDataHolder().getSentenceHolder().add(new SentenceStructure(3, "src", "sent", "osent","tagx","status",null,"modifer","type"));
		myTester.getDataHolder().getSentenceHolder().add(new SentenceStructure(4, "src", "sent", "osent","tagx tagx","status",null,"modifer","type"));
		myTester.getDataHolder().getSentenceHolder().add(new SentenceStructure(5, "src", "sent", "osent","midsagittal fontanel present","status","tagx","modifer","type"));
		myTester.getDataHolder().getSentenceHolder().add(new SentenceStructure(6, "src", "sent", "osent","midsagittal fontanel","status","tag2","modifer","type"));
		
		myTester.oneLeadWordMarkup(myTester.getDataHolder().getCurrentTags());
		assertEquals("oneLeadMarkup", "tagx", myTester.getDataHolder().getSentence(3).getTag());		
    }
	
	@Test
	public void testUnknownWordBootstrapping(){
		
//		// 1. Preprocessing
//		Learner myTester1 = learnerFactory();
//		myTester1.getDataHolder().add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList("word1 unknown".split(" ")));
//		Set<String> expected = new HashSet<String>();
////		expected.add("")
//		assertEquals("unknownWordBootstrappingGetUnknownWord", expected , myTester1.unknownWordBootstrappingGetUnknownWord("(ee)"));
		
		
		
		// 3. Postprocessing
		Learner myTester3 = learnerFactory();
		
		myTester3.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"word1", "p", "role", "0", "0", "", ""}));
		myTester3.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"word2", "b", "role", "0", "0", "", ""}));
		myTester3.getDataHolder().add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"word3", "s", "role", "0", "0", "", ""}));
		
		myTester3.getDataHolder().add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList("word1 word1".split(" ")));
		myTester3.getDataHolder().add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList("word2 unknown".split(" ")));
		myTester3.getDataHolder().add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList("_wORd3 unknown".split(" ")));
		myTester3.getDataHolder().add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList("word?_4 unknown".split(" ")));
		myTester3.getDataHolder().add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList("nor unknown".split(" ")));
		myTester3.getDataHolder().add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList("word_6 unknown".split(" ")));

		
		myTester3.getDataHolder().getSentenceHolder().add(new SentenceStructure(0, "src", "word1 word_6 word2", "osent","lead","status","tag","modifer","type"));
		myTester3.getDataHolder().getSentenceHolder().add(new SentenceStructure(1, "src", "word_6 word2", "osent","lead","status","tag","modifer","type"));
		myTester3.getDataHolder().getSentenceHolder().add(new SentenceStructure(2, "src", "word1 word6 word2", "osent","lead","status","tag","modifer","type"));
		
		myTester3.unknownWordBootstrappingPostprocessing();
		assertEquals("unknownWordBootstrapping - Postprocessing", "word1 <B>word_6</B> word2", myTester3.getDataHolder().getSentence(0).getSentence());
		assertEquals("unknownWordBootstrapping - Postprocessing", "<B>word_6</B> word2", myTester3.getDataHolder().getSentence(1).getSentence());
		assertEquals("unknownWordBootstrapping - Postprocessing", "word1 word6 word2", myTester3.getDataHolder().getSentence(2).getSentence());
		
		myTester3.unknownWordBootstrappingPostprocessing();
		
	}
	
	@Test
	public void testDittoHelper() {
		String nPhrasePattern = "(?:<[A-Z]*[NO]+[A-Z]*>[^<]+?<\\/[A-Z]*[NO]+[A-Z]*>\\s*)+";
		String mPhrasePattern = "(?:<[A-Z]*M[A-Z]*>[^<]+?<\\/[A-Z]*M[A-Z]*>\\s*)+";
		
		Learner myTester = learnerFactory();
		assertEquals("ditto helper", 0, myTester.dittoHelper(myTester.getDataHolder(), 0, "prismatic calcified <N>cartilage</N>", nPhrasePattern, mPhrasePattern));
		
		assertEquals("ditto helper", 1, myTester.dittoHelper(
				myTester.getDataHolder(), 0, "<B>absent</B>", nPhrasePattern,
				mPhrasePattern));
		assertEquals("ditto helper", 21, 
				myTester.dittoHelper(myTester.getDataHolder(), 0, 
						"<B>in</B> tubes below visceral surface <B>of</B> <M>dermal</M> <N>bone</N>", 
						nPhrasePattern, mPhrasePattern));		
	}
	
	@Test
	public void testPhraseClauseHelper() {
		Learner myTester = learnerFactory();
		
		String sentence = "mid and distal <B>progressively</B> smaller , <B>becoming</B> <B>sessile</B> , <B>narrower</B> , <N>bases</N> obtuse to acuminate , <M><B>cauline</B></M> <B>usually</B> 15 or fewer <B>.</B>";		
		assertEquals("phraseChauseHelper - empty return", new ArrayList<String>(), myTester.phraseClauseHelper(sentence));
		
		sentence = "<M><B>cauline</B></M> <B>linear</B> or <B>oblong</B> , <B>crowded</B> or well separated , <B>usually</B> <B>not</B> surpassing <N>heads</N> <B>.</B>";
		List<String> target = new ArrayList<String>(2);
		target.add("");
		target.add("heads");
		assertEquals("phraseChauseHelper", target, myTester.phraseClauseHelper(sentence));
		
		sentence = "distal <M><B>cauline</B></M> <B>sessile</B> , ?<N>decurrent</N> <B>.</B>";
		target.clear();
		target.add("");
		target.add("decurrent");
		assertEquals("phraseChauseHelper", target, myTester.phraseClauseHelper(sentence));
	}
	
	@Test
	public void testPronounCharacterSubjectHelper() {
		Learner myTester = learnerFactory();
		List<String> target = new ArrayList<String>(2);
		String lead;
		String sentence;
		String modifier;
		String tag;
		
		// null
		lead = "prismatic calcified cartilage";
		sentence = "prismatic calcified <N>cartilage</N>";
		modifier = null;
		tag = null;
		assertEquals("pronounCharacterSubjectHelper null", null, myTester.pronounCharacterSubjectHelper(lead, sentence, modifier, tag));
		
		// case 1.1.1
		lead = "size of";
		sentence = "<B>size</B> <B>of</B> <N>lateral</N> <B>gular</B>";
		modifier = "";
		tag = "ditto";
		target.clear();
		target.add("");
		target.add("lateral");
		assertEquals("pronounCharacterSubjectHelper case 1.1.1", target, myTester.pronounCharacterSubjectHelper(lead, sentence, modifier, tag));
		
		// case 1.2.1.1
		lead = "body scale profile";
		sentence = "<M>body</M> <N>scale</N> <B>profile</B>";
		modifier = "body";
		tag = "scale";
		target.clear();
		target.add("body ");
		target.add("scale");
		assertEquals("pronounCharacterSubjectHelper case 1.2.1.1", target, myTester.pronounCharacterSubjectHelper(lead, sentence, modifier, tag));
		
		// case 1.2.1.1
		lead = "lyre_ shaped";
		sentence = "<N>lyre_</N> <B>shaped</B>";
		modifier = "";
		tag = "lyre_";
		target.clear();
		target.add("");
		target.add("ditto");
		assertEquals("pronounCharacterSubjectHelper case 1.2.1.2", target, myTester.pronounCharacterSubjectHelper(lead, sentence, modifier, tag));
				
		// case 1.2.2
		lead = "shape of";
		sentence = "<B>shape</B> <B>of</B> opercular <N>ossification</N>";
		modifier = "";
		tag = "ditto";
		target.clear();
		target.add("");
		target.add("ditto");
		assertEquals("pronounCharacterSubjectHelper case 1.2.2", target, myTester.pronounCharacterSubjectHelper(lead, sentence, modifier, tag));
	}
	
	@Test
	public void testPronounCharacterSubjectHelper4() {
		Learner myTester = learnerFactory();
		List<String> target = new ArrayList<String>(2);
		String lead;
		String sentence;
		String modifier;
		String tag;
		
		// null
		lead = "prismatic calcified cartilage";
		sentence = "prismatic calcified <N>cartilage</N>";
		modifier = null;
		tag = null;
		assertEquals("pronounCharacterSubjectHelper null", null, myTester.pronounCharacterSubjectHelper4(lead, sentence, modifier, tag));
	
//		
//				lead = "skull shape";
//				sentence = "<N>skull</N> <B>shape</B>";
//				modifier = "";
//				tag = "skull";
//				target.clear();
//				target.add("");
//				target.add("skull");
//				assertEquals("pronounCharacterSubjectHelper4", target, myTester.pronounCharacterSubjectHelper(lead, sentence, modifier, tag));
				
		
	}
	
	@Test
	public void testAndOrTagCase1Helper() {
		Learner myTester = learnerFactory();		
		String sPattern = Constant.SEGANDORPTN;
		String wPattern = Constant.ANDORPTN;
		Set<String> token = new HashSet<String>();
		token.addAll(Arrays.asList("and or nor".split(" ")));
		token.add("\\");
		token.add("and / or");
		
		// test case 1
		String pattern = "qqn&p";
		List<String> words = new ArrayList<String>();
		words.addAll(Arrays.asList("smaller undifferentiated <N>plates</N> or tesserae".split(" ")));
		
		List<List<String>> target = new ArrayList<List<String>>();
		List<String> mPatterns = new ArrayList<String>();
		mPatterns.add("qq");
		List<String> mSegments = new ArrayList<String>();
		mSegments.add("smaller undifferentiated");
		List<String> sPatterns = new ArrayList<String>();
		sPatterns.addAll(Arrays.asList("n p".split(" ")));
		List<String> sSegments = new ArrayList<String>();
		sSegments.addAll(Arrays.asList("<N>plates</N> tesserae".split(" ")));	
		
		List<String> tagAndModifier1 = new ArrayList<String>();
		tagAndModifier1.add("");
		tagAndModifier1.add("smaller undifferentiated plates or tesserae");
		List<String> tagAndModifier2 = new ArrayList<String>();
		
		List<String> update1 = new ArrayList<String>();
		List<String> update2 = new ArrayList<String>();
		update2.add("tesserae");
		
		target.add(mPatterns);
		target.add(mSegments);
		target.add(sPatterns);
		target.add(sSegments);
		
		target.add(tagAndModifier1);
		target.add(tagAndModifier2);
		
		target.add(update1);
		target.add(update2);
		
		assertEquals("andOrTagCase1Helper", target, myTester.andOrTagCase1Helper(pattern, wPattern, words, token));
//		List<List<String>> returned = myTester.andOrTagCase1Helper(pattern, wPattern, words, token);
//		System.out.println(returned);
		
		// test case 2
		pattern = "n&qqnbq";
		words.clear();
		words.addAll(Arrays.asList("<N>perforate</N> or fenestrate anterodorsal <N>portion</N> <B>of</B> palatoquadrate".split(" ")));
		mPatterns.clear();
		mSegments.clear();
		sPatterns.clear();
		sSegments.clear();
		
		mPatterns.add("qq");
		mSegments.add("fenestrate anterodorsal");
		sPatterns.addAll(Arrays.asList("n n".split(" ")));
		sSegments.addAll(Arrays.asList("<N>perforate</N> <N>portion</N>".split(" ")));
		
		tagAndModifier1.clear();
		tagAndModifier1.add("");
		tagAndModifier1.add("perforate or fenestrate anterodorsal portion");
		tagAndModifier2.clear();
		
		update1.clear();
		update2.clear();
		
		assertEquals("andOrTagCase1Helper", target, myTester.andOrTagCase1Helper(pattern, wPattern, words, token));	
	}
	
	@Test
	public void testFinalizeCompoundModifier() {
		Learner myTester = learnerFactory();	
		
		// case 1
		String modifier = "maxillary and [dentary] tooth_ bearing";
		String tag = "elements";
		String sentence = "maxillary and dentary <B>tooth_</B> bearing <N>elements</N>";
				
		assertEquals("finalizeCompoundModifier case 1", modifier,
				myTester.finalizeCompoundModifier(modifier, tag, sentence));
		
	}
	
	
	
	private Learner learnerFactory() {
		Learner tester;

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
		tester = new Learner(myConfiguration, tokenizer, myLearnerUtility);

		return tester;
	}
}
