package semanticMarkup.ling.learn;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import semanticMarkup.ling.transform.ISentenceDetector;
import semanticMarkup.ling.transform.ITokenizer;
import semanticMarkup.ling.transform.lib.UnsupervisedLearningSentenceDetector;
import semanticMarkup.ling.transform.lib.UnsupervisedLearningTokenizer;

public class SentenceLeadLengthComparatorTest {

	@Test
	public void test() {
		Configuration myConfiguration = new Configuration();
		ISentenceDetector sentenceDetector = new UnsupervisedLearningSentenceDetector(
				myConfiguration.getOpenNLPSentenceDetectorDir());
		ITokenizer tokenizer = new UnsupervisedLearningTokenizer(myConfiguration.getOpenNLPTokenizerDir());
		Utility myUtility = new Utility(myConfiguration, sentenceDetector, tokenizer);

		Learner myTester = new Learner(myConfiguration, tokenizer, myUtility);
		DataHolder target = new DataHolder(myConfiguration, myUtility);
		
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
