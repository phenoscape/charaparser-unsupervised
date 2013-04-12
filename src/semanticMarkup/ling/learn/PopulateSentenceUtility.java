package semanticMarkup.ling.learn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.tokenize.Tokenizer;

public class PopulateSentenceUtility {

	private Tokenizer myTokenizer;
	private SentenceDetectorME mySentenceDetector;

	public PopulateSentenceUtility(SentenceDetectorME sDetector, Tokenizer tokenizer) {
		this.myTokenizer = tokenizer;
		this.mySentenceDetector = sDetector;
	}

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
	
	String[] segmentSentence(String text) {
		String sentences[] = {};
		sentences = this.mySentenceDetector.sentDetect(text);
		return sentences;
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

	/**
	 * returns the first n words of the sentence
	 * 
	 * @param sent
	 *            the sentence
	 * @param n
	 *            number of words to be returned
	 * @return the first n words of the sentence. If the number of words in the
	 *         sentence is less than n, return all of them.
	 */
	public List<String> getFirstNWords(String sent, int n) {
		List<String> nWords = new ArrayList<String>();

		if (sent == null || sent == "") {
			return nWords;
		}

		String[] tokens = this.myTokenizer.tokenize(sent);
		int minL = tokens.length > n ? n : tokens.length;
		for (int i = 0; i < minL; i++) {
			nWords.add(tokens[i]);
		}

		return nWords;
	}

	/**
	 * Put all words in this sentence into the words map
	 * 
	 * @param sent
	 * @param words
	 *            a map mapping all words already known to its current count
	 * @return a new map of all words, including words in sent
	 */
	public Map<String, Integer> getAllWords(String sent,
			Map<String, Integer> words) {
		String[] tokens = this.myTokenizer.tokenize(sent);

		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i];
			if (words.containsKey(token)) {
				int count = words.get(token);
				count = count + 1;
				words.put(token, count);
			} else {
				words.put(token, 1);
			}
		}

		return words;
	}

}
