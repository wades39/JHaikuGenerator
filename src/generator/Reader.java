package generator;

import java.io.File;

/**
 * This object class will perform the reading of the source file and interfacing
 * with other object classes, in order to facilitate the reading and analysis of
 * the source file, which will then be parsed into a haiku poem by another,
 * different object class.
 * 
 * @author Kyler McMullin
 *
 */
public class Reader {

	// TODO: Implement necessary instance variables and representation scheme

	/**
	 * References the file the reader will read
	 */
	private File source;
	/**
	 * Indicates the language in which the reader will process the source file
	 */
	private String language;

	public Reader(File source, String language) {
		this.source = source;
		this.language = language;
	}

}
