package semanticMarkup.ling.learn;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "Users/nescent/Phenoscape/TEST2/target/descriptions";
		UnsupervisedClauseMarkup t = new UnsupervisedClauseMarkup(str,"biocreative2012","plain","test");
		
		t.populatesents();
		
		/*
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
		t.getTermCategories();*/
	}

}
