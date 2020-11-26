package oirepaivakirja;

import java.util.Arrays;
import java.util.Collection;
import kanta.SailoException;

/**Huolehtii Kirjaukset-, Oireet- ja Laakkeet-luokkien yhteistyöstä, 
 * välittää tietoja.
 * Lukee ja kirjoittaa tiedostoon.
 * @author Nelli Kiianmaa, nelli.j.kiianmaa@student.jyu.fi
 * @version1.1 19.2.2020
 * @version1.2 1.4.2020
 * @version1.2 22.4.2020
 *
 */
public class Oirepaivakirja {
    
        private Kirjaukset kirjaukset = new Kirjaukset();
        private Oireet oireet = new Oireet();
        private Laakkeet laakkeet = new Laakkeet();
    
        

        /**
         * Palauttaa päiväkirjan merkintöjen määrän
         * @return merkintöjen määrä
         * @example
          * <pre name="test">
          * Oirepaivakirja pk = new Oirepaivakirja();
          * Kirjaus olo = new Kirjaus(), 
          *  olo2 = new Kirjaus(), olo3 = new Kirjaus();
          * olo.rekisteroi();
          * olo.vastaaMerkinta(1, 1);
          * olo2.rekisteroi();
          * olo2.vastaaMerkinta(2, 1);
          * olo3.rekisteroi();
          * olo3.vastaaMerkinta(3,2);
          * pk.lisaa(olo);
          * pk.lisaa(olo2);
          * pk.lisaa(olo3);
          * pk.getMerkintoja() === 3;
          * </pre>
         */
        public int getMerkintoja() {
            return kirjaukset.getLkm();
        }
        
        
        /**
         * Palauttaa oireiden määrän
         * @return oireiden määrä
         * @example
         * <pre name="test">
         * Oirepaivakirja pk = new Oirepaivakirja();
          * Oire olo = new Oire(), 
          * olo2 = new Oire(), olo3 = new Oire();
          * olo.rekisteroi();
          * olo.vastaaOire();
          * olo2.rekisteroi();
          * olo2.vastaaOire();
          * olo3.rekisteroi();
          * olo3.vastaaOire();
          * pk.lisaa(olo);
          * pk.lisaa(olo2);
          * pk.lisaa(olo3);
          * pk.getOireLkm() === 3;
          * </pre>
         */
        public int getOireLkm() {
            return oireet.getLkm();
        }
        
        
        /**
         * Palauttaa laakkeiden määrän
         * @return laakkeiden määrä
         * @example
         * <pre name="test">
         * Oirepaivakirja pk = new Oirepaivakirja();
          * Laake tabu = new Laake(), 
          * tabu2 = new Laake(), tabu3 = new Laake();
          * tabu.vastaaLaake();
          * tabu.rekisteroi();
          * tabu2.vastaaLaake();
          * tabu2.rekisteroi();
          * tabu3.vastaaLaake();
          * tabu3.rekisteroi();
          * pk.lisaa(tabu);
          * pk.lisaa(tabu2);
          * pk.lisaa(tabu3);
          * pk.getLaakeLkm() === 3;
          * pk.annaLaake(0)=== null;
          * pk.annaLaake(1)=== "parasetamoli 1000 mg";
          * pk.annaLaake(2)=== "ibuprofeeni 200 mg";
          * pk.annaLaake(3)=== "ketoprofeeni 100 mg";
          * </pre>
         */
        public int getLaakeLkm() {
            return laakkeet.getLkm();
        }



        /**
         * Lisää oirepäiväkirjaan uuden kirjauksen
         * @param kirjaus uusi kirjaus
         */
        public void lisaa(Kirjaus kirjaus) {
            kirjaukset.lisaa(kirjaus);
        }
        
        
        /**Lisää päiväkirjaan oireen 
         * @param oire oire
         */
        public void lisaa(Oire oire) {
        oireet.lisaa(oire);
            
        }
        
        
        /**Lisää päiväkirjaan lääkkeen 
         * @param laake laake
         */
        public void lisaa(Laake laake) {
        laakkeet.lisaa(laake);
            
        }
        

