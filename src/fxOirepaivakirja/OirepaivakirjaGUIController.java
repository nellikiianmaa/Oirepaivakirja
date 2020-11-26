package fxOirepaivakirja;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import kanta.SailoException;
import oirepaivakirja.Kirjaus;
import oirepaivakirja.Laake;
import oirepaivakirja.Oire;
import oirepaivakirja.Oirepaivakirja;


/**Oirepäiväkirja, johon voi kirjata omat oireet ja lääkkeet
 * Puuttuu: yhteistyö laake-luokan kanssa, oireiden ja lääkkeiden muokkaaminen
 * Muutos suunnitelmaan: lisätty hakukenttä, jonka avulla voidaan hakea kirjauksia, 
 * hakuehtona mikä tahansa merkkijono

 * @author Nelli Kiianmaa, nelli.j.kiianmaa@student.jyu.fi
 * @version1.1 2.3.2020
 * @version1.2 2.4.2020 - tiedostosta lukeminen ja tallentaminen toimii
 * @version1.3 23.4.2020 - tietojen syöttäminen toimii käyttöliittymästä
 */
public class OirepaivakirjaGUIController implements Initializable {
    
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelVirhe;
   // @FXML private ScrollPane panelKirjaus; //testivaiheen apukenttä
    @FXML private ListChooser<Kirjaus> chooserKirjaukset;
    @FXML private TextField editPaiva;
    @FXML private TextField editKello;
    @FXML private ComboBoxChooser<Oire> editOire1;
    @FXML private ComboBoxChooser<Oire> editOire2;
    @FXML private ComboBoxChooser<Oire> editOire3;
    @FXML private ComboBoxChooser<Laake> editLaake1;
    @FXML private ComboBoxChooser<Laake> editLaake2;
    @FXML private ComboBoxChooser<Laake> editLaake3;
    @FXML private TextArea editLisatietoja; 
    @FXML private TextField tekstiEtsi;

    
    
        @Override
        public void initialize(URL url, ResourceBundle bundle) {
            alusta();
        }
        
          
        @FXML private void handleTulosta() {
            TulostusController.tulosta(null);
            TulostusController tulostusCtrl = TulostusController.tulosta(null); 
            tulostaValitut(tulostusCtrl.getTextArea()); 
        } 
    
        @FXML private void handleAvaa() {
               avaa();
              }
        
        
        @FXML private void handleMuokkaaKirjaus() {
                muokkaa();
              }
        
    
        @FXML private void handleLisaaKirjaus() { 
                uusiKirjaus();
              }
       
   
        
       @FXML private void handleTietojaIkkuna() {
               ModalController.showModal(OirepaivakirjaGUIController.class.getResource("TietojaDialogView.fxml"), "Tietoja", null, "");
              }
      
     
    
      @FXML private void handleOhje() {
              avustus();
          }
    
        
      @FXML private void handlePoista () {
             poistaKirjaus();
          }
      
      
      @FXML private void handleTallenna () {
               tallenna();
            }
       
       
       @FXML private void handleEtsi () {
           etsi();
         }
        
          
      @FXML private void handleLopeta () {
          Alert alert = new Alert(AlertType.CONFIRMATION);
          alert.setTitle("Valitse");
          alert.setHeaderText(null);
          alert.setContentText("Lopetetaanko? Samalla tallennetaan kaikki tiedot");
    
          ButtonType buttonTypeYes = new ButtonType("Tallenna ja poistu", ButtonData.OK_DONE);
          ButtonType buttonTypeCancel = new ButtonType("Peruuta", ButtonData.CANCEL_CLOSE);
    
          alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
    
          Optional<ButtonType> result = alert.showAndWait();
          if ( result.get() == buttonTypeYes ) { System.out.println("Talletetaan"); tallenna();
          Platform.exit();}
      }
      
      
      @FXML private void handleSulje () {
          tallenna();
          Platform.exit();
      }


  
//--------------------------------------------------------------------------------------;
//Suoraaan käyttöliittymään liittyvää koodia:;

