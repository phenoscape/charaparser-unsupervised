package semanticMarkup.ling.learn;

public class Sentence {
	
	private String sentence;
	private String originalsent;
	private String lead;
	private String status;
	private String tag;
	private String modifier;
	private String type;

	public Sentence(String sent, String os, String l, String st, String tg, String m, String ty) {
		// TODO Auto-generated constructor stub
		this.sentence=sent;
		this.originalsent=os;
		this.lead=l;
		this.status=st;
		this.tag=tg;
		this.modifier=m;
		this.type=ty;
	}
	
	public String getSentence() {
		return this.sentence;
	}
	
	public void setSentence(String sent) {
		this.sentence=sent;
	}
	
	public String getOriginalSentence() {
		return this.sentence;
	}
	
	public void setOriginalSentence(String os) {
		this.originalsent=os;
	}
	
	public String getTag() {
		return this.tag;
	}
	
	public void setTag(String tg) {
		this.tag=tg;
	}
	
	public String getModifier() {
		return this.modifier;
	}
	
	public void setModifier(String m) {
		this.modifier=m;
	}

}