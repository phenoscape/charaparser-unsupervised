package semanticMarkup.ling.learn;

import java.util.List;
import java.util.Map;

import semanticMarkup.core.Treatment;

public class Learner {

	public Learner() {
		// TODO Auto-generated constructor stub
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
