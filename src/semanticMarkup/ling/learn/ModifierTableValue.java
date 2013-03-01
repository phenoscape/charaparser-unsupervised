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
}
