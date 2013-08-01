package semanticMarkup.ling.transform.lib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;
import semanticMarkup.ling.Sentence;
import semanticMarkup.ling.transform.ISentenceDetector;

public class UnsupervisedLearningSentenceDetector implements ISentenceDetector{
	private SentenceDetectorME mySentenceDetector;
	
	public UnsupervisedLearningSentenceDetector(String openNLPSentenceDetectorDir) {
		InputStream sentModelIn;

		try {
			sentModelIn = new FileInputStream(openNLPSentenceDetectorDir);
			SentenceModel model = new SentenceModel(sentModelIn);
			this.mySentenceDetector = new SentenceDetectorME(model);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<Sentence> segment(String text) {
		String[] sentenceArray = this.mySentenceDetector.sentDetect(text);
		List<Sentence> sentences = new LinkedList<Sentence>();
		for (String sentence: sentenceArray) {
			Sentence sentenceElement = new Sentence(sentence);
			sentences.add(sentenceElement);
			
		}
		
		return sentences;
	}

}
