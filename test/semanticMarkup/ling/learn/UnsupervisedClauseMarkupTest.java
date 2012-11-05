package semanticMarkup.ling.learn;

import org.junit.Test;

public class UnsupervisedClauseMarkupTest {
	
	@Test
	public void testUnsupervisedClauseMarkup() {
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
