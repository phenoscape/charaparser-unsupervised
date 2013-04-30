package semanticMarkup.ling.learn;

public class Configuration {
	
	private String learningMode;
	private String wordNetDictDir;
	private String openNLPModelDir;
	private String openNLPSentenceDetectorDir;
	private String openNLPTokenizerDir;
	
	private static final String defaultWordNetDictDir = "res/WordNet/WordNet-3.0/dict";
	private static final String defaultOpenNLPModelDir = "res";

	public Configuration(String learningMode) {
		this.learningMode = learningMode;
		this.wordNetDictDir = System.getProperty("charaparser.wordnet.dir");
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
	}
	
	String getWordNetDictDir() {
		return this.wordNetDictDir;
	}
	
	void setWordNetDictDir(String wordNetDictDir){
		this.wordNetDictDir=wordNetDictDir;
	}
	
	String getOpenNLPSentenceDetectorDir() {
		return this.openNLPSentenceDetectorDir;
	}
	
	void setOpenNLPSentenceDetectorDir(String openNLPSentenceDetectorDir){
		this.openNLPSentenceDetectorDir=openNLPSentenceDetectorDir;
	}
	
	String getOpenNLPTokenizerDir() {
		return this.openNLPTokenizerDir;
	}
	
	void setOpenNLPTokenizerDir(String openNLPTokenizerDir){
		this.openNLPTokenizerDir=openNLPTokenizerDir;
	}
	
	String getOpenNLPModelDir() {
		return this.openNLPModelDir;
	}
	
	void setOpenNLPModelDir(String openNLPModelDir){
		this.openNLPModelDir=openNLPModelDir;
		this.openNLPSentenceDetectorDir = this.openNLPModelDir+"//en-sent.bin";
		this.openNLPTokenizerDir=this.openNLPModelDir+"//en-token.bin";
	}

}
