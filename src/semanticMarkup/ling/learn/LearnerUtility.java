package semanticMarkup.ling.learn;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LearnerUtility {

	private Configuration myConfiguration;
	
	public LearnerUtility(Configuration configuration) {
		this.myConfiguration = configuration;
	}
	
	public boolean tagSentence(DataHolder myDataHolder, int sentenceID, String tag) {
		PropertyConfigurator.configure("conf/log4j.properties");
		Logger myLogger = Logger.getLogger("learn.discover.ruleBasedLearn.tagIt");
		myLogger.trace(String.format("Enter (%d, %s)", sentenceID, tag));
		
		// case 1
		if (!StringUtility.createMatcher("\\w+", tag).find()) {
			myLogger.trace("Tag is not a word. Return");
			return false;
		} else {
			// case 2
			if (StringUtility.createMatcher("^(" + Constant.STOP + ")\\b", tag)
					.find()) {
				myLogger.trace(String
						.format("\t:tag %s starts with a stop word, ignore tagging requrest",
								tag));
				return false;
			} else {
				// case 3
				int maxLength = this.myConfiguration.getMaxTagLength();
				if (tag.length() > maxLength) {
					maxLength = this.myConfiguration.getMaxTagLength();
					tag = tag.substring(0, maxLength);
					myLogger.debug(String.format("\ttag: %s longer than %d)",
							tag, maxLength));
				} else {
					;
				}
				Sentence sentence = myDataHolder.getSentenceHolder().get(
						sentenceID);
				sentence.setTag(tag);
				myLogger.debug(String.format(
						"\t:mark up sentence #%d with tag %s", sentenceID, tag));
				return true;
			}
		}
	}

}
