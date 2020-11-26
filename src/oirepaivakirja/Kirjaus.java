package oirepaivakirja;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Random;
import fi.jyu.mit.ohj2.Mjonot;

/**Tuntee Kirjausten kentät, muuttaa merkkijonon kirjausten tiedoiksi ja 
  * järjestelee ja antaa tietoja indeksin perusteella.
  * @author Nelli Kiianmaa
  * @version1.1 17.3.2020
  * @version1.2 30.3.2020
  * @version1.3 30.3.2020
  *
  */
public class Kirjaus implements Cloneable, Comparable<Kirjaus> {
    
    private int id = 0;
    private  String pvm = "";
    private String klo = "";
    private int oire1 = 0;
    private int oire2 = 0;
    private int oire3 = 0;
    private int laake1 = 0;
    private int laake2 = 0;
    private int laake3 = 0;
    private String lisatiedot = "";
    private static int seuraavaNro    = 1;//0 on tyhjä
    
    
    
  /**Rekisteröidään uusi päiväkirjamerkintä
   * @return merkinnän id
   * @example
   * <pre name="test">
   * Kirjaus kirjaus = new Kirjaus();
   * kirjaus.rekisteroi()=== 3;
   * kirjaus.rekisteroi()=== 4;
   * kirjaus.rekisteroi()=== 5;
   * </pre>
   */
   public int rekisteroi() {
      id = seuraavaNro;
      seuraavaNro++;
      return id;
   }



   /**Luo merkinnän tiedot testaamista varten
 * @param oireita kuinka monta oiretta on rekisteröity (joukko, josta arvotaan)
 * @param laakkeita kuinka monta lääkettä on rekisteröity (joukko, josta arvotaan)
  * 
   */
    public void vastaaMerkinta(int oireita, int laakkeita) {
       Random r = new Random();
       int p = r.nextInt(27)+1;
       int k = r.nextInt(4)+1;
       int v = 2020;
       pvm = p + "." + k + "." + v;
       klo = "12:00";
       
       //oireiden arvonta
       int i;
       int j;
       int maara=3;//3 eri oiretta
       
       int[] arvat = new int[maara];
       if(maara > oireita) maara=oireita;
       int[] luvut = new int[oireita];
       
       for(j=0; j<oireita; j++) {
           luvut[j] = j+1;
       }
       
       for(i=0; i<maara; i++) {
         int maxind = oireita-i;  
         int indeksi = r.nextInt(maxind);
         arvat[i] = luvut[indeksi];
         maxind = oireita-i-1;
         luvut[indeksi] = luvut[maxind];
       }
             
       oire1 = arvat[0]; arvat[0] = 0;
       oire2 = arvat[1]; arvat[1] = 0;
       oire3 = arvat[2]; arvat[2] = 0;
       
       //laakkeiden arvonta
       maara =3 ;//3 eri laaketta
       if(maara > laakkeita) maara=laakkeita;
       luvut = new int[laakkeita];
       
       for(j=0; j<laakkeita; j++) {
           luvut[j] = j+1;
       }
       
       for(i=0; i<maara; i++) {
           int maxind = laakkeita-i;  
           int indeksi = r.nextInt(maxind);
           arvat[i] = luvut[indeksi];
           maxind = laakkeita-i-1;
           luvut[indeksi] = luvut[maxind];
         }
               
       laake1 = arvat[0];
       laake2 = arvat[1];
       laake3 = arvat[2];
       
       String[] lisat = {"Stressaava viikko","Söin suklaata","Opiskelin koko yön","Pitkä treeni","","","","", "", ""};
       lisatiedot = (lisat[new Random().nextInt(lisat.length)]);
       if (lisatiedot == "") lisatiedot = "";
     }




    /**Tulostetaan kirjauksen tiedot 
     * @param out tietovirta johon tulostetaan
     */
       public void tulosta(PrintStream out) {
           out.println(String.format("%03d", id, 3) + "  " + pvm + " " + klo);
           out.println("Oireet: " + oire1 + " " + oire2 + " " + oire3 + ", Lääkkeet:"
           + laake1 + " " + laake2 + " " + laake3 + " ");
           out.println(lisatiedot);
           out.println("________________________________________");
           
       }

