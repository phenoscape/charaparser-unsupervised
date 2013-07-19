package semanticMarkup.ling.learn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import opennlp.tools.tokenize.TokenizerME;

import semanticMarkup.core.Treatment;

public class UnsupervisedClauseMarkup implements ITerminologyLearner {	
	// Date holder
	public DataHolder myDataHolder;
	
	// Configuration
	private Configuration myConfiguration;
		
	// Utility
	private Utility myUtility;

	// Learner
	private Learner myLearner;

	/**
	 * Constructor of UnsupervisedClauseMarkup class. Create a new
	 * UnsupervisedClauseMarkup object.
	 * 
	 * @param learningMode
	 *            learning mode. There two legal values, "adj" and "plain"
	 * @param wordnetDir
	 *            directory of WordNet dictionary
	 */
	public UnsupervisedClauseMarkup(String learningMode, String wordnetDir) {
		//this.chrDir = desDir.replaceAll("descriptions.*", "characters/");
		
		this.myConfiguration = new Configuration();
		this.myUtility = new Utility(myConfiguration);
		this.myDataHolder = new DataHolder(myConfiguration, myUtility);
		myLearner = new Learner(this.myConfiguration, this.myUtility);
		
	}

	public void learn(List<Treatment> treatments) {
		this.myDataHolder = this.myLearner.Learn(treatments);
	}

	// interface methods
	public Map<Treatment, List<String>> getSentences() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		System.out.println("Method: getSentences\n");
		return null;
	}

	public Map<Treatment, List<String>> getSentencesForOrganStateMarker() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		System.out.println("Method: getSentencesForOrganStateMarker\n");
		return null;
	}

	public List<String> getAdjNouns() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		Set<String> myAdjNounSet = new HashSet<String>();

		Iterator<Sentence> iter = this.myDataHolder.getSentenceHolder()
				.iterator();

		while (iter.hasNext()) {
			Sentence sentence = iter.next();
			String modifier = sentence.getModifier();
			String tag = sentence.getTag();
			if (tag.matches("^\\[.*$")) {
				modifier = modifier.replaceAll("\\[.*?\\]", "").trim();
				myAdjNounSet.add(modifier);
			}
		}

		List<String> myAdjNouns = new ArrayList<String>();
		myAdjNouns.addAll(myAdjNounSet);

		return myAdjNouns;
	}

	public Map<String, String> getAdjNounSent() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		Map<String, String> myAdjNounSent = new HashMap<String, String>();

		// collect sentences that need adj-nn disambiguation
		Iterator<Sentence> iter = this.myDataHolder.getSentenceHolder()
				.iterator();

		while (iter.hasNext()) {
			Sentence sentence = iter.next();
			String modifier = sentence.getModifier();
			String tag = sentence.getTag();
			if ((!(modifier.equals(""))) && (tag.matches("^\\[.*$"))) {
				modifier = modifier.replaceAll("\\[.*?\\]", "").trim();
				myAdjNounSent.put(tag, modifier);
			}
		}

		return myAdjNounSent;
	}

	public Set<String> getBracketTags() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		System.out.println("Method: getAdjNounsSent\n");
		return null;
	}

	public Set<String> getWordRoleTags() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		System.out.println("Method: getSentenceTags\n");
		return null;
	}

	public Map<String, Set<String>> getWordToSources() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		Map<String, Set<String>> myWordToSources = new HashMap<String, Set<String>>();

		Iterator<Sentence> iter = this.myDataHolder.getSentenceHolder()
				.iterator();

		TokenizerME myTokenizer = this.myUtility.getTokenizer();
		while (iter.hasNext()) {
			Sentence sentenceElement = iter.next();
			String source = sentenceElement.getSource();
			String sentence = sentenceElement.getSentence();			
			String[] words = myTokenizer.tokenize(sentence);
			for (int i = 0; i < words.length; i++) {
				String word = words[i];
				if (!myWordToSources.containsKey(word))
					myWordToSources.put(word, new HashSet<String>());
				myWordToSources.get(word).add(source);
			}
		}

		return myWordToSources;
	}

	public Map<String, Set<String>> getRoleToWords() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		System.out.println("Method: getRoleToWords\n");
		return null;

	}

	public Map<String, Set<String>> getWordsToRoles() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		System.out.println("Method: getWordsToRoles\n");
		return null;
	}

	public Map<String, String> getHeuristicNouns() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		return this.myDataHolder.getHeuristicNounTable();
	}

	public Map<Treatment, List<String>> getSentenceTags() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		System.out.println("Method: getTermCategories\n");
		return null;
	}

	public Map<String, Set<String>> getTermCategories() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		System.out.println("Method: getTermCategories\n");
		return null;
	}


	//Utilities
	public DataHolder getDataHolder() {
		return this.myDataHolder;
	}

}