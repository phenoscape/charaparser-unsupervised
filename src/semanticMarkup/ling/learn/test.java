package semanticMarkup.ling.learn;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import semanticMarkup.core.Treatment;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "Users/nescent/Phenoscape/TEST2/target/descriptions";
		UnsupervisedClauseMarkup t = new UnsupervisedClauseMarkup(str,"biocreative2012","plain","test");
		
		t.populatesents();
		
		List<Treatment> l_test = new ArrayList<Treatment>();
		//Map<String, String> m_test = new HashMap<String,String>();
		
		
		t.learn(l_test);
		t.getSentences();
		t.getSentencesForOrganStateMarker();
		t.getAdjNouns();
		t.getAdjNounSent();
		t.getSentenceTags();
		t.getBracketTags();
		t.getWordRoleTags();
		t.getWordToSources();
		t.getRoleToWords();
		t.getWordsToRoles();
		t.getHeuristicNouns();
		t.getTermCategories();
	}
}