   /**
   * Tulostetaan kirjauksen tiedot
   * @param os tietovirta johon tulostetaan
   */
    public void tulosta(OutputStream os) {
         tulosta(new PrintStream(os));
    }
    


    /**Palauttaa kellonajan
     * @return klo
     */
    public String getKlo() {
        return klo;
    }



    /**Palauttaa päivämäärän
     * @return pvm
     */ 
    public String getPvm() {
        return pvm;
    }


    
     /**Palauttaa oireen1 tunnusnumeron (id:n)
      * @return oireen id
       */
      public int getOire1() {
             return oire1;
        }
      
  
     
     /**Palauttaa oireen1 tunnusnumeron (id:n)
      * @return oireen id
      */
      public int getOire2() {
             return oire2;
      }
      
      
      /**Palauttaa oireen1 tunnusnumeron (id:n)
       * @return oireen id
       */
       public int getOire3() {
              return oire3;
       }
       
       
       /**Palauttaa laakkeen1 tunnusnumeron (id:n)
        * @return lääkkeen id
         */
        public int getLaake1() {
               return laake1;
          }
       
       /**Palauttaa laakkeen2 tunnusnumeron (id:n)
        * @return lääkkeen id
        */
        public int getLaake2() {
               return laake2;
        }
        
        
        /**Palauttaa laakkeen tunnusnumeron (id:n)
         * @return lääkkeen id
         */
         public int getLaake3() {
                return laake3;
         }
         
         
         /**Palautetaan lisätiedot
          * @return lisatiedot
          */
         public String getLisatiedot() {
              return lisatiedot;
          }
         

       /**Palauttaa kirjauksen tunnusnumeron (id:n)
        * @return kirjauksen id
        */
        public int getId() {
               return id;
        }
    
        /**
         * Asettaa id:n ja samalla varmistaa että
         * seuraava numero on aina suurempi kuin tähän mennessä suurin.
         * @param nr asetettava tunnusnumero
         */
        private void setId(int nr) {
            id = nr;
            if (id >= seuraavaNro) seuraavaNro = id + 1;
        }

    /**
     * Palautetaan mihin oireeseen oire_id kuuluu
     * @param i oireen indeksi
     * @param oireid oireen tunnusnro
     */
     @SuppressWarnings("unused")
    public void setOire1Id(int i, int oireid) {
       oire1 = oireid;
    }
     
     /**
      * Palautetaan mihin oireeseen oire_id kuuluu
      * @param i oireen indeksi
      * @param oireid oireen tunnusnro
      */
      @SuppressWarnings("unused")
    public void setOire2Id(int i, int oireid) {
        oire2= oireid;
     }
      
      /**
       * Palautetaan mihin oireeseen oire_id kuuluu
       * @param i oireen indeksi
       * @param oireid oireen tunnusnro
       */
       @SuppressWarnings("unused")
    public void setOire3Id(int i, int oireid) {
         oire3 = oireid;
      }
     
 
    /**Palauttaa merkinnän tunnusnumeron (id:n) päiväkirjan riville. 
     * @return merkinnän id
     * @example
     * <pre name="test">
     * Kirjaus voinnit = new Kirjaus();
     * voinnit.rekisteroi();
     * voinnit.getRivi() === "  6                     "
     * voinnit.rekisteroi();
     * voinnit.getRivi() === "  7                     ";
     * </pre>
     */
     public String getRivi() {  //get-metodit sanallisille nimille
         return /*String.format("%3d", id, 3)*/"   " + "        " + getPvm() + "      " + getKlo() +"       " ;
         //return String.format("%03d", id, 3); 
     }

