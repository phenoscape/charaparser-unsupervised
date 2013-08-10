package semanticMarkup.ling.learn;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import semanticMarkup.know.lib.WordNetPOSKnowledgeBase;
import semanticMarkup.ling.learn.dataholder.DataHolder;
import semanticMarkup.ling.learn.dataholder.SentenceStructure;
import semanticMarkup.ling.transform.ITokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPSentencesTokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPTokenizer;

public class SentenceLeadLengthComparatorTest {

	@Test
	public void test() {
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
		Learner myTester = new Learner(myConfiguration, tokenizer, myLearnerUtility);
		DataHolder target = new DataHolder(myConfiguration, myLearnerUtility.getWordFormUtility());
		
		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent nor", "osent","lead lead","status",null,"m","type"}));
		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent and", "osent","lea","status","","m","type"}));
		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent", "osent","lead lead lead","status","unknown","m","type"}));
		
//		target.add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent", "osent","lead1","status","unknown","m","type"}));
//		target.add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent nor", "osent","lead","status",null,"m","type"}));
//		target.add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent and", "osent","lea","status","","m","type"}));
		
		target.getSentenceHolder().add(new SentenceStructure(2, "src", "sent", "osent","lead lead lead","status","unknown","m","type"));
		target.getSentenceHolder().add(new SentenceStructure(0, "src", "sent nor", "osent","lead lead","status",null,"m","type"));
		target.getSentenceHolder().add(new SentenceStructure(1, "src", "sent and", "osent","lea","status","","m","type"));
		
		SentenceLeadLengthComparator myComparator = new SentenceLeadLengthComparator(false); 
		
		Collections.sort(myTester.getDataHolder().getSentenceHolder(), myComparator);
		
		assertEquals("", target.getSentenceHolder(), myTester.getDataHolder().getSentenceHolder());
		
	}

}
