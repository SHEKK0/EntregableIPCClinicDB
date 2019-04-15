/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicdb;

import DBAccess.ClinicDBAccess;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.C;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import model.Appointment;
import model.Days;
import model.Doctor;
import model.Patient;

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
    private ImageView imagePersona;
    @FXML
    private Button seeDate;
    @FXML
    private Button deleteDate;

    
    private ArrayList<Appointment> list;
    private ClinicDBAccess clinic;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        seeDate.disableProperty().bind(Bindings.isEmpty(tabCitas.getSelectionModel().getSelectedItems()));
        deleteDate.disableProperty().bind(Bindings.isEmpty(tabCitas.getSelectionModel().getSelectedItems()));
        seeDate.setOnAction(e-> System.out.println("Hola esto falta por arreglar"));
        deleteDate.setOnAction(e -> {
            if (confirm("cita?")) {
                Appointment aEliminar = tabCitas.getSelectionModel().getSelectedItem();
                if (true) { // La cita aun no ha sucedido
                    list.remove(aEliminar);
                    //eliminar de la tabla
                    tabCitas.getItems().remove(aEliminar);
                    tabCitas.getSelectionModel().setSelectionMode(null);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(clinic.getClinicName());
                    alert.setHeaderText("¡No se puede eliminar!");
                    alert.setContentText("La cita ya ha sucedido.");

                    alert.showAndWait();
                }
            }
        });
        
    }
    
      private boolean confirm(String string) { // Los delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(clinic.getClinicName());
        alert.setHeaderText("¿Eliminar " + string);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;

    }
    
    public void setName(String name){
        textNombre.setText(name);
        textNombre.setDisable(true);
    }
    public void setTelf(String tel){
        textTel.setText(tel);
        textTel.setDisable(true);
    }
    public void setImage(Image image) throws FileNotFoundException{
            imagePersona.setImage(image);
        }
    public void setClinic(ClinicDBAccess clinic) {
        this.clinic = clinic;
    }
    public void setId(String Id){
        textId.setText(Id);
        textId.setDisable(true);
    }
    
    public void setTable(ArrayList<Appointment> list){
        this.list = list;

        tabCitas.getItems().addAll(list);
        
        colDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDateTime"));
        colMed.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDoctor().getName()+ " " + cellData.getValue().getDoctor().getSurname()));
    }
    public ArrayList<Appointment> getTable() {
        return list;
    }

    
    @FXML
    private void closeButton(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
         stage.close();
    }

    
}
