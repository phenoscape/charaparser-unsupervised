package semanticMarkup.ling.learn;

public class ModifierTableValue {

	private int count;
	private boolean isTypeModifier;

	public ModifierTableValue(int c, boolean isTM) {
		this.count = c;
		this.isTypeModifier = isTM;
	}

	public void setCount(int c) {
		this.count = c;
	}

	public int getCount() {
		return this.count;
	}

	public void setIsTypeModifier(boolean isTM) {
		this.isTypeModifier = isTM;
	}

	public boolean getIsTypeModifier() {
		return this.isTypeModifier;
	}
	
	public boolean equals(Object obj){
		if (obj==this){
			return true;
		}
		
		if (obj==null||obj.getClass()!=this.getClass()){
			return false;
		}
		
		ModifierTableValue myModifierTableValue = (ModifierTableValue) obj;
		
		return ((this.count == myModifierTableValue.getCount()) 
				&& (this.isTypeModifier == myModifierTableValue.getIsTypeModifier()));
		
	}

	public int hashCode() {
		int hash = (new Integer(this.count)).hashCode()
				+ (new Boolean(this.isTypeModifier)).hashCode();

		return hash;
	}
}
