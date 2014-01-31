package semanticMarkup.ling.learn.knowledge;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import semanticMarkup.know.lib.WordNetPOSKnowledgeBase;
import semanticMarkup.ling.learn.Configuration;
import semanticMarkup.ling.learn.Learner;
import semanticMarkup.ling.learn.utility.LearnerUtility;
import semanticMarkup.ling.transform.ITokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPSentencesTokenizer;
import semanticMarkup.ling.transform.lib.OpenNLPTokenizer;

public class InitiationTest {
	
	private Initiation tester;

	@Before
	public void initialize() {
		this.tester = InitiationFactory();
	}

	private Initiation InitiationFactory() {
		Initiation tester;

		Configuration myConfiguration = new Configuration();
		ITokenizer tokenizer = new OpenNLPTokenizer(
				myConfiguration.getOpenNLPTokenizerDir());
		ITokenizer sentenceDetector = new OpenNLPSentencesTokenizer(
				myConfiguration.getOpenNLPSentenceDetectorDir());
		WordNetPOSKnowledgeBase wordNetPOSKnowledgeBase = null;
		try {
			wordNetPOSKnowledgeBase = new WordNetPOSKnowledgeBase(myConfiguration.getWordNetDictDir(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		LearnerUtility myLearnerUtility = new LearnerUtility(sentenceDetector,
				tokenizer, wordNetPOSKnowledgeBase);
		tester = new Initiation(myLearnerUtility, myConfiguration.getNumLeadWords()); 
				
		return tester;
	}

	@Test
	public void testHandleSentence() {
		// handleString
		// null
		assertEquals("Result", null, tester.handleSentence(null));
		// ""
		assertEquals("Result", "", tester.handleSentence(""));
		// remove (.a.)
		assertEquals("Result", "word word word word .",
				tester.handleSentence("word (.a.) word (a) word ( a ) word."));
		// remove [.a.]
		assertEquals("Result", "word word word word .",
				tester.handleSentence("word [.a.] word [a] word [ a ] word."));
		// remove {.a.}
		assertEquals("Result", "word word word word .",
				tester.handleSentence("word {.a.} word {a} word { a } word."));
		// to fix basi- and hypobranchial
		assertEquals(
				"Result",
				"word cup_ shaped word cup_ shaped word cup_ shaped word .",
				tester.handleSentence("word cup --- shaped word cup-shaped word cup ---------        shaped word."));

		// multiple spaces => 1 space
		assertEquals("Result", "word word word .",
				tester.handleSentence("word  word	 word."));
		// remove multipe spaces at the beginning
		assertEquals("Result", "word word .",
				tester.handleSentence("  	word word."));
		// remove multipe spaces at the rear
		assertEquals("Result", "word word .",
				tester.handleSentence("word word.    "));
	}
	
	@Test
	public void testHandleText() {
		// handleTest (Fully finished - Dongye 01/08)
		// null
		assertEquals("Result", null, tester.handleText(null));
		// ""
		assertEquals("Result", "", tester.handleText(""));
		// remove " and '
		assertEquals("Result", "words word", tester.handleText("word's wo\"rd"));
		// plano - to
		assertEquals("Result", "word to word",
				tester.handleText("word -to word"));
		//
		assertEquals("Result", "word -shaped",
				tester.handleText("word ______shaped"));
		// unhide <i>
		assertEquals("Result", "word <i> word.",
				tester.handleText("word &lt;i&gt; word."));
		// unhide </i>
		assertEquals("Result", "word </i> word.",
				tester.handleText("word &lt;/i&gt; word."));
		// remove 2a. (key marks)
		assertEquals("Result", "word", tester.handleText("7b. word"));
		// remove HTML entities
		assertEquals("Result", "word   word",
				tester.handleText("word &amp; word"));
		// " & " => " and "
		assertEquals("Result", "word and word.",
				tester.handleText("word & word."));
		// "_" => "-"
		assertEquals("Result", "word-word.", tester.handleText("word_word."));
		// absent ; => absent;
		assertEquals("Result", "word; word; word.",
				tester.handleText("word ;word ;word."));
		// absent;blade => absent; blade
		assertEquals("Result", "word; word; word.",
				tester.handleText("word;word;word."));
		assertEquals("Result", "word: word. word.",
				tester.handleText("word:word.word."));
		// 1 . 5 => 1.5
		assertEquals("Result", "word 1.5 word 384739.84 word.",
				tester.handleText("word 1 . 5 word 384739 . 84 word."));
		// #diam . =>diam.
		assertEquals("Result", "word diam. word diam. word.",
				tester.handleText("word diam . word diam . word."));
		// ca . =>ca.
		assertEquals("Result", "word ca. word ca. word.",
				tester.handleText("word ca . word ca . word."));
		// cm|mm|dm|m
		assertEquals("Result", "word 12 cm[DOT] word 376 mm[DOT] word.",
				tester.handleText("word 12 cm . word 376 mm. word."));

	}

}