    private Oirepaivakirja oirepaivakirja;
    private Kirjaus kirjausKohdalla;
    private TextArea areaKirjaus = new TextArea();


    /**
     * Tekee tarvittavat muut alustukset
     * Alustetaan myös jäsenlistan kuuntelija 
     */
    protected void alusta() {
        chooserKirjaukset.clear();
        chooserKirjaukset.addSelectionListener(e -> naytaKirjaus());     
    }
    

    /**
     *Tyhjentää kirjaukset listasta
     * Etsii haettavaa tekstiä kirjauksista Enterin painalluksella
     */
    private void etsi () {
        tallenna();
        chooserKirjaukset.clear();
        tekstiEtsi.setOnKeyPressed(e -> { if(e.getCode() == KeyCode.ENTER) { lueEtsittavat();}});
    }
    

    /**
     * Lukee etsittävät kirjaukset tiedostosta
     *  
     */
    private String lueEtsittavat() {
        try {
            oirepaivakirja.lueTiedostosta();
            haeEtsittavat();
            return null;
        } catch (SailoException e) {
            haeEtsittavat();
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;       
          }
    }
        
        
    /**
     * Lisää listaan kaikki rivit, jotka täyttävät hakuehdon
     * 
     */   
    private void haeEtsittavat() {
            String etsittava =  tekstiEtsi.getText();
             for (int i = 0; i < oirepaivakirja.getMerkintoja(); i++) {
                 Kirjaus kirjaus = oirepaivakirja.annaMerkinta(i);
                 String rivi = kirjaus.getRivi() 
                         + oirepaivakirja.annaOire(kirjaus.getOire1()) + " "
                         + oirepaivakirja.annaOire(kirjaus.getOire2()) + " " 
                         + oirepaivakirja.annaOire(kirjaus.getOire3()) + "        "
                         + oirepaivakirja.annaLaake(kirjaus.getLaake1()) + " " 
                         + oirepaivakirja.annaLaake(kirjaus.getLaake2()) + " "
                         + oirepaivakirja.annaLaake(kirjaus.getLaake3()) + "      "
                         + kirjaus.getLisatiedot(); 
                 String merkinta = rivi.replace("null", "");
                 if (merkinta.contains(etsittava))chooserKirjaukset.add(merkinta, kirjaus);
             }
         }

    
    
    @SuppressWarnings("unused")
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
   

    
    /**
     * Alustaa oirepaivakirjan lukemalla sen valitun nimisestä tiedostosta
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto() { 
        try {
            oirepaivakirja.lueTiedostosta();
            hae(0);
            return null;
        } catch (SailoException e) {
            try {
                hae(0); 
            } catch (SailoException e1) {
                e1.printStackTrace();
            }
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
     }


    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        chooserKirjaukset.clear();
        lueTiedosto();
        return true;
    }

    
    /**
     * Näytetään kirjauksen tiedot kirjauksen dialogi-ikkunaan
     */
    private void muokkaa() {
        if ( kirjausKohdalla == null ) return; 
        try { 
            Kirjaus kirjaus; 
            kirjaus = KirjausDialogController.kysyKirjaus(null, kirjausKohdalla.clone(), oirepaivakirja); 
            if ( kirjaus== null ) return; 
            oirepaivakirja.korvaaTaiLisaa(kirjaus); 
            hae(kirjaus.getId()); 
        } catch (CloneNotSupportedException e) { 
            // 
        } catch (SailoException e) { 
            Dialogs.showMessageDialog(e.getMessage()); 
        } 
    }     


