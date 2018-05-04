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

/**Klasa, która tworzy przeciwnika*/
public class Ghost extends Rectangle {

	/** zmienna przechowujaca ostatnio wykonany ruch przez obiekt*/
	private Point lastMove;
	
		/**Konstruktor*/
		public Ghost(int _x, int _y){
			this.x = _x;	// x oraz y odziedziczone z rectangle
			this.y = _y;
			lastMove = new Point(0 ,0);
		}
		/** getter ostatniego ruchu*/
		public Point GetLastMove()
		{
			return lastMove;
		}
		/**metodaa przesuwajaca wspolrzedne obiektu wg przekazanych wspolrzednych kierunku*/
		public void MoveGhost(int dX, int dY, int width, int height, List<Rectangle2D> wallList)
		{
			Point direction = ValidateGhostCoordinates(new Point(dX, dY), width, height);
			direction = CheckGhostForCollision(new Point(dX, dY), wallList);
			this.setLocation(this.x + direction.x, this.y + direction.y);
			lastMove.setLocation(dX, dY);
		}
		/**metoda przesuwajaca wspolrzedne obiektu wg przekazanych wspolrzednych kierunku*/
		public void MoveGhost(Point direction, int width, int height, List<Rectangle2D> wallList)
		{
			direction = ValidateGhostCoordinates(direction, width, height);
			direction = CheckGhostForCollision(direction, wallList);
			this.setLocation(this.x + direction.x, this.y + direction.y);
			lastMove.setLocation(direction);
		}
		/**metoda rysujaca obiekt*/
		public void DrawGhost(Graphics g)
		{
			setBounds(this.x,this.y,15,15);
			g.setColor(Color.red); 
			g.fillRect(this.x,this.y,width,height);
		}
		/**metoda kontrolujaca by obiekt miescil sie wewnatrz ramki */
		private Point ValidateGhostCoordinates(Point movement, int width, int height)
		// kontroluje czy ghost nie ucieka poza okno
		// jezeli dany ruch wykroczy poza - zmieniamy kierunek na przeciwny
		{
			//Random randGen = new Random();
			Point validatedMovement = new Point(movement);
			if( this.x + movement.x < 0 || this.x + movement.x > width - this.width){
				validatedMovement.setLocation(-movement.x, movement.y);
			}
			else if( this.y + movement.y < 0 || this.y + movement.y > height - this.height){
				validatedMovement.setLocation(movement.x, -movement.y);
			}
			return validatedMovement;
		}
		/**metoda kontrolujaca by obiekt nie wjezdzal na sciane - wykrywanie kolizji */
		private Point CheckGhostForCollision(Point movement, List<Rectangle2D> wallList)
		// to samo co wy¿ej tylko ze œcianami
		{
			Point validatedMovement = new Point(movement);
			Rectangle2D futureGhostPosition = new Rectangle(this.x + movement.x, this.y + movement.y, this.width, this.height);
			boolean collisionDetected = false;
			for (int i = 0; i < wallList.size(); ++i)
			// przegladamy wszystkie sciany w celu sprawdzenia, czy nastapi kolizja
			{
				Rectangle2D wall = wallList.get(i);
				if (futureGhostPosition.intersects(wall))	// jezeli po przesunieciu ghost'a bedzie kolizja - przerywamy
				{
					collisionDetected = true;
					break;
				}
			}
			if (collisionDetected) // wykryto kolizje - zmieniamy kierunek na przciwny
			{
				validatedMovement.setLocation(-validatedMovement.x, -validatedMovement.y);
			}
			return validatedMovement;
		}
		
		
		
		
}
