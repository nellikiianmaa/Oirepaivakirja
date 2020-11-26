package fxOirepaivakirja;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oirepaivakirja.Kirjaus;

/**
     * Kysytään kirjauksen tiedot luomalla sille uusi dialogi
     * 
     * @author Nelli Kiianmaa
     * @version1.1 6.4.2020
     *
     */
    public class LisaaDialogController implements ModalControllerInterface<Kirjaus>,Initializable  {

        @FXML private TextField editPvm;
        @FXML private TextField editKlo;
        @FXML private TextField editOire1;
        @FXML private TextField editOire2;
        @FXML private TextField editOire3;
        @FXML private TextField editLaake1;
        @FXML private TextField editLaake2;
        @FXML private TextField editLaake3;
        @FXML private TextField editLisatiedot;    
        @FXML private Label labelVirhe;

       
        @Override
        public void initialize(URL url, ResourceBundle bundle) {
            alusta();  
        }
        
       
        //tässä getRivi() tällä hetkellä tuo pelkän id:n kirjausriville
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

    // ========================================================    
        private Kirjaus kirjausKohdalla;
        private TextField edits[];
       

        /**
         * Tyhjentään tekstikentät 
         * @param edits taulukko, jossa tyhjennettäviä tekstikenttiä
         */
        public static void tyhjenna(TextField[] edits) {
            for (TextField edit : edits)
                edit.setText("");
        }


        /**
         * Tekee tarvittavat muut alustukset. Mm laittaa edit-kentistä tulevan
         * tapahtuman menemään kasitteleMuutosJaseneen-metodiin ja vie sille
         * kentännumeron parametrina.
         */
        protected void alusta() {
            edits = new TextField[]{editPvm, editKlo, editOire1, editOire2, editOire3, 
                    editLaake1, editLaake2, editLaake3, editLisatiedot};
            int i = 0;
            for (TextField edit : edits) {
                final int k = ++i;
                edit.setOnKeyReleased( e -> kasitteleMuutosJaseneen(k, (TextField)(e.getSource())));
            }
        }
        
        
        @Override
        public void setDefault(Kirjaus oletus) {
            kirjausKohdalla = oletus;
            naytaKirjaus(edits, kirjausKohdalla);
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
            editPvm.requestFocus();
        }
        
        
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
         * Käsitellään jäseneen tullut muutos
         * @param edit muuttunut kenttä
         */
        private void kasitteleMuutosJaseneen(int k, TextField edit) {
            if (kirjausKohdalla == null) return;
            String s = edit.getText();
            String virhe = null;
            
            switch (k) {
               case 1 : virhe = kirjausKohdalla.setPvm(s); break;
               case 2 : virhe = kirjausKohdalla.setKlo(s); break;
              // case 3 : virhe = kirjausKohdalla.setOire1(s); break;
               case 4 : virhe = kirjausKohdalla.setOire2(s); break;
               case 5 : virhe = kirjausKohdalla.setOire3(s); break;
               case 6 : virhe = kirjausKohdalla.setLaake1(s); break;
               case 7 : virhe = kirjausKohdalla.setLaake2(s); break;
               case 8 : virhe = kirjausKohdalla.setLaake3(s); break;
               case 9 : virhe = kirjausKohdalla.setLisatiedot(s); break;
               default:
               
            }
            if (virhe == null) {
                Dialogs.setToolTipText(edit,"");
                edit.getStyleClass().removeAll("virhe");
                naytaVirhe(virhe);
            } else {
                Dialogs.setToolTipText(edit,virhe);
                edit.getStyleClass().add("virhe");
                naytaVirhe(virhe);
            }
        }
        
        
        /**
         * Näytetään jäsenen tiedot TextField komponentteihin
         * @param edits taulukko jossa tekstikenttiä
         * @param kirjaus näytettävä kirjaus
         */
        public static void naytaKirjaus(TextField[] edits, Kirjaus kirjaus) {
            if (kirjaus == null) return;
            edits[0].setText(kirjaus.getPvm());
            edits[1].setText(kirjaus.getKlo());
            /*edits[2].setText(kirjaus.getOire1());
            edits[3].setText(kirjaus.getOire2());
            edits[4].setText(kirjaus.getOire3());
            edits[5].setText(kirjaus.getLaake1());
            edits[6].setText(kirjaus.getLaake1());
            edits[7].setText(kirjaus.getLaake2());
            edits[8].setText(kirjaus.getLaake3());
            edits[9].setText(kirjaus.getLisatiedot());*/
        }
        
        
        /**
         * Luodaan kirjauksen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
         * TODO: korjattava toimimaan
         * @param modalityStage mille ollaan modaalisia, null = sovellukselle
         * @param oletus mitä dataan näytetään oletuksena
         * @return null jos painetaan Cancel, muuten täytetty tietue
         */
        public static Kirjaus kysyKirjaus(Stage modalityStage, Kirjaus oletus) {
            return ModalController.<Kirjaus, LisaaDialogController>showModal(
                        LisaaDialogController.class.getResource("JasenDialogView.fxml"),
                        "Oirepäiväkirja",
                        modalityStage, oletus, null 
                    );
        }

}