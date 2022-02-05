package generation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.TreeSet;

/**
 * Represents a dictionary
 * 
 * @author wades39 (Kyler McMullin)
 * @version May 17, 2021
 */
public class Dictionary implements Serializable {

	/**
	 * Generated serial version UID
	 * 
	 * I implemented Serializable so that I can save an existing dictionary to a
	 * file and parse it later.
	 */
	private static final long serialVersionUID = -4630408472876562172L;

	/**
	 * Binary search tree with a balance requirement.
	 */
	private TreeSet<Word> tree;

	/**
	 * Instantiates an empty dictionary
	 */
	public Dictionary() {
		tree = new TreeSet<>();
	}

	/**
	 * Instantiates and builds a dictionary based off of a text file.
	 * 
	 * @param filePath - The path to the text dictionary
	 * @throws FileNotFoundException when the file cannot be found
	 */
	public Dictionary(String filePath) throws FileNotFoundException {
		this();

		// Try to build the dictionary from a text file containing words
		try {
			buildFromText(filePath);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Builds the dictionary based off of a file's contents
	 * 
	 * @param filePath - Path to the file to parse
	 * @throws IOException            when there's an error reading the file
	 * @throws ClassNotFoundException when there's an error compiling the dictionary
	 */
	private void buildFromText(String filePath) throws IOException, ClassNotFoundException {

		// Open the text file as a resource so that it can be accessed from within the
		// jar
		InputStream is = Dictionary.class.getResourceAsStream(filePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		// Iterate over the lines of the text file. Parse and add them to the dictionary
		String str;
		while ((str = br.readLine()) != null) {

			String[] arr = str.split("\\s");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < arr.length - 1; i++) {
				sb.append(arr[i] + (arr.length > 2 ? " " : ""));
			}

			Word w = new Word(sb.toString().toLowerCase(), arr[arr.length - 1]);

			// Add the word if it doesn't exist. If it does, update its list of roles.
			if (!tree.contains(w)) {
				tree.add(w);
			} else {
				Word wrd = tree.ceiling(w);
				wrd.addRole(arr[arr.length - 1]);
			}
		}
	}

	/**
	 * Reads a Dictionary object saved to a file and parses it, overwriting this
	 * Dictionary's data with that from the file.
	 * 
	 * @param filePath - File to read from
	 * @throws FileNotFoundException  when the file cannot be found
	 * @throws IOException            when there is an error with reading the file
	 * @throws ClassNotFoundException when there is an error when reconstructing the
	 *                                Dictionary from the file.
	 */
	public void fromFile(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException {
		// This exists to make it work in a jar
		String path = filePath.replace("src/generation/", "");

		// Open the file as a resource so that the file can be used from within the jar
		URL resource = Dictionary.class.getResource(path);

		ObjectInputStream ois = new ObjectInputStream(resource.openStream());

		Dictionary dFromFile = (Dictionary) ois.readObject();

		this.tree = dFromFile.tree;

		ois.close();
	}

	/**
	 * Saves this Dictionary to a file.
	 * 
	 * @param filePath - File path to save to
	 * @throws FileNotFoundException when the file cannot be found
	 * @throws IOException           when there is an error writing to the file
	 */
	public void toFile(String filePath) throws FileNotFoundException, IOException {
		ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(filePath));
		ous.writeObject(this);
		ous.close();
	}

	/**
	 * Gets and returns the word from the dictionary.
	 * 
	 * @param word - Word to return
	 * @return word if found, null otherwise
	 */
	public Word getWord(String word) {
		if (!tree.contains(new Word(word, null))) {
			return null;
		} else {
			return tree.ceiling(new Word(word, null));
		}
	}

}