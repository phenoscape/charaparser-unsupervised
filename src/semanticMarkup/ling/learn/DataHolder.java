package semanticMarkup.ling.learn;

import java.util.ArrayList;
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
	
//	/**
//	 * This method updates a new word in the unknownWord table
//	 * 
//	 * @param newWord
//	 * @param sourceWord
//	 * @return if any updates occurred, returns true; otherwise, returns false
//	 */
//	public boolean updateUnknownWord(String newWord, String flag) {
//		boolean result = false;
//		Iterator<Map.Entry<String, String>> iter = this.unknownWordTable
//				.entrySet().iterator();
//
//		while (iter.hasNext()) {
//			Map.Entry<String, String> unknownWord = iter.next();
//			if (unknownWord.getKey().equals(newWord)) {
//				unknownWord.setValue(flag);
//				result = true;
//			}
//		}
//
//		return result;
//	}
	
	/**
	 * 
	 * @param word
	 * @param flag
	 */
	public void updateUnknownWord(String word, String flag){
		this.unknownWordTable.put(word, flag);
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
		Logger myLogger = Logger.getLogger("updateTable.resolveConflict");
		
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
	
	/**
	 * Discount existing pos, but do not establish suggested pos
	 * 
	 * @param newWord
	 * @param oldPOS
	 * @param newPOS
	 * @param mode
	 *            "byone" - reduce certainty 1 by 1. "all" - remove this POS
	 */
	public void discountPOS(String newWord, String oldPOS, String newPOS,
			String mode) {
		/**
		 * 1. Find the flag of newWord in unknownWords table
		 * 1. Select all words from unknownWords table who has the same flag (including newWord)
		 * 1. From wordPOS table, select certaintyU of the (word, oldPOS) where word is in the words list
		 *     For each of them
		 *     1.1 Case 1: certaintyu less than 1, AND mode is "all"
		 *         1.1.1 Delete the entry from wordpos table
		 *         1.1.1 Update unknownwords
		 *             1.1.1.1 Case 1: the pos is "s" or "p"
		 *                 Delete all entries contains word from singularplural table as well
		 *         1.1.1 Insert (word, oldpos, newpos) into discounted table
		 */
		
		PropertyConfigurator.configure( "conf/log4j.properties" );
		Logger myLogger = Logger.getLogger("updateTable.discountPOS");
		
		myLogger.trace("Enter discountPOS");
		
		// get the flag of the newWord
		String flag = this.unknownWordTable.get(newWord);

		// get the word list
		List<String> wordList = new ArrayList<String>();
		Iterator<Map.Entry<String, String>> unknownWordIter = this.unknownWordTable.entrySet().iterator();
		while (unknownWordIter.hasNext()) {
			Map.Entry<String, String> e = unknownWordIter.next();
			if (e.getValue().equals(flag)) {
				wordList.add(e.getKey());
			}
		}
		myLogger.debug(wordList.toString());
		
		//wordList.add(newWord);
		
		for (int i=0;i<wordList.size();i++) {
			String word = wordList.get(i);
			WordPOSKey key = new WordPOSKey(word, oldPOS);
			if (this.wordPOSTable.containsKey(key)) {
				WordPOSValue value = this.wordPOSTable.get(key);
				int cU = value.getCertaintyU();
				if (cU <= 1 && mode.equals("all")) {
					this.wordPOSTable.remove(key);
					this.updateUnknownWord(word, "unknown");
					// delete from SingularPluralHolder
					if (oldPOS.matches("^.*[sp].*$")) {
						// list of entries to be deleted
						ArrayList<SingularPluralPair> delList = new ArrayList<SingularPluralPair>();

						// find entries to be deleted, put them into delList
						Iterator<SingularPluralPair> iterSPTable = this.singularPluralTable.iterator();
						while (iterSPTable.hasNext()) {
							SingularPluralPair spp = iterSPTable.next();
							if (spp.getSingular().equals(word)
									|| spp.getPlural().equals(word)) {
								delList.add(spp);
							}
						}

						// delete all entries in delList from singularPluralTable
						Iterator<SingularPluralPair> delListIter = delList.iterator();
						while (delListIter.hasNext()) {
							SingularPluralPair del = delListIter.next();
							this.singularPluralTable.remove(del);
						}
					}
					
					DiscountedKey dKey = new DiscountedKey(word, oldPOS);
					this.discountedTable.put(dKey, newPOS);
				}
				else {
					WordPOSValue temp = this.wordPOSTable.get(key);
					int certaintyU = temp.getCertaintyU();
					temp.setCertiantyU(certaintyU-1);
					this.wordPOSTable.put(key, temp);
				}
			}
		}
		
		/*
		Iterator<String> wordListIter = wordList.iterator();
		while (wordListIter.hasNext()){
			String word = wordListIter.next();
			WordPOSKey myWordPOSkey = new WordPOSKey(word, oldPOS);
			WordPOSValue myWordPOSValue = this.myDataHolder.getWordPOSHolder().get(myWordPOSkey);
			
		}
		
		
		
		
		while (iter.hasNext()) {
			Map.Entry<String, String> e = iter.next();
			if (e.getValue().equals(flag)) {
				String word = e.getKey();
				WordPOSKey key = new WordPOSKey(word, oldPOS);
				WordPOSValue value = this.myDataHolder.getWordPOSHolder().get(key);
				int cU = value.getCertaintyU();
				if (cU < 1 && mode.equals("all")) {
					this.myDataHolder.getWordPOSHolder().remove(key);
					this.myDataHolder.updateUnknownWord(word, "unknown");
					if (oldPOS.matches("^.*[sp].*$")) {
						// list of entries to be deleted
						ArrayList<SingularPluralPair> delList = new ArrayList<SingularPluralPair>();

						// find entries to be deleted, put them into delList
						Iterator<SingularPluralPair> iterSPTable = this.myDataHolder.getSingularPluralHolder()
								.iterator();
						while (iterSPTable.hasNext()) {
							SingularPluralPair spp = iterSPTable.next();
							if (spp.getSingular().equals(word)
									|| spp.getPlural().equals(word)) {
								delList.add(spp);
							}
						}

						// delete all entries in delList from
						// getSingularPluralHolder()
						for (int i = 0; i < delList.size(); i++) {
							this.myDataHolder.getSingularPluralHolder().remove(delList.get(i));
						}
					}

					DiscountedKey dKey = new DiscountedKey(word, oldPOS);
					this.myDataHolder.getDiscountedHolder().put(dKey, newPOS);
				}
			}
		}
		*/
		
		myLogger.trace("Quite discountPOS");
	}

	/******** Utilities *************/
	
	public void add2Holder(byte holderID, List<String> args){
		
		if (holderID == DataHolder.UNKNOWNWORD) {
			this.unknownWordTable = this.add2UnknowWordHolder(this.unknownWordTable, args);
		}
		
		if (holderID == DataHolder.WORDPOS) {
			this.wordPOSTable = this.add2WordPOSHolder(this.wordPOSTable, args);
		}
		
		if (holderID == DataHolder.SINGULAR_PLURAL) {
			this.singularPluralTable = this.add2SingularPluralHolder(this.singularPluralTable, args);
		}
		
		if (holderID == DataHolder.DISCOUNTED) {
			this.discountedTable = this.add2DiscountedHolder(this.discountedTable, args);
		}
	}
	
	public Map<String, String> add2UnknowWordHolder(Map<String, String> unknownWordHolder, List<String> args){
		int index = 0;
		
		String word = args.get(index++);
		String flag = args.get(index++);
		unknownWordHolder.put(word, flag);
		
		return unknownWordHolder;
	}
	
	public Map<WordPOSKey, WordPOSValue> add2WordPOSHolder(Map<WordPOSKey, WordPOSValue> wordPOSHolder, List<String> args){
		int index = 0;
		
		String word = args.get(index++);
		String POS = args.get(index++);
		String role = args.get(index++);
		int certaintyU = new Integer(args.get(index++));
		int certaintyL = new Integer(args.get(index++));
		String savedFlag = args.get(index++);
		String savedID = args.get(index++);
		wordPOSHolder.put(
				new WordPOSKey(word, POS), 
				new WordPOSValue(role, certaintyU, certaintyL, savedFlag, savedID));
		
		return wordPOSHolder; 
	}
	
	public Set<SingularPluralPair> add2SingularPluralHolder(Set<SingularPluralPair> singularPluralHolder, List<String> args){
		int index = 0;
		
		String singular = args.get(index++);
		String plural = args.get(index++);
		singularPluralHolder.add(new SingularPluralPair(singular, plural));
		
		return singularPluralHolder; 
	}
	
	public Map<DiscountedKey, String> add2DiscountedHolder(Map<DiscountedKey, String> discountedHolder, List<String> args){
		int index = 0;
		
		String word = args.get(index++);
		String POS = args.get(index++);
		String newPOS = args.get(index++);
		discountedHolder.put(new DiscountedKey(word, POS), newPOS);
		
		return discountedHolder; 
	}

}
