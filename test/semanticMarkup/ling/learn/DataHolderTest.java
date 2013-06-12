package semanticMarkup.ling.learn;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class DataHolderTest {
	
	private DataHolder tester;
	
	@Before
	public void initialize(){
		Configuration myConfiguration = new Configuration();
		Utility myUtility = new Utility(myConfiguration);
		tester = new DataHolder(myConfiguration, myUtility);
	}

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
		//assertEquals("markKnown - stop word", 0,
		//		tester.markKnown("page", "", "", "", 0));		
		
		// case 1 & 2
		tester.markKnown("dentinous", "b", "", "wordpos", 1);
		
		// case 2
		tester.markKnown("lamentous", "b", "", "wordpos", 1);
		
	}



	@Test
	public void testUpdatePOS() {
		Configuration myConfiguration = new Configuration();
		Utility myUtility = new Utility(myConfiguration);
		Learner myTester = new Learner(myConfiguration, myUtility);
		assertEquals("updatePOS - no update", 0, myTester.getDataHolder().updatePOS("NUM", "n", "", 1));
		assertEquals("updatePOS - no update", 0, myTester.getDataHolder().updatePOS("two", "s", "", 1));
		assertEquals("updatePOS - no update", 0, myTester.getDataHolder().updatePOS("series", "p", "", 1));
		assertEquals("updatePOS - no update", 0, myTester.getDataHolder().updatePOS("heights", "n", "", 1));
		
		Map<WordPOSKey, WordPOSValue> target = new HashMap<WordPOSKey, WordPOSValue>();
		target.put(new WordPOSKey("word1", "n"), new WordPOSValue("role1", 2, 0, null, null));
		myTester.getDataHolder().updatePOS("word1", "n", "role1", 2);
		assertEquals("updatePOS - add", target, myTester.getDataHolder().getWordPOSHolder());
		
	}

	@Test
	public void testChangePOS() {
		Configuration myConfiguration = new Configuration();
		Utility myUtility = new Utility(myConfiguration);
		Learner myTester = new Learner(myConfiguration, myUtility);
		//assertEquals("changePOS", "", myTester.getDataHolder().changePOS("newWord", "oldPOS", "newPOS", "newRole", 3));
	}

	
	@Test
	public void testAddSingularPluralPair() {
		// Method addSingularPluralPair
		assertEquals("addSigularPluralPair - pair not exist", true, tester.addSingularPluralPair("sword", "pword"));
		tester.getSingularPluralHolder().add(new SingularPluralPair("sword2",""));
		assertEquals("addSigularPluralPair - one word exist", true, tester.addSingularPluralPair("sword2", "pword2"));
		tester.getSingularPluralHolder().add(new SingularPluralPair("sword3","pword3"));
		assertEquals("addSigularPluralPair - pair exists", false, tester.addSingularPluralPair("sword3", "pword3"));	
	}
	
	@Test
	public void testIsInSingularPluralPair() {
		// Method inSingularPluralPair
		assertEquals("inSingularPluralPair - null", false,
				tester.isInSingularPluralPair("word"));
		tester.getSingularPluralHolder().add(new SingularPluralPair(
				"word1", ""));
		assertEquals("inSingularPluralPair - singular match", true,
				tester.isInSingularPluralPair("word1"));
		tester.getSingularPluralHolder().add(new SingularPluralPair("",
				"word2"));
		assertEquals("inSingularPluralPair - plural match", true,
				tester.isInSingularPluralPair("word2"));
		tester.getSingularPluralHolder().add(new SingularPluralPair(
				"word3", "word3"));
		assertEquals("inSingularPluralPair - both match", true,
				tester.isInSingularPluralPair("word3"));
	}
	
	@Test
	public void testAddModifier(){
		Map<String, ModifierTableValue> target = new HashMap<String, ModifierTableValue>();
		target.put("word", new ModifierTableValue(1,false));
		tester.addModifier("word", 10);
		assertEquals("addModifier - add", target, tester.getModifierHolder());
		target.put("word", new ModifierTableValue(10,false));
		tester.addModifier("word", 9);
		assertEquals("addModifier - add", target, tester.getModifierHolder());
	}
	
	@Test
	public void testUpdateUnknownWord(){
		Map<String, String> target = new HashMap<String, String>();
		target.put("word", "word");
		tester.getUnknownWordHolder().put("word", "unknown");
		tester.updateUnknownWord("word", "word");
		assertEquals("updateUnknownWord - add", target, tester.getUnknownWordHolder());
	}
	
	@Test
	public void testResolveConflict(){
		Configuration myConfiguration = new Configuration();
		Utility myUtility = new Utility(myConfiguration);
		DataHolder myTester = new DataHolder(myConfiguration, myUtility);
		myTester.getSentenceHolder().add(new Sentence("source", "word branches word1 end", "word branches word1 end", "lead", "status", "ignore", null, null));
		myTester.getSentenceHolder().add(new Sentence("source", "word branches word2 end", "word branches word2 end", "lead", "status", "nonignore", null, null));
		myTester.getSentenceHolder().add(new Sentence("source", "word branches word3 end", "word branches word3 end", "lead", "status", null, null, null));
		assertEquals("resolveConfilct - otherPOS", "otherPOS", myTester.resolveConflict("word1", "bPOS", "otherPOS"));
		assertEquals("resolveConfilct - otherPOS", "bPOS", myTester.resolveConflict("word2", "bPOS", "otherPOS"));
		assertEquals("resolveConfilct - otherPOS", "bPOS", myTester.resolveConflict("word3", "bPOS", "otherPOS"));
	}
	
	@Test
	public void testDiscountPOS(){
		Configuration myConfiguration = new Configuration();
		Utility myUtility = new Utility(myConfiguration);
		DataHolder myTester = new DataHolder(myConfiguration, myUtility);
		myTester.add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList(new String[] {"word1", "flag1"}));
		myTester.add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList(new String[] {"word2", "unknown"}));
		myTester.add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList(new String[] {"word3", "flag1"}));
		myTester.add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList(new String[] {"word4", "flag2"}));
		myTester.add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList(new String[] {"word5", "flag1"}));
		
		myTester.add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"word1", "s", "role1", "1", "1", null, null}));
		myTester.add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"word2", "p", "role1", "2", "1", null, null}));
		
		myTester.add2Holder(DataHolder.SINGULAR_PLURAL, Arrays.asList(new String[] {"word1", "word1plural"}));
		myTester.add2Holder(DataHolder.SINGULAR_PLURAL, Arrays.asList(new String[] {"word1singular", "word1"}));
		myTester.add2Holder(DataHolder.SINGULAR_PLURAL, Arrays.asList(new String[] {"word2singular", "word2plural"}));
		
		Map<String, String> targetUnknownWordHolder = new HashMap<String, String>();
		targetUnknownWordHolder = myTester.add2UnknowWordHolder(targetUnknownWordHolder, Arrays.asList(new String[] {"word1", "unknown"}));
		targetUnknownWordHolder = myTester.add2UnknowWordHolder(targetUnknownWordHolder, Arrays.asList(new String[] {"word2", "unknown"}));
		targetUnknownWordHolder = myTester.add2UnknowWordHolder(targetUnknownWordHolder, Arrays.asList(new String[] {"word3", "flag1"}));
		targetUnknownWordHolder = myTester.add2UnknowWordHolder(targetUnknownWordHolder, Arrays.asList(new String[] {"word4", "flag2"}));
		targetUnknownWordHolder = myTester.add2UnknowWordHolder(targetUnknownWordHolder, Arrays.asList(new String[] {"word5", "flag1"}));
		
		Map<WordPOSKey, WordPOSValue> targetWordPOSHolder = new HashMap<WordPOSKey, WordPOSValue>();
		targetWordPOSHolder = myTester.add2WordPOSHolder(targetWordPOSHolder, Arrays.asList(new String[] {"word2", "p", "role1", "1", "1", null, null}));
		
		Set<SingularPluralPair> targetSingularPluralHolder = new HashSet<SingularPluralPair>();
		targetSingularPluralHolder = myTester.add2SingularPluralHolder(targetSingularPluralHolder, Arrays.asList(new String[] {"word2singular", "word2plural"}));
		
		Map<DiscountedKey, String> targetDiscountedHolder = new HashMap<DiscountedKey, String>();
		targetDiscountedHolder = myTester.add2DiscountedHolder(targetDiscountedHolder, Arrays.asList(new String[] {"word2", "p", "newPOS"}));
		
		myTester.discountPOS("word1", "s", "newPOS", "all");
		myTester.discountPOS("word2", "p", "newPOS", "notAll");
		
		assertEquals("discountPOS - delete - UnknownWord", targetUnknownWordHolder, myTester.getUnknownWordHolder());
		assertEquals("discountPOS - delete - WordPOS", targetWordPOSHolder, myTester.getWordPOSHolder());
		assertEquals("discountPOS - delete - SingularPlural", targetSingularPluralHolder, myTester.getSingularPluralHolder());
	}
	
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
	
	@Test
	public void testGetParentSentenceTag(){
		Configuration myConfiguration = new Configuration();
		Utility myUtility = new Utility(myConfiguration);
		DataHolder myTester = new DataHolder(myConfiguration, myUtility);
		
		myTester.add2Holder(DataHolder.SENTENCE, 
				Arrays.asList(new String[] {"src0","s0","begin with lowercase","l0","s0",null,"m0","t0"}));
		myTester.add2Holder(DataHolder.SENTENCE, 
				Arrays.asList(new String[] {"src1","s1","Begin with lowercase","l1","s1","ignore","m1","t1"}));
		myTester.add2Holder(DataHolder.SENTENCE, 
				Arrays.asList(new String[] {"src2","s2","Begin with uppercase","l2","s2","ignore","m2","t2"}));
		myTester.add2Holder(DataHolder.SENTENCE, 
				Arrays.asList(new String[] {"src3","s3","end with colon:  	","l3","s3",null,"m[3][","t3"}));
		myTester.add2Holder(DataHolder.SENTENCE, 
				Arrays.asList(new String[] {"src4","s4","begin with lowercase","l4","s4","t4","m4","t4"}));
		
		assertEquals("getParentSentenceTag", "[parenttag]",myTester.getParentSentenceTag(0));
		assertEquals("getParentSentenceTag", "[parenttag]",myTester.getParentSentenceTag(1));
		assertEquals("getParentSentenceTag", "[m3 ]",myTester.getParentSentenceTag(4));		
	}
	
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
	
	@Test
	public void testRemoveLyEndingBoundary(){
		Configuration myConfiguration = new Configuration();
		Utility myUtility = new Utility(myConfiguration);
		DataHolder myTester = new DataHolder(myConfiguration, myUtility);
		myTester.add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"word1ly", "b", "role1", "1", "1", null, null}));
		myTester.add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"word2ly", "*", "role1", "1", "1", null, null}));
		assertEquals("RemoveLyEndingBoundary", "word2", myTester.tagSentWithMTRemoveLyEndingBoundary("word1ly word2"));
		assertEquals("RemoveLyEndingBoundary", "word2ly word2", myTester.tagSentWithMTRemoveLyEndingBoundary("word2ly word2"));
	}
	
	@Test 
	public void testTagSentWithMTPreProcessing(){
		Configuration myConfiguration = new Configuration();
		Utility myUtility = new Utility(myConfiguration);
		DataHolder myTester = new DataHolder(myConfiguration, myUtility);
		assertEquals("RemoveLyEndingBoundary - remove <>", "word1  word3", myTester.tagSentWithMTPreProcessing("word1 <word2> word3"));
		assertEquals("RemoveLyEndingBoundary remove beginning stop words", "word", myTester.tagSentWithMTPreProcessing("after <word2> after above word"));
		assertEquals("RemoveLyEndingBoundary remove ending -ly words", "word1", myTester.tagSentWithMTPreProcessing("word1 <word2> word3ly word4ly"));
	}
	
	@Test 
	public void testGetSumCertaintyU(){
		Configuration myConfiguration = new Configuration();
		Utility myUtility = new Utility(myConfiguration);
		DataHolder myTester = new DataHolder(myConfiguration, myUtility);
		myTester.add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"target", "pos1", "role", "1", "5", null, null}));
		myTester.add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"target", "pos2", "role", "1", "5", null, null}));
		myTester.add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"target", "pos3", "role", "1", "5", null, null}));
		myTester.add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"word", "pos", "role", "1", "5", null, null}));
		myTester.add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"target", "pos4", "role", "1", "5", null, null}));
		myTester.add2Holder(DataHolder.WORDPOS, Arrays.asList(new String[] {"target", "pos5", "role", "1", "5", null, null}));
		assertEquals("getSumCertaintyU", 5 ,myTester.getSumCertaintyU("target"));
		
	}

	@Test
	public void testSingularPluralVariations(){
		Set<SingularPluralPair> singularPluralTable = new HashSet<SingularPluralPair> ();
		singularPluralTable.add(new SingularPluralPair("vertebra", "vertebrae"));
		singularPluralTable.add(new SingularPluralPair("curimatidae","curimatida"));
		singularPluralTable.add(new SingularPluralPair("bone","bones"));
		assertEquals("singularPluralVariations", "vertebra|vertebrae", tester.singularPluralVariations("vertebra", singularPluralTable));
		assertEquals("singularPluralVariations", "curimatidae|curimatida", tester.singularPluralVariations("curimatidae", singularPluralTable));
		assertEquals("singularPluralVariations", "curimatida|curimatidae", tester.singularPluralVariations("curimatida", singularPluralTable));
		assertEquals("singularPluralVariations", "bones|bone", tester.singularPluralVariations("bones", singularPluralTable));
	}

	@Test
	public void testCheckPOSInfo(){
		Configuration myConfiguration = new Configuration();
		Utility myUtility = new Utility(myConfiguration);
		DataHolder myTester = new DataHolder(myConfiguration, myUtility);
		
		myTester.add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"word1", "pos3", "role", "1", "4", "", ""}));
		myTester.add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"word2", "pos3", "role", "1", "4", "", ""}));
		myTester.add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"word1", "pos1", "role", "3", "4", "", ""}));
		myTester.add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"word2", "pos1", "role", "3", "4", "", ""}));
		myTester.add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"word1", "pos2", "role", "2", "4", "", ""}));
		myTester.add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"word2", "pos2", "role", "2", "4", "", ""}));

		List<POSInfo> target1 = new ArrayList<POSInfo>();
		target1.add(new POSInfo("123", "b", "", 1, 1));
		assertEquals("checkPOSInfo - digit", target1, myTester.checkPOSInfo("123"));
		
		List<POSInfo> target2 = new ArrayList<POSInfo>();
		assertEquals("checkPOSInfo - not found", target2, myTester.checkPOSInfo("abc"));
		
		List<POSInfo> target3 = new ArrayList<POSInfo>();
		target3.add(new POSInfo("word1", "pos1", "role", 3, 4));
		target3.add(new POSInfo("word1", "pos2", "role", 2, 4));
		target3.add(new POSInfo("word1", "pos3", "role", 1, 4));
		assertEquals("checkPOSInfo - found multiple", target3, myTester.checkPOSInfo("word1"));
	}
	
	@Test
	public void testUpdateTableNNConditionHelper(){
		assertEquals("updateTableNN case 0 - true", true, tester.updateTableNNConditionHelper("word"));
		assertEquals("updateTableNN case 1 stop words - false", false, tester.updateTableNNConditionHelper(" page"));
		assertEquals("updateTableNN case 2 -ly ending words - false", false, tester.updateTableNNConditionHelper("hello abcly "));
		assertEquals("updateTableNN case 3 forbidden words - false", false, tester.updateTableNNConditionHelper("nor $%^iwopf0-v"));
	}
	
}
