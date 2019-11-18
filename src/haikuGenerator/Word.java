package haikuGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * This object represents a word and all of its given followers
 * 
 * @author Kyler McMullin
 *
 */
public class Word {

	/**
	 * The actual string version of the word object
	 */
	private String term;

	/**
	 * Represents a collection of all of the words that follow this word object
	 */
	private HashMap<Word, Integer> followers;

	/**
	 * Initializes the word object
	 * 
	 * @param term
	 */
	public Word(String term) {
		this.term = cleanToken(term);
		followers = new HashMap<>();
	}

	/**
	 * Returns the term which the Word object represents
	 */
	public String toString() {
		return term;
	}

	/**
	 * Cleans a given token such that it will be in a usable format
	 * 
	 * @param token
	 * @return cleanedToken
	 */
	private String cleanToken(String token) {
		token = token.toLowerCase();
		char[] chars = token.toCharArray();
		StringBuilder cleanedToken = new StringBuilder();
		for (char c : chars) {
			if ((Character.isAlphabetic(c) || Character.isDigit(c) || c == '\'')) {
				cleanedToken.append(c);
			}
		}
		return cleanedToken.toString();
	}

	/**
	 * Adds the provided word to this word's set of known followers. If it is
	 * already in the set, it's frequency value is incremented.
	 * 
	 * @param followingWord
	 */
	public void addFollower(Word followingWord) {
		if (followers.containsKey(followingWord)) {
			followers.put(followingWord, followers.get(followingWord) + 1);
		} else {
			followers.put(followingWord, 1);
		}
	}

	/**
	 * Returns whether or not this word has a follower.
	 * 
	 * @return
	 */
	public boolean hasFollower() {
		if (followers.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a random follower.
	 * 
	 * @return randFollower
	 */
	public Word getRandomFollower() {
		return (Word) followers.keySet().toArray()[(int) (Math.random() * followers.size())];
	}

	/**
	 * Returns the top follower.
	 * 
	 * @return topFollower
	 */
	public Word getTopFollower() {
		Word top = null;
		int highestFreq = 0;
		for (Word s : followers.keySet()) {
			if (followers.get(s) > highestFreq) {
				highestFreq = followers.get(s);
				top = s;
			}
		}
		return top;
	}
	
	/**
	 * Returns the bottom follower
	 * 
	 * @return botFollower
	 */
	public Word getBotFollower() {
		Word bot = null;
		int highestFreq = Integer.MAX_VALUE;
		for (Word s : followers.keySet()) {
			if (followers.get(s) < highestFreq) {
				highestFreq = followers.get(s);
				bot = s;
			}
		}
		return bot;
	}

	/**
	 * Returns the median follower.
	 * 
	 * @return midFollower
	 */
	public Word getMidFollower() {
		ArrayList<Integer> freqs = new ArrayList<>();
		for (int i : followers.values()) {
			freqs.add(i);
		}
		Collections.sort(freqs);
		int median = freqs.get(freqs.size() / 2);
		for (Word s : followers.keySet()) {
			if (followers.get(s) == median) {
				return s;
			}
		}
		throw new NoSuchElementException();
	}
}
