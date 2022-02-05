package generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeSet;

/**
 * This class acts as a graph connecting all of the words obtained through the
 * file parsing process.
 * 
 * @author wades39 (Kyler McMullin)
 * @version April 28, 2021
 */
public class Chain {

	private TreeSet<String> knownWords;
	private HashMap<String, Link> links;

	private Link lastWord;

	/**
	 * Instantiate a chain.
	 */
	public Chain() {
		knownWords = new TreeSet<>();
		links = new HashMap<>();
		lastWord = null;
	}

	/**
	 * Put a word into the Chain.
	 * 
	 * @param word        - Word to insert
	 * @param endOfClause - Whether the word being inserted is at the end of a
	 *                    clause
	 */
	public void put(String word, boolean endOfClause) {
		knownWords.add(word);
		Link thisWord = null;

		// If we've already seen this word, do this
		if (links.containsKey(word)) {
			thisWord = links.get(word);
		} else {
			thisWord = new Link(word);
			links.put(word, thisWord);
		}

		if (lastWord != null) {
			lastWord.addConnection(thisWord);
		}

		// if thisWord is at the end of a clause, set lastWord to null
		// otherwise, set it to thisWord
		lastWord = endOfClause ? null : thisWord;
	}
	
	/**
	 * @return a random link from the chain
	 */
	public Link getRandom() {
		ArrayList<String> keys = new ArrayList<>(links.keySet());
		String key = keys.get((new Random()).nextInt(keys.size()));
		return links.get(key);
	}
}
