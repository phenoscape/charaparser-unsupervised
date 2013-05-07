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

public class Configuration {
	
	private String learningMode;
	private String wordNetDictDir;
	private String openNLPModelDir;
	private String openNLPSentenceDetectorDir;
	private String openNLPTokenizerDir;
	
	private WordNetAPI myWN = null;
	private SentenceDetectorME mySentenceDetector = null;
	private TokenizerME myTokenizer = null;
	
	private static final String defaultWordNetDictDir = "res/WordNet/WordNet-3.0/dict";
	private static final String defaultOpenNLPModelDir = "res";
	private static final String defaultLearningMode = "plain";

	public Configuration() {
		//this.learningMode = learningMode;
		this.learningMode = System.getProperty("charaparser.learningmode");
		if (this.learningMode == null){
			this.learningMode = Configuration.defaultLearningMode;
		}
		this.wordNetDictDir = System.getProperty("charaparser.wordnet.dict.dir");
		if (this.wordNetDictDir == null) {
			this.wordNetDictDir = Configuration.defaultWordNetDictDir;
		}
		String tempOpenNLPModelDir = System.getProperty("charaparser.opennlp.model.dir");
		if ( tempOpenNLPModelDir==null){
			this.openNLPSentenceDetectorDir=System.getProperty("charaparser.opennlp.model.sent.dir");
			if (this.openNLPSentenceDetectorDir==null){
				this.openNLPSentenceDetectorDir = Configuration.defaultOpenNLPModelDir+"//en-sent.bin";
			}
			
			this.openNLPTokenizerDir=System.getProperty("charaparser.opennlp.model.token.dir");
			if (this.openNLPTokenizerDir==null){
				this.openNLPTokenizerDir=Configuration.defaultOpenNLPModelDir+"//en-token.bin";
			}			
		}
		else{
			this.setOpenNLPModelDir(tempOpenNLPModelDir);
		}
		
		// get those tools
		// Get WordNetAPI instance
		try {
			this.myWN = new WordNetAPI(this.wordNetDictDir, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Get OpenNLP sentence detector
		InputStream sentModelIn;
		try {
			sentModelIn = new FileInputStream("res/en-sent.bin");
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
			tokenModelIn = new FileInputStream("res/en-token.bin");
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
	}
	
	public String getLearningMode() {
		// TODO Auto-generated method stub
		return this.learningMode;
	}
	
	public void setLearningMode(String learningMode) {
		// TODO Auto-generated method stub
		this.learningMode = learningMode;
	}
	
	public String getWordNetDictDir() {
		return this.wordNetDictDir;
	}
	
	public void setWordNetDictDir(String wordNetDictDir){
		this.wordNetDictDir=wordNetDictDir;
	}
	
	public String getOpenNLPSentenceDetectorDir() {
		return this.openNLPSentenceDetectorDir;
	}
	
	public void setOpenNLPSentenceDetectorDir(String openNLPSentenceDetectorDir){
		this.openNLPSentenceDetectorDir=openNLPSentenceDetectorDir;
	}
	
	public String getOpenNLPTokenizerDir() {
		return this.openNLPTokenizerDir;
	}
	
	public void setOpenNLPTokenizerDir(String openNLPTokenizerDir){
		this.openNLPTokenizerDir=openNLPTokenizerDir;
	}
	
	public String getOpenNLPModelDir() {
		return this.openNLPModelDir;
	}
	
	public void setOpenNLPModelDir(String openNLPModelDir){
		this.openNLPModelDir=openNLPModelDir;
		this.openNLPSentenceDetectorDir = this.openNLPModelDir+"//en-sent.bin";
		this.openNLPTokenizerDir=this.openNLPModelDir+"//en-token.bin";
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
