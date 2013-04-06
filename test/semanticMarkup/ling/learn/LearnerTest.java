package semanticMarkup.ling.learn;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import semanticMarkup.core.Treatment;

public class LearnerTest {

	@Test
	public void testLearn() {
		
		String str = "C:/Users/Dongye/Dropbox/ATEST/target/descriptions";
		FileLoader fileLoader = new FileLoader(str);
		fileLoader.load();

		List<Treatment> tms = fileLoader.getTreatmentList();
		
		DataHolder results = new DataHolder();
		
		Map<String, String> myHeuristicNounTable = results.getHeuristicNounTable();
		myHeuristicNounTable.put("word1", "type1");
		
		List<Sentence> mySentenceTable = results.getSentenceTable();
		mySentenceTable.add(new Sentence("source1", "sentence1", "originalSentence", "lead1", "status1", "tag1", "modifier1", "type1"));
		
		Learner tester = new Learner();
		
		assertEquals ("learner", results, tester.Learn(tms));
		
		results = tester.Learn(tms);
		
		assertEquals ("learner", results, tester.Learn(tms));
	}

}
