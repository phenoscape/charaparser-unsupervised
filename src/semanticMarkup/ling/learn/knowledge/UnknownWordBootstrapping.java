package semanticMarkup.ling.learn.knowledge;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import semanticMarkup.ling.learn.Constant;
import semanticMarkup.ling.learn.KnownTagCollection;
import semanticMarkup.ling.learn.LearnerUtility;
import semanticMarkup.ling.learn.StringUtility;
import semanticMarkup.ling.learn.dataholder.DataHolder;
import semanticMarkup.ling.learn.dataholder.SentenceStructure;

public class UnknownWordBootstrapping implements IModule {
	private LearnerUtility myLearnerUtility;

	public UnknownWordBootstrapping(LearnerUtility learnerUtility) {
		this.myLearnerUtility = learnerUtility;
	}

	@Override
	public void run(DataHolder dataholderHandler) {
		unknownWordBootstrapping(dataholderHandler);
	}
	
	public void unknownWordBootstrapping(DataHolder dataholderHandler) {
		PropertyConfigurator.configure("conf/log4j.properties");
		Logger myLogger = Logger.getLogger("learn.unknownWordBootstrapping");
		myLogger.trace("[unknownWordBootstrapping]Start");
		
		unknownWordBootstrappingPreprocessing(dataholderHandler);
		unknownWordBootstrappingMain(dataholderHandler);
		unknownWordBootstrappingPostprocessing(dataholderHandler);
		
		myLogger.trace("[unknownWordBootstrapping]End");
	}
	
	public void unknownWordBootstrappingPreprocessing(DataHolder dataholderHandler) {
		this.myLearnerUtility.tagAllSentences(dataholderHandler, "singletag", "sentence");
	}
	
	public void unknownWordBootstrappingMain(DataHolder dataholderHandler) {
		String plMiddle = "(ee)";
		
		int newInt = 0;
		do {
//			this.unknownWordBootstrappingGetUnknownWord(plMiddle);
		} while (newInt > 0);
	}

	public void unknownWordBootstrappingPostprocessing(DataHolder dataholderHandler) {
		// pistillate_zone
		// get all nouns from wordPOS holder
		Set<String> POSTags = new HashSet<String>();
		POSTags.add("p");
		POSTags.add("s");
		Set<String> nouns = dataholderHandler.getWordsFromWordPOSByPOSs(
				POSTags);
		
		// get boudaries
		Set<String> boundaries = new HashSet<String>();
		Set<String> words = dataholderHandler.getWordsFromUnknownWord("^.*_.*$", true,
						"^unknown$", true);
		Iterator<String> wordIter = words.iterator();
		String pattern = "_(" + StringUtils.join(nouns, "|") + ")$";
		while (wordIter.hasNext()) {
			String word = wordIter.next();
			Pattern p1 = Pattern.compile("^[a-zA-Z0-9_-]+$");
			Matcher m1 = p1.matcher(word);
			Pattern p2 = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
			Matcher m2 = p2.matcher(word);
			if (m1.matches() && (!m2.matches())) {
				if (!StringUtility.createMatcher(
						"\\b(" + Constant.FORBIDDEN + ")\\b", word).find()) {
					boundaries.add(word);
				}
				dataholderHandler.updateDataHolder(word, "b", "", "wordpos", 1);
			}
		}
		
		// if the boundaries is not empty
		if (boundaries.size() > 0) {
			Iterator<SentenceStructure> iter = dataholderHandler
					.getSentenceHolderIterator();
			while (iter.hasNext()) {
				SentenceStructure sentenceItem = iter.next();
				String tag = sentenceItem.getTag();
				String sentence = sentenceItem.getSentence();
				int sentenceID = sentenceItem.getID();

				if ((!(StringUtils.equals(tag, "ignore")) || (tag == null))
						&& (StringUtility.createMatcher(
								"(^| )(" + StringUtils.join(boundaries, "|")
										+ ") ", sentence).find())) {
					KnownTagCollection tags = new KnownTagCollection(null,
							null, null, boundaries, null, null);
					sentence = this.myLearnerUtility.annotateSentence(sentence, tags, dataholderHandler.BMSWords);
					SentenceStructure updatedSentence = dataholderHandler.getSentence(sentenceID);
					updatedSentence.setSentence(sentence);
				}
			}
		}
	}

}
