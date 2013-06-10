package semanticMarkup.ling.learn;

public class StringAndInt {

	private String s;
	private int i;
	
	public StringAndInt(String s, int i) {
		this.s = s;
		this.i = i;
	}
	
	public String getString() {
		return s;
	}
	
	public int getInt() {
		return i;
	}
	
	public int hashCode() {		
		return this.s.hashCode() + this.i;
	}
	
	public boolean equals(Object obj){
		if (obj==this){
			return true;
		}
		
		if (obj==null||obj.getClass()!=this.getClass()){
			return false;
		}
		
		StringAndInt myStringAndInt = (StringAndInt) obj;
		
		return (   (StringUtility.equalsWithNull(this.s, myStringAndInt.getString()))
				&& (this.i == myStringAndInt.getInt())
				);
	}

}