        /**Palauttaa oireen selitteen sen id:n perusteella
         * @param oireid oireen tunnusnro
         * @return oireen sanallinen selite
         * @example
         * <pre name="test">
         * Oirepaivakirja pk = new Oirepaivakirja();
          * Oire olo = new Oire(), 
          * olo2 = new Oire(), olo3 = new Oire();
          * olo.vastaaOire();
          * olo.rekisteroi();
          * pk.annaOire(0) === null;
          * olo2.vastaaOire();
          * olo2.rekisteroi();
          * olo3.vastaaOire();
          * olo3.rekisteroi();
          * pk.lisaa(olo);
          * pk.lisaa(olo2);
          * pk.lisaa(olo3);
          * pk.annaOire(0) === null;
          * pk.annaOire(1) === "pääkipu";
          * Oire[] lista = pk.getOireLista();
          * lista[0]==="pääkipu";
          * 
          * </pre>
         */
         public String annaOire(int oireid) {
           return oireet.annaOire(oireid);
        }
         
         
         /**
         * @param oireid1 ensimmäisen oireen id
         * @param oireid2 toisen oireen id
         * @param oireid3 kolmannen oireen id
         * @return oireiden nimet merkkijonona
         */
        public String annaOireet(int oireid1, int oireid2, int oireid3) {
             String s = oireet.annaOire(oireid1) + " " + oireet.annaOire(oireid2) 
             + " " + oireet.annaOire(oireid3);
             return s;
          }
         
         /**Palautetaan oire oireid:n perusteella
         * @param oireid oireeen tunnusnro
         * @return Oire
         */
        public Oire anna(int oireid) {
             return oireet.anna(oireid);
          }
        
        
         
        /**Palauttaa lääkkeen selitteen sen id:n perusteella
         * @param laakeid lääkkeen tunnusnro
          * @return lääkkeen sanallinen selite
          */
         public String annaLaake(int laakeid) {
            return laakkeet.annaLaake(laakeid);
         }
         
