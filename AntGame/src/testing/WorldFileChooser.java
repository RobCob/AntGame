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
    JTextArea info; 
    File file;
    JFileChooser fc;
    Tile[] tiles; //Used so that other elements in the game can have access to the uploaded worldFile
    boolean correct; //keeps track of whether the uploaded file is a correct one

    public WorldFileChooser() {
        super(new BorderLayout());
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create a file chooser
        fc = new JFileChooser();
        
        
        //Create the Upload file button
        openButton = new JButton("Upload World File");
        openButton.addActionListener(this);
        
        //Create the text field to display information to the user
        info = new JTextArea(5,20);
        info.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(info);
        

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(info);

        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
        
        //Add content to the window.
        frame.add(buttonPanel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Used so that other elements in the game can have access to the uploaded worldFile
     */
    public Tile[] getTiles(){
    	return tiles;
    }

    public void actionPerformed(ActionEvent e) {

        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(WorldFileChooser.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                this.parseFile(); //parse the selected file
                if(correct){
                	info.setText("The uploaded world file is correct");
                }
                else{
                	info.setText("The uploaded world file is incorrect");
                }
            }
        }
    }
    
    public void parseFile(){
    	WorldReader wr = new WorldReader();
    	String map = wr.readFromFile(file.getAbsolutePath());
    	tiles = wr.createStateList(map);
		correct = wr.checkWorldSemantics(tiles);
		tiles = wr.removeLineSeparators(tiles);
    }
    
    public static void main(String[] args) {
    	new WorldFileChooser();
    }
}
