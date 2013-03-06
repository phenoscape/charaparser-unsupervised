package semanticMarkup.ling.learn;

public class DiscountedKey {
	
	private String word;
	private String pos;;

	public DiscountedKey(String w, String p) {
		// TODO Auto-generated constructor stub
		this.word=w;
		this.pos=p;
	}
	
	public String getWord(){
		return this.word;
	}
	
	public String getPOS(){
		return this.pos;
	}
	
	public boolean equals(DiscountedKey dKey) {
		return ((this.word.equals(dKey.getWord())) 
				&& (this.pos.equals(dKey.getPOS())));
	}

	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + (this.word == null ? 0 : this.word.hashCode());
		hash = hash * 31 + (this.pos == null ? 0 : this.pos.hashCode());
		return hash;
	}
	

}