         /**
         * @param laakeid1 ensimmäisen lääkkeen id
          * @param laakeid2 toisen lääkkeen id
          * @param laakeid3 kolmannen lääkkeen id
          * @return lääkkeiden nimet merkkijonona
          */
         public String annaLaakkeet(int laakeid1, int laakeid2, int laakeid3) {
              String s = laakkeet.annaLaake(laakeid1) + " " + laakkeet.annaLaake(laakeid2) 
              + " " + laakkeet.annaLaake(laakeid3);
              return s;
           }
          
          
          /**Palauttaa lääkkeen selitteen sen id:n perusteella
          * @param laakeid lääkkeen tunnusnro
           * @return lääkkeen sanallinen selite
           */
           public Laake annaLaakeOlio(int laakeid) {
             return laakkeet.anna(laakeid);
          }
           
        
        /**
         * Palauttaa i:n kirjauksen
         * @param i monesko kirjaus palautetaan
         * @return viite i:teen kirjaukseen
         * @throws IndexOutOfBoundsException jos i väärin
         */
        public Kirjaus annaMerkinta(int i) throws IndexOutOfBoundsException {
            return kirjaukset.anna(i);
        }

        
        /**
         * Lukee oirepaivakirjan tiedot tiedostosta
         * @throws SailoException jos lukeminen epäonnistuu
         * 
         * @example
         * <pre name="test">
         * #import kanta.SailoException;
         * #THROWS SailoException 
         * #import java.io.*;
         * #import java.util.*;
         * 
         *  Oirepaivakirja oirepaivakirja = new Oirepaivakirja();
         *  
         *  Kirjaus kipu1 = new Kirjaus(); kipu1.vastaaMerkinta(1,3); kipu1.rekisteroi();
         *  Kirjaus kipu2 = new Kirjaus(); kipu2.vastaaMerkinta(3, 2); kipu2.rekisteroi();
         *  Oire sarky21 = new Oire(); sarky21.vastaaOire();
         *  Oire sarky11 = new Oire(); sarky11.vastaaOire();
         *  Oire sarky22 = new Oire(); sarky22.vastaaOire(); 
         *  Oire sarky12 = new Oire(); sarky12.vastaaOire(); 
         *  Laake tabu11 = new Laake(); tabu11.vastaaLaake();
         *  Laake tabu22 = new Laake(); tabu22.vastaaLaake(); 
         *  Laake tabu12 = new Laake(); tabu12.vastaaLaake(); 
         *  Laake tabu23 = new Laake(); tabu23.vastaaLaake();
         *   
         *  String hakemisto = "testi";
         *  File dir = new File(hakemisto);
         *  File ftied  = new File(hakemisto+"/kirjaukset.dat");
         *  File fhtied = new File(hakemisto+"/oireet.dat");
         *  File fitied = new File(hakemisto+"/laakkeet.dat");
         *  dir.mkdir();  
         *  ftied.delete();
         *  fhtied.delete();
         *  fitied.delete();
         *  oirepaivakirja.lisaa(kipu1);
         *  oirepaivakirja.lisaa(kipu2);
         *  oirepaivakirja.lisaa(sarky21);
         *  oirepaivakirja.lisaa(sarky11);
         *  oirepaivakirja.lisaa(sarky22);
         *  oirepaivakirja.lisaa(sarky12);
         *  oirepaivakirja.lisaa(tabu11);
         *  oirepaivakirja.lisaa(tabu22);
         *  oirepaivakirja.lisaa(tabu12);
         *  oirepaivakirja.lisaa(tabu23);
         *  try {
         *        oirepaivakirja.tallenna(); 
         *  } catch (SailoException e) {
         *  e.printStackTrace(); 
         *  }
         *  oirepaivakirja = new Oirepaivakirja();
         *  oirepaivakirja.lueTiedostosta();
         *  Kirjaukset kirjaukset = new Kirjaukset();
         * try {
         *       kirjaukset.lueTiedostosta();
         *  }       catch (SailoException e1) {
         *         e1.printStackTrace();
         * }  
         *  Iterator<Kirjaus> it = kirjaukset.iterator();
         *  it.next() === kipu1;
         *  it.next() === kipu2;
         *  it.hasNext() === false;
         *  oirepaivakirja.lisaa(kipu2);
         *  oirepaivakirja.lisaa(sarky12);
         *  oirepaivakirja.lisaa(tabu23);
         *  try {
         *  oirepaivakirja.tallenna();
         *  } catch (SailoException e) {
         * e.printStackTrace();
         *} 
         *  ftied.delete()  === true;// tulee false
         *  fhtied.delete() === true;
         *  fitied.delete() === true;
         *  File fbak = new File(hakemisto+"/kirjaukset.bak");
         *  File fhbak = new File(hakemisto+"/oireet.bak");
         *  File fibak = new File(hakemisto+"/laakkeet.bak");
         *  fbak.delete() === true;
         *  fhbak.delete() === true;
         *  fibak.delete() === true;
         *  dir.delete() === true;
         * </pre>
         */
        public void lueTiedostosta() throws SailoException {
            kirjaukset = new Kirjaukset(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
            oireet = new Oireet();
            laakkeet = new Laakkeet();
            
            String virhe = "";
            
            try {
                kirjaukset.lueTiedostosta();
            } catch ( SailoException ex ) {
                virhe += ex.getMessage();
            }
            if ( !"".equals(virhe) ) throw new SailoException(virhe);
            
            try {
                oireet.lueTiedostosta();
            } catch ( SailoException ex ) {
                virhe += ex.getMessage();
            }
            if ( !"".equals(virhe) ) throw new SailoException(virhe);
            
            try {
                laakkeet.lueTiedostosta();
            } catch ( SailoException ex ) {
                virhe += ex.getMessage();
            }
            if ( !"".equals(virhe) ) throw new SailoException(virhe);
            
        }
        


        /**
         * Tallenttaa oirepaivakirjan tiedot tiedostoon.  
         * Vaikka kirjausten tallettamien epäonistuisi, niin yritetään silti tallettaa
         * oireita ja lääkkeitä ennen poikkeuksen heittämistä.
         * @throws SailoException jos tallettamisessa ongelmia
         */
        public void tallenna() throws SailoException {
            String virhe = "";
            try {
                kirjaukset.tallenna();
            } catch ( SailoException ex ) {
                virhe = ex.getMessage();
            }

            try {
                oireet.tallenna();
            } catch ( SailoException ex ) {
                virhe += ex.getMessage();
            }
            if ( !"".equals(virhe) ) throw new SailoException(virhe);
            
            try {
                laakkeet.tallenna();
            } catch ( SailoException ex ) {
                virhe += ex.getMessage();
            }
            if ( !"".equals(virhe) ) throw new SailoException(virhe);
        }

        
        /** 
        * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
        * @param hakuehto hakuehto  
        * @param k etsittävän kentän indeksi  
        * @return tietorakenteen löytyneistä jäsenistä 
        * @throws SailoException Jos jotakin menee väärin
        */ 
       public Collection<Kirjaus> etsi(String hakuehto, int k) throws SailoException { 
           return kirjaukset.etsi(hakuehto, k); 
       } 
       

       /** 
        * Korvaa kirjauksen tietorakenteessa.  Ottaa kirjauksen omistukseensa. 
        * Etsitään samalla tunnusnumerolla oleva kirjaus.  Jos ei löydy, 
        * niin lisätään uutena kirjauksena. 
        * @param kirjaus lisätäävän kirjauksen viite.  Huom tietorakenne muuttuu omistajaksi 
        * @throws SailoException jos tietorakenne on jo täynnä 
        */ 
       public void korvaaTaiLisaa(Kirjaus kirjaus) throws SailoException { 
           kirjaukset.korvaaTaiLisaa(kirjaus); 
       } 


       
       /**Palauttaa koko oirelistan
     * @return oireet
     */
    public Oireet getOireet() {
           return oireet;
       }
    
    
    /**Palauttaa koko oirelistan
     * @return oireet
     */
    public Oire[] getOireLista() {
           Oire[] lista = new Oire[getOireLkm()]; 
           int i = 0;
           for (Oire oire: oireet) {
                lista[i] = oire;
                i++;
           }   
           Arrays.sort(lista);
           return lista;
       }
    
    /**Palauttaa koko laakelistan
     * @return laakkeet
     */
    public Laake[] getLaakeLista() {
           Laake[] lista = new Laake[getLaakeLkm()]; 
           int i = 0;
           for (Laake laake: laakkeet) {
                lista[i] = laake;
                i++;
           }   
           Arrays.sort(lista);
           return lista;
       }
    
    
    /**Palauttaa koko oirelistan
     * @return oireet
     */
    public Kirjaus[] getKirjausLista() {
           Kirjaus[] lista = new Kirjaus[getMerkintoja()]; 
           int i = 0;
           for (Kirjaus kirjaus: kirjaukset) {
                lista[i] = kirjaus;
                i++;
           }   
           Arrays.sort(lista);
           return lista;
       }

    /**Palauttaa oireiden nimet listassa
     * @return oireet
     */
    public String[] getOireNimet() {
           String[] lista = new String[getOireLkm()]; 
           int i = 0;
           for (Oire oire: oireet) {
           lista[i] = oire.getVointi();
           i++;
           }   
           Arrays.sort(lista);
           return lista;
       }
    
    
    /**Palauttaa lääkkeiden nimet listassa
     * @return oireet
     */
    public String[] getLaakeNimet() {
           String[] lista = new String[getLaakeLkm()]; 
           int i = 0;
           for (Laake laake: laakkeet) {
           lista[i] = laake.getLaake();
           i++;
           }   
           Arrays.sort(lista);
           return lista;
       }


    /**
     * Poistaa kirjauksen
     * @param kirjaus kirjaus joka poistetaan
     * @return montako kirjausta poistettiin
     * @example
     */
    public int poista(Kirjaus kirjaus) {
        if ( kirjaus == null ) return 0;
        int ret = kirjaukset.poista(kirjaus.getId()); 
        return ret; 
    }
   
        
        /**
         * Testiohjelma oirepaivakirjasta
         * @param args ei käytössä
         */
        public static void main(String args[]) {
            Oirepaivakirja oirepaivakirja = new Oirepaivakirja();

            Kirjaus oireet1 = new Kirjaus(), 
            oireet2 = new Kirjaus();
            oireet1.rekisteroi();
            oireet1.vastaaMerkinta(2, 5);
            oireet2.rekisteroi();
            oireet2.vastaaMerkinta(10, 5);

            oirepaivakirja.lisaa(oireet1);
            oirepaivakirja.lisaa(oireet2);
            
            
             Oirepaivakirja pk = new Oirepaivakirja();
             Laake tabu = new Laake(), 
             tabu2 = new Laake(), tabu3 = new Laake();
             tabu.vastaaLaake();
             tabu.rekisteroi();
             System.out.println(pk.annaLaake(0));
             tabu2.vastaaLaake();
             tabu2.rekisteroi();
             tabu3.vastaaLaake();
             tabu3.rekisteroi();
             
             pk.lisaa(tabu);
             pk.lisaa(tabu2);
             pk.lisaa(tabu3);
             System.out.println(pk.annaLaake(0));
             System.out.println(pk.annaLaake(1));
             Oire[] lista = oirepaivakirja.getOireLista();
             System.out.println(lista.length);
             try {
                oirepaivakirja.tallenna();
            } catch (SailoException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
             
             try {
                oirepaivakirja.lueTiedostosta();
            } catch (SailoException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
             
             
            //System.out.println(oirepaivakirja.annaMerkinta(1));//tarvitaan toString()??

            System.out.println("============= Oirepaivakirjan testi =================");

            for (int i = 0; i < oirepaivakirja.getMerkintoja(); i++) {
                Kirjaus kirjaus = oirepaivakirja.annaMerkinta(i);
                //List<Oire> oireet = oireet.annaOire(i);
                System.out.println("Merkintä paikassa: " + i);
                kirjaus.tulosta(System.out);
                //oireet.tulosta(System.out);
            }
        }


       

       
 
    }
