package semanticMarkup.ling.learn;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import semanticMarkup.ling.learn.dataholder.DataHolder;

public class AdditionalBootstrapping implements LearningModule {

	public AdditionalBootstrapping() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * bootstrapping using clues such as shared subject different boundary and
	 * one lead word
	 */
	@Override
	public DataHolder run(DataHolder myDataHolder) {
		PropertyConfigurator.configure("conf/log4j.properties");
		Logger myLogger = Logger.getLogger("learn.additionalBootStrapping");
		myLogger.trace("Enter additionalBootStrapping");

		return myDataHolder;
	}


	
	public int oneLeadWordMarkup(DataHolder myDataHolder, List<String> tagList) {
		PropertyConfigurator.configure("conf/log4j.properties");
		Logger myLogger = Logger
				.getLogger("learn.additionalBootStrapping.oneLeadWordMarkup");
		int sign = 0;		

		myLogger.trace("Return: " + sign);
		return 0;
	}

	public int wrapupMarkup() {
		// TODO Auto-generated method stub
		return 0;
	}

}
