package Pacman;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/** Klasa reprezentuj¹ca jeden poziom (level) gry */
public class GameLevel {
	
	/**numer poziomu*/
	private Integer _numer_poziomu;
	
	/**szerokosc poziomu*/
	private Integer _szerokosc;
	
	/**wysokosc poziomu*/
	private Integer _wysokosc;
	
	/**liczba zyc*/
	private Integer _liczba_zyc;
	
	/**liczba duszkow*/
	private Integer _liczba_duszkow;
	
	/**szybkosc duszkow*/
	private Integer _szybkosc_duszkow;
	
	/**liczba punkcikow (tokenow) do zebrania */
	private Integer _liczba_punkcikow;
	
	/**lista wektorow, gdzie kazdy wektor jest reprezentowany przez 4 wspolrzedne w postaci [Xp,Yp, Xk, Yk]- (Xp,Yp)- poczatek linii, (Xk,Yk) - koniec linii. Linie symbolizuja sciany w labiryncie */
	private List<Integer[]> _linie = new ArrayList<Integer[]>();

    /** Sciezka pliku, gdzie jest przechowywany plik konfiguracyjny*/
	private String _sciezka;
    
	
	/** Konstruktor</br> 
	 * @param katalog_etapu sciezka katalogu z plikami etapu
	 * dane_konfiguracyjne to slownik, ktory reprezentuje plik konfiguracyjny
	 */
	public GameLevel (String sciezka){
		Properties dane_konfiguracyjne = new Properties();
		InputStream wejscie = null;
		
			
	try{	
		wejscie = new FileInputStream(sciezka);
		/**za³adowanie do zmiennej dane_konfiguracyjne zawartoœci pliku tekstowego*/
		dane_konfiguracyjne.load(wejscie);
		
		_numer_poziomu = new Integer(Integer.parseInt(dane_konfiguracyjne.getProperty("numer_poziomu")));
		_szerokosc = new Integer(Integer.parseInt(dane_konfiguracyjne.getProperty("szerokosc")));
		_wysokosc = new Integer(Integer.parseInt(dane_konfiguracyjne.getProperty("wysokosc")));
		_liczba_zyc = new Integer(Integer.parseInt(dane_konfiguracyjne.getProperty("liczba_zyc")));
		_liczba_duszkow = new Integer(Integer.parseInt(dane_konfiguracyjne.getProperty("liczba_duszkow")));
		_szybkosc_duszkow = new Integer(Integer.parseInt(dane_konfiguracyjne.getProperty("szybkosc_duszkow")));
		_liczba_punkcikow = new Integer(Integer.parseInt(dane_konfiguracyjne.getProperty("liczba_punkcikow")));
		_sciezka = sciezka;
		
		
		//parsowanie linijki tekstu, która zawiera dane wszystkich scian w labiryncie
		String wczytana_linijka_tekstu_z_danymi_linii = dane_konfiguracyjne.getProperty("linie");
		
		
		String [] sciany_labiryntu = wczytana_linijka_tekstu_z_danymi_linii.split(";");
		
		for (int i = 0; i < sciany_labiryntu.length; i++) {

		    	Integer[] wynik = new Integer[4];
		    	String tmp[] = sciany_labiryntu[i].split(",");
		    	for(int p = 0; p<4; p++){
		    		wynik[p] = Integer.parseInt(tmp[p]);
		    	}
		    	_linie.add(wynik);
		}
	} catch (IOException ex) {
		ex.printStackTrace();}
		
	}
	
	
// Gettery, s¹ one po to, ¿eby dostaæ siê do prywatnych pól klasy GameLevel
	//Integer GetNumerPoziomu(){
	//	return _numer_poziomu;
	//}
	
	/**@return zwraca szerokosc */
	Integer GetSzerokosc(){
		return _szerokosc;
	}
	
	/**@return zwraca wysokosc */
	Integer GetWysokosc(){
		return _wysokosc;
	}
	
	Integer GetLiczbaDuszkow(){
		return _liczba_duszkow;
	}
	
	Integer GetSzybkoscDuszkow(){
		return _szybkosc_duszkow;
	}
		
	Integer GetLiczbaZyc(){
		return _liczba_zyc;
	}
	Integer GetNumerPoziomu(){
		return _numer_poziomu;
	}
	Integer GetLiczbaPunkcikow(){
		return _liczba_punkcikow;
	}
	/*
	String Getsciezka(){
		return _sciezka;
	}
	*/
	
	/** @return zwraca liczbe linii */
	Integer GetLiczbalinii(){
		return _linie.size();
	}
	
	/** 
	 * @param index - index linii, któr¹ chcemy pobraæ
	 * @return zwraca wektor linii o podanym indeksie */
	Integer[] GetLinia(int index){
		return _linie.get(index);
	}
	
     
}
