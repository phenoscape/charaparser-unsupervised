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
	
	private Configuration myConfiguratio;
	private Utility myUtility;
	
	public DataHolder(Configuration myConfiguration, Utility myUtility) {
		this.myConfiguratio = myConfiguration;
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
		
		myLogger.trace("Quite discountPOS");
	}
	
	
	/**
	 * Given a new role, and the old role, of a word, decide the right role to
	 * return
	 * 
	 * @param oldRole
	 * @param newRole
	 * @return oldRole or newRole, whichever wins
	 */
	public String mergeRole(String oldRole, String newRole) {

		// if old role is "*", return the new role
		if (oldRole.equals("*")) {
			return newRole;
		}
		// if the new role is "*", return the old rule
		else if (newRole.equals("*")) {
			return oldRole;
		}

		// if the old role is empty, return the new role
		if (oldRole.equals("")) {
			return newRole;
		}
		// if the new role is empty, return the old role
		else if (newRole.equals("")) {
			return oldRole;
		}
		// if the old role is not same as the new role, return "+"
		else if (!oldRole.equals(newRole)) {
			return "+";
		}
		// if none of above apply, return the old role by default
		else {
			return oldRole;
		}
	}
	
	/**
	 * Find the tag of the sentence of which this sentid (clause) is a part of
	 * 
	 * @param sentID
	 * @return a tag
	 */
	public String getParentSentenceTag(int sentID) {
		/**
		 * 1. Get the originalsent of sentence with sentID 
		 * 1. Case 1: the originalsent of $sentence sentID starts with a [a-z\d] 
		 * 1.1 select modifier and tag from Sentence where tag is not "ignore" OR tag is null 
		 *      AND originalsent COLLATE utf8_bin regexp '^[A-Z].*' OR originalsent rlike ': *\$' AND id < sentID 
		 * 1.1 take the tag of the first sentence (with smallest id), get its modifier and tag 
		 * 1.1 if modifier matches \w, tag = modifier + space + tag 
		 * 1.1 remove [ and ] from tag 
		 * 1. if tag matches \w return [+tag+], else return [parenttag]
		 */

		String originalSentence = this.sentenceTable.get(sentID)
				.getOriginalSentence();
		String tag = "";
		String oSentence = "";
		if (originalSentence.matches("^\\s*[^A-Z].*$")) {
		//if (originalSent.matches("^\\s*([a-z]|\\d).*$")) {
			for (int i = 0; i < sentID; i++) {
				Sentence sentence = this.sentenceTable.get(i);
				tag = sentence.getTag();
				oSentence = sentence.getOriginalSentence();
				boolean flag = (tag == null)? true : (!tag.matches("ignore"));

				if (flag && ((oSentence.matches("^[A-Z].*$")) || (oSentence
								.matches("^.*:\\s*$")))) {
					String modifier = sentence.getModifier();
					if (modifier.matches("^.*\\w.*$")) {
						if (tag == null) {
							tag = "";
						}
						tag = modifier + " " + tag;
						tag = tag.replaceAll("[\\[\\]]", "");
					}
					break;
				}
			}
		}

		return tag.matches("^.*\\w.*$") ? "[" + tag + "]" : "[parenttag]" ;
	}
	
	/**
	 * 
	 * @param tag
	 * @return
	 */
	public List<String> getMTFromParentTag(String tag) {
		String modifier = "";
		String newTag = "";

		Pattern p = Pattern.compile("^\\[(\\w+)\\s+(\\w+)\\]$");
		Matcher m = p.matcher(tag);
		if (m.lookingAt()) {
			modifier = m.group(1);
			newTag = m.group(2);
		} else {
			p = Pattern.compile("^(\\w+)\\s+(\\w+)$");
			m = p.matcher(tag);
			if (m.lookingAt()) {
				modifier = m.group(1);
				newTag = m.group(2);
			}

		}
		List<String> pair = new ArrayList<String>();
		pair.add(modifier);
		pair.add(newTag);

		return pair;
	}
	
	/**
	 * Remove ly ending word which is a "b" in the WordPOS, from the modifier
	 * 
	 * @param modifier
	 * @return the new modifer
	 */
	public String tagSentWithMTRemoveLyEndingBoundary(String modifier) {
		
		Pattern p = Pattern.compile("^(\\w+ly)\\s*(.*)$");
		Matcher m = p.matcher(modifier);
		while (m.lookingAt()) {
			String wordly = m.group(1);
			String rest = m.group(2);
			WordPOSKey wp = new WordPOSKey(wordly, "b");
			if (this.wordPOSTable.containsKey(wp)) {
				modifier = rest;
				m = p.matcher(modifier);
			} else {
				break;
			}
		}
		
		return modifier;
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
		
		if (holderID == DataHolder.SENTENCE) {
			this.sentenceTable = this.add2SentenceHolder(this.sentenceTable,args);
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

	public List<Sentence> add2SentenceHolder(List<Sentence> sentenceTable,
			List<String> args) {
		int index = 0;
		
		String source=args.get(index++);
		String sentence=args.get(index++);
		String originalSentence=args.get(index++);
		String lead=args.get(index++);
		String status=args.get(index++);
		String tag=args.get(index++);
		String modifier=args.get(index++);
		String type=args.get(index++);
		
		sentenceTable.add(new Sentence(source, sentence, originalSentence, lead, status, tag, modifier, type));
		return sentenceTable;

	}

	/**
	 * 
	 * @param sentID
	 * @param sentence
	 * @param modifier
	 * @param tag tag could be "null"
	 * @param label
	 */
	public void tagSentenceWithMT(int sentID, String sentence, String modifier,
			String tag, String label) {
		/**
		 * 1. Do some preprocessing of modifier and tag 
		 *     1. Remove -ly words 
		 *     1. Update modifier and tag of sentence sentID in Sentence
		 */
		
		PropertyConfigurator.configure( "conf/log4j.properties" );
		Logger myLogger = Logger.getLogger("updateTable.tagSentenceWithWT");
		
		myLogger.trace("Enter tagSentenceWithMT");
		
		//modifier preprocessing
		modifier = this.tagSentWithMTPreProcessing(modifier);
		tag = this.tagSentWithMTPreProcessing(tag);
		
		//Remove any -ly ending word which is a "b" in the WordPOS, from the modifier
		modifier = this.tagSentWithMTRemoveLyEndingBoundary(modifier);

		modifier = StringUtility.removeAll(modifier, "(^\\s*|\\s*$)");
		tag = StringUtility.removeAll(tag, "(^\\s*|\\s*$)");

		if (tag == null) {
			this.getSentenceHolder().get(sentID).setTag(null);
			this.getSentenceHolder().get(sentID).setModifier(modifier);			
		}
		else {
			if (tag.length() > this.myConfiguratio.getMaxTagLength()) {
				tag = tag.substring(0, this.myConfiguratio.getMaxTagLength());
			}
			this.sentenceTable.get(sentID).setTag(tag);
			this.sentenceTable.get(sentID).setModifier(modifier);	
		}

		for (int i = 0; i < this.sentenceTable.size(); i++) {
			this.sentenceTable.get(sentID).setTag(tag);
			this.sentenceTable.get(sentID).setModifier(modifier);
		}

		myLogger.trace(label);
		myLogger.trace("Quite tagSentenceWithMT");
	}
	
	public String tagSentWithMTPreProcessing(String text) {		
		text = text.replaceAll("<\\S+?>", "");

		text = StringUtility.removeAllRecursive(text, "^(" + Constant.STOP
				+ "|" + Constant.FORBIDDEN+")\\b\\s*");

		// remove stop and forbidden words from ending
		text = StringUtility.removeAllRecursive(text, "\\s*\\b(" + Constant.STOP
				+ "|" + Constant.FORBIDDEN + "|\\w+ly)$");

		// remove all pronoun words
		text = StringUtility.removeAllRecursive(text, "\\b(" + Constant.PRONOUN
				+ ")\\b");
		
		return text;
	}
	
	public int getSumCertaintyU(String word) {
		int sumCertaintyU = 0;
		Iterator<Map.Entry<WordPOSKey, WordPOSValue>> iter = this.wordPOSTable.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<WordPOSKey, WordPOSValue> e = iter.next();
			if (e.getKey().getWord().equals(word)) {
				sumCertaintyU += e.getValue().getCertaintyU();
			}
		}
		
		return sumCertaintyU;
	}

	/**
	 * return singular and plural variations of the word
	 * 
	 * @param word
	 * @return all variations of the word
	 */
	public String singularPluralVariations(String word, Set<SingularPluralPair> singularPluralHolder) {
		String variations = word + "|";
		Iterator<SingularPluralPair> iter = singularPluralHolder.iterator();
		while (iter.hasNext()) {
			SingularPluralPair pair = iter.next();
			String sg = pair.getSingular();
			String pl = pair.getPlural();
			if (sg.equals(word) && (!pl.equals(""))) {
				variations = variations + pl + "|";
			}
			if (pl.equals(word) && (!sg.equals(""))) {
				variations = variations + sg + "|";
			}
		}

		variations = StringUtility.removeAll(variations, "\\|+$");

		return variations;
	}
}