     /**
     * Tietojen tallennus
     * @return null jos onnistuu, muuten virhe tekstinä
     */
      private String tallenna() {
          try {
                 oirepaivakirja.tallenna();
                  return null;
               } catch (SailoException ex) {
                   Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
                   return ex.getMessage();
               }
       }

        
        /**
         * Tarkistetaan onko tallennus tehty
         * @return true jos saa sulkea sovelluksen, false jos ei
         */
        public boolean voikoSulkea() {
            tallenna();
            return true;
        }
      
    
       /**
       * Näyttää listasta valitun jäsenen tiedot
       */
       protected void naytaKirjaus() {
           kirjausKohdalla = chooserKirjaukset.getSelectedObject();
               if (kirjausKohdalla == null) return;
                 //  KirjausDialogController.kysyKirjaus(null, kirjausKohdalla, oirepaivakirja);
                }
        
       
       
        //Rivin tai sen osan katkaisu, jos liian pitkä? 
        //Osien pituus tietynmittaiseksi, että menee näytöllä oikeaan kohtaan?
        //formaatti ekoille muuttujille, että menevät kohdakkain?
       // ruutu isommaksi SceneBuilderissa?
        /**
         * Hakee kirjausten tiedot listaan
         * @param knro kirjauksen numero, joka aktivoidaan haun jälkeen
         * @throws SailoException heittää poikkeuksen, jos ei onnistu (mutta herjaa, että ei tapahdu)
         */
        @SuppressWarnings("unused")
        protected void hae(int knro) throws SailoException{
           chooserKirjaukset.clear();
           Kirjaus[] lista = oirepaivakirja.getKirjausLista();
            for (int i = 0; i < oirepaivakirja.getMerkintoja(); i++) {
                Kirjaus kirjaus = lista[i];
                String rivi = kirjaus.getRivi() 
                        + oirepaivakirja.annaOireet(kirjaus.getOire1(), kirjaus.getOire2(), kirjaus.getOire3()) + " /   " 
                        + oirepaivakirja.annaLaakkeet(kirjaus.getLaake1(), kirjaus.getLaake2(), kirjaus.getLaake3()) + " /   "
                        + kirjaus.getLisatiedot(); 
                String merkinta = rivi.replace("null", "");
                chooserKirjaukset.add(merkinta, kirjaus);
            }
        }
        
        
    
    /**
         * Luo uuden kirjauksen jota aletaan editoimaan 
         */
        protected void uusiKirjaus() {
            try {
                Kirjaus uusi = new Kirjaus();
                uusi = KirjausDialogController.kysyKirjaus(null, uusi, oirepaivakirja); 
                if ( uusi == null ) return;
                uusi.rekisteroi();
                //uusi.vastaaMerkinta(oirepaivakirja.getOireLkm(), oirepaivakirja.getLaakeLkm());
                oirepaivakirja.lisaa(uusi);
                //halusi tähän tämän try-catchin
                hae(uusi.getId());
            } catch (SailoException e) {
                Dialogs.showMessageDialog("Kirjauksen luomisessa ongelmia! " + e.getMessage());
                return;
            }
        }
        

