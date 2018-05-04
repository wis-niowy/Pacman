package Pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
/**Klasa, która tworzy bohatera*/
public class Player extends Rectangle{
	
	/**konstruktor*/
	public Player(int _x, int _y){
		this.x = _x;
		this.y = _y;
	}
	/**metoda przesuwajaca wspolrzedne obiektu wg przekazanych wspolrzednych kierunku*/
	public void MovePlayer(int dX, int dY, int width, int height, List<Rectangle2D> wallList)
	{
		Point direction = ValidatePlayerCoordinates(new Point(dX, dY), width, height);
		direction = CheckPlayerForCollision(direction, wallList);
		this.setLocation(this.x + direction.x, this.y + direction.y);
	}
	/**metoda rysujaca obiekt*/
	public void DrawPlayer(Graphics g)
	{
		setBounds(x,y,15,15);
		g.setColor(Color.yellow); 
		g.fillRect(x,y,width,height);
	}
	/**metoda kontrolujaca by obiekt miescil sie wewnatrz ramki */
	private Point ValidatePlayerCoordinates(Point movement, int width, int height)
	// kontroluje czy pacman nie ucieka poza okno
	// jezeli dany ruch wykroczy poza - zatrzymujemy pacman'a
	{
		Point validatedMovement = new Point(movement);
		if( this.x + movement.x < 0 || this.x + movement.x > width - this.width){
			validatedMovement.setLocation(0, 0);
		}
		else if( this.y + movement.y < 0 || this.y + movement.y > height - this.height){
			validatedMovement.setLocation(0, 0);
		}
		
		return validatedMovement;
	}
	/**metoda kontrolujaca by obiekt nie wjezdzal na sciane - wykrywanie kolizji */
	private Point CheckPlayerForCollision(Point movement, List<Rectangle2D> wallList)
	{
		if (movement == new Point(0, 0))
			return movement;
		Point validatedMovement = new Point(movement);
		Rectangle2D futurePlayerPosition = new Rectangle(this.x + movement.x, this.y + movement.y, this.width, this.height);
		boolean collisionDetected = false;
		for (int i = 0; i < wallList.size(); ++i)
		// przegladamy wszystkie sciany w celu sprawdzenia, czy nastapi kolizja
		{
			Rectangle2D wall = wallList.get(i);
			if (futurePlayerPosition.intersects(wall))	// jezeli po przesunieciu ghost'a bedzie kolizja - przerywamy
			{
				collisionDetected = true;
				break;
			}
		}
		if (collisionDetected) // wykryto kolizje - zmieniamy kierunek na przciwny
		{
			validatedMovement.setLocation(0, 0);
		}
		return validatedMovement;
	}
}
