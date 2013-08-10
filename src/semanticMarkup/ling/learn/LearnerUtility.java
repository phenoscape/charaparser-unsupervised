package semanticMarkup.ling.learn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import semanticMarkup.know.lib.WordNetPOSKnowledgeBase;
import semanticMarkup.ling.Token;
import semanticMarkup.ling.transform.ITokenizer;

public class LearnerUtility {

	private ITokenizer mySentenceDetector;
	private ITokenizer mytokenizer;
	private WordFormUtility myWordFormUtility;
	private WordNetPOSKnowledgeBase myWordNetPOS;
	
	public LearnerUtility(ITokenizer sentenceDetector, ITokenizer tokenizer, WordNetPOSKnowledgeBase wordNetPOS) {
		this.mySentenceDetector = sentenceDetector;
		this.mytokenizer = tokenizer;
		this.myWordFormUtility = new WordFormUtility(wordNetPOS);
		this.myWordNetPOS = wordNetPOS;
	}
	
	public ITokenizer getTokenizer(){
		return this.mytokenizer;
	}
	
	public ITokenizer getSentenceDetector(){
		return this.mySentenceDetector;
	}
	
	public WordFormUtility getWordFormUtility(){
		return this.myWordFormUtility;
	}
	
	public WordNetPOSKnowledgeBase getWordNetPOSKnowledgeBase(){
		return this.myWordNetPOS;
	}
	
	// populate sentence utilities
	/**
	 * Given a file name, return its type
	 * 
	 * @param fileName
	 * @return return 1 if it is a file of character file, or 2 if it is a
	 *         description file, otherwise return 0
	 */
	public int getType(String fileName) {
		// remove pdf.xml
		fileName = fileName.replaceAll(".*\\.xml_", "");
		// remove all non_ charaters
		fileName = fileName.replaceAll("[^_]", "");

		// a character file
		if (fileName.length() == 0) {
			return 1;
		}

		// a description file
		if (fileName.length() == 1) {
			return 2;
		}

		return 0;
	}
	
	


	/**
	 * replace '.', '?', ';', ':', '!' within brackets by some special markers,
	 * to avoid split within brackets during sentence segmentation
	 * 
	 * @param text
	 * @return
	 */
	public String hideMarksInBrackets(String text) {

		if (text == null || text == "") {
			return text;
		}

		String hide = "";
		int lRound = 0;
		int lSquare = 0;
		int lCurly = 0;

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			switch (c) {
			case '(':
				lRound++;
				hide = hide + c;
				break;
			case ')':
				lRound--;
				hide = hide + c;
				break;
			case '[':
				lSquare++;
				hide = hide + c;
				break;
			case ']':
				lSquare--;
				hide = hide + c;
				break;
			case '{':
				lCurly++;
				hide = hide + c;
				break;
			case '}':
				lCurly--;
				hide = hide + c;
				break;
			default:
				if (lRound + lSquare + lCurly > 0) {
					if (c == '.') {
						hide = hide + "[DOT] ";
					} else if (c == '?') {
						hide = hide + "[QST] ";
					} else if (c == ';') {
						hide = hide + "[SQL] ";
					} else if (c == ':') {
						hide = hide + "[QLN] ";
					} else if (c == '!') {
						hide = hide + "[EXM] ";
					} else {
						hide = hide + c;
					}
				} else {
					hide = hide + c;
				}
			}
		}
		return hide;

	}

	/**
	 * Restore '.', '?', ';', ':', '!' within brackets
	 * 
	 * @param text
	 * @return the restored string
	 */
	public String restoreMarksInBrackets(String text) {

		if (text == null || text == "") {
			return text;
		}

		// restore "." from "[DOT]"
		text = text.replaceAll("\\[\\s*DOT\\s*\\]", ".");
		// restore "?" from "[QST]"
		text = text.replaceAll("\\[\\s*QST\\s*\\]", "?");
		// restore ";" from "[SQL]"
		text = text.replaceAll("\\[\\s*SQL\\s*\\]", ";");
		// restore ":" from "[QLN]"
		text = text.replaceAll("\\[\\s*QLN\\s*\\]", ":");
		// restore "." from "[DOT]"
		text = text.replaceAll("\\[\\s*EXM\\s*\\]", "!");

		return text;
	}

	/**
	 * Add space before and after all occurence of the regex in the string str
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public String addSpace(String str, String regex) {

		if (str == null || str == "" || regex == null || regex == "") {
			return str;
		}

		Matcher matcher = Pattern.compile("(^.*)(" + regex + ")(.*$)").matcher(
				str);
		if (matcher.lookingAt()) {
			str = addSpace(matcher.group(1), regex) + " " + matcher.group(2)
					+ " " + addSpace(matcher.group(3), regex);
			return str;
		} else {
			return str;
		}
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
	List<Token> segmentSentence(String text) {
		List<Token> sentences;
		
		//hide abbreviations
		text = this.hideAbbreviations(text);
		
		// do sentence segmentation
		
		sentences = this.mySentenceDetector.tokenize(text);
		
		// restore Abbreviations
		
		for (Token sentence: sentences){
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

	/**
	 * Convert a collection of words to a string of those words separated by "|"
	 * 
	 * @param c
	 *            collection of words
	 * @return string of pattern. If the collection is null or empty, return an
	 *         empty string
	 */
	public static String Collection2Pattern(Collection<String> c) {
		if (c == null) {
			return "";
		}

		String pattern = "";

		Iterator<String> iter = c.iterator();
		while (iter.hasNext()) {
			String element = iter.next();
			pattern = pattern + element + "|";
		}

		if (!pattern.equals("")) {
			pattern = pattern.substring(0, pattern.length() - 1);
		}

		return pattern;
	}

	/**
	 * Convert a pattern with words separated by "|" to a set
	 * 
	 * @param pattern
	 *            the pattern
	 * @return a set. If the input is null or empty string, return a empty set
	 */
	public static Set<String> Pattern2Set(String pattern) {
		Set<String> set = new HashSet<String>();

		if (StringUtils.equals(pattern, null)
				|| StringUtils.equals(pattern, "")) {
			return (set);
		}

		set.addAll(Arrays.asList(pattern.split("|")));

		return set;
	}

}
