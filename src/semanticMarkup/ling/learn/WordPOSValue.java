package semanticMarkup.ling.learn;

public class WordPOSValue {
	
	private String role;
	private int certaintyU;
	private int certaintyL;
	private String savedFlag;
	private String savedID;

	public WordPOSValue(String r, int cu, int cl, String sf, String si) {
		this.role=r;
		this.certaintyU=cu;
		this.certaintyL=cl;
		this.savedFlag=sf;
		this.savedID=si;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public void setRole(String r) {
		this.role=r;
	}
	
	public int getCertaintyU() {
		return this.certaintyU;
	}
	
	public void setCertiantyU(int cu) {
		this.certaintyU=cu;
	}
	
	public int getCertaintyL() {
		return this.certaintyL;
	}
	
	public void setCertiantyL(int cl) {
		this.certaintyL=cl;
	}

	public String getSavedFlag() {
		return this.savedFlag;
	}
	
	public void setSavedFlag(String sf) {
		this.savedFlag=sf;
	}
	
	public String getSavedID() {
		return this.savedID;
	}
	
	public void setSavedID(String si) {
		this.savedID=si;
	}
}
