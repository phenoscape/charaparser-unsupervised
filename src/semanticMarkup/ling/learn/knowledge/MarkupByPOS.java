package semanticMarkup.ling.learn.knowledge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import semanticMarkup.ling.learn.auxiliary.KnownTagCollection;
import semanticMarkup.ling.learn.dataholder.DataHolder;
import semanticMarkup.ling.learn.dataholder.SentenceStructure;
import semanticMarkup.ling.learn.utility.LearnerUtility;
import semanticMarkup.ling.learn.utility.StringUtility;

public class MarkupByPOS implements IModule {
	private LearnerUtility myLearnerUtility;
	private Logger myLogger;

	public MarkupByPOS(LearnerUtility learnerUtility) {
		this.myLearnerUtility = learnerUtility;
		PropertyConfigurator.configure("conf/log4j.properties");
		myLogger = Logger.getLogger("learn.unknownWordBootstrapping");
	}

	

	@Override
	public void run(DataHolder dataholderHandler) {
		
		
		
		
		int sign = 0;
		Set<String> token = new HashSet<String>();
		token.add("################################");
		do {
			sign = 0;
			this.tagUnknownSentences(dataholderHandler, "singletag");	
			
			
			for (SentenceStructure sentenceItem : dataholderHandler.getSentenceHolder()) {
				if (sentenceItem.getTag() == null) {
					List<String> words = new ArrayList<String>();
					words.addAll(Arrays.asList(sentenceItem.getSentence().split("\\s+")));
					String ptn = this.myLearnerUtility.getSentencePtn(dataholderHandler, token, words.size()+1, words);
					
					if (StringUtility.isEntireMatchedNullSafe(ptn, "")) {
						myLogger.trace("Case 1");
					}
					else if (StringUtility.isEntireMatchedNullSafe(ptn, "")) {
						myLogger.trace("Case 2");
					}
					else {
						myLogger.trace("Case 3");
					}
				}
			}
			
		} while (sign > 0);
		
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
