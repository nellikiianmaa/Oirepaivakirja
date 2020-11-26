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
import java.util.NoSuchElementException;

import kanta.SailoException;

/**Pitää yllä päiväkirjaa, lisää ja poistaa kirjauksia, lukee ja kirjoittaa kirjaukset
 * tiedostoon, osaa etsiä ja lajitella kirjauksia
 * @author Nelli Kiianmaa, nelli.j.kiianmaa@student.jyu.fi
 * @version1.1 17.3.2020
 * @version1.2 30.3.2020
 *
 */
public class Kirjaukset implements Iterable<Kirjaus> {
    private static final int MAX_MERKINTOJA   = 5;
    private boolean muutettu = false;
    private int              lkm           = 0;
    private String tiedostonPerusNimi = "kirjaukset";
    private Kirjaus  alkiot[]      = new Kirjaus[MAX_MERKINTOJA];
    

    /**
     * Oletusmuodostaja
     */
    public Kirjaukset( ) {
       
   }

    /**
     * Lisää uuden kirjauksen tietorakenteeseen. Ottaa merkinän omistukseensa.
     * @param kirjaus lisättävän kirjauksen viite. Huom! tietorakenne muuttuu omistajaksi
     * @example
     * <pre name="test">
     * Kirjaukset merkinnat = new Kirjaukset();
     * Kirjaus hiki = new Kirjaus(), hiki2 = new Kirjaus();
     * merkinnat.getLkm() === 0;
     * merkinnat.lisaa(hiki); merkinnat.getLkm() === 1; 
     * merkinnat.lisaa(hiki2); merkinnat.getLkm() === 2; 
     * merkinnat.lisaa(hiki); merkinnat.getLkm() === 3;
     * merkinnat.anna(0) === hiki;
     * merkinnat.anna(1) === hiki2;
     * merkinnat.anna(1) == hiki === false;
     * merkinnat.anna(1) == hiki2 === true;
     * merkinnat.lisaa(hiki); merkinnat.getLkm() === 4;
     * merkinnat.lisaa(hiki); merkinnat.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Kirjaus kirjaus)  {
        if (lkm >= alkiot.length) {
            Kirjaus[] uusi = new Kirjaus[lkm + 5];
            for(int i=0; i<alkiot.length; i++) {
                uusi[i]=alkiot[i];  
            }
             alkiot=uusi;
             
          }
        alkiot[lkm++] = kirjaus;
        muutettu = true;

    }
   
    /**
     * Palauttaa viitteen i:teen jäseneen.
     * @param i monennenko merkinnän viite halutaan
     * @return viite merkintään, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Kirjaus anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    

    /**
     * Palautetaan mihin oireeseen oire_id kuuluu
     * @param i oireen 1 indeksi
     * @param oireid oireen tunnusnro
     * @param kirjausid alkiot-taulukon indeksi
     */
     public void setOire1Id(int i, int oireid, int kirjausid) {
       for(int j=0; j<lkm; j++) {
         if (alkiot[j].getId()== kirjausid) { alkiot[j].setOire1Id(i, oireid); return; }
       }
    }
     
     /**
      * Palautetaan mihin oireeseen oire_id kuuluu
      * @param i oireen 2 indeksi
      * @param oireid oireen tunnusnro
      * @param kirjausid alkiot-taulukon indeksi
      */
      public void setOire2Id(int i, int oireid, int kirjausid) {
        for(int j=0; j<lkm; j++) {
          if (alkiot[j].getId()== kirjausid) { alkiot[j].setOire2Id(i, oireid); return; }
        }
     }
      
      
      /**
       * Palautetaan mihin oireeseen oire_id kuuluu
       * @param i oireen 3 indeksi
       * @param oireid oireen tunnusnro
       * @param kirjausid alkiot-taulukon indeksi
       */
       public void setOire3Id(int i, int oireid, int kirjausid) {
         for(int j=0; j<lkm; j++) {
           if (alkiot[j].getId()== kirjausid) { alkiot[j].setOire3Id(i, oireid); return; }
         }
      }
       
       
   
