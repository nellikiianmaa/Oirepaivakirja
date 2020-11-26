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

/**Lisää laakeita, lukee ja kirjoittaa laakeet
 * tiedostoon, osaa etsiä ja lajitella laakeita 
 * @author Nelli Kiianmaa
 * @version 9.3.2020
 *
 */
public class Laakkeet implements Iterable<Laake> {

    private Collection<Laake> alkiot = new ArrayList<Laake>();
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "laakkeet";
    
  
    /**
     * Oletusmuodostaja
     */
    public Laakkeet( ) {
       
   }

    /**
     * Lisää uuden laakeen tietorakenteeseen. Ottaa merkinän omistukseensa.
     * @param laake lisättävän lääkkeen viite
     * @example
     * <pre name="test">
     * Laakkeet laakkeet = new Laakkeet();
     * Laake sarky = new Laake(), sarky2 = new Laake(); 
     * sarky.vastaaLaake(); sarky.rekisteroi();
     * laakkeet.getLkm() === 0;
     * laakkeet.lisaa(sarky); laakkeet.getLkm() === 1;
     * laakkeet.lisaa(sarky2); laakkeet.getLkm() === 2;
     * laakkeet.lisaa(sarky); laakkeet.getLkm() === 3;
     * laakkeet.lisaa(sarky); laakkeet.getLkm() === 4;
     * laakkeet.lisaa(sarky); laakkeet.getLkm() === 5; 
     * </pre>
     */
    public void lisaa(Laake laake) {
        alkiot.add(laake);
        muutettu = true;
    }


    /**
     * Lukee harrastukset tiedostosta.
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Laakkeet mommot = new Laakkeet();
     *  Laake tabu21 = new Laake(); tabu21.vastaaLaake(); tabu21.rekisteroi();
     *  Laake tabu11 = new Laake(); tabu11.vastaaLaake(); tabu11.rekisteroi();
     *  Laake tabu22 = new Laake(); tabu22.vastaaLaake(); tabu22.rekisteroi();
     *  Laake tabu12 = new Laake(); tabu12.vastaaLaake(); tabu12.rekisteroi();
     *  Laake tabu23 = new Laake(); tabu23.vastaaLaake(); tabu23.rekisteroi();
     *  String tiedNimi = "testiLaakkeet";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  mommot.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  mommot.lisaa(tabu21);
     *  mommot.lisaa(tabu11);
     *  mommot.lisaa(tabu22);
     *  mommot.lisaa(tabu12);
     *  mommot.lisaa(tabu23);
     *  mommot.tallenna();
     *  Laakkeet tabletit = new Laakkeet();
     *  tabletit.lueTiedostosta(tiedNimi);     
     *  Iterator<Laake> i = tabletit.iterator();
     *  i.next().toString() === tabu21.toString();
     *  i.next().toString() === tabu11.toString();
     *  i.next().toString() === tabu22.toString();
     *  i.next().toString() === tabu12.toString();
     *  i.next().toString() === tabu23.toString();
     *  i.hasNext() === false;
     *  tabletit.lisaa(tabu23);
     *  tabletit.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Laake laake = new Laake();
                laake.parse(rivi); // voisi olla virhekäsittely
                lisaa(laake);
            }
            muutettu = false;

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
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
            for (Laake laake : this) {
                fo.println(laake.toString());
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
     * Palauttaa laakeiden lukumäärän
     * @return laaketen lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Iteraattori kaikkien laakeiden läpikäymiseen
     * @return laakeiteraattori
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Laakkeet hoidot = new Laakkeet();
     *  Laake tabu21 = new Laake(); hoidot.lisaa(tabu21);
     *  Laake tabu11 = new Laake(); hoidot.lisaa(tabu11);
     *  Laake tabu22 = new Laake(); hoidot.lisaa(tabu22);
     *  Laake tabu12 = new Laake(); hoidot.lisaa(tabu12);
     *  Laake tabu23 = new Laake(); hoidot.lisaa(tabu23);
     * 
     *  Iterator<Laake> i2=hoidot.iterator();
     *  i2.next() === tabu21;
     *  i2.next() === tabu11;
     *  i2.next() === tabu22;
     *  i2.next() === tabu12;
     *  i2.next() === tabu23;
     *  i2.next() === tabu12;  #THROWS NoSuchElementException  
     * </pre>
     */
    @Override
    public Iterator<Laake> iterator() {
        return alkiot.iterator();
    }


