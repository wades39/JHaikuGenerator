package generator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * This class refers to a word and a map of all words that follow it (including
 * their frequency in the text).
 * 
 * @author Kyler McMullin
 *
 */
public class Word {

	/**
	 * The word the object is referencing
	 */
	private String term;

	/**
	 * Represents a set of all words that can follow the word, along with how
	 * frequently they occur
	 */
	private HashMap<Word, Integer> possibleFollowers;

	/**
	 * Represents a word from the text, accounting for words that follow it.
	 * 
	 * @param term
	 */
	public Word(String term) {
		this.term = cleanToken(term);
		possibleFollowers = new HashMap<>();
	}

	/**
	 * Returns a cleaned token. Removes unnecessary punctuation, sets to lower case.
	 * 
	 * @param token
	 * @return cleanToken
	 */
	private String cleanToken(String token) {
		String clean = token.toLowerCase(); // sets token to lower case
		clean = clean.replace(".", "").replace(",", "") // removes unnecessary punctuation
				.replace(":", "").replace(";", "").replace("\"", "");
		// if last character is an apostrophe, it is removed
		if (clean.toCharArray()[clean.length() - 1] == '\'') {
			clean = clean.substring(0, clean.length() - 1);
		}
		return clean;
	}

	/**
	 * Returns the string value of the word object
	 * 
	 * @return wordString
	 */
	public String toString() {
		return term;
	}

	/**
	 * Returns the word that has the most occurrences after this word instance.
	 * 
	 * @return mostFrequentFollowingWord
	 */
	public Word getTopFollower() {
		// Every word in the set will have a higher value than this
		int highestFreq = Integer.MIN_VALUE;

		/*
		 * top will contain the word which occurs most frequently following this word
		 * (i.e. if the text is "the big red door is afraid of the dog, because the dog
		 * ate a small door", and the current word is "the," the top following word
		 * would be "dog.")
		 */
		Word top = null;

		// references to the set of words
		HashSet<Word> set = (HashSet<Word>) possibleFollowers.keySet();

		/*
		 * For every word in the set of words that are known to follow the current word,
		 * if it occurs more frequently than any other, it is the top word
		 * 
		 * In reference to the example given above the word variable, there are two
		 * words that follow "the": "big" and "dog". "big" occurs once, "dog" occurs
		 * twice, making "dog" the most frequent word.
		 */
		for (Word w : set) {
			if (possibleFollowers.get(w) > highestFreq) {
				highestFreq = possibleFollowers.get(w);
				top = w;
			}
		}

		return top;
	}

	/**
	 * Returns the word that follows this word instance at the median frequency
	 * 
	 * @return medianFollower
	 */
	public Word getMedianFollower() {

		// will hold the frequency values
		int[] frequencies = new int[possibleFollowers.values().size() - 1];

		// code will not work if there is nothing in the array
		if (frequencies.length == 0) {
			throw new IllegalArgumentException();
		}

		// places the frequency values into the array
		int i = 0;
		for (int freq : possibleFollowers.values()) {
			frequencies[i] = freq;
			i++;
		}

		// sorts frequency values from smallest to largest
		Arrays.sort(frequencies);

		int median = Integer.MIN_VALUE;

		// if frequencies contains an odd number of values, return middle
		if (frequencies.length % 2 == 1) {
			median = frequencies[frequencies.length / 2]; // length/2 = middle index
		} else {
			median = frequencies[frequencies.length / 2]; // higher bound middle
			// if length = 4, then it is 2 however, index of 2 is one above the
			// true middle (the best option)
		}

		// return word that is mapped to median frequency
		for (Word w : possibleFollowers.keySet()) {
			if (possibleFollowers.get(w) == median) {
				return w;
			}
		}

		// if there is no median value, throw NSEE
		throw new NoSuchElementException();
	}

	/**
	 * Returns a random word that follows this word instance.
	 * 
	 * @return randomFollowingWord
	 */
	public Word getRandomFollower() {

		// Stores the possible words into a set
		HashSet<Word> set = (HashSet<Word>) possibleFollowers.keySet();

		// Stores the set into an array
		Object[] words = set.toArray();

		// Generates a random index (maximum size is (words.length - 1))
		int index = (int) (Math.random() * (double) words.length);

		return (Word) words[index];
	}

	/**
	 * Returns the word that follows this word instance the least frequently.
	 */
	public Word getBottomFollower() {
		// all possible values of i are smaller than index
		int index = Integer.MAX_VALUE;
		// finds the smallest value of i and assigns it to index
		for (int i : possibleFollowers.values()) {
			if (i < index) {
				index = i;
			}
		}
		// returns the word in the index that has the lowest frequency
		return (Word) possibleFollowers.keySet().toArray()[index];
	}

	/**
	 * Adds a follower to this word instance. Should the follower already exist, its
	 * frequency is incremented.
	 * 
	 * @param term
	 */
	public void addFollower(Word term) {
		// if the given word is a known follower, increment its frequency by 1
		if (possibleFollowers.containsKey(term)) {
			possibleFollowers.put(term, possibleFollowers.get(term) + 1);
		}
		// otherwise, add the given word to the list of known followers and set its
		// frequency to 1
		else {
			possibleFollowers.put(term, 1);
		}
	}

	/**
	 * Returns a WordSet containing all words that follow this word instance.
	 * 
	 * @return followerSet
	 */
	public WordSet getFollowers() {
		// create new WordSet
		WordSet words = new WordSet();
		// for each word in the list of known followers, add it to the WordSet
		for (Word follower : possibleFollowers.keySet()) {
			words.add(follower);
		}
		// return the WordSet containing all words that follow this word instance
		return words;
	}
}
