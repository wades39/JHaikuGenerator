package haikuGenerator;

import java.io.IOException;

/**
 * This object class will handle the generation of a random haiku poem from an
 * analyzed source file.
 * 
 * @author Kyler McMullin
 *
 */
public class Generator {

	/**
	 * References a set of all known words in the source text
	 */
	private WordSet allWords;

	/**
	 * Used to build each line of the poem word by word
	 */
	private StringBuilder sBuilder;

	/**
	 * Contains the finalized poem that the generator has generated.
	 */
	private String poem;

	/**
	 * Stores the last word used
	 */
	private String lastWord;

	/**
	 * Indicates whether or not the poem will be generated from the last word on the
	 * previous line. (I.E. if the last word of line 1 was "dog," the next line
	 * would start with a word that is known to follow "dog" in the source text)
	 */
	private boolean continuous;

	/**
	 * References the syllableGetter class
	 */
	private SyllableGetter syl;

	/**
	 * References the type of generation to use
	 */
	private String gent;

	/**
	 * References the language the generator will use
	 */
	private String language;
	/**
	 * Indicates how many syllables have been generated so far
	 */
	public int generatedSyls;

	/**
	 * Constructs the Generator object with the necessary variables.
	 * 
	 * @param allWords:   the set of all words contained in the text
	 * @param language:   the language in which the words will be interpreted
	 * @param type:       the type of generation that the user wishes to utilize
	 * @param continuous: whether or not the program should generate from the last
	 *                    word of the previous line
	 */
	public Generator(WordSet allWords, String language, String type, boolean continuous) {
		this.allWords = allWords;
		this.continuous = continuous;
		this.language = language;
		gent = type;
		syl = new SyllableGetter(language);
		lastWord = "";
		generatedSyls = 0;
	}

	/**
	 * Enacts the process of generating a poem.
	 * 
	 * @throws IOException
	 */
	public void generate() throws IOException {
		sBuilder = new StringBuilder();

		sBuilder.append(genLine(5) + "\n");
		sBuilder.append(genLine(7) + "\n");
		sBuilder.append(genLine(5));

		poem = sBuilder.toString();
	}

	/**
	 * Generates and returns a line of the poem, based on an integer variable which
	 * dictates how many syllables the line should contain.
	 * 
	 * @param syllables: the number of syllables the line should contain
	 * @return line
	 * @throws IOException
	 */
	private String genLine(int syllables) throws IOException {
		int ct = 0;
		StringBuilder ln = new StringBuilder();

		if (continuous && lastWord.isBlank()) {
			Word w = allWords.getRandom();
			while (!w.hasFollower()) {
				w = allWords.getRandom();
			}
			lastWord = w.toString();
			ln.append(lastWord);
			ct += syl.getSyllables(lastWord);
			generatedSyls += syl.getSyllables(lastWord);
		} else if (continuous && !lastWord.isBlank()) {
			Word w = allWords.get(lastWord);
			while (!w.hasFollower()) {
				w = allWords.getRandom();
			}
			w = w.getRandomFollower();
			lastWord = w.toString();
			ln.append(lastWord);
			ct += syl.getSyllables(lastWord);
			generatedSyls += syl.getSyllables(lastWord);
		} else if (lastWord.isBlank()) {
			lastWord = allWords.getRandom().toString();
			ln.append(lastWord);
			ct += syl.getSyllables(lastWord);
			generatedSyls += syl.getSyllables(lastWord);
		}

		// until ct = syllables, try to pick preferred type, otherwise pick random
		while (ct < syllables) {
			switch (gent) {
			case "Most Frequent":
				Word wo = allWords.get(lastWord).getTopFollower();
				while (!wo.hasFollower()) {
					wo = allWords.getRandom();
				}
				String word = wo.toString();
				if (ct + syl.getSyllables(word) <= syllables) {
					ln.append(" " + word);
				} else {
					boolean LOE = false;
					while (!LOE) {
						word = allWords.get(lastWord).getRandomFollower().toString();
						if (ct + syl.getSyllables(word) <= syllables) {
							ln.append(" " + word);
							LOE = true;
						}
					}
				}
				ct += syl.getSyllables(word);
				generatedSyls += syl.getSyllables(word);
				lastWord = word;
				break;
			case "Median":
				wo = allWords.get(lastWord).getTopFollower();
				while (!wo.hasFollower()) {
					wo = allWords.getRandom();
				}
				word = wo.toString();
				if (ct + syl.getSyllables(word) <= syllables) {
					ln.append(" " + word);
				} else {
					boolean LOE = false;
					while (!LOE) {
						word = allWords.get(lastWord).getRandomFollower().toString();
						if (ct + syl.getSyllables(word) <= syllables) {
							ln.append(" " + word);
							LOE = true;
						}
					}
				}
				ct += syl.getSyllables(word);
				generatedSyls += syl.getSyllables(word);
				lastWord = word;
				break;
			case "Least Frequent":
				wo = allWords.get(lastWord).getTopFollower();
				while (!wo.hasFollower()) {
					wo = allWords.getRandom();
				}
				word = wo.toString();
				if (ct + syl.getSyllables(word) <= syllables) {
					ln.append(" " + word);
				} else {
					boolean LOE = false;
					while (!LOE) {
						word = allWords.get(lastWord).getRandomFollower().toString();
						if (ct + syl.getSyllables(word) <= syllables) {
							ln.append(" " + word);
							LOE = true;
						}
					}
				}
				ct += syl.getSyllables(word);
				generatedSyls += syl.getSyllables(word);
				lastWord = word;
				break;
			case "Random":
				wo = allWords.get(lastWord).getTopFollower();
				while (!wo.hasFollower()) {
					wo = allWords.getRandom();
				}
				word = wo.toString();
				if (ct + syl.getSyllables(word) <= syllables) {
					ln.append(" " + word);
				} else {
					boolean LOE = false;
					while (!LOE) {
						word = allWords.get(lastWord).getRandomFollower().toString();
						if (ct + syl.getSyllables(word) <= syllables) {
							ln.append(" " + word);
							LOE = true;
						}
					}
				}
				ct += syl.getSyllables(word);
				generatedSyls += syl.getSyllables(word);
				lastWord = word;
				break;
			default:
				throw new IllegalArgumentException();
			}
		}

		if (!continuous) {
			lastWord = ""; // resets last word
		}

		return ln.toString();
	}

	/**
	 * Returns the poem last generated by the generator object.
	 * 
	 * @return poem
	 */
	public String getPoem() {
		return poem;
	}

	/**
	 * Changes the language that the generator is using
	 * 
	 * @param language
	 */
	public void changeLanguage(String language) {
		this.language = language;
		syl.changeLanguage(this.language);
	}

}