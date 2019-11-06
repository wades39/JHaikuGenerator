package haikuGenerator;

import java.util.HashSet;

/**
 * This class is a HashSet<Word> which allows external classes to quickly and
 * easily reference a set of words.
 * 
 * This class will see implementation in two places:
 * 
 * 1. containing a set of words, housing all words that occur in the text
 * 
 * 2. when the Word class returns a list of all words that follow the instance
 * which it refers to
 * 
 * @author Kyler McMullin
 *
 */
public class WordSet {

	/**
	 * The set which houses the words.
	 */
	private HashSet<Word> wordSet;

	/**
	 * Represents a set of word objects.
	 */
	public WordSet() {
		wordSet = new HashSet<Word>();
	}

	/**
	 * Adds a word to the set.
	 * 
	 * @param word
	 */
	public void add(Word word) {
		wordSet.add(word);
	}

}