    /**
     * Haetaan kaikki kirjauksen laakeet
     * @param laakeid lääkkeen id
     * @return tietorakenne jossa viiteet löydettyihin laakeisiin
      * @example
     * <pre name="test">
     * #import java.util.*;
     *  Laakkeet voinnit = new Laakkeet();
     *  Laake Laake1 = new Laake(); Laake1.vastaaLaake(); 
     *  Laake1.rekisteroi(); voinnit.lisaa(Laake1);
     *  
     *  Laake Laake2 = new Laake(); Laake2.vastaaLaake(); 
     *  Laake2.rekisteroi(); voinnit.lisaa(Laake2);
     *  
     *  voinnit.getLkm() === 2;
     *  voinnit.annaLaake(1) === "parasetamoli 1000 mg";
     *  voinnit.annaLaake(2) === "ibuprofeeni 200 mg";
     *  voinnit.annaLaake(3) === null;
     * </pre> 
     */
    public String annaLaake(int laakeid) {
        for (Laake laake : alkiot) {
            if (laake.getLaakeId() == laakeid) {
                return laake.getLaake();
                }
        }
        return null;
    }
    
  
    /**Palautetaan lääkeolio lääkeid:n perusteella
     * @param laakeid oireen id
     * @return lääkeolio
     */
    public Laake anna(int laakeid) {
        for (Laake laake : alkiot) {
            if (laake.getLaakeId() == laakeid) {
                return laake;
                }
        }
        return null;
    }

    /**laakelistan tulostus
     */
    public void tulosta() {
        for (Laake laake: alkiot) {
            laake.tulosta(System.out); 
            }
       }
    
    
    /**
     * Testiohjelma harrastuksille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Laakkeet laakkeet = new Laakkeet();
        Laake sarky1 = new Laake();
        Laake sarky2 = new Laake();
        Laake sarky3 = new Laake();
        Laake sarky4 = new Laake();
        Laake sarky11 = new Laake();
        Laake sarky12 = new Laake();
        Laake sarky13 = new Laake();
        Laake sarky14 = new Laake();

        System.out.println(laakkeet.getLkm());

        sarky1.vastaaLaake();
        sarky1.rekisteroi();
        sarky2.vastaaLaake();
        sarky2.rekisteroi();
        sarky3.vastaaLaake();
        sarky3.rekisteroi();
        sarky4.vastaaLaake();
        sarky4.rekisteroi();
        sarky11.vastaaLaake();
        sarky11.rekisteroi();
        sarky12.vastaaLaake();
        sarky12.rekisteroi();



        laakkeet.lisaa(sarky1);
        laakkeet.lisaa(sarky2);
        laakkeet.lisaa(sarky3);
        laakkeet.lisaa(sarky4);
        laakkeet.lisaa(sarky11);
        laakkeet.lisaa(sarky12);

        System.out.println(laakkeet.getLkm());
        System.out.println(laakkeet.annaLaake(1));

        sarky1.tulosta(System.out);
        sarky2.tulosta(System.out);
        sarky3.tulosta(System.out);
        sarky4.tulosta(System.out);
        sarky11.tulosta(System.out);
        sarky12.tulosta(System.out);
        sarky13.tulosta(System.out);
        sarky14.tulosta(System.out);
        laakkeet.tulosta();

        try {
            laakkeet.tallenna();
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

 

        System.out.println("============= laakeet testi =================");

        for (Laake laake : laakkeet) {
            System.out.print(laake.getLaakeId() + " ");
            laake.tulosta(System.out);
        }
        
    }

   
}
