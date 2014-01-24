package semanticMarkup.ling.learn.knowledge;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import semanticMarkup.ling.learn.dataholder.DataHolder;
import semanticMarkup.ling.learn.utility.LearnerUtility;
import semanticMarkup.ling.learn.utility.StringUtility;

public class HeuristicNounsLearner implements IModule {
	private LearnerUtility myLearnerUtility;

	public HeuristicNounsLearner(LearnerUtility learnerUtility) {
		this.myLearnerUtility = learnerUtility;
	}

	@Override
	public void run(DataHolder dataholderHandler) {
		PropertyConfigurator.configure("conf/log4j.properties");
		Logger myLogger = Logger.getLogger("learn.addHeuristicsNouns");

		myLogger.trace("Enter addHeuristicsNouns");

		Set<String> nouns = this.learnHeuristicsNouns();
		myLogger.debug("Nouns learned from heuristics:");
		myLogger.debug("\t" + nouns.toString());
		myLogger.debug("Total: " + nouns.size());

		List<Set<String>> results = this.characterHeuristics();
		Set<String> rnouns = results.get(0);
		Set<String> descriptors = results.get(1);
		addDescriptors(descriptors);
		addNouns(rnouns);

		// dataholderHandler.printHolder(DataHolder.SINGULAR_PLURAL);

		myLogger.debug("Total: " + nouns.size());
		Iterator<String> iter = nouns.iterator();
		myLogger.info("Learn singular-plural pair");
		while (iter.hasNext()) {
			String e = iter.next();
			myLogger.trace("Check Word: " + e);

			if ((e.matches("^.*\\w.*$"))
					&& (!StringUtility.isMatchedWords(e, "NUM|"
							+ this.myLearnerUtility.getConstant().NUMBER + "|" + this.myLearnerUtility.getConstant().CLUSTERSTRING
							+ "|" + this.myLearnerUtility.getConstant().CHARACTER + "|"
							+ this.myLearnerUtility.getConstant().PROPERNOUN))) {
				myLogger.trace("Pass");

				// same word may have two different pos tags
				String[] nounArray = e.split("\\|");
				for (int i = 0; i < nounArray.length; i++) {
					String nounAndPOS = nounArray[i];
					Pattern p = Pattern.compile("(\\w+)\\[([spn])\\]");
					Matcher m = p.matcher(nounAndPOS);
					if (m.lookingAt()) {
						String word = m.group(1);
						String pos = m.group(2);
						dataholderHandler.updateDataHolder(word, pos, "*",
								"wordpos", 0);

						if (pos.equals("p")) {
							String plural = word;
							String singular = this.myLearnerUtility
									.getWordFormUtility().getSingular(plural);
							if (singular != null) {
								if (!singular.equals("")) {
									dataholderHandler.addSingularPluralPair(
											singular, plural);
								}
							}
						}

						if (pos.equals("s")) {
							String singular = word;
							List<String> pluralList = this.myLearnerUtility
									.getWordFormUtility().getPlural(singular);
							Iterator<String> pluralIter = pluralList.iterator();
							while (pluralIter.hasNext()) {
								String plural = pluralIter.next();
								if (plural != null) {
									if (!plural.equals("")) {
										dataholderHandler
												.addSingularPluralPair(
														singular, plural);
									}
								}
							}
						}
					}
				}
			}
		}

		myLogger.trace("Quite addHeuristicsNouns");
	}

}
