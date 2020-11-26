package fxOirepaivakirja;
    
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import oirepaivakirja.Oirepaivakirja;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * @author Nelli Kiianmaa, nelli.j.kiianmaa@student.jyu.fi
 * Vika: virhetyyli ei toimi
 * @version1.1 
 * @version1.2 1.4.2020
 * 
 * Pääohjelma oirepäiväkirja-ohjelman käynnistämiseksi
 */
public class OirepaivakirjaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("OirepaivakirjaGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final OirepaivakirjaGUIController oirepaivakirjaCtrl = (OirepaivakirjaGUIController)ldr.getController();

            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("oirepaivakirja.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Oirepäiväkirja");
            primaryStage.setOnCloseRequest((event) -> {
                    if ( !oirepaivakirjaCtrl.voikoSulkea() ) event.consume();
                });
            
            Oirepaivakirja oirepaivakirja = new Oirepaivakirja();  
            oirepaivakirjaCtrl.setOirepaivakirja(oirepaivakirja); 
            
            primaryStage.show();
            if ( !oirepaivakirjaCtrl.avaa() ) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
        
        /**
         * Käynnistetään käyttöliittymä 
         * @param args komentorivin parametrit
         */
        public static void main(String[] args) {
            launch(args);
        }
    }
