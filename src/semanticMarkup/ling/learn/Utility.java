package semanticMarkup.ling.learn;

import java.io.IOException;

import semanticMarkup.know.lib.WordNetPOSKnowledgeBase;
import semanticMarkup.ling.transform.ITokenizer;

public class Utility {

	private WordNetPOSKnowledgeBase myWN = null;
	
	private ITokenizer mySentenceDetector;
	private ITokenizer myTokenizer;

	private PopulateSentenceUtility myPopulateSentenceUtility = null;
	private WordFormUtility myWordFormUtility = null;
	private LearnerUtility myLearnerUtility = null;
	
	public Utility(Configuration configuration, ITokenizer sentenceDetector, ITokenizer tokenizer) {
		// get those tools
		// Get WordNetAPI instance
		try {
			this.myWN = new WordNetPOSKnowledgeBase(configuration.getWordNetDictDir(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.mySentenceDetector = sentenceDetector;
		this.myTokenizer = tokenizer;
		
		this.myPopulateSentenceUtility = new PopulateSentenceUtility();
		this.myWordFormUtility = new WordFormUtility(this.myWN);
		this.myLearnerUtility = new LearnerUtility(configuration, this.mySentenceDetector, this.myTokenizer);
	}
	
	public  PopulateSentenceUtility getPopulateSentenceUtility(){
		return this.myPopulateSentenceUtility;
	}
	
	/**
	 * 
	 * @return
	 */
	public WordFormUtility getWordFormUtility(){
		return this.myWordFormUtility;
	}
	
	public LearnerUtility getLearnerUtility() {
		return this.myLearnerUtility;
	}

	public WordNetPOSKnowledgeBase getWordNet() {
		return this.myWN;
	}
	
	public ITokenizer getSentenceDetector(){
		return this.mySentenceDetector;
	}

}
