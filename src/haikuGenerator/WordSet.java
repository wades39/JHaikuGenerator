package haikuGenerator;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * This class is a HashMap, mapping word objects to their string values.
 * 
 * @author Kyler McMullin
 *
 */
public class WordSet extends HashMap<Word, String> {

	/**
	 * Java was yelling at me to add this
	 */
	private static final long serialVersionUID = 5905309584989230807L;

	/**
	 * Returns a random word contained by the wordSet.
	 * 
	 * @return Word
	 */
	public Word getRandom() {
		return (Word) this.keySet().toArray()[(int) (Math.random() * this.keySet().size())];
	}

	/**
	 * Returns the word that matches the provided token, however if no match is
	 * found, a NoSuchElementException is thrown
	 * 
	 * @return Word
	 * @throws NoSuchElementException when no word object has a string value that
	 *                                matches the provided token
	 */
	public Word get(String token) {
		if (!(token.isEmpty())) {
			for (Word w : this.keySet()) {
				if (w.toString().equals(token)) {
					return w;
				}
			}
			throw new NoSuchElementException(token);
		}
		return null;
	}
}