       /**
        * Lukee kirjaukset tiedostosta. 
        * @param tied tiedoston perusnimi
        * @throws SailoException jos lukeminen epäonnistuu
        * 
        * @example
        * <pre name="test">
        * #THROWS SailoException 
        * #import java.io.File;
        * 
        *  Kirjaukset kirjaukset = new Kirjaukset();
        *  Kirjaus vointi1 = new Kirjaus(), vointi2 = new Kirjaus();
        *  vointi1.vastaaMerkinta(3,3);
        *  vointi2.vastaaMerkinta(2,2);
        *  String hakemisto = "testikirjaukset";
        *  String tiedostonPerusNimi = "kirjaukset";
        *  String tiedNimi = hakemisto + tiedostonPerusNimi;
        *  File ftied = new File(tiedNimi+".dat");
        *  File dir = new File(hakemisto);
        *  dir.mkdir();
        *  ftied.delete();
        *  kirjaukset.lueTiedostosta(tiedNimi); #THROWS SailoException
        *  kirjaukset.lisaa(vointi1);
        *  kirjaukset.lisaa(vointi2);
        //*  kirjaukset.tallenna();
        *  Kirjaukset merkintoja = new Kirjaukset();  //JUnit piti virheenä samaa nimeä, joten tein eri
        *  merkintoja.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
        *  Iterator<Kirjaus> i = merkintoja.iterator();
        *  i.next() === vointi1;
        *  i.next() === vointi2;
        *  i.hasNext() === false;
        *  merkintoja.lisaa(vointi2);
        //*  merkintoja.tallenna();
        *  ftied.delete() === true;
        *  File fbak = new File(tiedNimi+".bak");
        *  fbak.delete() === true;
        *  dir.delete() === true;
        * </pre>
        */
       public void lueTiedostosta(String tied) throws SailoException {
           setTiedostonPerusNimi(tied);
           try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
               String rivi = fi.readLine();
               if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");

               while ( (rivi = fi.readLine()) != null ) {
                   rivi = rivi.trim();
                   if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                   Kirjaus kirjaus = new Kirjaus();
                   kirjaus.parse(rivi); // voisi olla virhekäsittely
                   lisaa(kirjaus);
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
        * Tallentaa jäsenistön tiedostoon.  
        * Tiedoston muoto:
        * @throws SailoException jos talletus epäonnistuu
        */
       public void tallenna() throws SailoException {
           if ( !muutettu ) return;

           File fbak = new File(getBakNimi());
           File ftied = new File(getTiedostonNimi());
           fbak.delete(); // if .. System.err.println("Ei voi tuhota");
           ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

           try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
               fo.println(alkiot.length);
               for (Kirjaus kirjaus : this) {
                   fo.println(kirjaus.toString());
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
        * Luokka kirjausten iteroimiseksi.
        * @example
        * <pre name="test">
        * #import kanta.Sailo.Exception;
        * #THROWS SailoException 
        * #PACKAGEIMPORT
        * #import java.util.*;
        * 
        * Kirjaukset kirjaukset = new Kirjaukset();
        * Kirjaus yokko1 = new Kirjaus(), yokko2 = new Kirjaus();
        * yokko1.rekisteroi(); yokko2.rekisteroi();
        *
        * kirjaukset.lisaa(yokko1); 
        * kirjaukset.lisaa(yokko2); 
        * kirjaukset.lisaa(yokko1); 
        * 
        * StringBuffer ids = new StringBuffer(30);
        * for (Kirjaus kirjaus:kirjaukset)   // Kokeillaan for-silmukan toimintaa
        *      ids.append(" "+kirjaus.getId());           
        * 
        * String tulos = " " + yokko1.getId() + " " + yokko2.getId() + " " + yokko1.getId();
        * 
        * ids.toString() === tulos; 
        * 
        * ids = new StringBuffer(30);
        * for (Iterator<Kirjaus>  i=kirjaukset.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
        *      Kirjaus kirjaus = i.next();
        *       ids.append(" "+kirjaus.getId());           
        * }
        * 
        * ids.toString() === tulos;
        * 
        * Iterator<Kirjaus>  i=kirjaukset.iterator();
        * i.next() == yokko1  === true;
        * i.next() == yokko2  === true;
        * i.next() == yokko1  === true;
        * 
        * i.next();  #THROWS NoSuchElementException  
        * </pre>
        */

    public class KirjauksetIterator implements Iterator<Kirjaus> {
        private int kohdalla = 0;
        
        
    /**
     * Onko olemassa vielä seuraavaa jäsentä
     * @see java.util.Iterator#hasNext()
     * @return true jos on vielä jäseniä
     */
      @Override
      public boolean hasNext() {
          return kohdalla < getLkm();
        }
      
      
      /**
       * Annetaan seuraava kirjaus
       * @return seuraava kirjaus
       * @throws NoSuchElementException jos seuraava alkiota ei enää ole
       * @see java.util.Iterator#next()
       */
      @Override
      public Kirjaus next() throws NoSuchElementException {
          if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
          return anna(kohdalla++);
      }


      /**
       * Tuhoamista ei ole toteutettu
       * @throws UnsupportedOperationException aina
       * @see java.util.Iterator#remove()
       */
      @Override
      public void remove() throws UnsupportedOperationException {
          throw new UnsupportedOperationException("Me ei poisteta");
      }
  }


      /**
       * Palautetaan iteraattori kirjauksistaan.
       * @return kirjaus iteraattori
       */
      @Override
      public Iterator<Kirjaus> iterator() {
          return new KirjauksetIterator();
      }
      
     
      /**
       * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
       * @param hakuehto hakuehto 
       * @param k etsittävän kentän indeksi  
       * @return tietorakenteen löytyneistä jäsenistä 
       * @example 
       * <pre name="test"> 
        * #import kanta.SailoException;
        * #import java.io.*;
        * #import java.util.*;
        * #THROWS SailoException  
        *   Kirjaukset kirjaukset = new Kirjaukset(); 
        *   Kirjaus kirjaus1 = new Kirjaus(); kirjaus1.parse("1|28.1.2020|12:00|1|"); 
        *   Kirjaus kirjaus2 = new Kirjaus(); kirjaus2.parse("2|2.2.2020|2:30|2|"); 
        *   Kirjaus kirjaus3 = new Kirjaus(); kirjaus3.parse("3|8.1.2020|2:03|8|"); 
        *   Kirjaus kirjaus4 = new Kirjaus(); kirjaus4.parse("4|28.3.2020|11:30|2|"); 
        *   Kirjaus kirjaus5 = new Kirjaus(); kirjaus5.parse("5|2.2.2020|12:50|4|"); 
        *   kirjaukset.lisaa(kirjaus1); kirjaukset.lisaa(kirjaus2); kirjaukset.lisaa(kirjaus3); kirjaukset.lisaa(kirjaus4); kirjaukset.lisaa(kirjaus5);
        *   // TODO: toistaiseksi palauttaa kaikki kirjaukset 
        * </pre>
       */
    public Collection<Kirjaus> etsi(@SuppressWarnings("unused") String hakuehto, @SuppressWarnings("unused") int k) { 
          Collection<Kirjaus> loytyneet = new ArrayList<Kirjaus>(); 
          for (Kirjaus kirjaus : this) { 
              loytyneet.add(kirjaus);  
          } 
          return loytyneet; 
      }


    
    /**Palauttaa kirjausten lukumäärän
     * @return kirjaustenlukumäärän
     */
    public int getLkm () {
        return lkm;
    }
    

    /**Palauttaa kirjauksen tunnusnumeron (id:n)
     * @param i kirjauksen indeksi
     * @return merkinnän id
     */
     public int getId(int i) {
            return alkiot[i].getId();
     }
    
     /**
      * Korvaa kirjauken tietorakenteessa.  Ottaa kirjauksen omistukseensa.
      * Etsitään samalla tunnusnumerolla oleva kirjaus.  Jos ei löydy,
      * niin lisätään uutena.
      * @param kirjaus lisättävän kirjauksen viite.  Huom tietorakenne muuttuu omistajaksi
      * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * #import kanta.SailoException; 
      * #import java.io.*;
     * #import java.util.*;
     * Kirjaukset kirjaukset = new Kirjaukset();
     * Kirjaus alku1 = new Kirjaus(), alku2 = new Kirjaus();
     * alku1.rekisteroi(); alku2.rekisteroi();
     * kirjaukset.getLkm() === 0;
     * kirjaukset.korvaaTaiLisaa(alku1); kirjaukset.getLkm() === 1;
     * kirjaukset.korvaaTaiLisaa(alku2); kirjaukset.getLkm() === 2;
     * Kirjaus alku3 = alku1.clone();
     * Iterator<Kirjaus> it = kirjaukset.iterator();
     * it.next() == alku1 === true;
     * kirjaukset.korvaaTaiLisaa(alku3); kirjaukset.getLkm() === 2;
     * it = kirjaukset.iterator();
     * Kirjaus j0 = it.next();
     * j0 === alku3;
     * j0 == alku3 === true;
     * j0 == alku1 === false;
     * </pre>
     */
     public void korvaaTaiLisaa(Kirjaus kirjaus) throws SailoException {
         int id =  kirjaus.getId();
         for (int i = 0; i < lkm; i++) {
             if ( alkiot[i].getId() == id ) {
                 alkiot[i] = kirjaus;
                 muutettu = true;
                 return;
             }
         }
         lisaa(kirjaus);
     }
     

     /** 
      * Poistaa kirjauksen jolla on valittu id  
      * @param id poistettavan kirjauksen id
      * @return 1 jos poistettiin, 0 jos ei löydy
      * @example 
        * <pre name="test"> 
        * #THROWS SailoException  
        * Kirjaukset kirjaukset = new Kirjaukset(); 
        * Kirjaus alku1 = new Kirjaus(), alku2 = new Kirjaus(), alku3 = new Kirjaus(); 
        * alku1.rekisteroi(); alku2.rekisteroi(); alku3.rekisteroi(); 
        * int id1 = alku1.getId(); 
        * kirjaukset.lisaa(alku1); kirjaukset.lisaa(alku2); kirjaukset.lisaa(alku3); 
        * kirjaukset.poista(id1+1) === 1; 
        * kirjaukset.getLkm() === 2; 
        * kirjaukset.poista(id1) === 1; kirjaukset.getLkm() === 1; 
        * kirjaukset.poista(id1+3) === 0; kirjaukset.getLkm() === 1; 
        * </pre>   
        */ 
        public int poista(int id) { 
           int ind = etsiId(id); 
           if (ind < 0) return 0; 
           lkm--; 
           for (int i = ind; i < lkm; i++) 
                alkiot[i] = alkiot[i + 1]; 
                alkiot[lkm] = null; 
                muutettu = true; 
               return 1; 
           } 
     
     
     /** 
      * Etsii kirjauksen id:n perusteella 
      * @param id tunnusnumero, jonka mukaan etsitään 
      * @return löytyneen kirjauksen indeksi tai -1 jos ei löydy 
      * @example
      * <pre name="test"> 
      * #THROWS SailoException  
      * Kirjaukset kirjaukset = new Kirjaukset(); 
      * Kirjaus alku1 = new Kirjaus(), alku2 = new Kirjaus(), alku3 = new Kirjaus(); 
      * alku1.rekisteroi(); alku2.rekisteroi(); alku3.rekisteroi(); 
      * int id1 = alku1.getId(); 
      * kirjaukset.lisaa(alku1); kirjaukset.lisaa(alku2); kirjaukset.lisaa(alku3); 
      * kirjaukset.etsiId(id1+1) === 1; 
      * kirjaukset.etsiId(id1+2) === 2; 
      * </pre> 
      */ 
     public int etsiId(int id) { 
         for (int i = 0; i < lkm; i++) 
             if (id == alkiot[i].getId()) return i; 
         return -1; 
     } 
     
    
    /**Testataan luokkaa Päiväkirja
     * @param args ei käytössä
     */
     public static void main(String args[]) {
            Kirjaukset kirjaukset = new Kirjaukset();

            Kirjaus olo = new Kirjaus(), 
            olo2 = new Kirjaus(), olo3 = new Kirjaus();
            olo.rekisteroi();
            olo.vastaaMerkinta(1, 1);
            olo2.rekisteroi();
            olo2.vastaaMerkinta(2, 1);
            olo3.rekisteroi();
            olo3.vastaaMerkinta(3,2);

            kirjaukset.lisaa(olo);
            kirjaukset.lisaa(olo2);
            kirjaukset.lisaa(olo3);
            System.out.println(kirjaukset.getLkm());
            try {
                kirjaukset.tallenna();
            } catch (SailoException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


                System.out.println("============= kirjaukset testi =================");

                for (int i = 0; i < kirjaukset.getLkm(); i++) {
                    Kirjaus merkinta = kirjaukset.anna(i);
                    System.out.println("Kirjaus nro: " + i);
                    merkinta.tulosta(System.out);
                }
            }

   
     }

                

           

    

