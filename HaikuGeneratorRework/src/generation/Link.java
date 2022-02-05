package generation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * The data-holding part of the chain.
 * 
 * @author wades39 (Kyler McMullin)
 * @version April 28, 2021
 */
public class Link {

	private String word;
	private HashSet<Link> outboundConnections;

	/**
	 * Instantiates a chain link with word as its data
	 * 
	 * @param word - The word to associate this link to
	 */
	public Link(String word) {
		this.word = word;
		outboundConnections = new HashSet<>();
	}

	/**
	 * Creates a mapping from this link to a link this one connects to outwardly.
	 * 
	 * @param other - The Link to which this link connects
	 */
	public void addConnection(Link other) {
		outboundConnections.add(other);
	}

	/**
	 * @return the word associated with this link.
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @return a random link from the outbound connections of this link
	 */
	public Link getRandom() {
		ArrayList<Link> links = new ArrayList<>(outboundConnections);
		return links.get((new Random()).nextInt(links.size()));
	}
}
