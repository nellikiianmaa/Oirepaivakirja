package oirepaivakirja;

import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;

/**Tuntee Laakkeen kentät, muuttaa merkkijonon laakkeen tiedoiksi ja
 * järjestelee ja antaa tietoja indeksin perusteella
 * @author Nelli Kiianmaa
 * @version1.1  17.3.2020
 * @version1.2 31.3.2020
 *
 */
public class Laake implements Comparable<Laake>{
    
    private int laake_id = 0;
    private String laake = "";
    private static int seuraavaNro=0;

    
    /**Parmetriton muodostaja
     */
    public Laake() {

    }
    
    
    /**Muodostaja
     * @param laake_id laakkeen id
     */
    public Laake(int laake_id) {
      this.laake_id = laake_id;
      this.laake = vastaaLaake();  
    }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot laakeelle
     * @return laake
     */
    public String vastaaLaake() {
        //Random r = new Random();
        String[] lista = {"parasetamoli 1000 mg","ibuprofeeni 200 mg","ketoprofeeni 100 mg","hieronta",
                "nukkuminen", "rentoutus", "estolääke 100 mg", "kylmäpakkaus", "kkk",
                "kohtauslääke 100 mg", "antibiootti 100 mg", "kortisonivoide 2%", "perusvoide",
                "puhuminen", "yskänlääke", "allergiasuihke"};

        //laake = lista[r.nextInt(lista.length)];
        laake = lista[seuraavaNro];
        
        return laake;
    }
    
    
    /**
     * Tulostetaan laakeen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(laake_id + " " + laake);
    }

    /**
     * Tulostetaan laakeen tiedot (kerhossa henkilön tiedot)??
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Antaa lääkkeelle seuraavan rekisterinumeron.
     * @return lääkkeelle uusi tunnus_nro
     * @example
     * <pre name="test">
     *   Laake sarky = new Laake();
     *   sarky.rekisteroi();
     *   Laake sarky2 = new Laake();
     *   sarky2.rekisteroi();
     *   int n1 = sarky.getLaakeId();
     *   int n2 = sarky2.getLaakeId();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        seuraavaNro++;//0 on tyhjä arvo, koska kentän voi jättää tyhjäksi
        laake_id = seuraavaNro;
        return laake_id;
    }
    
    /**Uudelle lääkkeelle id (kun rekisterissä on käsin lisättyjä) (periaatteessa sama kuin setLaakeId(int nr))
     * @param lkm lääkkeiden lukumäärä rekisterissä
     * @return laake_id
     */
    public int rekisteroi(int lkm) {
        seuraavaNro = lkm;
        seuraavaNro++;
        laake_id = seuraavaNro;
        return laake_id;
    }
    
    /**
     * Asettaa laake_id:n ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    @SuppressWarnings("unused")
    private void setLaakeId(int nr) {
        laake_id = nr;
        if (laake_id >= seuraavaNro) seuraavaNro = laake_id + 1;
    }

    /**
     * Palautetaan lääkkeen oma id
     * @return lääkkeen id
     */
    public int getLaakeId() {
        return laake_id;
    }
    
    /**
     * Palautetaan laakeen selite
     * @return laakeen selite
     */
    public String getLaake() {
        return laake;
    }
    
    /**Asetetaan lääkkeen selite syötteen perusteella
     * @param s syöte
     * @return laake
     */
    public String setLaake(String s) {
        laake = s;
        return null;
    }
 
    /**
     * Palauttaa oireen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return kirjaus tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Laake laake = new Laake();
     *   laake.parse(" 2 |   ibuprofeeni 200 mg ");
     *   laake.toString().startsWith("2|ibuprofeeni 200 mg") === true; 
     * </pre>  
     */
    @Override
    public String toString() {
        return laake_id + "|" +  laake; 

    }


    /**
     * Selvitää laakkeen tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva laake_id.
     * @param rivi josta harrastuksen tiedot otetaan
     */

    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        laake_id = Mjonot.erota(sb, '|', laake_id);
        laake = Mjonot.erota(sb, '|', laake);
    }
    

    @SuppressWarnings("hiding")
    @Override
    public boolean equals(Object laake) {
        if ( laake == null ) return false;
        return this.toString().equals(laake.toString());
    }


    @Override
    public int hashCode() {
        return laake_id;
    }
    
    /**Oireiden nimien vertailija
     * Tarvitaan käyttöliittymän oirelistan sorttaamiseksi aakkosjärjestykseen
     */
    @Override
    public int compareTo(Laake verrattava) {
        return getLaake().compareTo(verrattava.getLaake());    
    }

    /**
     * Uusi laake 
     * @param s syöte käyttöliittymästä, uuden laakkeen nimi
     * @param lkm laakkeiden lukumäärä rekisterissä
     * @return null
     */
    public String setLaake(String s, int lkm) {
           laake = s;
           laake_id = lkm + 1;
           return null;
       }   

    /**
     * Testiohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Laake laake = new Laake();
        Laake laake2 = new Laake();
        Laake laake3 = new Laake();
        laake.vastaaLaake();//ensin tämä ja sitten vasta rekisteröidään
        laake.rekisteroi();
        laake.tulosta(System.out);
        laake2.vastaaLaake();
        laake2.rekisteroi();
        laake2.tulosta(System.out);
        laake3.vastaaLaake();
        laake3.rekisteroi();
        laake3.tulosta(System.out);
    }
}


