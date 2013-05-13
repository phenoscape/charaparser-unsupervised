package semanticMarkup.ling.learn;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

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

}
