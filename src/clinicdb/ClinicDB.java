package clinicdb;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import DBAccess.ClinicDBAccess;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author javiD
 */
public class ClinicDB extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLclinicDB.fxml"));
        Parent root = (Parent)loader.load();
        FXMLclinicDBController controller = loader.getController(); // Importante para que pueda cerrar.
        // Lo mismo que cerrar, si no se cambia y ya.
        stage.setOnHidden(event -> controller.exitApplication());
        Scene scene = new Scene(root);
        stage.setTitle("IPC MEDICAL SERVICES CLINIC");
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(900);

        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
