package haikuGenerator;

import java.io.BufferedWriter;
import java.io.File;
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
	}

	/**
	 * Reads the source file, creating the necessary data from the text to allow
	 * generation
	 * 
	 * @throws IOException
	 */
	public void read() throws IOException {
		// places all distinct words from the text into a wordSet
		while (scan.hasNext()) {
			String s = scan.next();
			if (s.equals("-")) {
				continue;
			}
			Word w = new Word(s);
			allWords.put(w, w.toString());
			bw.append(w.toString() + " ");
		}
		bw.close();

		scan.close();
		scan = new Scanner(temporary);

		for (Word w : allWords.keySet()) {
			scan.useDelimiter(w.toString() + " ");
			scan.next(); // skips to first instance of the word

			while (scan.hasNext()) {
				String line = scan.next();
				String[] arr = line.split("\\s");
				System.out.println(arr[0]);
				if (arr[0].isBlank() || arr[0].isEmpty() || arr[0] == null) {
					continue;
				}
				w.addFollower(allWords.get(arr[0]));
			}
			scan.close();
			scan = new Scanner(temporary);
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
