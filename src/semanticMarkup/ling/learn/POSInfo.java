package semanticMarkup.ling.learn;

public class POSInfo implements Comparable<POSInfo>{

	private String POS;
	private String role;
	private int certaintyU;
	private int certaintyL;

	public POSInfo(String p, String r, int cU, int cL) {
		this.POS = p;
		this.role = r;
		this.certaintyU = cU;
		this.certaintyL = cL;
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

		if (aCU * bCL > bCU * aCL) {
			return -1;
		} else if (aCU * bCL == bCU * aCL) {
			return 0;
		} else {
			return 1;
		}
	}
}
