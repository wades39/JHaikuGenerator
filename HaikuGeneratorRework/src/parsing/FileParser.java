package parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import generation.Chain;

/**
 * Parses a text file and adds its contents to a chain.
 * 
 * @author wades39 (Kyler McMullin)
 * @version April 28, 2021
 */
public class FileParser {

	/**
	 * The chain to store data to. Since this is passed in by reference, we don't
	 * need to have a method return this Chain, as all changes made here will be
	 * reflected in the class where this Chain instance originated.
	 */
	private Chain chain;
	/**
	 * Scanner used to read the passed in text file.
	 */
	private Scanner sc;

	/**
	 * Constructs a FileParser to parse a text file.
	 * 
	 * @param filePath - The path of the file to parse.
	 * @param chain    - The chain to store the data to.
	 * @throws FileNotFoundException when the file cannot be found.
	 */
	public FileParser(String filePath, Chain chain) throws FileNotFoundException {
		this.chain = chain;
		sc = new Scanner(new File(filePath));
	}

	/**
	 * Parses the file and puts the data into the chain.
	 */
	public void parse() {
		// Read the file. While there's a next word, add it to the chain.
		// The chain will do all of the processing it needs to in order to maintain
		// its structure.
		while (sc.hasNext()) {
			String term = sc.next();
			// Sanitize the term by removing all punctuation.
			String termSanitized = term.replaceAll("[.,!?\"\'\n\r\t]", "").toLowerCase();
			// I patched out the endOfClause functionality here because it was causing too
			// many issues with generation.
			chain.put(termSanitized, false /* ".?!".indexOf(term.charAt(term.length() - 1)) != -1 */);
		}
	}
}
