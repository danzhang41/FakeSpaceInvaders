package invaders;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Invaders extends JFrame{

public Invaders(){
		
		add(new Board(Color.black));        
        setResizable(false);
        pack();    
        setTitle("Spaceship");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {                
	                JFrame ex = new Invaders();
	                ex.setVisible(true);                
	            }
	        });                
	}
}
