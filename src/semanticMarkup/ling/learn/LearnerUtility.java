package semanticMarkup.ling.learn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import semanticMarkup.ling.Sentence;
import semanticMarkup.ling.transform.ISentenceDetector;
import semanticMarkup.ling.transform.ITokenizer;

public class LearnerUtility {

	private Configuration myConfiguration;
	private ISentenceDetector mySentenceDetector;
	private ITokenizer mytokenizer;
	
	public LearnerUtility(Configuration configuration, ISentenceDetector sentenceDetector, ITokenizer tokenizer) {
		this.myConfiguration = configuration;
		this.mySentenceDetector = sentenceDetector;
		this.mytokenizer = tokenizer;
	}
	
	public ITokenizer getTokenizer(){
		return this.mytokenizer;
	}
	
	public ISentenceDetector getSentenceDetector(){
		return this.mySentenceDetector;
	}
	
	public List<String> tokenizeText(String sentence, String mode) {
		if (StringUtils.equals(mode, "firstseg")) {
			sentence = getSentenceHead(sentence);
		}
		else {
			;
		}
		
		String[] tempWords = sentence.split("\\s+");
		List<String> words = new ArrayList<String>();
		words.addAll(Arrays.asList(tempWords));
		
		return words;
	}
	
	/**
	 * Get the portion in the input sentence before any of ,:;.[(, or any
	 * preposition word, if any
	 * 
	 * @param sentence
	 *            the input sentence
	 * @return the portion in the head
	 */
	public String getSentenceHead(String sentence) {
		PropertyConfigurator.configure("conf/log4j.properties");
		Logger myLogger = Logger
				.getLogger("learn.populateSentence.getFirstNWords.getHead");

		if (sentence == null) {
			return sentence;
		}
		else if (sentence.equals("")) {
			return sentence;
		} 
		else {
			String head = "";
			int end = sentence.length();

			String pattern1 = " [,:;.\\[(]";
			String pattern2 = "\\b" + "(" + Constant.PREPOSITION + ")" + "\\s";

			myLogger.trace("Pattern1: " + pattern1);
			myLogger.trace("Pattern2: " + pattern2);

			Pattern p1 = Pattern.compile(pattern1);
			Pattern p2 = Pattern.compile(pattern2);

			Matcher m1 = p1.matcher(sentence);
			Matcher m2 = p2.matcher(sentence);

			boolean case1 = m1.find();
			boolean case2 = m2.find();
			
			if (case1 || case2) {
				// case 1
				if (case1) {
					int temp1 = m1.end();
					end = temp1 < end ? temp1 : end;
					end = end - 1;
				}
				// case 2
				else {
					int temp2 = m2.end();
					end = temp2 < end ? temp2 : end;
				}

				head = sentence.substring(0, end - 1);
			}
			else {
				head = sentence;
			}

			myLogger.trace("Return: " + head);
			return head;
		}
	}
	
	/**
	 * Segment a text into sentences using the OpenNLP sentence detector. Note
	 * how dot after any abbreviations is handled: to avoid segmenting at
	 * abbreviations, the dots of abbreviations are first replaced by a special
	 * mark before the text is segmented. Then after the segmentation, they are
	 * restored back.
	 * 
	 * @param text
	 * @return List of Sentence
	 */
	List<Sentence> segmentSentence(String text) {
		List<Sentence> sentences;
		
		//hide abbreviations
		text = this.hideAbbreviations(text);
		
		// do sentence segmentation
		
		sentences = this.mySentenceDetector.segment(text);
		
		// restore Abbreviations
		
		for (Sentence sentence: sentences){
			String contentHideAbbreviations = sentence.getContent();
			String contentRestoreAbbreviations = this.restoreAbbreviations(contentHideAbbreviations);
			sentence.setContent(contentRestoreAbbreviations); 
		}
		
		return sentences;
	}
	
	/**
	 * replace the dot (.) mark of abbreviations in the text by a special mark
	 * ([DOT])
	 * 
	 * @param text
	 * @return the text after replacement
	 */
	public String hideAbbreviations(String text) {
		String pattern = "(^.*)("
				+Constant.PEOPLE_ABBR
				+"|"+Constant.ARMY_ABBR
				+"|"+Constant.INSTITUTES_ABBR
				+"|"+Constant.COMPANIES_ABBR
				+"|"+Constant.PLACES_ABBR
				+"|"+Constant.MONTHS_ABBR
				+"|"+Constant.MISC_ABBR
				+"|"+Constant.BOT1_ABBR
				+"|"+Constant.BOT2_ABBR
				+"|"+Constant.LATIN_ABBR
				+")(\\.)(.*$)";
		//pattern = "(^.*)(jr|abc)(\\.)(.*$)";
		
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher m;
		m= p.matcher(text);
		while (m.matches()){
			String head = m.group(1);
			String abbr = m.group(2);
			String dot = m.group(3);
			String remaining = m.group(4);
			dot = "[DOT]";
			text= head+abbr+dot+remaining;
			m=p.matcher(text);
		}
		
		return text;
	}
	
	/**
	 * restore the dot (.) mark of abbreviations in the text from special mark
	 * ([DOT])
	 * 
	 * @param text
	 * @return the text after replacement
	 */
	public String restoreAbbreviations(String text) {
		String pattern = "(^.*)("
				+Constant.PEOPLE_ABBR
				+"|"+Constant.ARMY_ABBR
				+"|"+Constant.INSTITUTES_ABBR
				+"|"+Constant.COMPANIES_ABBR
				+"|"+Constant.PLACES_ABBR
				+"|"+Constant.MONTHS_ABBR
				+"|"+Constant.MISC_ABBR
				+"|"+Constant.BOT1_ABBR
				+"|"+Constant.BOT2_ABBR
				+"|"+Constant.LATIN_ABBR
				+")(\\[DOT\\])(.*$)";
		//pattern = "(^.*)(jr|abc)(\\.)(.*$)";
		
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher m;
		m= p.matcher(text);
		while (m.matches()){
			String head = m.group(1);
			String abbr = m.group(2);
			String dot = m.group(3);
			String remaining = m.group(4);
			dot = ".";
			text= head+abbr+dot+remaining;
			m=p.matcher(text);
		}
		
		return text;
	}


}
