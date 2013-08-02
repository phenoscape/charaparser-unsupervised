package semanticMarkup.ling.transform;

import java.util.List;

import semanticMarkup.ling.Sentence;



/**
 * ISentenceDetector splits a String into a list of Sentences.
 * 
 * @author meng
 */
public interface ISentenceDetector {
	/**
	 * @param text
	 * @return List of Sentences
	 */
	public List<Sentence> segment(String text);
}
