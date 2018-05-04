package Pacman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**Klasa odpowiadajaca za rysowanie labiryntu w roznych konfiguracjach*/
public class MazeJFrame extends JFrame /*implements KeyListener*/ {
	
	
	/**Zmienna, sluzaca do przechowywania narysowanego obiektu przez kontruktor innej klasy - MazeJpanel*/
	private JPanel panel;
	/** Zmienna przechowujaca obecny poziom gry*/
	private int currentScore;

	public MazeJFrame(int score) {
		super("Pacman");
		currentScore = score;
	}
	public MazeJFrame(int m, Menu.ExitLevelListener listener) {
		super("Pacman");
	    InitializeJPanel(m, listener);
	}
	
	public void InitializeJPanel(int m, Menu.ExitLevelListener listener)
	{
	    panel = new MazeJpanel(currentScore);
	    ((MazeJpanel)panel).InitializeGameLevel(m);
	    ((MazeJpanel)panel).setExitEventListener(listener); 		// ustawiamy listenera zdarzenia
		add(panel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setVisible(true);

	}
		 
}

