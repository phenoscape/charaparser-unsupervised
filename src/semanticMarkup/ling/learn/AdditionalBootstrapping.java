package semanticMarkup.ling.learn;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class AdditionalBootstrapping implements LearningModule {
	private DataHolder myDataHolder;

	public AdditionalBootstrapping() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * bootstrapping using clues such as shared subject different boundary and
	 * one lead word
	 */
	@Override
	public DataHolder run(DataHolder dataHolder) {
		this.myDataHolder = dataHolder;

		PropertyConfigurator.configure("conf/log4j.properties");
		Logger myLogger = Logger.getLogger("learn.additionalBootStrapping");
		myLogger.trace("Enter additionalBootStrapping");

		int flag = 0;

		do {
			myLogger.trace(String.format("Enter one do-while loop iteration"));
			flag = 0;

			// warmup markup
			int cmReturn = wrapupMarkup();
			myLogger.trace(String
					.format("wrapupMarkup() returned %d", cmReturn));
			flag += cmReturn;

			// one lead word markup
			List<String> tags = this.myDataHolder.getCurrentTags();
			int omReturn = oneLeadWordMarkup(tags);
			myLogger.trace(String.format("oneLeadWordMarkup() returned %d",
					omReturn));
			flag += omReturn;

			// doit markup
			int dmReturn = wrapupMarkup();
			myLogger.trace(String.format("doItMarkup() returned %d", dmReturn));
			flag += dmReturn;

			myLogger.trace(String.format("Quite this iteration with flag = %d",
					flag));
		} while (flag > 0);

		return myDataHolder;
	}

	public int oneLeadWordMarkup(List<String> tagList) {
		PropertyConfigurator.configure("conf/log4j.properties");
		Logger myLogger = Logger
				.getLogger("learn.additionalBootStrapping.oneLeadWordMarkup");
		String tags = StringUtility.joinList("|", tagList);
		int sign = 0;
		myLogger.trace(String.format("Enter (%s)", tags));

		for (int i = 0; i < myDataHolder.getSentenceHolder().size(); i++) {
			Sentence sentence = myDataHolder.getSentenceHolder().get(i);
			String tag = sentence.getTag();
			String lead = sentence.getLead();

			if ((tag == null) && (lead.matches("% %"))) {
				if (StringUtility.createMatcher(
						String.format("\\b%s\\|", lead), tags).find()) {
					// tagIt(i, lead);
					myLogger.trace(String.format(
							"updateTable(%s, n, -, wordpos, 1)", lead));
					sign += this.myDataHolder.updateTable(lead, "n", "-",
							"wordpos", 1);
				}
			}
		}

		myLogger.trace("Return: " + sign);
		return 0;
	}

	public int wrapupMarkup() {
		// TODO Auto-generated method stub
		return 0;
	}

}
