package kanta;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Alustava luokka päivämäärää varten
 * @author Vesa Lappalainen
 * @version 1.0, 07.02.2003
 * @version 1.1, 14.02.2003
 * @version 1.2, 17.02.2003
 * @version 1.3, 11.02.2008
 */
public class Pvm {

    private int pv;
    private int kk;
    private int vv;

    /** Taulukko kuukausien pituuksista. Oma rivi  karkausvuosille */
    public static final int KPITUUDET[][] = {
            // 1  2  3  4  5  6  7  8  9 10 11 12
            { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
            { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }
    };

    /**
     * Palautetaan tieto siitÃ¤ onko tutkittava vuosi karkausvuosi vai ei
     * @param vv tutkittava vuosi
     * @return 1 jos on karkausvuosi ja 0 jos ei ole
     * @example
     * <pre name="test">
     *   karkausvuosi(1900) === 0
     *   karkausvuosi(1900) === 0
     *   karkausvuosi(1901) === 0
     *   karkausvuosi(1996) === 1
     *   karkausvuosi(2000) === 1
     *   karkausvuosi(2001) === 0
     *   karkausvuosi(2004) === 1
     * </pre>
     */
    public static int karkausvuosi(int vv) {
        if ( vv % 400 == 0 ) return 1;
        if ( vv % 100 == 0 ) return 0;
        if ( vv % 4 == 0 ) return 1;
        return 0;
    }
    
   
    
    /**
     *  Muuttaa päivämäärän nykypäivälle.
     *  Todo: pitää vaihtaa hakemaan päivämäärä oikeasti. 
     *  Mutta perinnässäkään ei nyt saa käyttää enempää attribuutteja kuin tässä on
     */
    public void paivays() {
        pv = 13;
        kk = 2;
        vv = 2012;
    }


    /**
     * Alustetaan päivämäärä. 0-arvot eivät muuta vastaavaa attribuuttia  
     * @param ipv päivän alustus
     * @param ikk kuukauden alustus
     * @param ivv vuoden alustus
     * @example 
     * <pre name="test">
     * Pvm pvm = new Pvm(21, 1, 2015);
     * pvm.toString() === "21.1.2015";
     *
     * pvm.alusta(29, 2, 2015);
     * pvm.toString() === "21.1.2015";
     *
     * pvm.alusta(0, 2, 2015);
     * pvm.toString() === "21.2.2015";
     *
     * pvm.alusta(0,0,2013);
     * pvm.toString() === "21.2.2013";
     *
     * pvm.alusta(0, 13, 2014);
     * pvm.toString() === "21.2.2013";
     * </pre>

     */
    public final void alusta(int ipv, int ikk, int ivv) {
          if (ivv < 0 || ikk < 0 || ikk > 12 ||
                    ipv < 0 || ipv > KPITUUDET[karkausvuosi(vv)][kk-1]) {
                    this.vv = getVv();
                    this.kk = getKk();
                    this.pv = getPv();
                    } 
            else {
            if ( ivv > 0 ) {this.vv = ivv; 
                if ( this.vv < 50 ) this.vv += 2000;
                if ( this.vv < 100 ) this.vv += 1900;
                }
               if (ivv == 0) this.vv = getVv();
            
            if (ikk > 0 && ikk <= 12) this.kk = ikk; else this.kk = getKk();
            if (ipv > 0 && ipv<= KPITUUDET[karkausvuosi(vv)][kk-1]) this.pv = ipv; else this.pv = getPv();
            }
           
            }
       
        


    /** Alustetaan kaikki attribuutit oletusarvoon */
    public Pvm() {
        this(0, 0, 0);
    }


    /** 
     * Alustetaan kuukausi ja vuosi oletusarvoon
     * @param pv päivän alustusarvo
     */
    public Pvm(int pv) {
        this(pv, 0, 0);
    }


    /**
     * Alustetaan vuosi oletusarvoon
     * @param pv päivän alustusarvo
     * @param kk kuukauden oletusarvo
     */
    public Pvm(int pv, int kk) {
        this(pv, kk, 0);
    }


    /**
     * Alustetaan vuosi oletusarvoon
     * @param pv päivän alustusarvo
     * @param kk kuukauden oletusarvo
     * @param vv vuoden alustusarvo
     */
    public Pvm(int pv, int kk, int vv) {
        paivays();
        alusta(pv, kk, vv);
    } 


    /**
     * Alustetaan päivämäärä merkkijonosta
     * @param s muotoa 12.3.2008 oleva merkkijono
     */
    public Pvm(String s) {
        paivays();
        pvmParse(s);
    } 


    /**
     * Päivämäärä merkkijonona
     * @return päivämäärä muodossa 17.2.2007
     * @example
     * <pre name="test">
     *   Pvm tammi2011 = new Pvm(1,1,2011);
     *   tammi2011.toString() === "1.1.2011"
     *   Pvm helmi2011 = new Pvm(1,2,2011);
     *   helmi2011.toString() === "1.2.2011"
     *   Pvm tanaan = new Pvm(14,2,2011);
     *   tanaan.toString()    === "14.2.2011"
     *   Pvm maalis97 = new Pvm(1,3,1997);
     *   maalis97.toString()  === "1.3.1997"
     * </pre>
     */
    @Override
    public String toString() {
        return pv + "." + kk + "." + vv;
    }


    /**
     * Ottaa päivämäärän tiedot merkkijonosta joka on muotoa 17.2.2007
     * Jos joku osa puuttuu, sille käytetään tämän päivän arvoa oletuksena.
     * @param sb tutkittava merkkijono
     */
    protected final void pvmParse(StringBuilder sb) {
        int p = Mjonot.erota(sb, '.', 0);
        int k = Mjonot.erota(sb, '.', 0);
        int v = Mjonot.erota(sb, ' ', 0);
        alusta(p, k, v);
        // tai alusta(Mjonot.erota(sb,'.',0),Mjonot.erota(sb,'.',0),Mjonot.erota(sb,'.',0));
    }


    /**
     * Ottaa päivämäärän tiedot merkkijonosta joka on muotoa 17.2.2007
     * Jos joku osa puuttuu, sille käytetään tämän päivän arvoa oletuksena.
     * @param s tutkittava merkkijono
     */
    protected final void pvmParse(String s) {
        pvmParse(new StringBuilder(s));
    }


    /**
     * Ottaa päivämäärän tiedot merkkijonosta joka on muotoa 17.2.2007
     * Jos joku osa puuttuu, sille käytetään tämän päivän arvoa oletuksena.
     * @param s tutkittava merkkijono
     * 
     * @example
     * <pre name="test">
     * Pvm pvm = new Pvm(11,3,2003);
     * pvm.parse("12");           pvm.toString() === "12.3.2003";
     * pvm.parse("..2001");       pvm.toString() === "12.3.2001";
     * pvm.parse("..2009 14:30"); pvm.toString() === "12.3.2009"; 
     * </pre>
     */
    public void parse(String s) {
        pvmParse(s);
    }


    /**
     * Ottaa päivämäärän tiedot merkkijonosta joka on muotoa 17.2.2007
     * Jos joku osa puuttuu, sille käytetään tämän päivän arvoa oletuksena.
     * Merkkijonosta otetaan pois vain se osa, jota tarvitaan.
     * @param sb tutkittava merkkijono
     * 
     * @example
     * <pre name="test">
     * Pvm pvm = new Pvm(11,3,2003);
     * StringBuilder jono = new StringBuilder("12");
     * pvm.parse(jono); pvm.toString() === "12.3.2003"; jono.toString() === "";
     * jono = new StringBuilder("..2001");
     * pvm.parse(jono); pvm.toString() === "12.3.2001"; jono.toString() === "";
     * jono = new StringBuilder("..2009 14:30");
     * pvm.parse(jono); pvm.toString() === "12.3.2009"; jono.toString() === "14:30";
     * </pre>
     */
    public void parse(StringBuilder sb) {
        pvmParse(sb);
    }


    // Lisätty saantimetodit:

    /**
     * @return päivän arvo
     * @example
     * <pre name="test">
     *   Pvm pv = new Pvm("14.2.2011");
     *   pv.getPv() === 14;
     * </pre>
     */
    public int getPv() {
        return pv;
    }


    /**
     * @return kuukauden arvo
     *  * @example
     * <pre name="test">
     *   Pvm pv = new Pvm("14.2.2011");
     *   pv.getKk() === 2;
     * </pre>
     */
    public int getKk() {
        return kk;
    }


    /**
     * @return vuoden arvo
     * @example
     * <pre name="test">
     *   Pvm pv = new Pvm("14.2.2011");
     *   pv.getVv() === 2011;
     * </pre>
     */
    public int getVv() {
        return vv;
    }
    
    /**
     * @param pv1 ensimmäinen päivämääräolio 
     * @param pv2 toinen päivämääräolio
     * @return -1, jos pv1 aikaisempi, 0, jos samat, 1, jos pv1 myöhäisempi
     * @example
     * <pre name="test">
     * Pvm a = new Pvm(13,12,1974); Pvm b = new Pvm(3,1,1984);
     *  compareTo(a, b)  === -1;
     *  compareTo(b, a) === 1;
     *  Pvm c = new Pvm(3,12,1974); Pvm d = new Pvm(13,12,1974);
     *  compareTo(c, d)  === -1;
     *  compareTo(d, c) === 1;
     *  Pvm e = new Pvm(13,11,1974); Pvm f = new Pvm(13,12,1974);
     *  compareTo(e, f)  === -1;
     *  compareTo(f, e) === 1; 
     *  Pvm g = new Pvm(13,12,1974); Pvm h = new Pvm(13,12,1974);
     *  compareTo(g, h)  === 0;
     *  compareTo(h, g) === 0; 
     * </pre>
     */
    public static int compareTo (Pvm pv1, Pvm pv2) {
        if (pv1.getVv() > pv2.getVv()) return 1;
        if (pv1.getVv() < pv2.getVv()) return -1;
        if (pv1.getKk() > pv2.getKk()) return 1;
        if (pv1.getKk() < pv2.getKk()) return -1; 
        if (pv1.getPv() > pv2.getPv()) return 1;
        if (pv1.getPv() < pv2.getPv()) return -1;
        return 0;
        
    }
    
    
    /**
     * @param pv2 toinen päivämääräolio
     * @return -1, jos olion pvm aikaisempi, 0, jos samat, 1, jos olion oma pvm myöhäisempi
     * @example
     * <pre name="test">
     * Pvm a = new Pvm(13,2,2012);
     * Pvm x = new Pvm(13,2,2012);
     * x.compareTo(a)  === 0;
     * Pvm b = new Pvm(1,1,2002); 
     * x.compareTo(b)  === 1;
     * Pvm c = new Pvm(13,12,1980); 
     * x.compareTo(c)  === -1;
     * </pre>
     */
    public int compareTo (Pvm pv2) {
        if (getPv() > pv2.getVv()) return 1;
        if (getVv() < pv2.getVv()) return -1;
        if (getKk() > pv2.getKk()) return 1;
        if (getKk() < pv2.getKk()) return -1; 
        if (getPv() > pv2.getPv()) return 1;
        if (getPv() < pv2.getPv()) return -1;
        return 0;  
    }

    /**
     * @param pv2 päivämäärä, johon verrataan
     * @return onko sama päivämäärä
     * @example
     * <pre name="test">
     * Pvm x = new Pvm(13,12,1980);
     * Pvm t = new Pvm(3,12,1999);
     * Pvm w = new Pvm(13,12,1980);
     * x.equals(w)  === true;
     * x.equals(t)  === false;
     * t.equals(x) === false;
     * w.equals(x)=== true;
     * t.equals(t)===true;
     * </pre>
     */
    public boolean equals(Pvm pv2) {
        if (compareTo(pv2)== 0) return true;
        return false;

      }


    /**
     * Testataan päivämäärä-luokkaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Pvm pvm = new Pvm();
        pvm.parse("12.1.1995");
        System.out.println(pvm);
        pvm.parse("15.3");
        System.out.println(pvm);
        pvm.parse("14");
        System.out.println(pvm.getPv());
        pvm.parse("-1.6");
        System.out.println(pvm);
        pvm.alusta(20, 11, 2011);
        System.out.println(pvm);
        pvm.alusta(29, 2, 2015);
        System.out.println(pvm);

    }
    
}

