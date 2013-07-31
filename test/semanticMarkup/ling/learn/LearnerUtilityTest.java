package semanticMarkup.ling.learn;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import semanticMarkup.ling.transform.ITokenizer;
import semanticMarkup.ling.transform.lib.UnsupervisedLearningTokenizer;

public class LearnerUtilityTest {
	
	private LearnerUtility tester;

	@Before
	public void initialize() {
		Configuration myConfiguration = new Configuration();
		ITokenizer tokenizer = new UnsupervisedLearningTokenizer(myConfiguration.getOpenNLPTokenizerDir());
		Utility myUtility = new Utility(myConfiguration, tokenizer);
		this.tester = new LearnerUtility(myConfiguration, tokenizer);
	}
	
	@Test
	public void testGetSentenceHead() {
		assertEquals("getSentenceHead - case 0.1 - null input", null, tester.getSentenceHead(null));
		assertEquals("getSentenceHead - case 0.2 - empty string input", "", tester.getSentenceHead(""));
		
		assertEquals("getSentenceHead - case 1", "word1 word2", tester.getSentenceHead("word1 word2 , word3"));
		assertEquals("getSentenceHead - case 1", "word1 word2", tester.getSentenceHead("word1 word2 : word3"));
		assertEquals("getSentenceHead - case 1", "word1 word2", tester.getSentenceHead("word1 word2 ; word3"));
		assertEquals("getSentenceHead - case 1", "word1 word2", tester.getSentenceHead("word1 word2 . word3"));
		assertEquals("getSentenceHead - case 1", "word1 word2", tester.getSentenceHead("word1 word2 [ word3"));
		assertEquals("getSentenceHead - case 1", "word1 word2", tester.getSentenceHead("word1 word2 ( word3"));
		
		assertEquals("getSentenceHead - case 2", "lepidotrichia", tester.getSentenceHead("lepidotrichia , of fin webs"));
		assertEquals("getSentenceHead - case 2", "bases of", tester.getSentenceHead("bases of tooth whorls"));
		
		
		assertEquals("getSentenceHead - case n", "word1 word2 word3", tester.getSentenceHead("word1 word2 word3"));
	}

}
