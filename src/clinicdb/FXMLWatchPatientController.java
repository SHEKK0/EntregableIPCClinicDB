/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicdb;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Appointment;
import model.Days;
import model.Doctor;
import model.Patient;
import javafx.beans.property.ReadOnlyStringWrapper;

/**
 * FXML Controller class
 *
 * @author javiD
 */
public class FXMLWatchPatientController implements Initializable {
    @FXML
    private TextField textNombre;
    @FXML
    private Font x1;
    private ImageView imagePersona;
    @FXML
    private TextField textTel;
    @FXML
    private TableView<Appointment> tabCitas;
    @FXML
    private TableColumn<Appointment, Days> colDate;
    @FXML
    private TableColumn<Appointment, String> colMed;
    @FXML
    private TextField textId;
    @FXML
    private Button closeButton;
    @FXML
    private ImageView imgPatient;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
    
    public void setName(String name){
        textNombre.setText(name);
        textNombre.setDisable(true);
    }
    public void setTelf(String tel){
        textTel.setText(tel);
        textTel.setDisable(true);
    }
    public void setImage(Image image){
        imagePersona.setImage(image);
    }
    public void setId(String Id){
        textId.setText(Id);
        textId.setDisable(true);
    }
    
    public void setTable(ArrayList<Appointment> list) {
        tabCitas.getItems().addAll(list);

        colDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDateTime"));

        colMed.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDoctor().getName()+ " " + cellData.getValue().getDoctor().getSurname()));
    }
    @FXML
    private void closeButton(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
         stage.close();
    }

    @FXML
    private void cargarImagen(ActionEvent event) {
    }
    
}
