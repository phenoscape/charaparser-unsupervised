package semanticMarkup.ling.learn.knowledge;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
		List<String> words = new ArrayList<String>();
		words.addAll(Arrays
				.asList("large interlocking <N>plates</N> <B>with</B> pronounced crescentic <N>margins</N>"
						.split(" ")));
		String ptn = "qqnbqqn";
		
		MarkupByPOS myTester = this.markupByPOSFactory();
		DataHolder myDataHolder = this.dataHolderFactory();
		
		myTester.CaseHandler(myDataHolder, null, words, ptn);
		
		
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
		tester = new DataHolder(myConfiguration, wordFormUtility);

		return tester;
	}

}
