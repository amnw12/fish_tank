/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishTank;

/**
 *
 * @author amnwaqar
 */

import java.awt.*;
import javax.swing.*;

public class FishTank extends JPanel{
    public final int PANEL_WIDTH = 500; public final int PANEL_HEIGHT = 500;
    private DrawingPanel drawPanel;
    public JButton addButton;
    private Timer timer;
    Fish fish;
    FishShoal shoal;

    public FishTank() {
        super(new BorderLayout()); 
        shoal = new FishShoal();
        
        drawPanel = new DrawingPanel();     //create DrawingPanel and add to center
        add(drawPanel, BorderLayout.CENTER);
        
        addButton = new JButton("Add Fish");
        addButton.addActionListener(action -> addFish());
        add(addButton, BorderLayout.SOUTH);
        
        timer = new Timer(8, execute ->
            {
                this.drawPanel.repaint();
            });
        timer.start();
    }
    
    private class DrawingPanel extends JPanel
    {   public DrawingPanel()
        {   setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            setBackground(Color.WHITE);
        }
    
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
           
            shoal.drawShoal(g);
        }
    }
    
    public static void main(String[] args) {
        FishTank myPanel = new FishTank();
        JFrame frame = new JFrame("Fish Tank"); //create frame to hold our JPanel subclass	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().add(myPanel);  //add instance of MyGUI to the frame
        frame.pack(); //resize frame to fit our Jpanel
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(new Point((d.width / 2) - (frame.getWidth() / 2), (d.height / 2) - (frame.getHeight() / 2)));
	//show the frame	
        frame.setVisible(true);
    }
    
    public void addFish()
    {
        fish = new Fish(shoal);
        shoal.add(fish);
        this.drawPanel.repaint();
    }
    
}
