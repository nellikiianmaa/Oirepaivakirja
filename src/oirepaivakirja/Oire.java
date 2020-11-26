package oirepaivakirja;

import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;

/**Tuntee Oireen kentät, muuttaa merkkijonon oireen tiedoiksi ja
 * järjestelee ja antaa tietoja indeksin perusteella
 * @author Nelli Kiianmaa, nelli.j.kiianmaa@student.jyu.fi
 * @version1.1 18.2.2020
 * @version1.2 31.3.2020
 * @version1.3 23.4.2020 - toimii käyttöliittymän kanssa
 *
 */
public class Oire implements Comparable<Oire>{
    
    private int oire_id = 0;
    private String vointi = "";
    private static int seuraavaNro=0;

    
    /**Parmetriton muodostaja
     */
    public Oire() {

    }
    
    
    /**Muodostaja
     * @param oire_id oireen id
     */
    public Oire(int oire_id) {
      this.oire_id = oire_id;
      this.vointi = vastaaOire();  
    }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot Oireelle
     * @return oire
     */
    public String vastaaOire() {
        //Random r = new Random();
        String[] oirelista = {"pääkipu","mahakipu","makeanhimo","niskasärky",
                "oksentelu", "mania", "alakulo", "kuumat aallot", "ihottuma",
                "unettomuus", "kuiva yskä", "korkea kuume", "hengenahdistus",
                "palelu", "lihassärky", "hikoilu"};

        vointi = oirelista[seuraavaNro];
        return vointi;
    }
    
    
    /**
     * Tulostetaan oireen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(vointi);
    }

    /**
     * Tulostetaan oireen tiedot 
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Antaa oireelle seuraavan rekisterinumeron.
     * @return oireen tunnus_nro
     * @example
     * <pre name="test">
     *   Oire sarky = new Oire();
     *   sarky.rekisteroi();
     *   Oire sarky2 = new Oire();
     *   sarky2.rekisteroi();
     *   int n1 = sarky.getOireId();
     *   int n2 = sarky2.getOireId();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        seuraavaNro++;//0 on tyhjä arvo, koska kentän voi jättää tyhjäksi
        oire_id = seuraavaNro;
        return oire_id;
    }
    
    
    
    /**Uudelle oireelle id (kun rekisterissä on käsin lisättyjä oireita) (periaatteessa sama kuin setOireId(int nr))
     * @param lkm oireiden lukumäärä rekisterissä
     * @return oire_id
     */
    public int rekisteroi(int lkm) {
        seuraavaNro = lkm;
        seuraavaNro++;
        oire_id = seuraavaNro;
        return oire_id;
    }
    
    
    /**
     * Asettaa oireid:n ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    @SuppressWarnings("unused")
    private void setOireId(int nr) {
        oire_id = nr;
        if (oire_id >= seuraavaNro) seuraavaNro = oire_id + 1;
    }
    

    /**
     * Palautetaan oireen oma id
     * @return oireen id
     */
    public int getOireId() {
        return oire_id;
    }
    
    /**
     * Palautetaan oireen selite
     * @return oireen selite
     */
    public String getVointi() {
        return vointi;
    }



    /**Asetetaan oireen selite syötteen perusteella
     * @param s syöte
     * @return vointi
     */
    public String setVointi(String s) {
        vointi = s;
        return null;
    }
    
    
            /**
             * Palauttaa oireen tiedot merkkijonona jonka voi tallentaa tiedostoon.
             * @return kirjaus tolppaeroteltuna merkkijonona 
             * @example
             * <pre name="test">
             *   Oire oire = new Oire();
             *   oire.parse(" 2  | mahakipu");
             *   oire.toString().startsWith("2|mahakipu") === true; 
             * </pre>  
             */
            @Override
            public String toString() {
                return oire_id + "|" +  vointi; 
        
            }


            /**
             * Selvitää oireen tiedot | erotellusta merkkijonosta.
             * Pitää huolen että seuraavaNro on suurempi kuin tuleva oire_id.
             * @param rivi josta harrastuksen tiedot otetaan
             */

            public void parse(String rivi) {
                StringBuffer sb = new StringBuffer(rivi);
                oire_id = Mjonot.erota(sb, '|', oire_id);
                vointi = Mjonot.erota(sb, '|', vointi);
            }
            
    
            @Override
            public boolean equals(Object oire) {
                if ( oire == null ) return false;
                return this.toString().equals(oire.toString());
            }
        
        
            @Override
            public int hashCode() {
                return oire_id;
            }
            
            /**Oireiden nimien vertailija
             * Tarvitaan käyttöliittymän oirelistan sorttaamiseksi aakkosjärjestykseen
             */
            @Override
            public int compareTo(Oire verrattava) {
                return getVointi().compareTo(verrattava.getVointi());    
            }
    
            /**
             * Uusi oire 
             * @param s syöte käyttöliittymästä, uuden oireen nimi
             * @param lkm oireiden lukumäärä rekisterissä
             * @return null
             */
            public String setOire(String s, int lkm) {
                   vointi = s;
                   oire_id = lkm + 1;
                   return null;
               }   
            
    /**
     * Testiohjelma Harrastukselle.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Oire oire = new Oire();
        Oire oire2 = new Oire();
        Oire oire3 = new Oire();
        oire.vastaaOire();
        oire.rekisteroi();
        oire.tulosta(System.out);
        oire2.vastaaOire();
        oire2.rekisteroi();
        oire2.tulosta(System.out);
        oire3.vastaaOire();
        oire3.rekisteroi();
        oire3.tulosta(System.out);
        
    }  
}



  

 




