package generator;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.ZonedDateTime;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
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
	 * Used to build the poem string
	 */
	private StringBuilder sb;
	/**
	 * References the Reader class
	 */
	private Reader reader;
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
		Window gui = new Window();

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
		this.setResizable(false);;
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

		// Instantiates and assigns a button that, when pressed, will begin the 
		// process of reading and analyzing the source file, then instantiating the
		// process of generating a new, random haiku from the file
		generate = new JButton();
		generate.setText("Generate");
		generate.setBounds(x, y, 140, 20);
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
		// TODO: Implement a JFileChooser dialog box, such that the source file can be retrieved
	}

	/**
	 * When the generate button is clicked, this method executes to ensure all
	 * prerequisites are met
	 */
	private void generateClicked() {
		// TODO: Implement reader class such that it will read the source file
	}

	/**
	 * When the language in the combobox is changed, this method reflects that
	 * change to the necessary classes
	 * 
	 * @param newLang
	 */
	private void languageChanged(String newLang) {
		// TODO:  Implement language change with syllableGetter object
	}
}
