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
import semanticMarkup.knowledge.lib.WordNetAPI;

public class Utility {

	private WordNetAPI myWN = null;
	private SentenceDetectorME mySentenceDetector = null;
	private TokenizerME myTokenizer = null;

	private PopulateSentenceUtility myPopulateSentenceUtility = null;
	private WordFormUtility myWordFormUtility = null;
	private LearnerUtility myLearnerUtility = null;
	
	public Utility(Configuration myConfiguration) {
		// get those tools
		// Get WordNetAPI instance
		try {
			this.myWN = new WordNetAPI(myConfiguration.getWordNetDictDir(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Get OpenNLP sentence detector
		InputStream sentModelIn;
		try {
			sentModelIn = new FileInputStream(myConfiguration.getOpenNLPSentenceDetectorDir());
			SentenceModel model = new SentenceModel(sentModelIn);
			this.mySentenceDetector = new SentenceDetectorME(model);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get OpenNLP tokenizer
		InputStream tokenModelIn;
		try {
			tokenModelIn = new FileInputStream(myConfiguration.getOpenNLPTokenizerDir());
			TokenizerModel model = new TokenizerModel(tokenModelIn);
			this.myTokenizer = new TokenizerME(model);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.myPopulateSentenceUtility = new PopulateSentenceUtility(this.mySentenceDetector, this.myTokenizer);
		this.myWordFormUtility = new WordFormUtility(this.myWN);
		this.myLearnerUtility = new LearnerUtility(myConfiguration);
	}
	
	public  PopulateSentenceUtility getPopulateSentenceUtility(){
		return this.myPopulateSentenceUtility;
	}
	
	public WordFormUtility getWordFormUtility(){
		return this.myWordFormUtility;
	}
	
	public LearnerUtility getLearnerUtility() {
		return this.myLearnerUtility;
	}

	public WordNetAPI getWordNet() {
		return this.myWN;
	}
	
	public TokenizerME getTokenizer() {
		return this.myTokenizer;
	}
	
	public SentenceDetectorME getSentenceDetector(){
		return this.mySentenceDetector;
	}

}
