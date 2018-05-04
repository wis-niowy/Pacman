package Pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Random;


public class Token extends Rectangle {
	/**Konstruktor z losowym ustawianiem punkcika */
	public Token(int width, int height, List<Rectangle2D> wallList)
	{
		Random randGen = new Random();
		this.x = randGen.nextInt(width - 10);
		this.y = randGen.nextInt(height - 10);
		this.width = 10;
		this.height = 10;
		while (CheckTokenForCollision(wallList) == false)
		{
			this.x = randGen.nextInt(width - 10);
			this.y = randGen.nextInt(height - 10);
		}
	}
	/**metoda rysujaca obiekt*/
	public void DrawToken(Graphics g)
	{
		setBounds(x,y,10,10);
		g.setColor(Color.orange); 
		g.fillOval(x,y,width,height);
	}
	/**metoda kontrolujaca by obiekt nie rysowal sie na scianie */
	private boolean CheckTokenForCollision(List<Rectangle2D> wallList)
	// sprawdzamy czy wylosowana pozycja nie trafia w sciane
	{
		boolean correctCoordinates = true;
		for (int i = 0; i < wallList.size(); ++i)
		// przegladamy wszystkie sciany w celu sprawdzenia, czy nastapi kolizja
		{
			Rectangle2D wall = wallList.get(i);
			if (this.intersects(wall))	// jezeli token trafia w sciane - zwracamy false (losujemy jeszcze raz)
			{
				correctCoordinates = false;
				break;
			}
		}
		return correctCoordinates;
	}
	
}
