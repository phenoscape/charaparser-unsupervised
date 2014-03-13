package semanticMarkup.ling.learn.knowledge;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

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

public class CoreBootstrappingLearnerTest {
	private CoreBootstrappingLearner tester;
	private Configuration configuration;
	private LearnerUtility myLearnerUtility;
	
	@Before
	public void initialize() {
		this.configuration = new Configuration();
		ITokenizer sentenceDetector = new OpenNLPSentencesTokenizer(
				configuration.getOpenNLPSentenceDetectorDir());
		ITokenizer tokenizer = new OpenNLPTokenizer(configuration.getOpenNLPTokenizerDir());
		
		WordNetPOSKnowledgeBase wordNetPOSKnowledgeBase = null;
		try {
			wordNetPOSKnowledgeBase = new WordNetPOSKnowledgeBase(configuration.getWordNetDictDir(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		this.myLearnerUtility = new LearnerUtility(sentenceDetector, tokenizer, wordNetPOSKnowledgeBase);
		this.tester = new CoreBootstrappingLearner(myLearnerUtility, configuration);
	}


	@Test
	public void testBuildPattern() {		
		CoreBootstrappingLearner myTester = this.tester;
		DataHolder myDataHolder = new DataHolder(this.configuration,
				myLearnerUtility.getConstant(),
				myLearnerUtility.getWordFormUtility());

		// Method buildPattern
//		assertEquals(
//				"buildPattern",
//				"(?:^\\b(?:one|two|three)\\b|^\\w+\\s\\b(?:one|two|three)\\b|^\\w+\\s\\w+\\s\\b(?:one|two|three)\\b)",
//				tester.buildPattern("one two three".split(" ")));
		
		HashSet<String> wordSet= new HashSet<String>();
		wordSet.add("teeth");
		wordSet.add("unicuspid");
		wordSet.add("with");
		myDataHolder.setCheckedWordSet(wordSet);
		
		
		assertEquals("buildPattern", null,
				myTester.buildPattern(myDataHolder, "teeth ; 9".split(" ")));
		
		assertEquals("buildPattern", 
				"(?:^\\b(?:variously|arranged)\\b|^\\w+\\s\\b(?:variously|arranged)\\b|^\\w+\\s\\w+\\s\\b(?:variously|arranged)\\b).*$",
				myTester.buildPattern(myDataHolder, "teeth variously arranged".split(" ")));
		
		wordSet.add("circuli");
		wordSet.add("present");
		wordSet.add("on");
		wordSet.add("hyohyoidei");
		wordSet.add("muscle");
		
		assertEquals("buildPattern", 
				"(?:^\\b(?:does|not|cross)\\b|^\\w+\\s\\b(?:does|not|cross)\\b|^\\w+\\s\\w+\\s\\b(?:does|not|cross)\\b).*$",
				myTester.buildPattern(myDataHolder, "does not cross".split(" ")));
		
		wordSet.addAll(Arrays.asList("lepidotrichia:of:passes:between:bases".split(":")));
		
		assertEquals("buildPattern", 
				"(?:^\\b(?:ankylosed|to)\\b|^\\w+\\s\\b(?:ankylosed|to)\\b|^\\w+\\s\\w+\\s\\b(?:ankylosed|to)\\b).*$",
				myTester.buildPattern(myDataHolder, "teeth ankylosed to".split(" ")));		
		
	}

}
