package haikuGenerator;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The window object class describes the GUI for the JHaikuGenerator program.
 * 
 * @author Kyler McMullin
 *
 */
public class Window extends javax.swing.JFrame {

	/**
	 * Automatically generated version ID (Eclipse kept yelling at me to include
	 * one)
	 */
	private static final long serialVersionUID = 8331994393360335268L;
	/**
	 * Button used to call to open a file browser
	 */
	private JButton browse;
	/**
	 * Button used to call to generate a poem
	 */
	private JButton generate;
	/**
	 * Indicates progress in generating/analyzing file
	 */
	private JProgressBar progress;
	/**
	 * Area which the resultant poem is written to
	 */
	private JTextArea result;
	/**
	 * Area where the file path of the source file is displayed
	 */
	private JTextPane fileLoc;
	/**
	 * comboBox containing supported languages
	 */
	private JComboBox<String> lang;
	/**
	 * References the frame's content pane
	 */
	private Container cp;
	/**
	 * References the source file
	 */
	private File source;
	/**
	 * References the selected language
	 */
	private String language;
	/**
	 * References the Reader class
	 */
	private Reader reader;
	/**
	 * References the Generator class
	 */
	private Generator gen;
	/**
	 * Allows the user to select the type of generation they wish to use
	 */
	private JComboBox<String> genType;
	/**
	 * Allows the user to dictate whether or not the generation will be continuous
	 */
	private JRadioButton isCont;
	/**
	 * Used to write to the log file
	 */
	private static BufferedWriter bw;
	/**
	 * References the log file
	 */
	private static File logFile;
	/**
	 * Lists supported languages
	 */
	private String[] supported;

	public static void main(String[] args) {

		// checks to see if error log file exists, and if not, it is created
		logFile = new File("./logs/errorLog.txt");
		try {
			try {
				// logFile.mkdirs();
				logFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
			bw = new BufferedWriter(new FileWriter(logFile));
			bw.write("JHaikuGenerator Error Log\n" + "-------------------------\n" + "Created:  " + ZonedDateTime.now()
					+ "\n" + "-------------------------\n");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// creates a new gui
		new Window();

		// TODO: Implement code to write to the error log

	}

	/**
	 * Constructs the graphical window for user interaction
	 */
	public Window() {
		initWindow();
		this.setVisible(true);
	}

	/**
	 * Initializes and places all components within the window
	 */
	public void initWindow() {

		// Sets the LookAndFeel of the GUI to that of the system operation the program
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		// Basic GUI setup (title, closing action, etc.)
		cp = this.getContentPane();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("JHaikuGenerator");
		this.setLayout(null);
		this.setResizable(false);
		;
		// TODO: Design an icon for the program
		// TODO: Implement code to establish the icon of the program

		// Used to keep track of the positioning of elements of the GUI
		int x = 15, y = 15, widest, deepest;

		// Instantiates and assigns a text box for the location of the source file
		// to be displayed
		fileLoc = new JTextPane();
		fileLoc.setBorder(BorderFactory.createLineBorder(Color.gray));
		fileLoc.setEditable(false);
		fileLoc.setForeground(Color.gray);
		fileLoc.setText(" Press browse to select file...");
		fileLoc.setBounds(x, y, 200, 20);
		x += 205;

		// Instantiates and assigns a button that, when pressed, will display
		// a file chooser dialog, where the user will select their source file
		browse = new JButton();
		browse.setText("Browse");
		browse.setBounds(x, y, 80, 20);
		widest = x + 95;
		x = 15;
		y += 25;

		// Instantiates and assigns a drop down option box from which the user can
		// select the language that they want their source file to be analyzed as
		lang = new JComboBox<String>();
		popLangsBox();
		lang.setSelectedIndex(0);
		lang.setBounds(x, y, 140, 20);
		x += 145;

		// Instantiates and assigns a drop down menu from which the user can select the
		// type of generation they wish to employ
		genType = new JComboBox<String>();
		genType.addItem("Most Frequent");
		genType.addItem("Median");
		genType.addItem("Least Frequent");
		genType.addItem("Random");
		genType.setSelectedIndex(0);
		genType.setBounds(x, y, 140, 20);
		x = 15;
		y += 25;

		// Instantiates and assigns a radio button that allows the user to dictate
		// whether or not they wish to generate continuous text (I.E. all words have a
		// parent)
		isCont = new JRadioButton();
		isCont.setText("Generate continously?");
		isCont.setSelected(false);
		isCont.setBounds(x, y, 285, 20);
		y += 25;

		// Instantiates and assigns a button that, when pressed, will begin the
		// process of reading and analyzing the source file, then instantiating the
		// process of generating a new, random haiku from the file
		generate = new JButton();
		generate.setText("Generate");
		generate.setBounds(x, y, 285, 20);
		x = 15;
		y += 25;

		// Instantiates and assigns a progress bar which will display to the user
		// the approximate progress of the generation of their haiku
		progress = new JProgressBar();
		progress.setMinimum(0);
		progress.setMaximum(Integer.MAX_VALUE);
		progress.setValue(0);
		progress.setForeground(Color.blue);
		progress.setBackground(Color.lightGray);
		progress.setBounds(x, y, widest - 30, 15);
		y += 20;

		// Instantiates and assigns a text box in which the generated haiku
		// will be displayed to the user
		result = new JTextArea();
		result.setBorder(BorderFactory.createLineBorder(Color.gray));
		result.setEditable(false);
		result.setForeground(Color.gray);
		result.setFont(new Font("Arial", 12, 12));
		result.setText(" Generated poem will be displayed here...");
		result.setBounds(x, y, widest - 30, 75);
		deepest = y + 90;

		// when browse button clicked, execute browseClicked method
		browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				browseClicked();
			}

		});

