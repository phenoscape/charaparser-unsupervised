package semanticMarkup.ling.learn;

import java.util.List;
import java.util.Map;

import semanticMarkup.core.Treatment;

public class Learner {

	private String learningMode;
	
	// Utilities
	private WordFormUtility myWordFormUtility;

	public Learner(String learningMode, String wordnetDir) {
		this.learningMode = learningMode;
		
		// Utilities
		this.myWordFormUtility = new WordFormUtility(wordnetDir);
	}

	public DataHolder Learn(List<Treatment> treatments) {
		DataHolder myDataHolder = new DataHolder();
		
		Map<String, String> myHeuristicNounTable = myDataHolder.getHeuristicNounTable();
		myHeuristicNounTable.put("word1", "type1");
		
		List<Sentence> mySentenceTable = myDataHolder.getSentenceTable();
		mySentenceTable.add(new Sentence("source1", "sentence1", "originalSentence", "lead1", "status1", "tag1", "modifier1", "type1"));

		return myDataHolder;
	}

}
