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
		tester = new DataHolder();
	}

	@Test
	public void testAddSingularPluralPair() {
		// Method addSingularPluralPair
		assertEquals("addSigularPluralPair - pair not exist", true, tester.addSingularPluralPair("sword", "pword"));
		tester.singularPluralTable.add(new SingularPluralPair("sword2",""));
		assertEquals("addSigularPluralPair - one word exist", true, tester.addSingularPluralPair("sword2", "pword2"));
		tester.singularPluralTable.add(new SingularPluralPair("sword3","pword3"));
		assertEquals("addSigularPluralPair - pair exists", false, tester.addSingularPluralPair("sword3", "pword3"));	
	}
	
	@Test
	public void testIsInSingularPluralPair() {
		// Method inSingularPluralPair
		assertEquals("inSingularPluralPair - null", false,
				tester.isInSingularPluralPair("word"));
		tester.singularPluralTable.add(new SingularPluralPair(
				"word1", ""));
		assertEquals("inSingularPluralPair - singular match", true,
				tester.isInSingularPluralPair("word1"));
		tester.singularPluralTable.add(new SingularPluralPair("",
				"word2"));
		assertEquals("inSingularPluralPair - plural match", true,
				tester.isInSingularPluralPair("word2"));
		tester.singularPluralTable.add(new SingularPluralPair(
				"word3", "word3"));
		assertEquals("inSingularPluralPair - both match", true,
				tester.isInSingularPluralPair("word3"));
	}
	
	@Test
	public void testAddModifier(){
		Map<String, ModifierTableValue> target = new HashMap<String, ModifierTableValue>();
		target.put("word", new ModifierTableValue(1,false));
		tester.addModifier("word", 10);
		assertEquals("addModifier - add", target, tester.modifierTable);
		target.put("word", new ModifierTableValue(10,false));
		tester.addModifier("word", 9);
		assertEquals("addModifier - add", target, tester.modifierTable);
	}
	
	@Test
	public void testUpdateUnknownWord(){
		Map<String, String> target = new HashMap<String, String>();
		target.put("word", "word");
		tester.unknownWordTable.put("word", "unknown");
		tester.updateUnknownWord("word", "word");
		assertEquals("updateUnknownWord - add", target, tester.unknownWordTable);
	}

}
