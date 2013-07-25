package semanticMarkup.ling.learn;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class WordRoleKey {
	private String word;
	private String sematicRole;

	public WordRoleKey(String word, String role) {
		this.word = word;
		this.sematicRole = role;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public String getRole() {
		return this.sematicRole;
	}

	@Override
	public boolean equals(Object obj){
		if (obj==this){
			return true;
		}
		
		if (obj==null||obj.getClass()!=this.getClass()){
			return false;
		}
		
		WordRoleKey myWordRoleKey = (WordRoleKey) obj;
		
		boolean case1 = StringUtils.equals(this.word, myWordRoleKey.getWord());
		boolean case2 = StringUtils.equals(this.sematicRole, myWordRoleKey.getRole());
		
		return (case1 && case2);	
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 29)
			.append(this.word)
			.append(this.sematicRole)
			.toHashCode();
	}
	
	@Override
	public String toString() {
		return String.format("Key: [Word: %s, Role: %s]", this.word, this.sematicRole);
	}
	
}
