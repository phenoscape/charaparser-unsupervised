package semanticMarkup.ling.learn.knowledge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;
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
					
					sign += CaseHandler(dataholderHandler, sentenceItem, words, ptn);

				}
			}
			
		} while (sign > 0);
		
	}
	
	public int CaseHandler(DataHolder dataholderHandler,
			SentenceStructure sentenceItem, List<String> words, String ptn) {
		int sign = 0;
		Matcher m21 = StringUtility.createMatcher(ptn, "^([mtqb]*)([np]+)((?<=p)q)");
		Matcher m22 = StringUtility.createMatcher(ptn, "^([mtqb]*)([np]+)(,|;|:|\\.|b)");
		boolean case21 = m21.find();
		boolean case22 = m22.find();
		if (StringUtility.isMatchedNullSafe(ptn, "^[qmb][,;:\\.]$")) {
			myLogger.trace("Case 1");
			// tagsentwmt($sentid, $sentence, "", "ditto", "remainnulltag-[R0]");
			dataholderHandler.tagSentenceWithMT(sentenceItem.getID(), 
					sentenceItem.getSentence(), "", "ditto", "remainnulltag-[R0]");
		}
		else if (case21 || case22) {
			myLogger.trace("Case 2");
			int start3 = 0;
			int end1 = 0;
			if (case21) {
				start3 = m21.start(3);
				end1 = m21.end(1); 
			}
			else {
				start3 = m22.start(3);
				end1 = m22.end(1);
			}
			
			String boundary = words.get(start3);
			String modifier = StringUtils.join(words.subList(0, end1), " ");
			
			Matcher m23 = StringUtility.createMatcher(modifier, String.format("\\b(%s)\\b", Constant.PREPOSITION));
			if (m23.find()) {
				int start2 = m23.start(2);
				int end2 = m23.end(2);
				List<String> tagWords = words.subList(start2, end2);
				if (tagWords.size()>1) {
					modifier = modifier + " " + StringUtils.join(tagWords.subList(0, tagWords.size()-1), " ");
					modifier = modifier.replaceAll("\\s*$", "");
				}
				String tag = tagWords.get(tagWords.size()-1);
				
				if (StringUtility.isMatchedNullSafe(tag, "<")) {
//					$sign += update($tag, "p", "-", "wordpos", 1)
					int result = dataholderHandler.updateDataHolder(tag, "p", "-", "wordpos", 1);
					sign = sign + result;
				}
				
				// nontagged words in modifier

				
				List<String> modifierList = getModifiersForUntag(modifier);
				for (String m : modifierList) {
					int result = dataholderHandler.updateDataHolder(m, "m", "",
							"modifiers", 1);
					sign += result;
				}
				
				
				// update boundary
				if (StringUtility.isMatchedNullSafe(boundary, "<")) {
				int result = dataholderHandler.updateDataHolder(boundary, "b", "", "wordpos", 1);
				sign += result;
				}
				
				modifier = modifier.replaceAll("<\\S+?>", "");
				tag = tag.replaceAll("<\\S+?>", "");
				
				dataholderHandler.tagSentenceWithMT(sentenceItem.getID(), sentenceItem.getSentence(), modifier, tag, "remainnulltag-[R1]");
				
			}
			
		}
		else if (StringUtility.isMatchedNullSafe(ptn, "^([^qpn,;:]*)([pn]+)[tmb]")){
			myLogger.trace("Case 3");
		}
		
		return sign;
	}



	public List<String> getModifiersForUntag(String modifier) {
		if (modifier == null) {
			return null;
		}
		
		List<String> modifiers = new LinkedList<String>();
		if (modifier.equals("")) {
			return modifiers;
		}
		
		String modifierCopy = modifier;
		Matcher m24 = StringUtility.createMatcher(modifierCopy, "(?:^| )(\\w+) (.*)");
		while (m24.find()) {
			String g1 = m24.group(1);
			modifiers.add(g1);
			
			modifierCopy = m24.group(2);
			m24 = StringUtility.createMatcher(modifierCopy, "(?:^| )(\\w+) (.*)");
		}
		
		return modifiers;
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
