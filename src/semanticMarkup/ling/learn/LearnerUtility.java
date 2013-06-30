package semanticMarkup.ling.learn;

import org.apache.commons.lang3.StringUtils;
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
	
	/**
	 * skip and/or cases
	 * skip leads with $stop words
	 * @return number of updates
	 */
	public int doItMarkup(DataHolder myDataHolder) {
		PropertyConfigurator.configure("conf/log4j.properties");
		Logger myLogger = Logger.getLogger("learn.additionalBootStrapping.doItMarkup");
		myLogger.trace("Enter");
		/**
	my ($id, $sent, $sth, $TAGS, $lead, $tag);
	my $sign = 0;
	print "doit markup\n" if $debug;
  	$sth = $dbh->prepare("Select sentid, lead, sentence from ".$prefix."_sentence where isnull(tag) or tag='' or tag='unknown'");
	$sth->execute();
	while(($id, $lead, $sent) = $sth->fetchrow_array()){
		if($sent=~/^.{0,40} (nor|or|and|\/)/){next;}
		if($lead=~/\b($stop)\b/){next;}
		print "sentid: $id: " if $debug;
 		($tag, $sign) = doit($id);   #for cases before relevant knowledge was learned.
    	if($tag =~/\w/){
      		tag($id, $tag);
    	}
	}
	return $sign;
		 */
		int sign = 0;		
		for (int i=0;i<myDataHolder.getSentenceHolder().size();i++) {
			myDataHolder.getSentenceHolder().get(i);
			String tag = myDataHolder.getSentenceHolder().get(i).getTag();
			if ((tag == null) 
					|| (StringUtils.equals(tag, ""))
					|| (StringUtils.equals(tag, "unknown"))) {
				int sentenceID = i;
				String lead = myDataHolder.getSentenceHolder().get(i).getLead();
				String sentence = myDataHolder.getSentenceHolder().get(i)
						.getSentence();
				
				if (StringUtility.createMatcher("^.{0,40} (nor|or|and|\\/)", sentence).find()) {
					continue;
				}
				
				if (StringUtility.createMatcher("\\b("+Constant.STOP+")\\b", lead).find()) {
					continue;
				}
				
				//StringAndInt tagAndSign = doIt(sentenceID);
				//String doItTag = tagAndSign.getString();
				//int doItID = tagAndSign.getInt();
				//if (StringUtility.createMatcher("\\w", tag).find()) {
				//	tagSentence(myDataHolder, doItID, doItTag);
				//}
			}
		}
		
		myLogger.trace("Return: "+sign);
		return 0;
	}

}
