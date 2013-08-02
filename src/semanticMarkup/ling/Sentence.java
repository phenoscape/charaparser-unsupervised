package semanticMarkup.ling;

import org.apache.commons.lang3.StringUtils;

import semanticMarkup.ling.learn.SentenceStructure;

public class Sentence {
	
	protected String content;

	public Sentence(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	@Override
	public String toString(){
		return this.content;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Sentence mySentence = (Sentence) obj;

		return (StringUtils.equals(this.content, mySentence.getContent()));
	}

}
