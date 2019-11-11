package haikuGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * This class acts as an object which will reference external sources (i.e.
 * known web sites) in the goal of obtaining the number of syllables contained
 * within a given word, and handled under a specified, supported language.
 * 
 * @author Kyler McMullin
 *
 */
public class SyllableGetter {

	private String language;

	/**
	 * Constructs the syllableGetter object
	 */
	public SyllableGetter(String language) {
		this.language = language;
	}

	/**
	 * Changes the language associated with the SyllableGetter object
	 * 
	 * @param language
	 */
	public void changeLanguage(String language) {
		this.language = language;
	}

	/*
	 * I could write specific algorithms which will get the number of syllables from
	 * words in their respective languages, but that would've required a lot of
	 * work.
	 * 
	 * Instead, I will be relying upon the Internet to get the syllable count for
	 * each word.
	 */

	/**
	 * Returns the number of syllables in a given token
	 * 
	 * @param token
	 * @return syllableCount
	 * @throws IOException 
	 */
	public int getSyllables(String token) throws IOException {
		switch (language) {
		// if the current language is English, it will call to a sub-
		// method which will delegate the process of getting syllables
		// for an English word
		case "English":
			return getEnglishSyllables(token);
		// same as above, but for Spanish
		case "Spanish":
			return getSpanishSyllables(token);
		// still the same, just for French
		case "French":
			return getFrenchSyllables(token);
		default:
			throw new IllegalArgumentException("Language not yet supported!");
		}
	}

	/**
	 * Returns the number of syllables in an English token
	 * 
	 * @param token
	 * @return syllableCount
	 * @throws IOException 
	 */
	private int getEnglishSyllables(String token) throws IOException {
		URL site = new URL(String.valueOf("https://writerlywords.com/syllables/" + token));
		HttpsURLConnection con = (HttpsURLConnection) site.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String line = "";
		while (!line.contains("<p id=\"ctl00_ContentPane_paragraphtext\" style=\"font-size: x-large\">Syllables in " + token.toUpperCase() + ":")) {
			line = in.readLine();
		}
		
		String[] arr = line.split("\\s");
		arr[14] = arr[14].replace("</p>", "");
		return Integer.parseInt(arr[14]);
	}

	/**
	 * Returns the number of syllables in a Spanish token
	 * 
	 * @param token
	 * @return syllableCount
	 */
	private int getSpanishSyllables(String token) {
		return 0;
	}

	/**
	 * Returns the number of syllables in a French token
	 * 
	 * @param token
	 * @return syllableCount
	 */
	private int getFrenchSyllables(String token) {
		return 0;
	}

}
