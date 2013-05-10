package semanticMarkup.ling.learn;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataHolder {
	// all unique words in the input treatments
	public Map<String, Integer> allWords;
	
	// Data holders
	
	// Table sentence
	public List<Sentence> sentenceTable = new LinkedList<Sentence>();
	public static final byte SENTENCE = 0;
	
	// Table unknownword
	public Map<String, String> unknownWordTable = new HashMap<String, String>();
	public static final byte UNKNOWNWORD = 1;

	// Table wordpos
	public Map<WordPOSKey, WordPOSValue> wordPOSTable = new HashMap<WordPOSKey, WordPOSValue>();
	public static final byte WORDPOS = 2;

	// Table heuristicnoun
	public Map<String, String> heuristicNounTable = new HashMap<String, String>();
	public static final byte HEURISTICNOUN = 3;

	// Table singularPlural
	public Set<SingularPluralPair> singularPluralTable = new HashSet<SingularPluralPair>();
	public static final byte SINGULAR_PLURAL = 4;

	// Table modifier
	public Map<String, ModifierTableValue> modifierTable = new HashMap<String, ModifierTableValue>();
	public static final byte MODIFIER = 5;

	// Table discounted
	public Map<DiscountedKey, String> discountedTable = new HashMap<DiscountedKey, String>();
	public static final byte DISCOUNTED = 6;
	

	public DataHolder() {
		this.allWords = new HashMap<String, Integer>();
		
		this.sentenceTable = new LinkedList<Sentence>();
		this.unknownWordTable = new HashMap<String, String>();
		this.wordPOSTable = new HashMap<WordPOSKey, WordPOSValue>();
		this.heuristicNounTable = new HashMap<String, String>();
		this.singularPluralTable = new HashSet<SingularPluralPair>();
		this.modifierTable = new HashMap<String, ModifierTableValue>();
		this.discountedTable = new HashMap<DiscountedKey, String>();
	}
	
	
	/** Sentence Table Utility***************************************/
	public List<Sentence> getSentenceTable(){
		return this.sentenceTable;
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

	
	
}
