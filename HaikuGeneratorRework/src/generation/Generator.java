package generation;

import java.io.IOException;

import javax.swing.JOptionPane;


/**
 * Handles the generation of phrases
 * 
 * @author wades39 (Kyler McMullin)
 * @version April 28, 2021
 */
public class Generator {

	private static Chain ch;
	private static Dictionary dict;
	private static Link link;

	/**
	 * Generates based off of the passed in chain
	 * 
	 * @param c - chain to use
	 * @return generated poem
	 * @throws IOException
	 */
	public static String generate(Chain c, Dictionary d) throws IOException {
		ch = c;
		dict = d;
		StringBuilder sb = new StringBuilder();
		link = ch.getRandom();
		
		int shortLen = 5, longLen = 7;
		
		sb.append(line(shortLen, true) + "\n");
		sb.append(line(longLen, false) + "\n");
		sb.append(line(shortLen, false) + "\n");

		return sb.toString();
	}

	/**
	 * Generates a line of the specified number of syllables
	 * 
	 * @param numSyllables - Number of syllables to have in the line
	 * @param newClause    - Whether or not this line is a new clause
	 * @return a line of the specified syllable count
	 * @throws IOException when there is an issue retrieving the number of syllables
	 *                     in a given word
	 */
	private static String line(int numSyllables, boolean newClause) throws IOException {
		int syls = 0;
		StringBuilder sb = new StringBuilder();
		String term = "";

		long timeoutVal = 5000; // set a timeout for 5 s

		long start = System.currentTimeMillis();
		while (syls < numSyllables) {

			// check to see whether or not to time out the generation
			if (System.currentTimeMillis() - start >= timeoutVal) {
				JOptionPane.showMessageDialog(null, "The generator timed out, please try again!", "Timeout",
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			Link newLink = link.getRandom();
			term = newLink.getWord();

			// if this line is a new clause
			if (newClause && syls == 0) {
				Word w = dict.getWord(term);
				if (w != null && !(w.hasRole(
						("n. —attrib. —n. —predic. —vs. —n.pl. —demons. —contr. —pron. —conj. —int. —prep. —interrog. —v.aux. —v. —adj. —adv.")
								.split(" ")))) {

					continue;
				}
			}

			int ctSyls = syllableGetter(term);

			if (syls + ctSyls > numSyllables) {
				continue;
			} else {
				sb.append(term + " ");
				syls += ctSyls;
				link = newLink;
			}
		}

		return sb.toString();
	}

	/**
	 * Generates an estimate on how many syllables are in a word.
	 * 
	 * @param word - Word whose syllables to count
	 * @return an estimate of how many syllables are in the input word.
	 * @throws IOException
	 */
	private static int syllableGetter(String word) throws IOException {
		return SyllableCounter.CountSyllables(word + " ");
	}
}
