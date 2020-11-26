package fxOirepaivakirja;

import java.util.Calendar;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kanta.SailoException;
import oirepaivakirja.Kirjaus;
import oirepaivakirja.Laake;
import oirepaivakirja.Oire;
import oirepaivakirja.Oirepaivakirja;


/**
     * Kysytään kirjauksen tiedot luomalla sille uusi dialogi
     * Puuttuu: yhteistyö laake-luokan kanssa, virheilmoitukset toimivat eri tavoin kuin alkuperäisessä,
     * 
     * @author Nelli Kiianmaa, nelli.j.kiianmaa@student.jyu.fi
     * @version1.1 6.4.2020
     * @version1.2 23.4.2020
     * @version1.3 25.4.2020 - versio, jossa toimii muiden tietojen paitsi lääkkeiden muokkaus
     *
     */
    public class KirjausDialogController implements ModalControllerInterface<Kirjaus> {

        @FXML private TextField editPaiva;
        @FXML private TextField editKello;
        @FXML private ComboBoxChooser<Oire> editOire1;
        @FXML private ComboBoxChooser<Oire> editOire2;
        @FXML private ComboBoxChooser<Oire> editOire3;
        @FXML private ComboBoxChooser<Laake> editLaake1;
        @FXML private ComboBoxChooser<Laake> editLaake2;
        @FXML private ComboBoxChooser<Laake> editLaake3;
        @FXML private TextField lisaaUusiOire;
        @FXML private TextField lisaaUusiLaake;
        

        @FXML private TextArea editLisatietoja;    
        @FXML private Label labelVirhe;
    
       
        @FXML private void handleOK() {
            if ( kirjausKohdalla != null && kirjausKohdalla.getRivi().trim().equals("") ) {
                naytaVirhe("Rivi ei saa olla tyhjä");
                return;
                
            }
            ModalController.closeStage(labelVirhe);
        }

      
        
        @FXML private void handleCancel() {
            kirjausKohdalla = null;
            ModalController.closeStage(labelVirhe);
        }
        
        
        @FXML private void handleUusiOire() {
            lisaaUusiOire();
        }
        
        @FXML private void handleUusiLaake() {
            lisaaUusiLaake();
        }

            
        

        
    // ========================================================    
        private Kirjaus kirjausKohdalla;
        private Oirepaivakirja oirepaivakirja;
        @SuppressWarnings("unused")
        private Oire oire;
        private Laake laake;

        
        /**
         * Tekee tarvittavat muut alustukset. Mm laittaa edit-kentistä tulevan
         * tapahtuman menemään kasitteleMuutosJaseneen-metodiin ja vie sille
         * kentännumeron parametrina.
         */

        @SuppressWarnings("unchecked")
        protected void alusta() {   
            oireetListaan();
            laakkeetListaan();
            kasitteleMuutosPaivaan(editPaiva);
            kasitteleMuutosKelloon(editKello);
            lisaaUusiOire();
            lisaaUusiLaake();
            editPaiva.setOnKeyReleased( e -> kasitteleMuutosPaivaan((TextField)(e.getSource())));
            editKello.setOnKeyReleased( e -> kasitteleMuutosKelloon((TextField)(e.getSource())));
            editLisatietoja.setOnKeyReleased( e -> kasitteleMuutosLisatietoihin((TextArea)(e.getSource())));
            editOire1.setOnAction( e -> kasitteleMuutosOireeseen1((ComboBoxChooser<Oire>)(e.getSource()),1));
            editOire2.setOnAction( e -> kasitteleMuutosOireeseen1( (ComboBoxChooser<Oire>)(e.getSource()),2));
            editOire3.setOnAction( e -> kasitteleMuutosOireeseen1((ComboBoxChooser<Oire>)(e.getSource()),3));
            editLaake1.setOnAction( e -> kasitteleMuutosLaakkeeseen1((ComboBoxChooser<Laake>)(e.getSource()),1));
            editLaake2.setOnAction( e -> kasitteleMuutosLaakkeeseen1((ComboBoxChooser<Laake>)(e.getSource()),2));
           // editLaake3.setOnAction( e -> kasitteleMuutosLaakkeeseen1((ComboBoxChooser<Laake>)(e.getSource()),3));
            }
        
        
        
       /**Asettaa oirepaivakirjan ja kutsuu alusta-metodia
        * @param oirepaivakirja rekisteriluokka
        * 
        */
       public void setOirepaivakirja(Oirepaivakirja oirepaivakirja) {
        this.oirepaivakirja = oirepaivakirja;
        alusta();
           
       }
      
       
       /**Vie oireet valikoihin aakkosjärjestyksessä
     * 
     */
       public void oireetListaan () {
           Oire[] lista = oirepaivakirja.getOireLista();
           for (int i=0; i<oirepaivakirja.getOireLkm();) {
               @SuppressWarnings("hiding")
               Oire oire=lista[i];
               editOire1.add(oire.getVointi(), oire);
               editOire2.add(oire.getVointi(), oire);
               editOire3.add(oire.getVointi(), oire);
               if (kirjausKohdalla.getOire1() == oire.getOireId()) editOire1.setSelectedIndex(i); 
               if (kirjausKohdalla.getOire2() == oire.getOireId()) editOire2.setSelectedIndex(i); 
               if (kirjausKohdalla.getOire3() == oire.getOireId()) editOire3.setSelectedIndex(i); 
               i++;
           }
       }
       
       
       /**Vie oireet valikoihin aakkosjärjestyksessä
        * 
        */
          public void laakkeetListaan () {
              Laake[] lista = oirepaivakirja.getLaakeLista();
              for (int i=0; i<oirepaivakirja.getLaakeLkm();) {
                  @SuppressWarnings("hiding")
                  Laake laake=lista[i];
                  editLaake1.add(laake.getLaake(), laake);
                  editLaake2.add(laake.getLaake(), laake);
                  //editLaake3.add(laake.getLaake(), laake);
                  if (kirjausKohdalla.getLaake1() == laake.getLaakeId()) editLaake1.setSelectedIndex(i); 
                  if (kirjausKohdalla.getLaake2() == laake.getLaakeId()) editLaake2.setSelectedIndex(i); 
                  //if (kirjausKohdalla.getLaake3() == laake.getLaakeId()) editLaake3.setSelectedIndex(i); 
                  i++;
              }
          }
    
       
     /**Asettaa oletuskirjauksen
     * @param oletus kirjaus
     * 
     */
        @Override
        public void setDefault(Kirjaus oletus) {
            kirjausKohdalla = oletus;
            naytaKirjaus(kirjausKohdalla);
        }
        
        
        private void setDefaultFocus() {
            editOire1.requestFocus();
        }


        
        @Override
        public Kirjaus getResult() {
            return kirjausKohdalla;
        }
        
        
        /**
         * Mitä tehdään kun dialogi on näytetty
         */
        @Override
        public void handleShown() {
            setDefaultFocus();
        }
        
        /**
         * Näytetään virheilmoitus tiedonsyötöstä
         */ 
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
         * Käsitellään aikaan tullut muutos
         * @param comboBoxChooser muuttunut aikakenttä
         */
       
        private void kasitteleMuutosKelloon(@SuppressWarnings("unused") TextField editKlo) {
            if (kirjausKohdalla == null) return;
            String s = editKello.getText();
            String virhe = null;
            virhe = kirjausKohdalla.setKlo(s);

            if (virhe == null) {
                Dialogs.setToolTipText(editKello,"");
                editKello.getStyleClass().removeAll("virhe");
                naytaVirhe(virhe);
            } else {
                Dialogs.setToolTipText(editKello,virhe);
                editKello.getStyleClass().add("virhe");
                labelVirhe.getStyleClass().add("virhe");
                naytaVirhe(virhe);
            }
        }
        


        
        /**
         * Käsitellään päivään tullut muutos
         * @param editPvm päivämäärävalinta
         */
     
        private void kasitteleMuutosPaivaan(@SuppressWarnings("unused") TextField editPvm) {
            if (kirjausKohdalla == null) return;
            String s = editPaiva.getText();
            String virhe = null;
            virhe = kirjausKohdalla.setPvm(s);

            if (virhe == null) {
                Dialogs.setToolTipText(editPaiva,"");
                editPaiva.getStyleClass().removeAll("virhe");
                naytaVirhe(virhe);
            } else {
                Dialogs.setToolTipText(editPaiva,virhe);
                editPaiva.getStyleClass().add("virhe");
                naytaVirhe(virhe);
            }
        }
        

        /**
         * Käsitellään lisätietoihin tullut muutos
         * @param tekstikenttä
         */
       
        private void kasitteleMuutosLisatietoihin(@SuppressWarnings("unused") TextArea textArea) {
                if (kirjausKohdalla == null) return;
                String s = editLisatietoja.getText(); 
                String virhe = null;
                virhe = kirjausKohdalla.setLisatiedot(s);

                if (virhe == null) {
                    Dialogs.setToolTipText(editLisatietoja,"");
                    editLisatietoja.getStyleClass().removeAll("virhe");
                    naytaVirhe(virhe);
                } else {
                    Dialogs.setToolTipText(editLisatietoja,virhe);
                    editLisatietoja.getStyleClass().add("virhe");
                    naytaVirhe(virhe);
                }
            }

       
       /**
        * Käsitellään oireeseen tullut muutos
        * @param editOire muuttunut oirekenttä
        * @param kentta mikä oire
        */
       private void kasitteleMuutosOireeseen1(ComboBoxChooser<Oire> editOire, int kentta) {
               if (kirjausKohdalla == null) return;
               Oire o = editOire.getSelectedObject();
               String virhe = null;
               virhe = kirjausKohdalla.setOire(kentta, o);

               if (virhe == null) {
                   Dialogs.setToolTipText(editOire,"");
                   editOire.getStyleClass().removeAll("virhe");
                   naytaVirhe(virhe);
               } else {
                   Dialogs.setToolTipText(editOire,virhe);
                   editOire.getStyleClass().add("virhe");
                   naytaVirhe(virhe);
               }
           }
       
       
       /**
        * Käsitellään lääkkeeseen tullut muutos
        * @param editLaake muuttunut lääkekenttä
        * @param kentta mikä lääke
        */
       private void kasitteleMuutosLaakkeeseen1(ComboBoxChooser<Laake> editLaake, int kentta) {
               if (kirjausKohdalla == null) return;
               Laake l = editLaake.getSelectedObject();
               String virhe = null;
               virhe = kirjausKohdalla.setLaake(kentta, l);

               if (virhe == null) {
                   Dialogs.setToolTipText(editLaake,"");
                   editLaake.getStyleClass().removeAll("virhe");
                   naytaVirhe(virhe);
               } else {
                   Dialogs.setToolTipText(editLaake,virhe);
                   editLaake.getStyleClass().add("virhe");
                   naytaVirhe(virhe);
               }
           }

       /**
        * Luo uuden oireen rekisteriin
        */
       protected void lisaaUusiOire() {
           if (lisaaUusiOire == null || lisaaUusiOire.getText().trim().equals("")) return;
           Oire uusi = new Oire();
           uusi.setVointi(lisaaUusiOire.getText()); 
           uusi.rekisteroi(oirepaivakirja.getOireLkm());//mahdollista lisätä oireita "manuaalisesti" dat.tiedostoon
           oirepaivakirja.lisaa(uusi); 
           if (lisaaUusiOire != null || !lisaaUusiOire.getText().trim().equals("")) {
               editLisatietoja.appendText("  / Lisätty rekisteriin uusi oire " + lisaaUusiOire.getText().toUpperCase()); lisaaUusiOire.clear();
               kasitteleMuutosLisatietoihin(editLisatietoja); 
           }
           try {
               oirepaivakirja.tallenna();
           } catch ( SailoException ex ) {
               ex.getMessage();
           }
       }
       
       
       
       /**
        * Luo uuden lääkkeen rekisteriin
        */
       protected void lisaaUusiLaake() {
           if (lisaaUusiLaake == null || lisaaUusiLaake.getText().trim().equals("")) return;
           Laake uusi = new Laake();
           uusi.setLaake(lisaaUusiLaake.getText()); 
           uusi.rekisteroi(oirepaivakirja.getLaakeLkm());//mahdollista lisätä lääkkeitä "manuaalisesti" dat.tiedostoon
           oirepaivakirja.lisaa(uusi); 
           if (lisaaUusiLaake != null || !lisaaUusiLaake.getText().trim().equals("")) {
               editLisatietoja.appendText("  / Lisätty rekisteriin uusi lääke " + lisaaUusiLaake.getText().toUpperCase()); lisaaUusiLaake.clear();
               kasitteleMuutosLisatietoihin(editLisatietoja); 
           }
           try {
               oirepaivakirja.tallenna();
           } catch ( SailoException ex ) {
               ex.getMessage();
           }
       }
      
           
 
        /**
         * Näytetään kirjauksen kellonaika ja lisätiedot
         * @param kirjaus kirjaus
         */
        public void naytaKirjaus(Kirjaus kirjaus) {
            if (kirjaus == null) return;
            if (kirjaus.getKlo().equals("")) editKello.setText(aika()); else editKello.setText(kirjaus.getKlo());
            //editKello.setText(kirjaus.getKlo());
            if (kirjaus.getPvm().equals("")) editPaiva.setText(paivays()); else editPaiva.setText(kirjaus.getPvm());
            editLisatietoja.setText(kirjaus.getLisatiedot());
        }

        
        /**
         * Otetaan päiväys koneen kalenterista
         * @return päiväys
         */
        
        public String paivays() {
            Calendar nyt = Calendar.getInstance();
            String pv = nyt.get(Calendar.DATE) + "";
            if ((nyt.get(Calendar.DATE) < 10)) pv = 0 + pv;
            int kk = nyt.get(Calendar.MONTH) + 1;
            String k = kk + "";
            if (kk < 10) k = 0 + k;
            return  pv + "." +
                    k  + "." +
                    nyt.get(Calendar.YEAR);
        }
        

        
        /**
         * Otetaan aika koneen kalenterista
         * @return aika
         */
        
        public String aika() {
            Calendar nyt = Calendar.getInstance();
            int min =  nyt.get(Calendar.MINUTE);
            String m = min + "";
            if (min < 10) m = 0 + m;
            int tun=  nyt.get(Calendar.HOUR_OF_DAY);
            String t = tun + "";
            if (tun < 10) t = 0 + t;
            return  t + ":" +
                    m;
        }
    
        


        /**
         * Luodaan kirjauksen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
         * @param modalityStage mille ollaan modaalisia, null = sovellukselle
         * @param oletus mitä dataan näytetään oletuksena
         * @param oirepaivakirja oirepaivakirja
         * @return null jos painetaan Cancel, muuten täytetty tietue
         */
        public static Kirjaus kysyKirjaus(Stage modalityStage, Kirjaus oletus, Oirepaivakirja oirepaivakirja) {
                return ModalController.<Kirjaus, KirjausDialogController>showModal(
                        KirjausDialogController.class.getResource("KirjausDialogView.fxml"),
                        "Oirepäiväkirja",
                        modalityStage, oletus, ctrl -> ctrl.setOirepaivakirja(oirepaivakirja));
        }

}