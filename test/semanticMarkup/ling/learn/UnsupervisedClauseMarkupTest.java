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
		
		FileLoader sentLoader = new FileLoader(str);
		sentLoader.load();
		sentLoader.getUnknownWordList();
		//assertEquals("Result", 1, sentLoader.GetType("Buckup_1998.xml_5c157037-01e4-4d48-8014-b1ebfc9dc120_8210ee00-8026-4fd9-974f-2f4cf6ce389f.txt"));
		//assertEquals("Result", 0, sentLoader.GetType("Buckup_1998.xml_8d819b51-b88a-459e-bcb2-c6137d8b95d7.txt"));
		
		
		
		
	}
}
