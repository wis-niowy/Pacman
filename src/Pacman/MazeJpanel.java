package Pacman;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Calendar;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Rectangle2D;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.geom.Line2D;

/** Pomocnicza klasa, ktora zostal¹ wygenerowana po to zeby uproscic sposob rysowania linii symbolizujacych sciany labiryntu
 *  Rysuje tak¿e bohatera, jego przeciwników i zawiera zaimplementowan¹ metodê sterowania bohaterem*/
public class MazeJpanel extends JPanel implements ActionListener,KeyListener {

	
	public Random randomGen;
	/**zmienne okreœlaj¹ce miejsce startu pacmana*/ 
	int x=0, y=0; 
	
	/**zmienne przechowuj¹ca szybkoœæ poruszania siê pacmana*/
	int velX=0, velY=0;
	
	
	/**Obiekt, który odœwie¿a mapê */
	Timer tm;
	/**zmienna, która przechowuje dane pobranie z pliku konfiguracyjnego*/
	GameLevel _gameLevel;
	
	/**licznik wyniku */
	int score;
	/**licznik pozostalych zyc */
	int lifesRemaining;
	
	/**zmienna przechowuj¹ca bohatera */
	public static Player player;
	
	/**grubosc scian*/
	private int LINE_STROKE = 20;
	/**lista z odcinkami (scianami danej planszy) */
	private static List<Rectangle2D> wallList;
	/**ilosc przeciwnikow*/
	private int GHOSTS_NUMBER;	
	/**szybkosc przeciwnikow*/
	private int GHOSTS_SPEED;	
	/**lista przechowuj¹ca przeciwników */
	public static List<Ghost> ghostsArray;
	/**ghost ktory osttnio spowodowal strate punktu */
	private Ghost lastCollidedGhost;
	/**ilosc tokenow --- TU USTAWIAC LICZBE PUNKCIKOW DO ZEBRANIA NA PLANSZY*/
	private int TOKEN_NUMBER = 2;
	/**lista przechowujaca tokeny (punkty do zbierania przez pacmana) */
	public static List<Token> tokenArray;
	/**lista przechowujaca liczniki pozostalych zyc */
	public static List<Rectangle2D> lifeList;
	/**zmienna przechowujaca rozmiar licznika zyc */
	private int rectSize;
	/**flaga mowiaca czy gra jest zapauzowana */
	private boolean isPaused;
	
	/**obiekt klasy rodzaju actionListener - kiedy gra ma sie skonczyc, uzywamy metody tego obiektu
	 * w celu powiadomienia klasy MENU, ze ma wlaczyc nastepny poziom
	 * klasa jest zdefiniowana wewnatrz klasy MENU*/
	private Menu.ExitLevelEvent exitLevel;
	/** Konstruktor</br> 
	 * @param lastScore int, przekazuje, jaki byl wynik uzysakny na poprzednich poziomach
	 */
	public MazeJpanel(int lastScore) {
		isPaused = false;
		score = 0;
		score += lastScore;
		rectSize = 15;
		tm = new Timer(3, this);
		wallList = new ArrayList<Rectangle2D>();
		ghostsArray = new ArrayList<Ghost>();
		tokenArray = new ArrayList<Token>();
		lifeList = new ArrayList<Rectangle2D>();
	}
	/**metoda ustawiajaca listenera zdarzenia zakonczenia poziomu */
	public void setExitEventListener(Menu.ExitLevelEvent listener)
	{
		this.exitLevel = listener;
	}
	
	public int GetScore()
	{
		return score;
	}
	
	/**metoda inicjalizujaca dany poziom gry
	 * @param lastScore int, numer obecnego poziomu gry
	 * */
	public void InitializeGameLevel(int m)
	{
		setBackground(Color.black);
		
		
		// KONWERTOWANIE INT M, KTORY OTRZYMALISMY NA STRING
			
			StringBuilder sb = new StringBuilder();
			sb.append("");
			sb.append(m);
			String strI = sb.toString();
			//gameLevel przechowuje parametry, które zosta³y wczytane z pliku kofiguracyjnego
			_gameLevel = new GameLevel("supportingfiles/"+strI+".properties");
			setPreferredSize(new Dimension(_gameLevel.GetSzerokosc(), _gameLevel.GetWysokosc()));
			randomGen = new Random();
			GHOSTS_NUMBER = _gameLevel.GetLiczbaDuszkow();
			GHOSTS_SPEED = _gameLevel.GetSzybkoscDuszkow();
			TOKEN_NUMBER = _gameLevel.GetLiczbaPunkcikow();
			for (int i = 0; i < GHOSTS_NUMBER; ++i)
			{
				ghostsArray.add(new Ghost(_gameLevel.GetSzerokosc() - 20, _gameLevel.GetWysokosc() - 20));	// prawy dolny
				
			}
			player = new Player(0, 0);
			InitializeWalls();
			
			for(int i = 0; i < TOKEN_NUMBER; ++i)
			// inicjalizujemy tokeny
			{
				tokenArray.add(new Token(_gameLevel.GetSzerokosc(), _gameLevel.GetWysokosc(), wallList));
			}
			for (int i = 0; i < _gameLevel.GetLiczbaZyc(); ++i)
			// inicjalizacja licznikow zyc
			{
				int rX = (i + 1) * 5 + i * rectSize;
				int rY = _gameLevel.GetWysokosc() - rectSize - 10;
				lifeList.add(new Rectangle(rX, rY, rectSize, rectSize));
			}
			lifesRemaining = _gameLevel.GetLiczbaZyc();
			tm.start();
			
	}
	
