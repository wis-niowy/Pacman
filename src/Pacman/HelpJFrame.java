package Pacman;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**Klasa, której zadaniem jest zczytywanie z pliku instrukcji do gry i przekierowanie jej do ramki*/
public class HelpJFrame extends JFrame{
	
	
	public HelpJFrame() throws FileNotFoundException{
		super("Pomoc i zasady gry");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,500);
		setLocation(400,200);
		
		FlowLayout one = new FlowLayout();
		setLayout(one);
			one.setVgap(20);
			one.setHgap(160);
	      File file = new File("plik.txt");
	      Scanner in = new Scanner(file);
	      while (in.hasNextLine())
	      {
		      String instrukcja = in.nextLine();
		      JLabel text1=new JLabel(instrukcja);
			      add(text1); 
			      add(new JLabel(new String("\n")));
	      }
	      in.close();
		      setVisible(true);
	}
	
}
