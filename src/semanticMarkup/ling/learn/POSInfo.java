package semanticMarkup.ling.learn;

public class POSInfo implements Comparable<POSInfo>{

	private String word;
	private String POS;
	private String role;
	private int certaintyU;
	private int certaintyL;

	public POSInfo(String w, String p, String r, int cU, int cL) {
		this.word = w;
		this.POS = p;
		this.role = r;
		this.certaintyU = cU;
		this.certaintyL = cL;
	}
	
	public String getWord() {
		return this.word;
	}

	public String getPOS() {
		return this.POS;
	}

	public String getRole() {
		return this.role;
	}

	public int getCertaintyU() {
		return this.certaintyU;
	}

	public int getCertaintyL() {
		return this.certaintyL;
	}

	public int compareTo(POSInfo b) {		
		// aCU    bCU
		// --- =  ---
		// aCL    bCL
        // 
		// aCU * bCL = bCU*aCL

		int aCU = this.certaintyU;
		int aCL = this.certaintyL;

		int bCU = b.getCertaintyU();
		int bCL = b.getCertaintyL();

		if (aCU * bCL < bCU * aCL) {
			return -1;
		} else if (aCU * bCL == bCU * aCL) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public int hashCode() {		
		int hashCode = 0;
		
		String wordAndPOSAndRole = this.word + this.POS + this.role;		
		hashCode = wordAndPOSAndRole.hashCode()+this.certaintyU+this.certaintyL;
		
		return hashCode;
	}
	
	public boolean equals(Object obj){
		if (obj==this){
			return true;
		}
		
		if (obj==null||obj.getClass()!=this.getClass()){
			return false;
		}
		
		POSInfo myPOSInfo = (POSInfo) obj;
		
		return (   (StringUtility.equalsWithNull(this.word, myPOSInfo.getWord()))
				&& (StringUtility.equalsWithNull(this.POS, myPOSInfo.getPOS()))
				&& (StringUtility.equalsWithNull(this.role, myPOSInfo.getRole()))
				&& (this.certaintyU == myPOSInfo.getCertaintyU())
				&& (this.certaintyL == myPOSInfo.getCertaintyL())
				);
				
	}
}