     /**
      * Palauttaa kirjauksen tiedot merkkijonona jonka voi tallentaa tiedostoon.
      * @return kirjaus tolppaeroteltuna merkkijonona 
      * @example
      * <pre name="test">
      *   Kirjaus kirjaus = new Kirjaus();
      *   kirjaus.parse(" 2  | 12.1.2020|  12:00  ");
      *   kirjaus.toString().startsWith("2|12.1.2020|12:00|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
      * </pre>  
      */
     @Override
     public String toString() {
         return "" +
                 getId() + "|" +
                 pvm + "|" +
                 klo + "|" +
                 oire1 + "|" +
                 oire2 + "|" +
                 oire3 + "|" +
                 laake1 + "|" +
                 laake2 + "|" +
                 laake3 + "|" +
                 lisatiedot;
     }


     /**
      * Selvitää jäsenen tiedot | erotellusta merkkijonosta
      * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
      * @param rivi josta jäsenen tiedot otetaan
      * 
      * @example
      * <pre name="test">
      *   Kirjaus kirjaus = new Kirjaus();
      *   kirjaus.parse(" 2  | 12.1.2020|  12:00  ");
      *   kirjaus.getId() === 2;
      *   kirjaus.toString().startsWith("2|12.1.2020|12:00|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
      *
      *   kirjaus.rekisteroi();
      *   int n = kirjaus.getId();
      *   kirjaus.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
      *   kirjaus.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
      *   kirjaus.getId() === n+20+1;
      *     
      * </pre>
      */
     public void parse(String rivi) {
         StringBuffer sb = new StringBuffer(rivi);
         setId(Mjonot.erota(sb, '|', getId()));
         pvm = Mjonot.erota(sb, '|', pvm);
         klo = Mjonot.erota(sb, '|', klo);
         oire1 = Mjonot.erota(sb, '|', oire1);
         oire2 = Mjonot.erota(sb, '|', oire2);
         oire3 = Mjonot.erota(sb, '|', oire3);
         laake1 = Mjonot.erota(sb, '|', laake1);
         laake2 = Mjonot.erota(sb, '|', laake2);
         laake3 = Mjonot.erota(sb, '|', laake3);
         lisatiedot = Mjonot.erota(sb, '|', lisatiedot);
     }
     
     
     @Override
     public boolean equals(Object kirjaus) {
         if ( kirjaus == null ) return false;
         return this.toString().equals(kirjaus.toString());
     }


     @Override
     public int hashCode() {
         return id;
     }
     

     /**Asettaa päivämäärän
     * @param s annettu syöte
     * @return null
     * @example
     * <pre name="test">
     * Kirjaus kirjaus = new Kirjaus();
     * kirjaus.setPvm("1.1.2000") === null;
     * kirjaus.setPvm("1.16.2000") === "Virhe päiväyksessä!";
     * </pre>
     */
    public String setPvm(String s) {
        if (tarkistaPaiva(s)==null) { 
            pvm = s;
            return null;
        }
        return tarkistaPaiva(s); 

    } 

    
    /**Tarkistaa päivämäärän oikeellisuuden
     * @param s uusi arvo
     * @return palauttaa null, jos oikein ja virheilmoituksen, jos virhe
     * * @example
     * <pre name="test">
     * tarkistaPaiva("12.4.2019")     === null;
     * tarkistaPaiva("kissa")         === "Vääriä merkkejä!";
     * tarkistaPaiva("12.2.")        ===  "Korjaa päiväys!";
     * tarkistaPaiva("12 23")         === "Vääriä merkkejä!";
     * tarkistaPaiva("12.19.2020")    === "Väärä kuukausi!";
     * tarkistaPaiva("10.19.2020")   === "Väärä kuukausi!";
     * tarkistaPaiva("1223")          ===  "Korjaa päiväys!";
     * tarkistaPaiva("12.3.2020")     === null;
     * tarkistaPaiva("12:23:00.0.0")      === "Vääriä merkkejä!";
     * tarkistaPaiva("29.2.2020")         === null;
     * tarkistaPaiva("12")                === "Korjaa päiväys!"
     * tarkistaPaiva("")                  === "Lisää päiväys!";
     * tarkistaPaiva(null)                === "Jono null";
     * tarkistaPaiva("31.4.2020")          === "Väärä päivä!";
     * </pre>
     */
    public static String tarkistaPaiva (String s) {
         
         if ( s == null ) return "Jono null";
         if ( s.equals("") ) return "Lisää päiväys!";
         
        //Jos sisältää vääriä merkkejä
        String sallitut = "0123456789.";
        int vali = 0;
        char merkki = ' ';
        for (int i=0; i<s.length(); i++) {
            if (merkki == '.') vali +=1;//pisteiden määrälaskuri
            for (int j=0; j<sallitut.length(); j++) {
                merkki = s.charAt(i);
                char verrattava = sallitut.charAt(j); 
                if (merkki!=verrattava && j==sallitut.length()-1) return "Vääriä merkkejä!";
                if (merkki==verrattava) break;
            }    
        }

        if (vali != 2) return "Korjaa päiväys!";
        
        //Parse päivä, kk ja vuosi;
        StringBuilder sb = new StringBuilder(s);
        int p = Mjonot.erota(sb, '.', 0);
        int k = Mjonot.erota(sb, '.', 0);
        int v = Mjonot.erota(sb, ' ', 0); 
        

        //Onko liian iso pvm?
        if (v > getNytVv()) return "Väärä vuosi!";
        if ( v== getNytVv() && k > getNytKk()) return "Väärä kuukausi!";
        if (v == getNytVv() && k == getNytKk() && p > getNytPv()) return "Väärä päivä!";

        //kuukausien pituudet oikein
        return tutkiPaiva(p, k, v);
    }
    
