package haikuGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This object class will perform the reading of the source file and interfacing
 * with other object classes, in order to facilitate the reading and analysis of
 * the source file, which will then be parsed into a haiku poem by another,
 * different object class.
 * 
 * @author Kyler McMullin
 *
 */
public class Reader {

	/**
	 * References the file the reader will read
	 */
	private File source;
	/**
	 * Iterates through each word in the file
	 */
	private Scanner scan;

	/**
	 * Holds all words in the text
	 */
	private WordSet allWords;

	/**
	 * Temporary file with cleaned tokens
	 */
	private File temporary;

	/**
	 * Used to write to a temp file
	 */
	private BufferedWriter bw;

	public int pos;

	/**
	 * Constructs the reader object
	 * 
	 * @param source
	 * @param language
	 * @throws IOException
	 */
	public Reader(File source) throws IOException {
		this.source = source;
		scan = new Scanner(source);
		allWords = new WordSet();
		temporary = File.createTempFile("JHaikuGenerator-", ".tmp");
		temporary.deleteOnExit();
		bw = new BufferedWriter(new FileWriter(temporary));
		this.source.canRead();
		pos = 0;
	}

	/**
	 * Reads the source file and populates a temporary file with the cleaned tokens.
	 * 
	 * @throws IOException
	 */
	public void read() throws IOException {
		// read all of the words in the source file and place them into a temporary file
		while (scan.hasNext()) {
			String wor = scan.next();
			if (!wor.equals("-")) {
				Word w = new Word(wor); // creates new word object and cleans its text representation
				if (temporary.length() == 0L) {
					bw.append(" ");
				}
				bw.append(w.toString() + " ");
				if (!allWords.containsValue(w.toString())) {
					allWords.put(w, w.toString());
				}
			}
		}

		bw.close();
		scan.close();

	}

	/**
	 * Populates the followers for each word
	 * 
	 * @throws FileNotFoundException
	 */
	public void popFollowers() throws FileNotFoundException {
		scan = new Scanner(temporary);
		pos = 0;

		// Apply followers, frequency
		for (Word w : allWords.keySet()) {
			scan.useDelimiter(" " + w.toString() + " ");
			scan.next(); // get to first occurrence of the word
			while (scan.hasNext()) {
				String line = scan.next();
				Scanner sc = new Scanner(line);
				if (sc.hasNext()) {
					line = sc.next();
				} else {
					break; // no next word
				}
				sc.close();
				w.addFollower(allWords.get(line));
			}
			scan.close();
			scan = new Scanner(temporary);
			pos++;
		}
	}

	/**
	 * Returns a WordSet containing all of the words in the text.
	 * 
	 * @return allWords
	 */
	public WordSet getAllWords() {
		return allWords;
	}
}