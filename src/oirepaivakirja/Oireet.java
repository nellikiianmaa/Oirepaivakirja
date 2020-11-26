package oirepaivakirja;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kanta.SailoException;

/**Lisää oireita, lukee ja kirjoittaa oireet
 * tiedostoon, osaa etsiä ja lajitella oireita 
 * @author Nelli Kiianmaa, nelli.j.kiianmaa@student.jyu.fi
 * @version 9.3.2020
 *
 */
public class Oireet implements Iterable<Oire> {

    private Collection<Oire> alkiot = new ArrayList<Oire>();
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "oireet";
    
  
    /**
     * Oletusmuodostaja
     */
    public Oireet( ) {
       
   }

    /**
     * Lisää uuden oireen tietorakenteeseen. Ottaa merkinän omistukseensa.
     * @param oire lisättävän oireen viite. Huom! tietorakenne muuttuu omistajaksi
     * @example
     * <pre name="test">
     * Oireet oireet = new Oireet();
     * Oire olo1 = new Oire(), olo2 = new Oire();
     * oireet.getLkm() === 0;
     * oireet.lisaa(olo1); oireet.getLkm() === 1;
     * oireet.lisaa(olo2); oireet.getLkm() === 2;
     * oireet.lisaa(olo1); oireet.getLkm() === 3;
     * oireet.lisaa(olo1); oireet.getLkm() === 4;
     * </pre>
     */
    public void lisaa(Oire oire) {
        alkiot.add(oire);
        muutettu = true;
    }
    

    /**
     * Lukee harrastukset tiedostosta.
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Oire oire = new Oire();
                oire.parse(rivi); // voisi olla virhekäsittely
                lisaa(oire);
            }
            muutettu = false;

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    
    /**
     * Lukee oireet tiedostosta.
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * #import kanta.SailoException;
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Oireet saryt = new Oireet();
     *  Oire pahaolo21 = new Oire(); pahaolo21.vastaaOire(); pahaolo21.rekisteroi();
     *  Oire pahaolo11 = new Oire(); pahaolo11.vastaaOire(); pahaolo11.rekisteroi();
     *  Oire pahaolo22 = new Oire(); pahaolo22.vastaaOire(); pahaolo22.rekisteroi();
     *  Oire pahaolo12 = new Oire(); pahaolo12.vastaaOire(); pahaolo12.rekisteroi();
     *  Oire pahaolo23 = new Oire(); pahaolo23.vastaaOire(); pahaolo23.rekisteroi();
     *  String tiedNimi = "testioireet";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  saryt.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  saryt.lisaa(pahaolo21);
     *  saryt.lisaa(pahaolo11);
     *  saryt.lisaa(pahaolo22);
     *  saryt.lisaa(pahaolo12);
     *  saryt.lisaa(pahaolo23);
     //*  saryt.tallenna();
     *  Oireet pipit = new Oireet();
     *  pipit.lueTiedostosta(tiedNimi);
     *  Iterator<Oire> i = pipit.iterator();
     *  i.next().toString() === pahaolo21.toString();
     *  i.next().toString() === pahaolo11.toString();
     *  i.next().toString() === pahaolo22.toString();
     *  i.next().toString() === pahaolo12.toString();
     *  i.next().toString() === pahaolo23.toString();
     *  i.hasNext() === false;
     *  pipit.lisaa(pahaolo23);
     //*  pipit.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    
    /**
     * Tallentaa harrastukset tiedostoon.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Oire oire : this) {
                fo.println(oire.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }

    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }

    
    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    

    /**
     * Palauttaa oireiden lukumäärän
     * @return Oireten lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Iteraattori kaikkien oireiden läpikäymiseen
     * @return Oireiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Oireet voinnit = new Oireet();
     *  Oire pipi21 = new Oire(); voinnit.lisaa(pipi21);
     *  Oire pipi11 = new Oire(); voinnit.lisaa(pipi11);
     *  Oire pipi22 = new Oire(); voinnit.lisaa(pipi22);
     *  Oire pipi12 = new Oire(); voinnit.lisaa(pipi12);
     *  Oire pipi23 = new Oire(); voinnit.lisaa(pipi23);
     * 
     *  Iterator<Oire> i2=voinnit.iterator();
     *  i2.next() === pipi21;
     *  i2.next() === pipi11;
     *  i2.next() === pipi22;
     *  i2.next() === pipi12;
     *  i2.next() === pipi23;
     *  i2.next() === pipi12;  #THROWS NoSuchElementException  
     * </pre>
     */
    @Override
    public Iterator<Oire> iterator() {
        return alkiot.iterator();
    }


    /**
     * Haetaan kirjaukseen kuuluvan oireen sanallinen selite
     * @param oireid kirjauksen oireid
     * @return tietorakenne jossa viiteet löydettyihin oireisiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     *  Oireet voinnit = new Oireet();
     *  Oire oire1 = new Oire(); oire1.vastaaOire(); 
     *  oire1.rekisteroi(); voinnit.lisaa(oire1);
     *  
     *  Oire oire2 = new Oire(); oire2.vastaaOire(); 
     *  oire2.rekisteroi(); voinnit.lisaa(oire2);
     *  
     *  voinnit.getLkm() === 2;
     *  voinnit.annaOire(2) === "mahakipu";
     *  voinnit.annaOire(3) === null;
     * </pre> 
     */
    public String annaOire(int oireid) {
        for (Oire oire : alkiot) {
            if (oire.getOireId() == oireid) {
                return oire.getVointi();
                }
        }
        return null;
        }
    

    /**Palautetaan oireolio oireid:n perusteella
     * @param oireid oireen id
     * @return oireolio
     */
    public Oire anna(int oireid) {
        for (Oire oire : alkiot) {
            if (oire.getOireId() == oireid) {
                return oire;
                }
        }
        return null;
    }
    

    /**Oirelistan tulostus
     */
    public void tulosta() {
        for (Oire oire : alkiot) {
            oire.tulosta(System.out); 
            }
       }
   
    
    /**
     * Testiohjelma oireille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Oireet voinnit = new Oireet();
        Oire sarky1 = new Oire();
        Oire sarky2 = new Oire();
        Oire sarky3 = new Oire();
        Oire sarky4 = new Oire();

        System.out.println(voinnit.getLkm());
        sarky1.vastaaOire();
        sarky1.rekisteroi();
        sarky2.vastaaOire();
        sarky2.rekisteroi();
        sarky3.vastaaOire();        
        sarky3.rekisteroi();
        sarky4.vastaaOire();
        sarky4.rekisteroi();
        
       

        voinnit.lisaa(sarky1);
        System.out.println(voinnit.annaOire(1));
        voinnit.lisaa(sarky2);
        voinnit.lisaa(sarky3);
        voinnit.lisaa(sarky4);

        System.out.println(voinnit.getLkm());

        sarky1.tulosta(System.out);
        sarky2.tulosta(System.out);
        sarky3.tulosta(System.out);
        sarky4.tulosta(System.out);
        voinnit.tulosta();
      
 

        System.out.println("============= Oireet testi =================");

        for (Oire oire : voinnit) {
            System.out.print(oire.getOireId() + " ");
            oire.tulosta(System.out);
        }
        
    }

    

   
}