    /**Tutkii, onko oikeellinen päivämäärä
     * @param ipv päivä
     * @param ikk kuukausi
     * @param ivv vuosi
     * @return null, jos ok, virheilmo, jos väärin
     * @example
     * <pre name="test">
     * tutkiPaiva(28,2,2020) === null;
     * tutkiPaiva(30,2,2020) === "Virhe päiväyksessä!";
     * </pre>
     */
    public static String tutkiPaiva(int ipv, int ikk, int ivv) {
        if (ivv < 1915 || 
                ikk < 1 || 
                ikk > 12 ||
                  ipv < 1 || ipv > kanta.Pvm.KPITUUDET[kanta.Pvm.karkausvuosi(ivv)][ikk-1]) 
          return "Virhe päiväyksessä!";
          return null;
          }
    
 
   /**
     * Palauttaa kalenterin vuoden
     * @return tämä vuosi
     */  
    public static int getNytVv() {
        Calendar nyt = Calendar.getInstance();
        return nyt.get(Calendar.YEAR);
    }
    
    /**
     * Palauttaa kalenterin vuoden
     * @return tämä vuosi
     */  
    public static int getNytKk() {
        Calendar nyt = Calendar.getInstance();
        return nyt.get(Calendar.MONTH) + 1;
    }
    
    /**
     * Palauttaa kalenterin vuoden
     * @return tämä vuosi
     */  
    public static int getNytPv() {
        Calendar nyt = Calendar.getInstance();
        return nyt.get(Calendar.DATE);
    }
    
    /**
     * Palauttaa kalenterin vuoden
     * @return tämä vuosi
     */  
    public int getVv() {
        //Parse päivä, kk ja vuosi;
        StringBuilder sb = new StringBuilder(getPvm());
        int p = Mjonot.erota(sb, '.', 0); 
        int k = Mjonot.erota(sb, '.', 0);
        int v = Mjonot.erota(sb, ' ', 0); 
        int yli = p + k;//kikkailu, kun näyttää punaista muuten
        yli = 0; 
        return v + yli;
    }
    
    /**
     * Palauttaa kalenterin vuoden
     * @return tämä vuosi
     */  
    public int getKk() {
        StringBuilder sb = new StringBuilder(getPvm());
        int p = Mjonot.erota(sb, '.', 0);
        int k = Mjonot.erota(sb, '.', 0);
        p = 0; //kikkailua
        return k + p;
    }
    
