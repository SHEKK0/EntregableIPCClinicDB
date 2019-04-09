package clinicdb;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import DBAccess.ClinicDBAccess;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Appointment;
import model.Doctor;
import model.Patient;
import clinicdb.FXMLWatchPatientController;

/**
 *
 * @author javiD
 */
public class FXMLclinicDBController implements Initializable {
    @FXML
    private Button newPacienteButton;
    
    @FXML
    private TableView<Patient> TabPaciente;
    @FXML
    private TableColumn<Patient,String> NPatient;
    @FXML
    private TableColumn<Patient,String> APatient;
    @FXML
    private TableColumn<Patient,String> IdPatient;
    @FXML
    private TableColumn<Patient,String> TelPatient;
    @FXML
    private TableView<Doctor> TabMedico;
    @FXML
    private TableColumn<Doctor,String> NMedico;
    @FXML
    private TableColumn<Doctor,String> AMedico;
    @FXML
    private TableColumn<Doctor,String> IdMedico;
    @FXML
    private TableColumn<Doctor,String> TelMedico;
    @FXML
    private Button deleteMedic;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField id;
    @FXML
    private TextField tel;
    @FXML
    private Button deletePatient;
    @FXML
    private AnchorPane inicioPane;
    @FXML
    private ChoiceBox choice;
    @FXML
    private ChoiceBox ID;
    @FXML
    private TabPane tabPane;
    @FXML
    private Button newMedicoButton;
    @FXML
    private Font x1;
    @FXML
    private TableView<Appointment> TabAppointment;
    @FXML
    private TableColumn<Appointment, String> colPatient;
    @FXML
    private TableColumn<Appointment, String> colMedico;
    @FXML
    private TableColumn<Appointment, String> colFecha;
    @FXML
    private Button newPacienteButton2;
    @FXML
    private Button addImageButton;
    @FXML
    private TextField tel1;
    @FXML
    private Button acceptButton;
    private TableColumn<?, ?> EmailPatient;
    @FXML
    private StackPane stackRootPane;
    @FXML
    private Button verPatient;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Añadimos la clinica al iniciar
        ClinicDBAccess clinic = ClinicDBAccess.getSingletonClinicDBAccess();
        //listas de pacientes , doctores y citas
        ArrayList<Patient> listPatients = clinic.getPatients();
        ArrayList<Doctor> listDoctors = clinic.getDoctors();
        ArrayList<Appointment> listCitas = clinic.getAppointments();
            
//-----------------------------------------------------------------------//
        // Añadir pacientes a la lista de pacientes desde el archivo        
        TabPaciente.getItems().addAll(listPatients);
        //Añadir medicos a la lista de medicos desde el archivo
        TabMedico.getItems().addAll(listDoctors);
        //Añadir citas a la lista
        TabAppointment.getItems().addAll(listCitas);
 //---------------------------------------------------------------------------//       
        
        choice.getItems().addAll("Paciente", "Médico", "Cita");
        choice.setValue("Paciente");
        ID.getItems().addAll("DNI","NIF","SS");
        ID.setValue("DNI");

//---------------------------------------------------------------------------//
        // TABLE VIEW PACIENTE //
        NPatient.setCellValueFactory(new PropertyValueFactory<>("name")); // Asegurarse que el nombre es el mismo que el de la clase. Asi puede recuperar el valor.
        APatient.setCellValueFactory(new PropertyValueFactory<>("surname"));
        IdPatient.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        TelPatient.setCellValueFactory(new PropertyValueFactory<>("telephon"));
 
        newPacienteButton.setOnAction( e -> {
            //NUEVO PACIENTE BOTON, A TERMINAR
            tabPane.getSelectionModel().select(3);
            choice.setValue("Paciente");
        });
        deletePatient.disableProperty().bind(Bindings.isEmpty(TabPaciente.getSelectionModel().getSelectedItems()));
        
        deletePatient.setOnAction(e -> {
            //ELIMINAR PACIENTE BOTON, A TERMINAR
            Patient aEliminar = TabPaciente.getSelectionModel().getSelectedItem();
            listPatients.remove(aEliminar); //falta añadir alerta si no se elimin
            TabPaciente.getItems().remove(aEliminar);
            TabPaciente.getSelectionModel().select(null);
        });
        
        verPatient.disableProperty().bind(Bindings.isEmpty(TabPaciente.getSelectionModel().getSelectedItems()));
        verPatient.setOnAction(e -> {
            Patient patient = TabPaciente.getSelectionModel().getSelectedItem();
             try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("FXMLWatchPatient.fxml"));
                loader.load();
                Parent p = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                FXMLWatchPatientController controller = loader.getController();
                controller.setName(patient.getName() + ", " + patient.getSurname());
                controller.setImage(patient.getPhoto());
                controller.setTelf(patient.getTelephon());
                controller.setTable(clinic.getPatientAppointments(patient.getIdentifier()));
                controller.setId(patient.getIdentifier());
                stage.show();
            }catch(IOException er){
                System.out.println("adkñlsjf");
            }
        });
//---------------------------------------------------------------------------//
        // TABLEVIEW MEDICO //
        NMedico.setCellValueFactory(new PropertyValueFactory<>("name")); // Asegurarse que el nombre es el mismo que el de la clase. Asi puede recuperar el valor.
        AMedico.setCellValueFactory(new PropertyValueFactory<>("surname"));
        IdMedico.setCellValueFactory(new PropertyValueFactory<>("id"));
        TelMedico.setCellValueFactory(new PropertyValueFactory<>("tel"));
        newMedicoButton.setOnAction( e -> {
            //NUEVO MÉDICO, A TERMINAR
            tabPane.getSelectionModel().select(3);
            choice.setValue("Médico");
        });
        deleteMedic.setOnAction(e -> {
            //ELIMINAR MEDICO BOTON, A TERMINAR
            Doctor aEliminar = TabMedico.getSelectionModel().getSelectedItem();
            listDoctors.remove(aEliminar); // falta añadir alerta si no se elimina
            
        });

// ----------------------------------------------------------------------//
        // AnchorPane add //
        choice.getSelectionModel().selectedIndexProperty().addListener((observable,oldValue,newValue) -> { // Para cambiar los textfield al decir paciente medico tal 
            switch (newValue.intValue()) {
                case 1:
                    //TextField hola = new TextField();
                   // System.out.println(addPane.getChildren()); // Null Pointer

            }
        });
        
        
// ----------------------------------------------------------------------// 

    }
    @FXML
    private void accept() {
            //AÑADIR BOTON ACEPTAR, A TERMINAR
        }

    @FXML
    private void verPaciente(ActionEvent event) {
    }


}

