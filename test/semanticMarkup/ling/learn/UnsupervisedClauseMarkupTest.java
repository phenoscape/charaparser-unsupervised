package semanticMarkup.ling.learn;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import semanticMarkup.core.Treatment;
import semanticMarkup.ling.learn.UnsupervisedClauseMarkup;
import semanticMarkup.ling.learn.FileLoader;

public class UnsupervisedClauseMarkupTest {
	
	@Test
	public void testUnsupervisedClauseMarkup() {
		String str = "/Users/nescent/Phenoscape/TEST2/target/descriptions";
		List<Treatment> treatments_l = new ArrayList<Treatment>();
				
		UnsupervisedClauseMarkup tester = new UnsupervisedClauseMarkup(str,"biocreative2012","plain","test");
		
		/*
		assertEquals("Result", null, tester.getAdjNouns());
		assertEquals("Result", null, tester.getAdjNounSent());
		assertEquals("Result", null, tester.getBracketTags());
		assertEquals("Result", null, tester.getHeuristicNouns());
		assertEquals("Result", null, tester.getRoleToWords());
		assertEquals("Result", null, tester.getSentences());
		assertEquals("Result", null, tester.getSentencesForOrganStateMarker());
		assertEquals("Result", null, tester.getSentenceTags());
		assertEquals("Result", null, tester.getTermCategories());
		assertEquals("Result", null, tester.getWordRoleTags());
		assertEquals("Result", null, tester.getWordsToRoles());
		assertEquals("Result", null, tester.getWordToSources());
		assertEquals("Result", true, tester.populatesents());
		*/
		
		//FileLoader sentLoader = new FileLoader(str);
		//sentLoader.load();
		//sentLoader.getUnknownWordList();
		//assertEquals("Result", 1, sentLoader.GetType("Buckup_1998.xml_5c157037-01e4-4d48-8014-b1ebfc9dc120_8210ee00-8026-4fd9-974f-2f4cf6ce389f.txt"));
		//assertEquals("Result", 0, sentLoader.GetType("Buckup_1998.xml_8d819b51-b88a-459e-bcb2-c6137d8b95d7.txt"));
		
		// test method hideMarksInBrackets
		assertEquals("Result", null, 
				tester.hideMarksInBrackets(null));
		assertEquals("Result", "", 
				tester.hideMarksInBrackets(""));
		assertEquals("Result", "before (word[DOT]  word) after",
				tester.hideMarksInBrackets("before (word. word) after"));
		assertEquals("Result", "before (word[QST]  word) after",
				tester.hideMarksInBrackets("before (word? word) after"));
		assertEquals("Result", "before (word[SQL]  word) after",
				tester.hideMarksInBrackets("before (word; word) after"));
		assertEquals("Result", "before (word[QLN]  word) after",
				tester.hideMarksInBrackets("before (word: word) after"));
		assertEquals("Result", "before (word[EXM]  word) after",
				tester.hideMarksInBrackets("before (word! word) after"));
		
		// test method restoreMarksInBrackets
		assertEquals("Result", null,
				tester.restoreMarksInBrackets(null));
		assertEquals("Result", "",
				tester.restoreMarksInBrackets(""));	
		assertEquals("Result", "before (word.  word) after",
				tester.restoreMarksInBrackets("before (word[DOT]  word) after"));	
		assertEquals("Result", "before (word?  word) after",
				tester.restoreMarksInBrackets("before (word[QST]  word) after"));
		assertEquals("Result", "before (word;  word) after",
				tester.restoreMarksInBrackets("before (word[SQL]  word) after"));
		assertEquals("Result", "before (word:  word) after",
				tester.restoreMarksInBrackets("before (word[QLN]  word) after"));
		assertEquals("Result", "before (word!  word) after",
				tester.restoreMarksInBrackets("before (word[EXM]  word) after"));
		
		// test method handleTest (Fully finished - Dongye 01/08)
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
		assertEquals("Result", "word   word", tester.handleText("word &amp; word"));
		// " & " => " and "
		assertEquals("Result", "word and word.",
				tester.handleText("word & word."));
		// "_" => "-"
		assertEquals("Result", "word-word.", 
				tester.handleText("word_word."));
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
		
		// test method addSpace
		// null
		assertEquals("Result", null, tester.addSpace(null,null));
		// ""
		assertEquals("Result", "", tester.addSpace("", ""));
		assertEquals("Result", "word , word ; word : word ! word ? word . ",
				tester.addSpace("word,word;word:word!word?word.", "\\W"));

		// test method handleString
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
		assertEquals("Result", "word cup_ shaped word cup_ shaped word cup_ shaped word .",
				tester.handleSentence("word cup --- shaped word cup-shaped word cup ---------        shaped word."));		
		
		// multiple spaces => 1 space
		assertEquals("Result", "word word word .",
				tester.handleSentence("word  word	 word."));
		// remove multipe spaces at the beginning
		assertEquals("Result", "word word .", tester.handleSentence("  	word word."));
		// remove multipe spaces at the rear
		assertEquals("Result", "word word .", tester.handleSentence("word word.    "));		
		
	}
}