		// when generate button clicked, execute generateClicked method
		generate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				generateClicked();
			}

		});

		// when the value of lang is changed, execute languageChanged method
		lang.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (supported[lang.getSelectedIndex()].equals(language)) {
					languageChanged(supported[lang.getSelectedIndex()]);
				}
			}

		});

		// adds the components to the GUI window
		cp.add(browse);
		cp.add(fileLoc);
		cp.add(generate);
		cp.add(lang);
		cp.add(progress);
		cp.add(result);
		cp.add(isCont);
		cp.add(genType);

		// sets the window size to appropriately display all of the components
		cp.setPreferredSize(new Dimension(widest, deepest));

		// finalizes the layout and contents of the GUI (the components' contents
		// can still change, regardless of this fact (i.e. text boxes' text can
		// still change))
		this.pack();
	}

	/**
	 * Populates the lang comboBox with languages that are currently supported by
	 * the program
	 */
	private void popLangsBox() {
		supported = new String[] { "English" };
		for (String langu : supported) {
			lang.addItem(langu);
		}
	}

	/**
	 * Opens a dialog for the user to select a file when the browse button is
	 * clicked
	 */
	private void browseClicked() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.showOpenDialog(this);
		source = fileChooser.getSelectedFile();
		fileLoc.setText(source.getAbsolutePath());
	}

	/**
	 * When the generate button is clicked, this method executes to ensure all
	 * prerequisites are met
	 */
	private void generateClicked() {
		try {
			reader = new Reader(source);
			reader.read();
			gen = new Generator(reader.getAllWords(), String.valueOf(lang.getSelectedItem()), String.valueOf(genType.getSelectedItem()), isCont.isSelected());
			gen.generate();
			result.setText(gen.getPoem());
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "The file you have selected either doesn't exist or cannot be read.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (NullPointerException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,  "You must select a file in order to continue.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,  "An error occurred while attempting to generate the poem.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * When the language in the combobox is changed, this method reflects that
	 * change to the necessary classes
	 * 
	 * @param newLang
	 */
	private void languageChanged(String newLang) {
		gen.changeLanguage(newLang);
	}
}
