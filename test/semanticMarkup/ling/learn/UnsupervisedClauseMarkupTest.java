package semanticMarkup.ling.learn;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import semanticMarkup.core.Treatment;
import semanticMarkup.io.input.lib.db.ParentTagProvider;
import semanticMarkup.ling.learn.UnsupervisedClauseMarkup;
import semanticMarkup.ling.learn.FileLoader;

import semanticMarkup.ling.transform.ISentenceDetector;
import semanticMarkup.ling.transform.ITokenizer;
import semanticMarkup.ling.transform.lib.UnsupervisedLearningSentenceDetector;
import semanticMarkup.ling.transform.lib.UnsupervisedLearningTokenizer;

public class UnsupervisedClauseMarkupTest {
	UnsupervisedClauseMarkup tester;

	@Before
	public void initialize() {
		tester = UnsupervisedClauseMarkupFactory();
	}

	@Test
	public void testReadAdjNouns() {
		DataHolder myDataHolder = tester.getDataHolder();
		List<SentenceStructure> sentenceTable = myDataHolder.getSentenceHolder();
		sentenceTable.add(new SentenceStructure(0, "source1", "word1 word2", "", "", "",
				"tag1", "modifier1", ""));
		sentenceTable.add(new SentenceStructure(1, "source2", "word2 word3", "", "", "",
				"[tag2", " modifier2[abc]", ""));
		sentenceTable.add(new SentenceStructure(2, "source3", "word3", "", "", "", "[tag3",
				"[abc]modifier2	", ""));
		sentenceTable.add(new SentenceStructure(3, "source4", "word1 word3 word4", "", "",
				"", "[tag4", "	mo[123]difier3", ""));

		List<String> resultGetAdjNouns = new ArrayList<String>();
		resultGetAdjNouns.add("modifier3");
		resultGetAdjNouns.add("modifier2");

		assertEquals("Method readAdjNouns", resultGetAdjNouns,
				tester.readAdjNouns());
	}

	@Test
	public void testReadAdjNounSent() {
		UnsupervisedClauseMarkup myTester = UnsupervisedClauseMarkupFactory();
		
		DataHolder myDataHolder = myTester.getDataHolder();
		List<SentenceStructure> sentenceTable = myDataHolder.getSentenceHolder();
		sentenceTable.add(new SentenceStructure(0, "source1", "word1 word2", "", "", "",
				"tag1", "modifier1", ""));
		sentenceTable.add(new SentenceStructure(1, "source2", "word2 word3", "", "", "",
				"[tag2", " modifier2[abc]", ""));
		sentenceTable.add(new SentenceStructure(2, "source3", "word3", "", "", "", "[tag3",
				"[abc]modifier2	", ""));
		sentenceTable.add(new SentenceStructure(3, "source4", "word1 word3 word4", "", "",
				"", "[tag4", "	mo[123]difier3", ""));

		Map<String, String> resultGetAdjNounSent = new HashMap<String, String>();
		resultGetAdjNounSent.put("[tag2", "modifier2");
		resultGetAdjNounSent.put("[tag3", "modifier2");
		resultGetAdjNounSent.put("[tag4", "modifier3");

		assertEquals("Method readAdjNouns", resultGetAdjNounSent,
				myTester.readAdjNounSent());
	}
	
	@Test
	public void testReadBracketTags() {
		UnsupervisedClauseMarkup myTester = UnsupervisedClauseMarkupFactory();
		
		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent", "osent","lead","status","tag","start","type"}));
		myTester.getDataHolder().add2Holder(DataHolder.SENTENCE, Arrays.asList(new String[] {"src", "sent", "osent","lead","status","[tag]","start end","type"}));
		
		Set<String> target = new HashSet<String>();
		target.add("end");
		
		assertEquals("Method readBracketTags", target, myTester.readBracketTags());
	}

	@Test
	public void testReadWordToSoures() {
		UnsupervisedClauseMarkup myTester = UnsupervisedClauseMarkupFactory();
		
		DataHolder myDataHolder = myTester.getDataHolder();
		List<SentenceStructure> sentenceTable = myDataHolder.getSentenceHolder();
		sentenceTable.add(new SentenceStructure(0, "source.ignore.1", "word1 word2", "", "", "",
				"tag1", "modifier1", ""));
		sentenceTable.add(new SentenceStructure(1, "source.ignore.2", "word2 word3", "", "", "",
				"[tag2", " modifier2[abc]", ""));
		sentenceTable.add(new SentenceStructure(2, "source.ignore.3", "word3", "", "", "", "[tag3",
				"[abc]modifier2	", ""));
		sentenceTable.add(new SentenceStructure(3, "source.ignore.4", "word1 word3 word4", "", "",
				"", "[tag4", "	mo[123]difier3", ""));

		// getWordToSources
		Map<String, Set<String>> resultGetWordToSources = new HashMap<String, Set<String>>();
		resultGetWordToSources.put("word1", new HashSet<String>());
		resultGetWordToSources.get("word1").add("source.1");
		resultGetWordToSources.get("word1").add("source.4");

		resultGetWordToSources.put("word2", new HashSet<String>());
		resultGetWordToSources.get("word2").add("source.1");
		resultGetWordToSources.get("word2").add("source.2");

		resultGetWordToSources.put("word3", new HashSet<String>());
		resultGetWordToSources.get("word3").add("source.2");
		resultGetWordToSources.get("word3").add("source.3");
		resultGetWordToSources.get("word3").add("source.4");

		resultGetWordToSources.put("word4", new HashSet<String>());
		resultGetWordToSources.get("word4").add("source.4");

		assertEquals("Method readWordToSources", resultGetWordToSources,
				myTester.readWordToSources());
	}

	@Test
	public void testReadHeuristicNouns() {
		UnsupervisedClauseMarkup myTester = UnsupervisedClauseMarkupFactory();
		
		DataHolder myDataHolder = myTester.getDataHolder();
		Map<String, String> myHeuristicNouns = myDataHolder
				.getHeuristicNounTable();
		myHeuristicNouns.put("word1", "type1");
		myHeuristicNouns.put("word2", "type2");

		Map<String, String> resultGetHeuristicNouns = new HashMap<String, String>();
		resultGetHeuristicNouns.put("word2", "type2");
		resultGetHeuristicNouns.put("word1", "type1");

		assertEquals("Method readHeuristicNouns", resultGetHeuristicNouns,
				myTester.readHeuristicNouns());
	}
	
	private UnsupervisedClauseMarkup UnsupervisedClauseMarkupFactory() {
		Configuration myConfiguration = new Configuration();

		ParentTagProvider parentTagProvider = new ParentTagProvider();
		ISentenceDetector sentenceDetector = new UnsupervisedLearningSentenceDetector(
				myConfiguration.getOpenNLPSentenceDetectorDir());
		Set<String> selectedSources = new HashSet<String>();
		ITokenizer tokenizer = new UnsupervisedLearningTokenizer(myConfiguration.getOpenNLPTokenizerDir());
		UnsupervisedClauseMarkup myUnsupervisedClauseMarkup = new UnsupervisedClauseMarkup("plain", parentTagProvider, selectedSources, sentenceDetector, tokenizer);
		
		return myUnsupervisedClauseMarkup;
	}

}