package semanticMarkup.ling.learn;

public class WordPOSKey {
	
	private String word;
	private String pos;

	public WordPOSKey(String w, String p) {
		this.word=w;
		this.pos=p;
	}
	
	public String getWord() {
		return this.word;
	}
	
	//public void setWord(String s) {
	//	this.word=s;
	//}
	
	public String getPOS() {
		return this.pos;
	}
	
	//public void setPOS(String p) {
	//	this.pos=p;
	//}

}
