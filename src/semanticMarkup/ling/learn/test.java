package semanticMarkup.ling.learn;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		UnsupervisedClauseMarkup t = new UnsupervisedClauseMarkup();
		t.learn();
		t.getSentences();
		t.getSentencesForOrganStateMarker();
		t.getAdjNouns();
		t.getAdjNounsSent();
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
