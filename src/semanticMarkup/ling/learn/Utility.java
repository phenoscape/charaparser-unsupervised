package semanticMarkup.ling.learn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import semanticMarkup.know.lib.WordNetPOSKnowledgeBase;
import semanticMarkup.ling.transform.ISentenceDetector;
import semanticMarkup.ling.transform.ITokenizer;

public class Utility {

	private WordNetPOSKnowledgeBase myWN = null;
	
	private ISentenceDetector mySentenceDetector;
	private ITokenizer myTokenizer;

	private PopulateSentenceUtility myPopulateSentenceUtility = null;
	private WordFormUtility myWordFormUtility = null;
	private LearnerUtility myLearnerUtility = null;
	
	public Utility(Configuration configuration, ISentenceDetector sentenceDetector, ITokenizer tokenizer) {
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
		this.myLearnerUtility = new LearnerUtility(configuration, this.mySentenceDetector, myTokenizer);
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
	
	public ISentenceDetector getSentenceDetector(){
		return this.mySentenceDetector;
	}

}
