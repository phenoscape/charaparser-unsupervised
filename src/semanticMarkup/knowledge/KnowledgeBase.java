package semanticMarkup.knowledge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import semanticMarkup.know.lib.WordNetPOSKnowledgeBase;
import semanticMarkup.ling.learn.Configuration;
import semanticMarkup.ling.learn.dataholder.DataHolder;

import semanticMarkup.ling.learn.knowledge.Constant;
import semanticMarkup.ling.learn.utility.StringUtility;
import semanticMarkup.ling.learn.utility.WordFormUtility;

public class KnowledgeBase {

	public KnowledgeBase() {
		// TODO Auto-generated constructor stub
	}

	public void importKnowledgeBase(DataHolder dataholderHandler, String kb,
			Constant constants) {
		// forbidden words
		for (String forbiddenWord : constants.forbiddenWords) {
			dataholderHandler.addToWordPOSHolder(forbiddenWord, "f", "", 1, 1,
					"", null);
		}

		// learnedboundarywords_ini_pato_singleword -> WordPOS
		FileReader file1 = null;
		try {
			file1 = new FileReader(kb
					+ "/learnedboundarywords_ini_pato_singleword.csv");
			BufferedReader reader = new BufferedReader(file1);
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String word = line.substring(1, line.length() - 1);
				if (!constants.forbiddenWords.contains(word)
						&& StringUtility.isMatchedNullSafe(word, "\\w")) {
					dataholderHandler.addToWordPOSHolder(word, "b", "", 1, 1,
							"", null);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found" + ": " + kb
					+ "/learnedboundarywords_ini_pato_singleword.csv");
		} catch (IOException e) {
			throw new RuntimeException("IO Error occured");
		} finally {
			if (file1 != null) {
				try {
					file1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// learnedmodifiers_initial -> Modifiers
		FileReader file2 = null;
		try {
			file2 = new FileReader(kb + "/learnedmodifiers_initial.csv");
			BufferedReader reader = new BufferedReader(file2);
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String word = line.substring(1, line.length() - 1);
				if (!constants.forbiddenWords.contains(word)
						&& StringUtility.isMatchedNullSafe(word, "\\w")) {
					dataholderHandler.addToModifierHolder(word, 1, true);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found" + ": " + kb
					+ "/learnedmodifiers_initial.csv");
		} catch (IOException e) {
			throw new RuntimeException("IO Error occured");
		} finally {
			if (file1 != null) {
				try {
					file1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// learnedstructurewords_ini_onto_lastword -> WordPOS
		FileReader file3 = null;
		try {
			file3 = new FileReader(kb
					+ "/learnedstructurewords_ini_onto_lastword.csv");
			BufferedReader reader = new BufferedReader(file3);
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] words = line.split(",");
				String word = words[0];
				word = word.substring(1, word.length() - 1);
				String POS = words[2];
				POS = POS.substring(1, POS.length() - 1);
				if (!constants.forbiddenWords.contains(word)
						&& StringUtility.isMatchedNullSafe(word, "\\w")) {
					dataholderHandler.addToWordPOSHolder(word, POS, "", 1, 1,
							"", null);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found" + ": " + kb
					+ "/learnedstructurewords_ini_onto_lastword.csv");
		} catch (IOException e) {
			throw new RuntimeException("IO Error occured");
		} finally {
			if (file1 != null) {
				try {
					file1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataHolder tester;

		Configuration myConfiguration = new Configuration();
		WordNetPOSKnowledgeBase wordNetPOSKnowledgeBase = null;
		try {
			wordNetPOSKnowledgeBase = new WordNetPOSKnowledgeBase(
					myConfiguration.getWordNetDictDir(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WordFormUtility wordFormUtility = new WordFormUtility(
				wordNetPOSKnowledgeBase);
		tester = new DataHolder(myConfiguration, wordFormUtility);

		Constant myConts = new Constant();
		KnowledgeBase myKB = new KnowledgeBase();
		myKB.importKnowledgeBase(tester, "kb", myConts);

		tester.write2File("");
	}
}
