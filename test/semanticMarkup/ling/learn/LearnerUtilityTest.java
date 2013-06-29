package semanticMarkup.ling.learn;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class LearnerUtilityTest {

	@Test
	public void testTagSentence() {
		Configuration myConfiguration = new Configuration();
		myConfiguration.setMaxTagLength(10);
		Utility myUtility = new Utility(myConfiguration);
		DataHolder myDataHolder = new DataHolder(myConfiguration, myUtility); 
		LearnerUtility myTester = new LearnerUtility(myConfiguration); 
		
		myDataHolder.add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent", "osent","lead","status","tag","m","type"}));

		assertEquals("tagIt - case 1", false, myTester.tagSentence(myDataHolder, 0, ""));
		assertEquals("tagIt - case 2", false, myTester.tagSentence(myDataHolder, 0, "page"));
		assertEquals("tagIt - case 3", true, myTester.tagSentence(myDataHolder, 0, "teeth"));
		myTester.tagSentence(myDataHolder, 0, "abcdefghijkl");
		assertEquals("tagIt - max tag length", "abcdefghij", myDataHolder.getSentenceHolder().get(0).getTag());
	}

}
