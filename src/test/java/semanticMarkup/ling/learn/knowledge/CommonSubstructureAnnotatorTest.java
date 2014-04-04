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
import semanticMarkup.ling.learn.dataholder.DataHolder;
import semanticMarkup.ling.learn.utility.LearnerUtility;
import semanticMarkup.ling.transform.ITokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPSentencesTokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPTokenizer;

public class CommonSubstructureAnnotatorTest {
	private CommonSubstructureAnnotator tester;
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
		this.tester = new CommonSubstructureAnnotator();
	}
	
	@Test
	public void testGetCommonStructures() {
		DataHolder myDataHolder = new DataHolder(this.configuration,
				myLearnerUtility.getConstant(),
				myLearnerUtility.getWordFormUtility());

		// "structure2" and "structure3" are common structures, "structure1" is
		// not
		myDataHolder.add2Holder(
				DataHolder.WORDPOS,
				Arrays.asList(new String[] { "structure1", "b", "role", "1",
						"1", "", "" }));
		myDataHolder.add2Holder(
				DataHolder.WORDPOS,
				Arrays.asList(new String[] { "structure2", "p", "role", "1",
						"1", "", "" }));
		myDataHolder.add2Holder(
				DataHolder.WORDPOS,
				Arrays.asList(new String[] { "structure3", "s", "role", "1",
						"1", "", "" }));

		myDataHolder.add2Holder(
				DataHolder.SENTENCE,
				Arrays.asList(new String[] { "src", "sent", "osent", "lead",
						"status", "tag1", "structure1", "type" }));
		myDataHolder.add2Holder(
				DataHolder.SENTENCE,
				Arrays.asList(new String[] { "src", "sent", "osent", "lead",
						"status", "tag2", "structure2", "type" }));
		myDataHolder.add2Holder(
				DataHolder.SENTENCE,
				Arrays.asList(new String[] { "src", "sent", "osent", "lead",
						"status", "tag3", "structure2", "type" }));
		myDataHolder.add2Holder(
				DataHolder.SENTENCE,
				Arrays.asList(new String[] { "src", "sent", "osent", "lead",
						"status", "tag3", "structure3", "type" }));

		Set<String> target = new HashSet<String>(Arrays.asList("tag3"));
		assertEquals("getCommonStructures", target,
				tester.getCommonStructures(myDataHolder));
	}

}
