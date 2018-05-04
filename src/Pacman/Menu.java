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
import java.io.FileNotFoundException;

public class Menu extends JFrame implements ActionListener {

	/**Zmienna przechowywujaca przycisk start*/
	
	private JButton start;
	/**Zmienna przechowywujaca przycisk level1*/
	
	private JButton level1;
	/**Zmienna przechowywujaca przycisk level2*/
	
	private JButton level2;
	/**Zmienna przechowywujaca przycisk help*/
	
	private JButton help;
	/**Zmienna przechowywujaca przycisk score*/
	
	private JButton score;
	
	/**zmienna przechowujaca okienko gry */
	private MazeJFrame gameFrame;
	
	/**Zmienna przechowujaca aktualny wynik */
	private int currentScore;
	
	/**Zmienna przechowujaca poziom gry */
	private int currentGameLevel;
	
	/**interfejs sluzacy do zglaszania/obslugi zdarzenia - zakonczenia poziomu */
	public interface ExitLevelEvent
	{
		public void eventAction(int mode, int score);
	}
	/**klasa implementujaca interfejs - obsluguje ona zdarzenie zakonczenia poziomu gry
	 * definicja metody eventAction mowi w jaki sposob program obsluzy zdarzenie zgloszone w klasie MazeJpanel
	 *  */
	public class ExitLevelListener implements ExitLevelEvent
	{
		public void eventAction(int mode, int score)
		// mode == 0 -- nastepna runda
		// mode == 1 -- game over
		{
			switch (mode)
			{
			case 0:
				currentScore += score;
				gameFrame.setVisible(false);	// zamykamy okno z poprzednim poziomem
				gameFrame = new MazeJFrame(currentScore);
				gameFrame.InitializeJPanel(++currentGameLevel, this);
				break;
			case 1:
				currentScore += score;
				gameFrame.setVisible(false);	// zamykamy okno z poprzednim poziomem
				gameFrame = null;
				break;
			}
		}
	}
	
	/** Konstruktor</br> 
	 * funkcja, ktora jest odpowiedzialna za rysowanie okienka Menu i stylizacje przyciskow
	 */
	public Menu() {
	
		super( "Pacman" );
					currentGameLevel = 1;
					currentScore = 0;
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					setSize(400, 800);
					setLocation(100,200);
					getContentPane().setBackground(Color.BLACK);
					FlowLayout one = new FlowLayout();
				setLayout(one);
					one.setVgap(20);
					one.setHgap(160);
				JLabel text=new JLabel("Pacman");
				  text.setForeground(Color.red);
				  text.setBackground(Color.black);
			      text.setOpaque(true);
		
								      
			        text.setFont(new Font("Cooper Black", 10, 80));
				    start = new JButton("Start");
				    level1 = new JButton("Level 1");
				    level2= new JButton("Level 2");
				    help = new JButton("Instrukcja");
				    score= new JButton("Najlepsze wyniki");
					
					
					start.setForeground(Color.green);
					level1.setForeground(Color.green);
					level2.setForeground(Color.green);
					help.setForeground(Color.green);
					score.setForeground(Color.green);
					start.setBackground(Color.BLACK);
					level1.setBackground(Color.BLACK);
					level2.setBackground(Color.BLACK);
					help.setBackground(Color.BLACK);
					score.setBackground(Color.BLACK);
				    start.setFont(new Font("Arial", 8, 20));
				    level1.setFont(new Font("Arial", 8,20));
				    level2.setFont(new Font("Arial", 8,20));
				    help.setFont(new Font("Arial", 8,20));
				    score.setFont(new Font("Arial", 8,20));

					 
				    start.addActionListener(this);
					level1.addActionListener(this);
					level2.addActionListener(this);
					help.addActionListener(this);
					score.addActionListener(this);
				    
					add(text); 
					add(start);
					add(level1);
					add(level2);
					add(help);
					add(score);
										
					setVisible(true);
		} // Menu
					//obsluga zdarzen z menu
					public void actionPerformed(ActionEvent e) {
						Object source = e.getSource();
						
						if(source == start)
						{
							gameFrame = new MazeJFrame(currentScore);
							
							gameFrame.InitializeJPanel(1, new ExitLevelListener());
						}
						
						if (source == help)
							try {
								new HelpJFrame();
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
							
						if(source == level1)
							new MazeJFrame(1, new ExitLevelListener());
						
						if(source == level2)
							new MazeJFrame(2, new ExitLevelListener());
							
					}	
					
		/**EventQueue.invokeLater(new Runnable() powoduje, ¿e kod, który jest umieszczony w metodzie run zostanie wykonany na pewno przez w¹tek uruchomiony przez klasy Swing - konkretniej przez w¹tek s³u¿¹cy do obs³ugi interfejsu graficznego.*/
		public static void Rysuj(){
			//EventQueue.invokeLater(new Runnable() powoduje, ¿e kod, który jest umieszczony w metodzie run zostanie wykonany na pewno przez w¹tek uruchomiony przez klasy Swing - konkretniej przez w¹tek s³u¿¹cy do obs³ugi interfejsu graficznego.
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					new Menu();
				}		
					
				
		}
			);
		}
		
	
};
