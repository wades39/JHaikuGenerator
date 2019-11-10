package haikuGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

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
public class WordSet extends HashMap<Word, String> {

	/**
	 * Java was yelling at me to add this
	 */
	private static final long serialVersionUID = 5905309584989230807L;

	public Word getRandom() {
		return (Word) this.keySet().toArray()[(int)(Math.random()*this.keySet().size())];
	}
	
	public Word get(String token) {
		ArrayList<Word> words = new ArrayList<Word>(this.keySet());
		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).toString().equals(token)) {
				return words.get(i);
			}
		}
		throw new NoSuchElementException(token);
	}
}
