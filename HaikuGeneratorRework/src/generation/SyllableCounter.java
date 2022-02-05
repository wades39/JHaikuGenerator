package generation;

/**
 * This class serves to approximate the number of syllables in a given String.
 * 
 * This class was written in order to remove all references to external
 * libraries, whose licenses I am unable to locate.
 * 
 * @author wades39 (Kyler McMullin)
 * @version Feb 5, 2022
 */
public class SyllableCounter {
	/**
	 * Returns the approximate number of syllables in the given string.
	 * @param str - Input string
	 * @return The approximate number of syllables in the string.
	 */
	public static int CountSyllables(String str) {
		
		/*
		 * Regex made by:  Yassin Hajaj
		 * 
		 * Source:  https://stackoverflow.com/a/33469085/10150533
		 */
		
		return (str.split("[aeiouy]+[^$e(,.:;!?)]")).length;
	}
}
