package semanticMarkup.ling.learn;

public class SingularPluralPair {
	
	private String singular;
	private String plural;

	public SingularPluralPair() {
		singular = null;
		plural = null;
	}
	
	public SingularPluralPair(String s, String p) {
		this.singular = s;
		this.plural = p;
	}
	
	public String getPlural() {
		return this.plural;
	}
	
	public String getSingular() {
		return this.singular;
	}

	public int hashCode() {
		int hash = 1;
		hash = hash * 31
				+ (this.singular == null ? 0 : this.singular.hashCode());
		hash = hash * 31 + (this.plural == null ? 0 : this.plural.hashCode());
		return hash;
	}

	public boolean equals(SingularPluralPair spp) {

		return ((spp.singular == this.singular) && (spp.plural == this.plural));
	}

}
