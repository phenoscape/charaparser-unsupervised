package semanticMarkup.ling.learn.knowledge;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import semanticMarkup.know.lib.WordNetPOSKnowledgeBase;
import semanticMarkup.ling.learn.Configuration;
import semanticMarkup.ling.learn.Learner;
import semanticMarkup.ling.learn.LearnerUtility;
import semanticMarkup.ling.learn.WordFormUtility;
import semanticMarkup.ling.learn.dataholder.DataHolder;
import semanticMarkup.ling.learn.dataholder.SentenceStructure;
import semanticMarkup.ling.transform.ITokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPSentencesTokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPTokenizer;

public class UnknownWordBootstrappingTest {
	private UnknownWordBootstrapping unknownWordBootstrappingModule;
	
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
		
		LearnerUtility learnerUtility = new LearnerUtility(sentenceDetector, tokenizer, wordNetPOSKnowledgeBase);
		this.unknownWordBootstrappingModule = new UnknownWordBootstrapping(learnerUtility);
	}
	
	@Test
	public void testUnknownWordBootstrapping(){
		
		// 1. Preprocessing
//		Learner myTester1 = learnerFactory();
//		myTester1.getDataHolder().add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList("word1 unknown".split(" ")));
//		Set<String> expected = new HashSet<String>();
////		expected.add("")
//		assertEquals("unknownWordBootstrappingGetUnknownWord", expected , myTester1.unknownWordBootstrappingGetUnknownWord("(ee)"));
		
		
		
		// 3. Postprocessing
		DataHolder myDataHolder3 = dataholderFactory();
		
		myDataHolder3.add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"word1", "p", "role", "0", "0", "", ""}));
		myDataHolder3.add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"word2", "b", "role", "0", "0", "", ""}));
		myDataHolder3.add2Holder(DataHolder.WORDPOS, 
				Arrays.asList(new String[] {"word3", "s", "role", "0", "0", "", ""}));
		
		myDataHolder3.add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList("word1 word1".split(" ")));
		myDataHolder3.add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList("word2 unknown".split(" ")));
		myDataHolder3.add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList("_wORd3 unknown".split(" ")));
		myDataHolder3.add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList("word?_4 unknown".split(" ")));
		myDataHolder3.add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList("nor unknown".split(" ")));
		myDataHolder3.add2Holder(DataHolder.UNKNOWNWORD, Arrays.asList("word_6 unknown".split(" ")));

		
		myDataHolder3.getSentenceHolder().add(new SentenceStructure(0, "src", "word1 word_6 word2", "osent","lead","status","tag","modifer","type"));
		myDataHolder3.getSentenceHolder().add(new SentenceStructure(1, "src", "word_6 word2", "osent","lead","status","tag","modifer","type"));
		myDataHolder3.getSentenceHolder().add(new SentenceStructure(2, "src", "word1 word6 word2", "osent","lead","status","tag","modifer","type"));
		
		unknownWordBootstrappingModule.unknownWordBootstrappingPostprocessing(myDataHolder3);
		assertEquals("unknownWordBootstrapping - Postprocessing", "word1 <B>word_6</B> word2", myDataHolder3.getSentence(0).getSentence());
		assertEquals("unknownWordBootstrapping - Postprocessing", "<B>word_6</B> word2", myDataHolder3.getSentence(1).getSentence());
		assertEquals("unknownWordBootstrapping - Postprocessing", "word1 word6 word2", myDataHolder3.getSentence(2).getSentence());
		
	}
	
	@Test
	public void testIsVerbEnding(){
		DataHolder myDataHolder = dataholderFactory();
		
//		assertEquals("isVerbEnding - case 1 - true", true, unknownWordBootstrappingModule.isVerbEnding(myDataHolder, "achenes"));
		assertEquals("isVerbEnding - case 1 - false", false, unknownWordBootstrappingModule.isVerbEnding(myDataHolder, "achenes"));
//		assertEquals("isVerbEnding - case 2 - true", true, unknownWordBootstrappingModule.isVerbEnding(myDataHolder, ""));
		assertEquals("isVerbEnding - case 2 - false", false, unknownWordBootstrappingModule.isVerbEnding(myDataHolder, "Armenia"));
	}
	
	private DataHolder dataholderFactory() {
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
		tester = new DataHolder(myConfiguration, wordFormUtility);

		return tester;
	}
	
	

}