	/** 
	 * funkcja rysujaca linie, bohatera, przeciwników i ustawiajaca kolor tla
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.gray);
		g.setFont(new Font("Arial", 8, 20));		// licznik punktow
		g.drawString("Wynik:"+score,5,40);
		
		for (int i = 0; i < lifesRemaining; ++i)
		// rysowanie punktow zycia
		{
			Rectangle2D currentRectangle = lifeList.get(i);
			g.setColor(Color.pink); 
			g.fillRect((int)currentRectangle.getX(), (int)currentRectangle.getY(),rectSize,rectSize);
		}
		g2d.setColor(Color.gray);
		for(int i = 0 ; i<_gameLevel.GetLiczbalinii() ; i++)
		{
	    	Integer[] wynik;
	    	
	    	// wynik wskazuje na pierwszy element wektora
	    	wynik = _gameLevel.GetLinia(i);

	    	//okreœla gruboœæ linii
	    	g2d.setStroke(new BasicStroke(LINE_STROKE));
	    	g2d.drawLine(wynik[0], wynik[1], wynik[2], wynik[3] );
		}
		addKeyListener(this);
		setFocusable(true);
		
		player.DrawPlayer(g);
		for (int i = 0; i < GHOSTS_NUMBER; ++i)
		{
			ghostsArray.get(i).DrawGhost(g);
		}
		for (int i = 0; i < TOKEN_NUMBER; ++i)
		{
			tokenArray.get(i).DrawToken(g);
		}
		
		ValidateGameState();
		
		setVisible(true);
		
	}
	
	
	/**Metoda okreœlaj¹ca zasady sterowania bohaterem*/
	public void actionPerformed(ActionEvent a){
		
		if (!isPaused){
		player.MovePlayer(velX, velY, _gameLevel.GetSzerokosc(), _gameLevel.GetWysokosc(), wallList);
		
		//randomowy ruch ghost'ow
		for (int i = 0; i < GHOSTS_NUMBER; ++i)
		{
			/*jezeli myRandomNumber = ..
			 * 0 - ruch w prawo
			 * 75 - ruch w dol
			 * 150 - ruch w lewo
			 * 225 - ruch w gore
			 * inne - ruch taki, jak poprzedni (zapisany w klasie ghost)*/
			int myRandomNumber = randomGen.nextInt(300);
			switch (myRandomNumber)
			{
			case 0:
				ghostsArray.get(i).MoveGhost(GHOSTS_SPEED, 0, _gameLevel.GetSzerokosc(), _gameLevel.GetWysokosc(), wallList);
				break;
			case 75:
				ghostsArray.get(i).MoveGhost(0, GHOSTS_SPEED, _gameLevel.GetSzerokosc(), _gameLevel.GetWysokosc(), wallList);
				break;
			case 150:
				ghostsArray.get(i).MoveGhost(-GHOSTS_SPEED, 0, _gameLevel.GetSzerokosc(), _gameLevel.GetWysokosc(), wallList);
				break;
			case 225:
				ghostsArray.get(i).MoveGhost(0, -GHOSTS_SPEED, _gameLevel.GetSzerokosc(), _gameLevel.GetWysokosc(), wallList);
				break;
			default:
				ghostsArray.get(i).MoveGhost(ghostsArray.get(i).GetLastMove(), _gameLevel.GetSzerokosc(), _gameLevel.GetWysokosc(),
						wallList);
				break;
			}
		}
			
		
		

		repaint();
		}
	}
	
