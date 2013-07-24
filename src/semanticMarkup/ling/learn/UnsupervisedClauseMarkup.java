package semanticMarkup.ling.learn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
	

	protected List<String> adjnouns;
	protected Map<String, String> adjnounsent;
	protected Set<String> bracketTags;
	protected Map<String, String> heuristicNouns;
	protected Map<String, Set<String>> roleToWords;
	protected Set<String> sentences;
	protected Map<Treatment, LinkedHashMap<String, String>> sentencesForOrganStateMarker;
	protected Map<Treatment, LinkedHashMap<String, String>> sentenceTags;
	protected Map<String, Set<String>> termCategories;
	protected Set<String> wordRoleTags;
	protected Map<String, Set<String>> wordsToRoles;
	protected Map<String, Set<String>> wordToSources;
	
	protected Map<String, Treatment> fileTreatments = new HashMap<String, Treatment>();
	private Set<String> selectedSources;


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

	// learn
	public void learn(List<Treatment> treatments) {
		this.myDataHolder = this.myLearner.Learn(treatments);
		
		// import data from data holder
		this.adjnouns = readAdjNouns();
		this.adjnounsent = readAdjNounSent();
		this.bracketTags = readBracketTags();
		this.heuristicNouns = readHeuristicNouns();
		this.roleToWords = readRoleToWords();
		this.sentences = readSentences(treatments);
		this.sentencesForOrganStateMarker = readSentencesForOrganStateMarker(treatments);
		this.sentenceTags = readSentenceTags(treatments);
		this.termCategories = readTermCategories();
		this.wordRoleTags = readWordRoleTags();
		this.wordsToRoles = readWordsToRoles();
		this.wordToSources = readWordToSources();	
	}
	
	// import data from data holder to class variables
	public List<String> readAdjNouns() {
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

	public Map<String, String> readAdjNounSent() {
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

	public Set<String> readBracketTags() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		Set<String> tags = new HashSet<String>();
		
		Iterator<Sentence> iter = this.getDataHolder().getSentenceHolder().iterator();
		
		while (iter.hasNext()) {
			Sentence sentence = iter.next();
			String thisTag = sentence.getTag();
			if (thisTag != null) {
				if (StringUtility.createMatcher("^\\[.*\\]$", thisTag).find()) {
					String thisModifier = sentence.getModifier();
					String modifier = thisModifier
							.replaceAll("\\[^\\[*\\]", "");
					if (!modifier.equals("")) {
						String tag;
						if (modifier.lastIndexOf(" ") < 0) {
							tag = modifier;
						} else {
							// last word from modifier
							tag = modifier
									.substring(modifier.lastIndexOf(" ") + 1); 
						}

						if (tag.indexOf("[") >= 0
								|| tag.matches(".*?(\\d|" + Constant.STOP
										+ ").*"))
							continue;
						tags.add(tag);
					}
				}
			}
		}
		
		return tags;
		
	}

	public Map<String, String> readHeuristicNouns() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		Map<String, String> myHeuristicNouns = new HashMap<String, String>();
		myHeuristicNouns.putAll(this.getDataHolder().getHeuristicNounHolder());
		
		return myHeuristicNouns;
		
	}

	// need wordroles table
	public Map<String, Set<String>> readRoleToWords() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		return null;
		
	}

	// use treatment
	public Set<String> readSentences(List<Treatment> treatments) {
		if (this.myDataHolder == null) {
			return null;
		}
		
		Set<String> result = new HashSet<String>();
		
		Iterator<Sentence> iter = this.getDataHolder().getSentenceHolder().iterator();
		while (iter.hasNext()) {
			Sentence sentenceObject = iter.next();
			String sentence = sentenceObject.getSentence();
			result.add(sentence);
		}

		return sentences;
	}

	// use treatment
	public HashMap<Treatment,  LinkedHashMap<String, String>> readSentencesForOrganStateMarker(List<Treatment> treatments) {
		if (this.myDataHolder == null) {
			return null;
		}
		
		HashMap<Treatment, LinkedHashMap<String, String>> sentences = new  HashMap<Treatment, LinkedHashMap<String, String>>();
		
		List<Sentence> sentenceHolder = this.getDataHolder().getSentenceHolder();
		String previousTreatmentId = "-1";
		for (int i = sentenceHolder.size()-1;i>=0;i--) {
			Sentence sentenceObject = sentenceHolder.get(i);
			String source = sentenceObject.getSource();
			String modifier = sentenceObject.getModifier();
			String tag = sentenceObject.getTag();
			String sentence = sentenceObject.getSentence().trim();
			String orginalSentence = sentenceObject.getOriginalSentence();
			
			if(sentence.length()!=0){
				String treatmentId = getTreatmentId(source);
				
				if(selectedSources.isEmpty() || selectedSources.contains(source)) {
					if(!treatmentId.equals(previousTreatmentId)) {
						previousTreatmentId = treatmentId;
					}
					
					String text = sentence;
					text = text.replaceAll("[ _-]+\\s*shaped", "-shaped").replaceAll("(?<=\\s)µ\\s+m\\b", "um");
					text = text.replaceAll("&#176;", "°");
					text = text.replaceAll("\\bca\\s*\\.", "ca");
					text = modifier+"##"+tag+"##"+text;
					
					Treatment treatment = fileTreatments.get(treatmentId);
					if(!sentences.containsKey(treatment))
						sentences.put(treatment, new LinkedHashMap<String, String>());
					sentences.get(treatment).put(source, text);
				}
			}
			
		}

		return sentences;
		
	}

	// use treatment
	public Map<Treatment, LinkedHashMap<String, String>> readSentenceTags(List<Treatment> treatments) {
		if (this.myDataHolder == null) {
			return null;
		}
		
		Map<Treatment, LinkedHashMap<String, String>> tags = new HashMap<Treatment, LinkedHashMap<String, String>>();		
		String previousTag = null;
		String previousTreatmentId = "-1";
		
		Iterator<Sentence> iter = this.getDataHolder().getSentenceHolder().iterator();
		while (iter.hasNext()) {
			Sentence sentenceObject = iter.next();
			
			String source = sentenceObject.getSource();
			String treatmentId = getTreatmentId(source);
			if(selectedSources.isEmpty() || selectedSources.contains(source)) {
				
				if(!treatmentId.equals(previousTreatmentId)) {
					previousTreatmentId = treatmentId;
					//listId++;
				}
				
				String tag = sentenceObject.getTag();
				if(tag == null)
					tag = "";
				tag = tag.replaceAll("\\W", "");
				
				Treatment treatment = fileTreatments.get(treatmentId);
				if(!tags.containsKey(treatment)) 
					tags.put(treatment, new LinkedHashMap<String, String>());
				if(!tag.equals("ditto")) {
					tags.get(treatment).put(source, tag);
					previousTag = tag;
				} else {
					tags.get(treatment).put(source, previousTag);
				}			
			}
		}

		return tags;
	}

	// need term_category table
	public Map<String, Set<String>> readTermCategories() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		return null;
		
	}

	// need wordrole table
	public Set<String> readWordRoleTags() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		return null;
		
	}

	// need wordrole table
	public Map<String, Set<String>> readWordsToRoles() {
		if (this.myDataHolder == null) {
			return null;
		}
		
		return null;
		
	}

	public Map<String, Set<String>> readWordToSources() {
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

	
	// interface methods
	public List<String> getAdjNouns() {
		return this.adjnouns;
	}

	public Map<String, String> getAdjNounSent() {
		return this.adjnounsent;
	}

	public Set<String> getBracketTags() {
		return this.bracketTags;
	}
	
	public Map<String, String> getHeuristicNouns() {
		return this.heuristicNouns;
	}
	
	public Map<String, Set<String>> getRoleToWords() {
		return this.roleToWords;

	}
	
	public Set<String> getSentences() {
		return this.sentences;
	}
	
	public Map<Treatment, LinkedHashMap<String, String>> getSentencesForOrganStateMarker() {
		return this.sentencesForOrganStateMarker;
	}	
	
	public Map<Treatment, LinkedHashMap<String, String>> getSentenceTags() {
		return this.sentenceTags;
	}
	
	public Map<String, Set<String>> getTermCategories() {
		return this.termCategories;
	}
	
	public Set<String> getWordRoleTags() {
		return this.wordRoleTags;
	}
	
	public Map<String, Set<String>> getWordsToRoles() {
		return this.wordsToRoles;
	}
	
	public Map<String, Set<String>> getWordToSources() {
		return this.wordToSources;
	}

	//Utilities
	public DataHolder getDataHolder() {
		return this.myDataHolder;
	}
	
	protected String getTreatmentId(String sourceString) {
		String[] sourceParts = sourceString.split("\\.");
		return sourceParts[0];
	}

	@Override
	public void learn(List<Treatment> treatments, String glossaryTable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readResults(List<Treatment> treatments) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> getTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getModifiers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Set<String>> getCategoryTerms() {
		// TODO Auto-generated method stub
		return null;
	}

}