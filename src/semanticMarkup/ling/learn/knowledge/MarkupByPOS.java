package semanticMarkup.ling.learn.knowledge;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import semanticMarkup.ling.learn.auxiliary.KnownTagCollection;
import semanticMarkup.ling.learn.dataholder.DataHolder;
import semanticMarkup.ling.learn.dataholder.SentenceStructure;
import semanticMarkup.ling.learn.utility.LearnerUtility;
import semanticMarkup.ling.learn.utility.StringUtility;

public class MarkupByPOS implements IModule {
	private LearnerUtility myLearnerUtility;

	public MarkupByPOS(LearnerUtility learnerUtility) {
		this.myLearnerUtility = learnerUtility;
	}

	

	@Override
	public void run(DataHolder myDataHolder) {
		// TODO Auto-generated method stub
		
	}
	
	public void tagUnknownSentences(DataHolder dataholderHandler, String mode) {
		KnownTagCollection knownTags = myLearnerUtility.getKnownTags(dataholderHandler, mode);
		
		Iterator<SentenceStructure> sentenceIter = dataholderHandler.getSentenceHolderIterator();
		String tag;
		String lead;
		String sentence;
		
		while(sentenceIter.hasNext()) {
			SentenceStructure sentenceItem = sentenceIter.next();
			tag = sentenceItem.getTag();
			lead = sentenceItem.getLead();
			if (tag == null && !StringUtility.isMatchedNullSafe(lead, "similar to .*")) {
				sentence = sentenceItem.getSentence();
				sentence = sentence.replaceAll("<\\S+?>", "");
				sentence = this.myLearnerUtility.annotateSentence(sentence, knownTags, dataholderHandler.getBMSWords());
				sentenceItem.setSentence(sentence);
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
