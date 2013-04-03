package semanticMarkup.ling.learn;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataHolder {

	// Data Holders
	// Table sentence
	public List<Sentence> sentenceTable = new LinkedList<Sentence>();

	// Table unknownwords
	public Map<String, String> unknownWordTable = new HashMap<String, String>();

	// Table wordpos
	public Map<WordPOSKey, WordPOSValue> wordPOSTable = new HashMap<WordPOSKey, WordPOSValue>();

	// Table heuristicnouns
	public Map<String, String> heuristicNounTable = new HashMap<String, String>();

	// Table singularPlural
	public Set<SingularPluralPair> singularPluralTable = new HashSet<SingularPluralPair>();

	// Table modifier
	public Map<String, ModifierTableValue> modifierTable = new HashMap<String, ModifierTableValue>();

	// Table discounted
	public Map<DiscountedKey, String> discountedTable = new HashMap<DiscountedKey, String>();

	public DataHolder() {
		sentenceTable = new LinkedList<Sentence>();
		unknownWordTable = new HashMap<String, String>();
		wordPOSTable = new HashMap<WordPOSKey, WordPOSValue>();
		heuristicNounTable = new HashMap<String, String>();
		singularPluralTable = new HashSet<SingularPluralPair>();
		modifierTable = new HashMap<String, ModifierTableValue>();
		discountedTable = new HashMap<DiscountedKey, String>();
	}
	
	public List<Sentence> getSentenceTable(){
		return this.sentenceTable;
	}
	
	

}
