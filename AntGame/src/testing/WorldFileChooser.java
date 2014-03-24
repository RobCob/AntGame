package testing;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import model.Tile;
import model.WorldReader;

public class WorldFileChooser extends JPanel implements ActionListener {
	
    JButton openButton;
    File file;
    JFileChooser fc;

    public WorldFileChooser() {
        super(new BorderLayout());
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create a file chooser
        fc = new JFileChooser();
        
        openButton = new JButton("Upload World File");
        openButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);

        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        
        //Add content to the window.
        frame.add(buttonPanel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(WorldFileChooser.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                this.parseFile(); //parse the selected file
            }
        }
    }
    
    public void parseFile(){
    	WorldReader wr = new WorldReader();
    	String map = wr.readFromFile(file.getAbsolutePath());
    	System.out.println(map); //Debug
    	Tile[] tiles = wr.createStateList(map);
		boolean correct = wr.checkWorldSemantics(tiles);
		System.out.println("Correct : " + correct);
		Tile[] tmp = wr.removeLineSeparators(tiles);
    }
    
    public static void main(String[] args) {
    	new WorldFileChooser();
    }
}