	/**Metoda okreœlaj¹ca co dzieje siê po wciœniêciu przycisku*/
	@Override
	public void keyPressed(KeyEvent a) {
		int c = a.getKeyCode();
		
		if(c == KeyEvent.VK_SPACE){
			isPaused = !isPaused;
		}
		else if(c == KeyEvent.VK_LEFT){
			velX = -1;
			velY = 0;
		}
		else if(c == KeyEvent.VK_RIGHT){
			velX = 1;
			velY = 0;
		}
		else if(c == KeyEvent.VK_UP){
			velX = 0;
			velY = -1;
		}
		else if(c == KeyEvent.VK_DOWN){
			velX = 0;
			velY = 1;
		}
	}
	/**Metoda okreœlaj¹ca co dzieje siê po puszczeniu przycisku*/
	@Override
	public void keyReleased(KeyEvent a) {
			velX=0;
			velY=0;
	}
	/**Metoda wygenerowana przez zaimplementowany KeyListener*/
	@Override
	public void keyTyped(KeyEvent a) {
		
	}
	/**metoda tworzaca liste scian jako obiekty typu Rectangle2D */
	private void InitializeWalls()
	// sciany podane jako wspolrzedne zamieniamy na obiekty rectangle, aby latwo sprawdzac kolizje
	{
		for (int i = 0; i < _gameLevel.GetLiczbalinii(); ++i)
		{
			Integer[] wynik = _gameLevel.GetLinia(i);
			Line2D segment = new Line2D.Double(new Point(wynik[0], wynik[1]), new Point(wynik[2], wynik[3]));
			Rectangle2D wall;
			if (segment.getX1() == segment.getX2())			// sciana pionowa
			{
				double height = Math.abs(segment.getY2() - segment.getY1()) + LINE_STROKE;
				int startY = (int)Math.min(segment.getY1(), segment.getY2()) - LINE_STROKE/2;
				wall = new Rectangle((int)segment.getX1() - LINE_STROKE/2, startY,
						LINE_STROKE, (int)height);
			}
			else //if (segment.getY1() == segment.getY2())	// sciana pozioma
			{
				double width = Math.abs(segment.getX2() - segment.getX1()) + LINE_STROKE;
				int startX = (int)Math.min(segment.getX1(), segment.getX2()) - LINE_STROKE/2;
				wall = new Rectangle(startX, (int)segment.getY1() - LINE_STROKE/2,
						(int)width, LINE_STROKE);
			}
			wallList.add(wall);
		}
	}
	/**metoda kontrolujaca czy gracz zebral jakis token (punkcik)*/
	private void CheckPointScore()
	// sprawdzamy czy doszlo do kolizji pacmana i tokena
	{
		for (int i = 0; i < TOKEN_NUMBER; ++i)
		{
			Token currentToken = tokenArray.get(i);
			if(currentToken.intersects(player))
			{
				score += 10;
				tokenArray.remove(i);
				TOKEN_NUMBER--;
				break;
			}
		}
		if (TOKEN_NUMBER == 0)
			// jezeli gracz zebral juz wszystkie punkciki
		{
			// zatrzymujemy zegar zamykanego levelu (obiektu) - KONIECZNE - w Javie nie usuwa sie obiektu klasy recznie, wszystko robi Garbgde Collector
			// a ten nie zawsze robi to od razu, czasem obiekt wisi w pamieci dluzej - pozostawiony zegar wciaz by tykal dla poprzedniego levelu
			// i wywolywal odswiezanie - a ten level chcemy miec juz usuniety
			tm.stop();
			exitLevel.eventAction(0, score); 		// wywolujemy zdarzenie - koniec obecnego poziomu
		}
	}
	/**metoda kontrolujaca czy gracz skusil (czy doszlo do kolizji z duszkiem) */
	private void CheckPointLoss()
	// sprawdzamy czy doszlo do kolizji pacmana i duszka
	{
		boolean isLost = false;
		for (int i = 0; i < GHOSTS_NUMBER; ++i)
		{
			Ghost currentGhost = ghostsArray.get(i);
			if(currentGhost != lastCollidedGhost && currentGhost.intersects(player))
			// jezeli nastapila kolizja z kolejnym (innym niz ostatnio ghostem) - zmniejsz liczbe zyc
			{
				lifesRemaining--;
				isLost = true;
				lastCollidedGhost = currentGhost;
				break;
			}
		}
		if (isLost)
		{
			lifeList.remove(lifeList.size() - 1);  // usuwamy ostatni z licznikow zyc z listy
			if (lifesRemaining > 0)
			{
				try
				{
					Thread.sleep(500);
				}
				catch(InterruptedException e)
				{
					System.out.println(e);
				}  
			}
			else
			{
				tm.stop();
				try
				{
					Thread.sleep(1500);
				}
				catch(InterruptedException e)
				{
					System.out.println(e);
				}  
				exitLevel.eventAction(1, score); 		// wywolujemy zdarzenie - koniec gry
			}
		}
	}
	/**metoda wywolujaca kontrole zebrania punkcika lub spotkania duszka */
	private void ValidateGameState ()
	{
		CheckPointScore();
		CheckPointLoss();
	}

}
