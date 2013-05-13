package semanticMarkup.ling.learn;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DataHolder {
	// all unique words in the input treatments
	public Map<String, Integer> allWords;
	
	// Data holders	
	// Table sentence
	private List<Sentence> sentenceTable = new LinkedList<Sentence>();
	public static final byte SENTENCE = 0;
	
	// Table unknownword
	private Map<String, String> unknownWordTable = new HashMap<String, String>();
	public static final byte UNKNOWNWORD = 1;

	// Table wordpos
	private Map<WordPOSKey, WordPOSValue> wordPOSTable = new HashMap<WordPOSKey, WordPOSValue>();
	public static final byte WORDPOS = 2;

	// Table heuristicnoun
	private Map<String, String> heuristicNounTable = new HashMap<String, String>();
	public static final byte HEURISTICNOUN = 3;

	// Table singularPlural
	private Set<SingularPluralPair> singularPluralTable = new HashSet<SingularPluralPair>();
	public static final byte SINGULAR_PLURAL = 4;

	// Table modifier
	private Map<String, ModifierTableValue> modifierTable = new HashMap<String, ModifierTableValue>();
	public static final byte MODIFIER = 5;

	// Table discounted
	private Map<DiscountedKey, String> discountedTable = new HashMap<DiscountedKey, String>();
	public static final byte DISCOUNTED = 6;
	
	private Utility myUtility;
	
	public DataHolder(Utility myUtility) {
		this.myUtility = myUtility;
		this.allWords = new HashMap<String, Integer>();
		
		this.sentenceTable = new LinkedList<Sentence>();
		this.unknownWordTable = new HashMap<String, String>();
		this.wordPOSTable = new HashMap<WordPOSKey, WordPOSValue>();
		this.heuristicNounTable = new HashMap<String, String>();
		this.singularPluralTable = new HashSet<SingularPluralPair>();
		this.modifierTable = new HashMap<String, ModifierTableValue>();
		this.discountedTable = new HashMap<DiscountedKey, String>();
	}
	
	/**
	 * This method updates a new word in the unknownWord table
	 * 
	 * @param newWord
	 * @param sourceWord
	 * @return if any updates occurred, returns true; otherwise, returns false
	 */
	public boolean updateUnknownWord(String newWord, String flag) {
		boolean result = false;
		Iterator<Map.Entry<String, String>> iter = this.unknownWordTable
				.entrySet().iterator();

		while (iter.hasNext()) {
			Map.Entry<String, String> unknownWord = iter.next();
			if (unknownWord.getKey().equals(newWord)) {
				unknownWord.setValue(flag);
				result = true;
			}
		}

		return result;
	}

	public boolean equals(Object obj) {
		if (obj==this){
			return true;
		}
		
		if (obj==null||obj.getClass()!=this.getClass()){
			return false;
		}
		
		DataHolder myDataHolder = (DataHolder) obj;
		
		return ((this.discountedTable.equals(myDataHolder.discountedTable))
				&& (this.heuristicNounTable.equals(myDataHolder.heuristicNounTable))
				&& (this.modifierTable.equals(myDataHolder.modifierTable))
				&& (this.sentenceTable.equals(myDataHolder.sentenceTable))
				&& (this.singularPluralTable.equals(myDataHolder.singularPluralTable))
				&& (this.unknownWordTable.equals(myDataHolder.unknownWordTable))
				&& (this.wordPOSTable.equals(myDataHolder.wordPOSTable))
				&& (this.allWords.equals(myDataHolder.allWords))
				);
	}	
	
	/** Sentence Table Utility***************************************/
	public List<Sentence> getSentenceHolder(){
		return this.sentenceTable;
	}

	public Map<String, String> getUnknownWordHolder(){
		return this.unknownWordTable;
	}

	public Map<WordPOSKey, WordPOSValue> getWordPOSHolder(){
		return this.wordPOSTable;
	}

	public Map<String, String> getHeuristicNounHolder(){
		return this.heuristicNounTable;
	}

	public Set<SingularPluralPair> getSingularPluralHolder(){
		return this.singularPluralTable;
	}

	public Map<String, ModifierTableValue> getModifierHolder(){
		return this.modifierTable;
	}

	public Map<DiscountedKey, String> getDiscountedHolder(){
		return this.discountedTable;
	}
	
	
	/** Heuristic Noun Table Utility*********************************/
	public Map<String, String> getHeuristicNounTable(){
		return this.heuristicNounTable;
	}
	
	
	/** Singular Plural Table Utility********************************/
	
	/**
	 * check if the word is in the singularPluralTable.
	 * 
	 * @param word
	 *            the word to check
	 * @return true if the word is in the SingularPluralTable; false otherwise.
	 */
	public boolean isInSingularPluralPair(String word) {
		Iterator<SingularPluralPair> iter = this.singularPluralTable.iterator();

		while (iter.hasNext()) {
			SingularPluralPair spp = iter.next();
			if ((spp.getSingular().equals(word))
					|| (spp.getPlural().equals(word))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * add the singular form and the plural form of a word into the
	 * singularPluarlTable
	 * 
	 * @param sgl
	 *            singular form
	 * @param pl
	 *            plural form
	 * @return if add a pair, return true; otherwise return false
	 */
	public boolean addSingularPluralPair(String sgl, String pl) {
		SingularPluralPair pair = new SingularPluralPair(sgl, pl);
		boolean result = this.singularPluralTable.add(pair);
		return result;
	}
	
	
	/** Unknown Word Table Utility***********************************/
	
	/**
	 * 
	 * @param word
	 * @param tag
	 */
	public void addUnknown(String word, String tag) {
		this.unknownWordTable.put(word, tag);
	}
	
	
	/** Modifier Table Utility***************************************/
	
	/**
	 * Take a new word, insert it into the modifier holder, or update its count in
	 * modifier holder if it already exists
	 * 
	 * @param newWord
	 * @param increment
	 * @return if anything changed in modifier holder, return true; otherwise
	 *         return false
	 */
	public int addModifier(String newWord, int increment) {
		int isUpdate = 0;

		if ((newWord.matches("(" + Constant.STOP + "|^.*\\w+ly$)"))
				|| (!(newWord.matches("^.*\\w.*$")))) {
			return isUpdate;
		}

		if (this.modifierTable.containsKey(newWord)) {
			int count = this.modifierTable.get(newWord).getCount();
			count = count + increment;
			this.modifierTable.get(newWord).setCount(count);
			// isUpdate = 1;
		} else {
			this.modifierTable.put(newWord, new ModifierTableValue(1, false));
			isUpdate = 1;
		}

		return isUpdate;
	}

	/**
	 * Pick one from bPOS and otherPOS and return it
	 * 
	 * @param newWord
	 * @param bPOS
	 * @param otherPOS
	 * @return if the newWord appears after a plural noun in the corpus, return the
	 *         bPOS; otherwise, return the otherPOS
	 */
	public String resolveConflict(String newWord, String bPOS, String otherPOS) {
		PropertyConfigurator.configure( "conf/log4j.properties" );
		Logger myLogger = Logger.getLogger("updateTable.markKnown.updatePOS.resolveConflict");
		
		myLogger.trace("Enter resolveConflict");

		int count = 0;
		List<Sentence> mySentenceHolder = this.getSentenceHolder();
		for (int i = 0; i < mySentenceHolder.size(); i++) {
			Sentence sentence = mySentenceHolder.get(i);
			boolean flag = false;
			flag = sentence.getTag() == null ? 
					true : (!sentence.getTag().equals("ignore"));
			if (flag) {
				String regex = "^.*([a-z]+(" + Constant.PLENDINGS + ")) ("
						+ newWord + ").*$";
				Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
				String originalSentence = sentence.getOriginalSentence();
				Matcher m = p.matcher(originalSentence);
				if (m.lookingAt()) {
					String plural = m.group(1).toLowerCase();
					if (this.myUtility.getWordFormUtility().getNumber(plural)
							.equals("p")) {
						count++;
					}
					if (count >= 1) {
						myLogger.trace("Quite resolveConflict, return " + bPOS);
						return bPOS;
					}
				}
			}
		}
		
		myLogger.trace("Quite resolveConflict, return otherPOS");
		return otherPOS;
	}
	
}
