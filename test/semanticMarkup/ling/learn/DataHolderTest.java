package semanticMarkup.ling.learn;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
		tester = new DataHolder(myUtility);
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
		DataHolder myTester = new DataHolder(myUtility);
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
		DataHolder myTester = new DataHolder(myUtility);
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
		DataHolder myTester = new DataHolder(myUtility);
		
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

}