    /**
     * Palauttaa kalenterin vuoden
     * @return tämä vuosi
     */  
    public int getPv() {
        StringBuilder sb = new StringBuilder(getPvm());
        int p = Mjonot.erota(sb, '.', 0);
        return p;
    }
    

     /**Asettaa kellonajan
     * @param s annettu syöte
     * @return null
     */
    public String setKlo(String s) {
           if (tarkista(s)==null) { 
               klo = s;
               return null;
           }
           return tarkista(s); 
    }

    /**
     * Tarkistetaan onko kellonaika "siedettävää muotoa".
     * @param jono tarkistettava kellonaika
     * @return virheilmoitus, jos virheellinen aika/ null, jos aika oikeaa muotoa
     * @example
     * <pre name="test">
     * tarkista("12:23")        === null;
     * tarkista("kissa")         === "Vääriä merkkejä!";
     * tarkista("12:23:")        === "Korjaa aika!";
     * tarkista("12 23")         === "Vääriä merkkejä!";
     * tarkista("12k23")         === "Vääriä merkkejä!";
     * tarkista("1223")         ===  "Korjaa aika!";
     * tarkista("12:30")         === null;
     * tarkista("25:30")         === "Tunnit väärin";
     * tarkista("12:65")         === "Minuutit väärin";
     * tarkista("12:23:00.0.0")  === "Vääriä merkkejä!";
     * tarkista("12:59")         === null;
     * tarkista("12")            === "Korjaa aika!"
     * tarkista("")              === "Lisää kellonaika!";
     * tarkista(null)            === "Jono null";
     //* setKlo("12:23")           ===  null;
     //* setKlo("kissa")           === "Vääriä merkkejä!";
     * </pre>
     */
    public static String tarkista(String jono) {
      if ( jono == null ) return "Jono null";
      if ( jono.equals("") ) return "Lisää kellonaika!";
      
      String sallitut = "0123456789:";
      
      int vali = 0;
      for (int i=0; i<jono.length(); i++) {
          for (int j=0; j<sallitut.length(); j++) {
              char merkki = jono.charAt(i);
              if (merkki == ':') vali = 1;//sisältää kaksoispisteen
              char verrattava = sallitut.charAt(j); 
              if (merkki!=verrattava && j==sallitut.length()-1) return "Vääriä merkkejä!";
              if (merkki==verrattava) break;
              
          } 
      }
      
      if (vali == 0) return "Korjaa aika!";
      
      int h, m; //double s;
      StringBuilder sb = new StringBuilder(jono);
      String pala;
      
      try {
          pala = Mjonot.erota(sb, ':', "");      h = Integer.parseInt(pala);
          pala = Mjonot.erota(sb, ':', "0");     m = Integer.parseInt(pala); if (pala.length() < 2) return "Minuutit väärin!";
         // pala = Mjonot.erota(sb, ':', "0");     s = Double.parseDouble(pala);    
      }
      catch (NumberFormatException ex) {
        return "Vääriä merkkejä jonossa";
      }
      if ( h < 0 || 24 <= h ) return "Tunnit väärin";
      if ( m < 0 || 60 <= m ) return "Minuutit väärin";
      //if ( s < 0 || 60 <= s ) return "Sekunnit väärin";
      
      if (jono.length() < 4 || jono.length() > 5) return "Korjaa aika!";
              
      return null;
    }


     /**Asettaa ensimmäisen oireen
     * @param o annettu syöte oliona
     * @return null
     */
    public String setOire1(Oire o) {
         oire1 = o.getOireId();
         return null;
     }


    /**Asettaa toisen oireen
     * @param s annettu syöte
     * @return null
     */
    public String setOire2(String s) {
         oire2 = Mjonot.erotaInt(s, 0);
         return null;
     }


    /**Asettaa toisen oireen
    * @param o annettu syöte oliona
    * @return null
    */
   public String setOire2(Oire o) {
        oire2 = o.getOireId();
        return null;
    }
    /**Asettaa kolmannen oireen
     * @param s annettu syöte
     * @return null
     */
    public String setOire3(String s) {
         oire3 = Mjonot.erotaInt(s, 0);
         return null;
     }


