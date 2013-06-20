package semanticMarkup.ling.learn;

import org.apache.commons.lang3.StringUtils;

public class IsAValue {
	
	private String instance;
	private String cls;

	public IsAValue(String i, String c) {
		this.instance = i;
		this.cls = c;
	}
	
	public String getInstance() {
		return this.instance;
	}
	
	public String getCls() {
		return this.cls;
	}

	public int hashCode() {		
		String instanceAndClass =this.instance + this.cls; 
		return instanceAndClass.hashCode();
	}
	
	public boolean equals(Object obj){
		if (obj==this){
			return true;
		}
		
		if (obj==null||obj.getClass()!=this.getClass()){
			return false;
		}
		
		IsAValue myIsAValue = (IsAValue) obj;
		
		return ((StringUtils.equals(this.instance, myIsAValue.getInstance())) 
				&& (this.cls == myIsAValue.getCls()));
	}

}
