package toBeDeleted;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;

import model.World;
import model.WorldReader;
import model.tile.Tile;

public class WorldFileChooser extends JPanel implements ActionListener {

	JButton openButton;
	JTextArea info;
	JFileChooser fc; // This is the file chooser
	World world; // Used so that other elements in the game can have access to the uploaded worldFile

	public WorldFileChooser() {

		super(new BorderLayout());
		JFrame frame = new JFrame("World File Chooser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create a file chooser
		fc = new JFileChooser();

		// Create the Upload file button
		openButton = new JButton("Upload World File");
		openButton.addActionListener(this);

		// Create the text field to display information to the user
		info = new JTextArea(5, 20);
		info.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(info);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(openButton);
		buttonPanel.add(info);

		// Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
		add(logScrollPane, BorderLayout.CENTER);

		// Add content to the window.
		frame.add(buttonPanel);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Used so that other elements in the game can have access to the uploaded worldFile
	 */
	public World getWorld() {
		return world;
	}

	public void actionPerformed(ActionEvent e) {

		// Handle open button action.
		if (e.getSource() == openButton) {
			int returnVal = fc.showOpenDialog(WorldFileChooser.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				World world = WorldReader.readWorld(file); // parse the selected file
				if (world == null) {
					info.setText("The uploaded world file is incorrect");
				} else {
					info.setText("The uploaded world file is correct");
				}
			}
		}
	}

	public static void main(String[] args) {
		new WorldFileChooser();
	}
}