    /**Asettaa kolmannen oireen
    * @param o annettu syöte oliona
    * @return null
    */
   public String setOire3(Oire o) {
        oire3 = o.getOireId();
        return null;
    }
   
   /**
 * @param kentta mikä oire
 * @param o oire käyttöliittymästä
 * @return null
 */
public String setOire(int kentta, Oire o) {
       switch (kentta) {
       case (1): oire1 = o.getOireId(); break;
       case (2): oire2 = o.getOireId(); break;
       case (3): oire3 = o.getOireId(); break;
       default: break;
       }
       return null;
   }

/**
* @param kentta mikä lääke
* @param l lääke käyttöliittymästä
* @return null
*/
public String setLaake(int kentta, Laake l) {
     switch (kentta) {
     case (1): laake1 = l.getLaakeId(); break;
     case (2): laake2 = l.getLaakeId(); break;
     case (3): laake3 = l.getLaakeId(); break;
     default: break;
     }
     return null;
 }
   
    /**Asettaa ensimmäisen lääkkeen
     * @param s annettu syöte
     * @return null
     */
    public String setLaake1(String s) {
         laake1 = Mjonot.erotaInt(s, 0);
         return null;
     }
    
    /**Asettaa toisen lääkkeen
     * @param s annettu syöte
     * @return null
     */
    public String setLaake2(String s) {
         laake2 = Mjonot.erotaInt(s, 0);
         return null;
     }
    
    /**Asettaa kolmannen lääkkeen
     * @param s annettu syöte
     * @return null
     */
    public String setLaake3(String s) {
         laake3 = Mjonot.erotaInt(s, 0);
         return null;
     }


     /**Asettaa lisätiedot
     * @param s annettu syöte
     * @return null
     */
    public String setLisatiedot(String s) {
         lisatiedot = s;
         return null;
     }


    /**
     * Kloonaa kirjauksen tiedoston muokkaamista varten
     */
    @Override
    public Kirjaus clone() throws CloneNotSupportedException {
                 Kirjaus uusi;
               uusi = (Kirjaus) super.clone();
               return uusi;
            }
    

    /**Päivämäärän vertailumetodi 
     * Tarvitaan käyttöliittymän kirjausten sorttaamiseksi nousevaan aikajärjestykseen
     * */
    @Override
    public int compareTo(Kirjaus verrattava) {
        if (getVv() > verrattava.getVv()) return 1;
        if (getVv() < verrattava.getVv()) return -1;
        if (getKk() > verrattava.getKk()) return 1;
        if (getKk() < verrattava.getKk()) return -1;
        if (getPv() > verrattava.getPv()) return 1;
        if (getPv() < verrattava.getPv()) return -1;
        return 0;     
    }
    
    
    /**Testataan luokkaa Kirjaus
     * @param args ei kaytossa
     */
    public static void main(String args[]) {
            Kirjaus merkinta = new Kirjaus();
            merkinta.rekisteroi();
            
            Kirjaus merkinta2 = new Kirjaus(), 
            merkinta3 = new Kirjaus();
            merkinta2.rekisteroi();
            merkinta3.rekisteroi();
            
            merkinta.tulosta(System.out);
            merkinta.vastaaMerkinta(1,1);
            merkinta.tulosta(System.out);

            merkinta2.vastaaMerkinta(2,2);
            merkinta2.tulosta(System.out);

            merkinta3.vastaaMerkinta(5,8);
          
            merkinta3.tulosta(System.out);
            merkinta3.setKlo("13:20");
            merkinta3.setPvm("01.01.2020");
            System.out.println(merkinta3.getPvm());
            System.out.println(merkinta3.getKlo());
            String palaute = tarkista("1:0");
            System.out.println (palaute);
            String palaute2 = tarkista("11:22");
            System.out.println (palaute2);
            String palaute23 = tarkistaPaiva("30.2.2020");
            System.out.println (palaute23);
            tutkiPaiva(30,2,2020);
            
        }



    

    }




