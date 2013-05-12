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
	

	public boolean equals(Object obj){
		if (obj==this){
			return true;
		}
		
		if (obj==null||obj.getClass()!=this.getClass()){
			return false;
		}
		
		WordPOSValue myWordPOSValue = (WordPOSValue) obj;
		
		//newRole = newRole.equals("*") ? "" : newRole;
		boolean case1 = 
				(role == null ? 
					myWordPOSValue.role == null : this.role.equals(myWordPOSValue.role)
				);
		boolean case2 = (this.certaintyU == myWordPOSValue.certaintyU);
		boolean case3 = (this.certaintyL == myWordPOSValue.certaintyL);
		boolean case4 = 
				(savedFlag == null ? 
					myWordPOSValue.savedFlag == null: this.savedFlag.equals(myWordPOSValue.savedFlag)
				);
		boolean case5 = 
				(savedID == null ? 
					myWordPOSValue.savedID == null: this.savedID.equals(myWordPOSValue.savedID)
				);

		return (case1 && case2 && case3 && case4 && case5);

	}

	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + (this.role == null ? 0 : this.role.hashCode());
		hash = hash * 31 + (this.savedFlag == null ? 0 : this.savedFlag.hashCode());
		hash = hash * 31 + (this.savedID == null ? 0 : this.savedID.hashCode());
		hash = hash + (new Integer(this.certaintyU)).hashCode() + (new Integer(this.certaintyL)).hashCode();
		return hash;
	}
}
