package semanticMarkup.ling.learn.knowledge;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import semanticMarkup.know.lib.WordNetPOSKnowledgeBase;
import semanticMarkup.ling.learn.Configuration;
import semanticMarkup.ling.learn.Learner;
import semanticMarkup.ling.learn.dataholder.DataHolder;
import semanticMarkup.ling.learn.utility.LearnerUtility;
import semanticMarkup.ling.transform.ITokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPSentencesTokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPTokenizer;

public class AndOrTagSetterTest {
	private AndOrTagSetter tester;
	private Configuration configuration;
	private LearnerUtility myLearnerUtility;

	@Before
	public void initialize() {
		this.configuration = new Configuration();
		ITokenizer tokenizer = new OpenNLPTokenizer(
				this.configuration.getOpenNLPTokenizerDir());
		ITokenizer sentenceDetector = new OpenNLPSentencesTokenizer(
				this.configuration.getOpenNLPSentenceDetectorDir());
		WordNetPOSKnowledgeBase wordNetPOSKnowledgeBase = null;
		try {
			wordNetPOSKnowledgeBase = new WordNetPOSKnowledgeBase(this.configuration.getWordNetDictDir(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		this.myLearnerUtility = new LearnerUtility(sentenceDetector,
				tokenizer, wordNetPOSKnowledgeBase);
		
		this.tester = new AndOrTagSetter(this.myLearnerUtility);
	}

	@Test
	public void testIsIsAndOrSentence(){
		String sentencePtn = null;
		String ptn1 = null;
		String ptn2 = null;
		List<String> words = new ArrayList<String>();
		
//		// case 1
//		words.clear();
//		words.addAll(Arrays.asList("posterior and <M>dorsal</M> to foramen <B>for</B> nerve <N>ii</N>".split(" ")));
//		sentencePtn = "q&mqqbqn";
//		ptn1="^(?:[mbq,]{0,10}[onp]+(?:,|(?=&)))+&(?:[mbq,]{0,10}[onp]+)"; // n,n,n&n
//		ptn2="^(?:[mbq,]{0,10}(?:,|(?=&)))+&(?:[mbq,]{0,10})[onp]+"; // m,m,&mn
//		
//		assertEquals("isIsAndOrSentence case 1", false,
//				tester.isIsAndOrSentenceHelper(words, sentencePtn, ptn1, ptn2));
		
		// case 2
		words.clear();
		words.addAll(Arrays.asList("elongate and <O>passes</O> <B>anterolaterally</B> through orbital <B>?</B> oor".split(" ")));
		sentencePtn = "q&obqqbq";
		ptn1="^(?:[mbq,]{0,10}[onp]+(?:,|(?=&)))+&(?:[mbq,]{0,10}[onp]+)"; // n,n,n&n
		ptn2="^(?:[mbq,]{0,10}(?:,|(?=&)))+&(?:[mbq,]{0,10})[onp]+"; // m,m,&mn
		
		assertEquals("isIsAndOrSentence case 2", true,
				tester.isIsAndOrSentenceHelper(words, sentencePtn, ptn1, ptn2));
		
		// case 3
		words.clear();
		words.addAll(Arrays.asList("<O>divides</O> <B>within</B> otic <N>capsule</N> <B>at</B> <B>the</B> <N>level</N> <B>of</B> <B>the</B> postorbital process".split(" ")));
		sentencePtn = "q,obqnbbnbbqq";
		ptn1="^(?:[mbq,]{0,10}[onp]+(?:,|(?=&)))+&(?:[mbq,]{0,10}[onp]+)"; // n,n,n&n
		ptn2="^(?:[mbq,]{0,10}(?:,|(?=&)))+&(?:[mbq,]{0,10})[onp]+"; // m,m,&mn
		
		assertEquals("isIsAndOrSentence case 3", false,
				tester.isIsAndOrSentenceHelper(words, sentencePtn, ptn1, ptn2));
		
		
		
	}

	

}
