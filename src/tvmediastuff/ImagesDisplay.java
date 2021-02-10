/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvmediastuff;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.Timer;

/**
 *
 * @author aryaan
 */
public class ImagesDisplay extends javax.swing.JPanel {
    // Initialize all necessary variables
    Timer timer;
    int index = 0;
    Image img;
    File dir = new File(getDirFilePath());
    // Lists all the files in the directory
    File[] filesList = dir.listFiles();
    String[] filesListString;
    int WIDTH = 0;
    int HEIGHT = 0;
    
    

    /**
     * Creates new form NewJPanel. Constructor for this images display panel.
     */
    public ImagesDisplay() {
        initComponents();
        
        // Checks if the directory exists and creates it if it doesn't
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        // Put files in string array and sort in alphabetical order
        filesListString = new String[filesList.length];
        for (int i = 0; i < filesList.length; i++) {
           filesListString[i] = filesList[i].getName();
        }
        Arrays.sort(filesListString);
        
        // On mac a .DS_Store file is created which is removed
        if (filesListString[0].equals(".DS_Store")) {
            filesListString = removeDSS(filesListString);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Initialize the dimensions of the width and height of the panel
        this.HEIGHT = this.getHeight();
        this.WIDTH = this.getWidth();
        // Define img variable and find the path to the images that the user want to show
        img = Toolkit.getDefaultToolkit().getImage(dir.getAbsolutePath() + "/" + filesListString[index]);
        // Draw image
        g.drawImage(img, 0, 0, WIDTH, HEIGHT, this);
    }
    
    /**
     * Returns the file path from the config file. Line 2.
     * 
     * @return String
     */
    public static String getDirFilePath() {
        //Config file path
        File config = new File("config.txt");
        // If it dodesnt exist, create it
        if (!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException ex) {
                System.out.println("An error occured with the config file. Try again.");
            }
        }
        String line = "";
        Scanner scan;
        // Find the path name given by the user in the config file
        try {
            scan = new Scanner(config);
            for (int i = 0; i < 2; i ++) {
                line = scan.nextLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("An error occured with finding the config flie. Try again.");
        }
        return line;
    }
    
    /**
     * Checks to see if the index is our of the range of the filesListString list. If it is, redefine
     * index value to 0.
     */
    public void checkImagesVideos() {
        // If index is equal to filesList length, restart counter
        if (index == filesListString.length) {
            index = 0;
        }
    }
    
    /**
     * Initialize timer with 6000ms delay with TimerListener as action tracker. Start timer.
     */
    public void anim() {
        //set a timer for animation
        timer = new Timer(6000, new TimerListener());
        timer.start();
    }
    
    /**
     * Removes the first element of the inputed string array.
     * 
     * @param filesListString String[]
     * @return String[]
     */
    public String[] removeDSS(String[] filesListString) {
        // Creates new string with all the same values but removes first element
        String[] anotherArray = new String[filesListString.length-1];
        for (int i = 1; i < anotherArray.length + 1; i ++) {
            anotherArray[i-1] = filesListString[i];
        }
        return anotherArray;
    }
    
    // Define timer param listener
    private class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Next index
            index++;
            
            // Check and make sure index is in range
            checkImagesVideos();

            //update window
            repaint();
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
