package semanticMarkup.ling.learn;

public class WordPOSValue {
	
	private String role;
	private int certaintye;
	private int certaintyl;
	private String savedFlag;
	private String savedID;

	public WordPOSValue(String r, int ce, int cl, String sf, String si) {
		this.role=r;
		this.certaintye=ce;
		this.certaintyl=cl;
		this.savedFlag=sf;
		this.savedID=si;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public void setRole(String r) {
		this.role=r;
	}
	
	public int getCertainTye() {
		return this.certaintye;
	}
	
	public void setCertianTye(int ct) {
		this.certaintye=ct;
	}
	
	public int getCertainTyl() {
		return this.certaintyl;
	}
	
	public void setCertianTyl(int cl) {
		this.certaintyl=cl;
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