        /*
             * Poistetaan listalta valittu jäsen
             */
            private void poistaKirjaus() {
                Kirjaus kirjaus = kirjausKohdalla;
                if ( kirjaus == null ) return;
                if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko kirjaus: " + kirjaus.getPvm() + " " + kirjaus.getKlo(), "Kyllä", "Ei") )
                    return;
                oirepaivakirja.poista(kirjaus);
                try {
                    hae(0);
                } catch (SailoException e) {

                    e.printStackTrace();
                }
            }
            
            
    /** 
     * Testivaiheessa tekee uudet oireet editointia varten 
     */ 
    public void uusiOire() { 
        String oire_1 = "";
        String oire_2 = "";
        String oire_3 = "";  
        kirjausKohdalla = chooserKirjaukset.getSelectedObject();
        
        //Kirjauksiin valittavien oireiden valinta Poista-napista (tehdään ensin)
        if (kirjausKohdalla == null) {;
        Oire uusi = new Oire();
        uusi.vastaaOire();
        uusi.rekisteroi();
        oirepaivakirja.lisaa(uusi); 
        }
        
       //Oireiden selitteiden liittäminen kirjauksiin
        if (kirjausKohdalla != null) {
        //hakee oirepaivakirjan kautta oirenumeroihin oireiden selitteet
        oire_1 = oirepaivakirja.annaOire(kirjausKohdalla.getOire1()); 
        oire_2 = oirepaivakirja.annaOire(kirjausKohdalla.getOire2()); 
        oire_3 = oirepaivakirja.annaOire(kirjausKohdalla.getOire3()); 
         
       //tulostus kirjauksen alapuolelle Poista-napista
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaKirjaus)) {
         os.print(kirjausKohdalla.getOire1() + " " + oire_1 + ", " 
                + kirjausKohdalla.getOire2() + " " + oire_2 + ", " 
                + kirjausKohdalla.getOire3() + " " + oire_3 + "\n");  
        }
        }
    }

    
    /** 
     * Testivaiheessa tekee uudet lääkkeet editointia varten 
     */ 
    public void uusiLaake() { 
        String laake_1 = "";
        String laake_2 = "";
        String laake_3 = "";  
        kirjausKohdalla = chooserKirjaukset.getSelectedObject();
        
        //Kirjauksiin valittavien lääkkeiden valinta Muokkaa-napista (tehdään ensin)
        if (kirjausKohdalla == null) {;
            Laake uusi = new Laake();
            uusi.vastaaLaake();
            uusi.rekisteroi();
            oirepaivakirja.lisaa(uusi); 
        }
        
       //Oireiden selitteiden liittäminen kirjauksiin
        if (kirjausKohdalla != null) {
            //hakee oirepaivakirjan kautta oirenumeroihin oireiden selitteet
            laake_1 = oirepaivakirja.annaLaake(kirjausKohdalla.getLaake1()); 
            laake_2 = oirepaivakirja.annaLaake(kirjausKohdalla.getLaake2()); 
            laake_3 = oirepaivakirja.annaLaake(kirjausKohdalla.getLaake3()); 
         
           //tulostus kirjauksen alapuolelle Poista-napista
            try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaKirjaus)) {
                 os.print(kirjausKohdalla.getLaake1() + " " + laake_1 + ", " 
                        + kirjausKohdalla.getLaake2() + " " + laake_2 + ", " 
                        + kirjausKohdalla.getLaake3() + " " + laake_3 +"\n");  
            }
        }
    }
    
   
   /**
    * Testivaiheessa: Näyttää listasta valitun jäsenen tiedot, tilapäisesti yhteen isoon edit-kenttään
    */
   protected void naytaOireet() {
       kirjausKohdalla = chooserKirjaukset.getSelectedObject();

       if (kirjausKohdalla == null) return;

           try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaKirjaus)) {
               kirjausKohdalla.tulosta(os);
       }
   }
    
    /**
     * @param oirepaivakirja oirepäiväkirja jota käytetään tässä käyttöliittymässä
     */
    public void setOirepaivakirja(Oirepaivakirja oirepaivakirja) {
        this.oirepaivakirja = oirepaivakirja;
        naytaKirjaus();
    }
  


    
    /**
     * Näytetään ohjelman suunnitelma erillisessä selaimessa.
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2020k/ht/nejokiia");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }

    /**Testivaiheessa
     * Tulostaa kirjauksen tiedot
     * @param os tietovirta johon tulostetaan
     * @param kirjaus tulostettava kirjaus
     */
    public void tulosta(PrintStream os, final Kirjaus kirjaus) {
        os.println("----------------------------------------------");
        kirjaus.tulosta(os);
        os.println("----------------------------------------------");  

    }
    
    
    /**Testivaiheessa
     * Tulostaa listassa olevat jäsenet tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki jäsenet");
            for (int i = 0; i < oirepaivakirja.getMerkintoja(); i++) {
                Kirjaus kirjaus = oirepaivakirja.annaMerkinta(i);
                tulosta(os, kirjaus);
                os.println("\n\n");
            }
        }
    }

}
        


