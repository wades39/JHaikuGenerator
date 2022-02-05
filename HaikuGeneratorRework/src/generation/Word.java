package generation;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a word in a dictionary.
 * 
 * @author wades39 (Kyler McMullin)
 * @version May 17, 2021
 */
public class Word implements Comparable<Word>, Serializable {

	/**
	 * generated serial version uid so that I can write the dictionary to the file
	 */
	private static final long serialVersionUID = 6748239689828118585L;

	/**
	 * The term that this Word represents.
	 */
	private String term;

	/**
	 * Stores the roles this word can play.
	 */
	private Set<String> roles;

	/**
	 * Creates a new instance of a Word
	 * 
	 * @param term - The actual word
	 * @param role - The role it plays in speech
	 */
	public Word(String term, String role) {
		this.term = term;
		roles = new HashSet<String>();
		roles.add(role);
	}

	/**
	 * Adds a role that this Word can play in speech.
	 * 
	 * @param role - The role this word plays
	 * @return true if a change has been made to the Word's roles.
	 */
	public boolean addRole(String role) {
		return roles.add(role);
	}

	/**
	 * Determines whether this Word can play the given role.
	 * 
	 * @param role - The role to search for
	 * @return true if this word can play the given role
	 */
	public boolean hasRole(String role) {
		return roles.contains(role);
	}

	/**
	 * Determines whether this Word can play the given role.
	 * 
	 * @param roleArr - The role to search for
	 * @return true if this word can play the given role
	 */
	public boolean hasRole(String[] roleArr) {
		boolean hasValidRole = false;

		for (String rol : roleArr) {
			hasValidRole = hasValidRole || hasRole(rol);
		}

		return hasValidRole;
	}

	/**
	 * @return the term that this Word refers to.
	 */
	public String term() {
		return term;
	}

	/**
	 * @return a Set containing all of the roles that this Word can play.
	 */
	public Set<String> roles() {
		return roles;
	}

	@Override
	public int compareTo(Word other) {
		return this.term.compareTo(other.term);
	}

}
