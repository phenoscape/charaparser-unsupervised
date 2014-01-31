package semanticMarkup.ling.learn.knowledge;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import semanticMarkup.know.lib.WordNetPOSKnowledgeBase;
import semanticMarkup.ling.learn.Configuration;
import semanticMarkup.ling.learn.Learner;
import semanticMarkup.ling.learn.dataholder.DataHolder;
import semanticMarkup.ling.learn.utility.LearnerUtility;
import semanticMarkup.ling.learn.utility.WordFormUtility;
import semanticMarkup.ling.transform.ITokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPSentencesTokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPTokenizer;

public class MarkupByPOSTest {

	@Test
	public void testCaseHandler() {
//		List<String> words = new ArrayList<String>();
//		words.addAll(Arrays
//				.asList("large interlocking <N>plates</N> <B>with</B> pronounced crescentic <N>margins</N>"
//						.split(" ")));
//		String ptn = "qqnbqqn";
//		
//		MarkupByPOS myTester = this.markupByPOSFactory();
//		DataHolder myDataHolder = this.dataHolderFactory();
//		
//		myTester.CaseHandler(myDataHolder, null, words, ptn);
		
		
	}
	
	@Test
	public void testGetModifierAndTagForCase2() {
		MarkupByPOS myTester = this.markupByPOSFactory();

		String modifier = "large interlocking";
		int start = 2;
		int end = 3;
		List<String> words = new ArrayList<String>();
		words.addAll(Arrays.asList("large interlocking <N>plates</N> <B>with</B> pronounced crescentic <N>margins</N>"
				.split(" ")));
		
		List<String> target = new LinkedList<String>();
		target.add("large interlocking");
		target.add("<N>plates</N>");

		assertEquals("getModifierAndTagForCase1", target, myTester.getModifierAndTagForCase2(modifier, start, end, words));
		
	}
	
	@Test
	public void testGetModifierAndTagForCase3() {
		MarkupByPOS myTester = this.markupByPOSFactory();
		
		String ptn = "ntqqq,qbbn";
		List<String> words = new ArrayList<String>();
		words.addAll(Arrays
				.asList("<N>orbit</N> <M>dorsal</M> or dorsolaterally facing , surrounded <B>laterally</B> <B>by</B> <N>endocranium</N>"
						.split(" ")));
		
		List<String> target = new LinkedList<String>();
		target.add("");
		target.add("orbit");
		
		assertEquals("getModifierAndTagForCase3", target, myTester.getModifierAndTagForCase3(ptn, words));
		
		ptn = "qbbnbtn";
		assertEquals("getModifierAndTagForCase3 - null", null, myTester.getModifierAndTagForCase3(ptn, words));
	}
	
	
	@Test
	public void testGetModifiersForUntag() {
		String modifier = "enlarged postorbital <N>tessera</N>";
		List<String> target = new LinkedList<String>();
		target.add("enlarged");		
		target.add("postorbital");
		
		MarkupByPOS myTester = this.markupByPOSFactory();
		assertEquals("getModifiersForUntag", target, myTester.getModifiersForUntag(modifier));
		assertEquals("getModifiersForUntag", "enlarged postorbital <N>tessera</N>", modifier);
	}
	
	private MarkupByPOS markupByPOSFactory() {
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
		
		LearnerUtility myLearnerUtility = new LearnerUtility(sentenceDetector, tokenizer, wordNetPOSKnowledgeBase);
		MarkupByPOS myMarkupByPOS = new MarkupByPOS(myLearnerUtility);
		
		return myMarkupByPOS;
	}
	
	private DataHolder dataHolderFactory() {
		DataHolder tester;

		Configuration myConfiguration = new Configuration();
		WordNetPOSKnowledgeBase wordNetPOSKnowledgeBase = null;
		try {
			wordNetPOSKnowledgeBase = new WordNetPOSKnowledgeBase(myConfiguration.getWordNetDictDir(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		WordFormUtility wordFormUtility = new WordFormUtility(wordNetPOSKnowledgeBase);
		Constant myConstant = new Constant();
		tester = new DataHolder(myConfiguration, myConstant, wordFormUtility);

		return tester;
	}

}
