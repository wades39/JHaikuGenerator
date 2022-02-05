package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import generation.Chain;
import generation.Dictionary;
import generation.Generator;
import parsing.FileParser;

/**
 * This class serves as the GUI of the program. It shows all of the elements on
 * the screen as well as the information used.
 * 
 * @author wades39 (Kyler McMullin)
 * @version May 17, 2021
 */
public class GUI {

	private JFrame window;
	private JPanel openPanel;
	private JPanel outputPanel;
	private JTextField openFilePath;
	private JTextArea generatorOutput;
	private JButton openBrowse;
	private JButton generate;
	private JButton exportText;
	private JLabel openFileLbl;
	private JLabel outputLbl;

	private Chain chain;

	private Dictionary dict;

	private String oldFilePath;

	private static final String dictPath = "src/generation/rsc/dictionary.dict";

	/**
	 * Creates and shows a GUI for the user to interact with.
	 */
	public GUI() {

		// set look and feel to be the default of the system
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {
		}

		// check to see if dictionary has been compiled.
		File f = new File(dictPath);
		// if it has, load it.
		if (f.exists()) {
			dict = new Dictionary();
			try {
				dict.fromFile(dictPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// otherwise create a new one
		else {
			try {
				dict = new Dictionary("rsc/dict.txt");
				// This only exists to make it work in a jar
				String newPath = dictPath.replace("src/", "");

				dict.toFile(newPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		chain = new Chain();

		// build the frame
		window = new JFrame();
		window.setTitle("Haiku Generator");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));

		// build the open panel
		openPanel = new JPanel();
		openFileLbl = new JLabel("Open file");
		openFilePath = new JTextField();
		openFilePath.setEditable(false);
		openFilePath.setText("C:/users/1234567/documents/document.txt      ");
		openBrowse = new JButton("Browse");
		generate = new JButton("Generate poem");

		openPanel.setLayout(new BorderLayout());
		openPanel.add(openFileLbl, BorderLayout.NORTH);
		openPanel.add(generate, BorderLayout.SOUTH);

		JPanel filePanel = new JPanel();
		filePanel.setLayout(new BorderLayout());
		filePanel.add(openBrowse, BorderLayout.EAST);
		filePanel.add(openFilePath, BorderLayout.CENTER);

		openPanel.add(filePanel, BorderLayout.CENTER);
		openPanel.setBorder(new EmptyBorder(7, 7, 7, 7));
		window.add(openPanel);

		// build the output panel
		outputPanel = new JPanel();
		outputLbl = new JLabel("Output");
		generatorOutput = new JTextArea();
		generatorOutput.setEditable(false);
		generatorOutput.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		generatorOutput.setText("Output text will be sent here.\n\n\n\n");
		exportText = new JButton("Export poem");
		outputPanel.setLayout(new BorderLayout());
		outputPanel.add(outputLbl, BorderLayout.NORTH);
		outputPanel.add(exportText, BorderLayout.SOUTH);
		outputPanel.add(generatorOutput, BorderLayout.CENTER);
		outputPanel.setBorder(new EmptyBorder(7, 7, 7, 7));
		window.add(outputPanel);

		// Add listeners to the buttons

		// when openBrowse is clicked, show a file chooser
		openBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();

				// Add a filter to only allow text files
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				fc.setFileFilter(filter);

				int returnVal = fc.showOpenDialog(window);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					openFilePath.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		// when generate is clicked, try to generate a poem
		generate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!oldFilePath.equals(openFilePath.getText())) {
					FileParser fp;
					try {
						chain = new Chain();
						fp = new FileParser(openFilePath.getText(), chain);
						fp.parse();
						generatorOutput.setText(Generator.generate(chain, dict));
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				} else {
					try {
						generatorOutput.setText(Generator.generate(chain, dict));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				oldFilePath = openFilePath.getText();

			}
		});
		// when exportText is clicked, show a save file chooser
		exportText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();

				// Add a filter to only allow text files
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				fc.setFileFilter(filter);

				int returnVal = fc.showSaveDialog(window);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						savePoemToFile(fc.getSelectedFile().getAbsolutePath());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(window, "There was an error saving your file!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		// pack the window
		window.pack();

		openFilePath.setText("Press browse to select a file.");
		oldFilePath = openFilePath.getText();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	/**
	 * Save a generated text to a file.
	 * 
	 * @param path
	 * @throws IOException when there's an issue with saving
	 */
	private void savePoemToFile(String path) throws IOException {
		File f = new File(path);
		if (!f.exists()) {
			f.createNewFile();
		}
		FileWriter fw = new FileWriter(f);

		fw.write(generatorOutput.getText());

		fw.close();
	}

	/**
	 * Program entry point. Creates and shows a GUI for the user to interact with.
	 * 
	 * @param args - Command line arguments.
	 */
	public static void main(String[] args) {
		new GUI();
	}
}