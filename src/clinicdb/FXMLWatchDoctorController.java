/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicdb;

import DBAccess.ClinicDBAccess;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Appointment;
import model.Days;
import model.ExaminationRoom;

/**
 * FXML Controller class
 *
 * @author javiD
 */
public class FXMLWatchDoctorController implements Initializable {
    @FXML
    private TextField textNombre;
    @FXML
    private ImageView imageDoctor;
    @FXML
    private TextField textId;
    @FXML
    private TextField textTel;
    @FXML
    private TextField textSala;
    @FXML
    private TableView<Appointment> tableCitas;
    @FXML
    private Button seeDate;
    @FXML
    private Button deleteDate;
    @FXML
    private TableColumn<Appointment,Days > colDate;
    @FXML
    private TableColumn<Appointment, String> colPatient;
    
    private ArrayList<Appointment> list;
    private ArrayList<Days> listDays;
    private ClinicDBAccess clinic;    
    @FXML
    private Font x1;
    @FXML
    private Text lunes;
    @FXML
    private Text martes;
    @FXML
    private Text miercoles;
    @FXML
    private Text jueves;
    @FXML
    private Text viernes;
    @FXML
    private Text sabado;
    @FXML
    private Text domingo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        seeDate.disableProperty().bind(Bindings.isEmpty(tableCitas.getSelectionModel().getSelectedItems()));
        deleteDate.disableProperty().bind(Bindings.isEmpty(tableCitas.getSelectionModel().getSelectedItems()));
        seeDate.setOnAction(e-> System.out.println("Hola esto falta por arreglar"));
        deleteDate.setOnAction(e -> {
            if (confirm("cita?")) {
                Appointment aEliminar = tableCitas.getSelectionModel().getSelectedItem();
                if (true) { // La cita aun no ha sucedido
                    list.remove(aEliminar);
                    //eliminar de la tabla
                    tableCitas.getItems().remove(aEliminar);
                    tableCitas.getSelectionModel().setSelectionMode(null);
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
        imageDoctor.setImage(image);
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

        tableCitas.getItems().addAll(list);
        
        colDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDateTime"));
        colPatient.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPatient().getName()+ " " + cellData.getValue().getPatient().getSurname()));
    }
    public ArrayList<Appointment> getTable() {
        return list;
    }
    public void setSala(ExaminationRoom ex){
        textSala.setDisable(false);
        textSala.setText(Integer.toString(ex.getIdentNumber()));
        textSala.setDisable(true);
    }
    
    public void setTableDays(ArrayList<Days> list){
        this.listDays = list;
        
        lunes.setFill(Color.GRAY);
        martes.setFill(Color.GRAY);
        miercoles.setFill(Color.GRAY);
        jueves.setFill(Color.GRAY);
        viernes.setFill(Color.GRAY);
        sabado.setFill(Color.GRAY);
        domingo.setFill(Color.GRAY);
        
        for(int i = 0; i < list.size(); i++){
            Days day = list.get(i);
            String dia = day.toString();
            
            if(dia.compareTo("Monday")== 0) lunes.setFill(Color.BLACK);
            if(dia.compareTo("Tuesday")== 0) martes.setFill(Color.BLACK);
            if(dia.compareTo("Wednesday")== 0) miercoles.setFill(Color.BLACK);
            if(dia.compareTo("Thursday")== 0) jueves.setFill(Color.BLACK);
            if(dia.compareTo("Friday")== 0) viernes.setFill(Color.BLACK);
            if(dia.compareTo("Saturday")== 0) sabado.setFill(Color.BLACK);
            if(dia.compareTo("Sunday")== 0) domingo.setFill(Color.BLACK);
            
        }
        
    }
    @FXML
    private void closeButton(ActionEvent event) {
        Stage stage = (Stage) seeDate.getScene().getWindow();
        stage.close();
    }
    
}
