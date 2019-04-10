package clinicdb;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import DBAccess.ClinicDBAccess;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Appointment;
import model.DateTimeAdapter;
import model.Doctor;
import model.Patient;
import clinicdb.FXMLWatchPatientController;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.util.Optional;
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
    private Button deleteDate;
    @FXML
    private Button addDate;
    @FXML
    private Button seePatientDate;
    @FXML
    private Button seeMedicDate;
    @FXML
    private Button seeDate;
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
    
    private ObservableList<Patient> listPatients;
    private ObservableList<Doctor> listDoctors;
    private ObservableList<Appointment> listCitas;
    private ClinicDBAccess clinic;
    @FXML
    private Button datePatient;
    @FXML
    private Button seeMedic;
    @FXML
    private Button dateMedic;
    @FXML
    private ImageView imageAdd;
    @FXML
    private TextField searchPatient;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Añadimos la clinica al iniciar
        clinic = ClinicDBAccess.getSingletonClinicDBAccess();
        //listas de pacientes , doctores y citas
        listPatients = FXCollections.observableList(clinic.getPatients());
        listDoctors = FXCollections.observableList(clinic.getDoctors());
        listCitas = FXCollections.observableList(clinic.getAppointments());

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


        datePatient.disableProperty().bind(Bindings.isEmpty(TabPaciente.getSelectionModel().getSelectedItems()));
        datePatient.setOnAction(e -> {
            tabPane.getSelectionModel().select(3);
            choice.setValue("Cita");
        });
        deletePatient.setOnAction(e -> {
            if(confirm(" paciente.")) {
                //ELIMINAR PACIENTE BOTON, A TERMINAR
                Patient aEliminar = TabPaciente.getSelectionModel().getSelectedItem();
                listPatients.remove(aEliminar); //falta añadir alerta si no se elimin
                TabPaciente.getItems().remove(aEliminar);
                TabPaciente.getSelectionModel().select(null);
            }
        });
        // TODO LO NECESARIO PARA VER AL PACIENTE COMPLETO FALTA (NO SE VE EL NOMBRE DEL DOCTOR)
        verPatient.disableProperty().bind(Bindings.isEmpty(TabPaciente.getSelectionModel().getSelectedItems()));

//---------------------------------------------------------------------------//
        // TABLEVIEW MEDICO //
        NMedico.setCellValueFactory(new PropertyValueFactory<>("name")); // Asegurarse que el nombre es el mismo que el de la clase. Asi puede recuperar el valor.
        AMedico.setCellValueFactory(new PropertyValueFactory<>("surname"));
        IdMedico.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        TelMedico.setCellValueFactory(new PropertyValueFactory<>("telephon"));
        newMedicoButton.setOnAction( e -> {
            //NUEVO MÉDICO, A TERMINAR
            tabPane.getSelectionModel().select(3);
            choice.setValue("Médico");
        });

        deleteMedic.disableProperty().bind(Bindings.isEmpty(TabMedico.getSelectionModel().getSelectedItems()));
        seeMedic.disableProperty().bind(Bindings.isEmpty(TabMedico.getSelectionModel().getSelectedItems()));
        dateMedic.disableProperty().bind(Bindings.isEmpty(TabMedico.getSelectionModel().getSelectedItems()));
        deleteMedic.setOnAction(e -> {
            //ELIMINAR MEDICO BOTON, A TERMINAR
            if(confirm("médico.")) {
                Doctor aEliminar = TabMedico.getSelectionModel().getSelectedItem();
                listDoctors.remove(aEliminar); // falta añadir alerta si no se elimina
                //eliminar de la tabla
                TabMedico.getItems().remove(aEliminar);
                TabMedico.getSelectionModel().setSelectionMode(null);
            }
        });
        //-----------------------------------//
        // ----------- Citas --------------- //
        seeMedicDate.disableProperty().bind(Bindings.isEmpty(TabAppointment.getSelectionModel().getSelectedItems()));
        seePatientDate.disableProperty().bind(Bindings.isEmpty(TabAppointment.getSelectionModel().getSelectedItems()));
        deleteDate.disableProperty().bind(Bindings.isEmpty(TabAppointment.getSelectionModel().getSelectedItems()));

        seePatientDate.setOnAction(e -> seePatient(TabAppointment.getSelectionModel().getSelectedItem().getPatient()));

        colPatient.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                             cellData.getValue().getPatient().getName()+
                                   " " + cellData.getValue().getPatient().getSurname()));

        colMedico.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                              cellData.getValue().getDoctor().getName()+
                               " " + cellData.getValue().getDoctor().getSurname()));
        colFecha.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((cellData.getValue().getAppointmentDateTime().toString())));



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
    private void seePatient(Patient patient) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXMLWatchPatient.fxml"));
            loader.load();
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            FXMLWatchPatientController controller = loader.getController();
            controller.setName(patient.getName() + ", " + patient.getSurname());
            //controller.setImage(patient.getPhoto()); //a mi me da null
            controller.setTelf(patient.getTelephon());
            controller.setTable(clinic.getPatientAppointments(patient.getIdentifier()));
            controller.setId(patient.getIdentifier());
            stage.show();
        }catch(IOException er){
            System.out.println("adkñlsjf");
        }
    }


    @FXML
     private void accept() {
            //AÑADIR BOTON ACEPTAR, A TERMINAR
        switch (choice.getValue().toString()) {
            case("Paciente"):
                Patient patient = null;
                boolean aux = existePaciente(listPatients, id.getText());
                if(!aux){
                patient = new Patient(
                        id.getText(),
                        name.getText(),
                        surname.getText(),

                listPatients.add(patient);
                TabPaciente.setItems(listPatients); // Refresh
                }
                break;
            case("Médico"):
                Doctor doctor =null;
                if(!existeMedico(listDoctors, id.getText())){
                doctor = new Doctor(
                        null,
                        null,
                        null,
                        null,
                        id.getText(),
                        name.getText(),
                        surname.getText(),
                        tel.getText(),
                        imageAdd.getImage());
                listDoctors.add(doctor);
                TabMedico.setItems(listDoctors);
        }
                break;
            default:
                break;
        }
        }
     
     /**
      * 
      * @param list
      * @param id
      * @return booleano de si existe o no ese paciente ya
      */
     public boolean existePaciente(ObservableList<Patient> list, String id){
         Boolean res = false;
         for(int i = 0; i < list.size(); i ++){
             if(list.get(i).getIdentifier().compareTo(id) == 0) return true;
         }
         return res;
     }
      /**
      * 
      * @param list
      * @param id
      * @return booleano de si existe o no ese médico ya
      */
      public boolean existeMedico(ObservableList<Doctor> list, String id){
         Boolean res = false;
         for(int i = 0; i < list.size(); i ++){
             if(list.get(i).getIdentifier().compareTo(id) == 0) return true;
         }
         return res;
     }
    @FXML
    public void exitApplication() { // Guarda en la base de datos.
        try {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(clinic.getClinicName());
            alert.setHeaderText("Saving data in DB");
            alert.setContentText("The application is saving the changes in the data into the database. This action can expend some minutes.");
            alert.show();
            clinic.saveDB();
            Platform.exit();
        }catch(Exception e) {}
    }
    private boolean confirm(String title) {
          Alert alert = new Alert(AlertType.CONFIRMATION);
          alert.setTitle("Borrar "+ title);
          alert.setContentText("¿Confirmar acción?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            return true;
        } else {
            // ... user chose CANCEL or closed the dialog
            return false;
        }
    }
    @FXML
    private void verPaciente(ActionEvent event) {
    }
    @FXML
     private void cargarImagen(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Cargar imagen");
        fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif","*.PNG"),
                    new FileChooser.ExtensionFilter("Todos", "*.*")
        );
        
        File selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        if(selectedFile != null){
            //Falta completar, no se como transformar de file a image
            String path = selectedFile.getPath();
            BufferedImage Bufferedimage = ImageIO.read(selectedFile);
            javafx.scene.image.Image image = SwingFXUtils.toFXImage(Bufferedimage, null);
            imageAdd.setImage(image);
        }
     }

     /**
     *   METODO PARA BUSCAR EN LA LISTA DE PACIENTES, NULL POINTER EXCEPTION CUANDO BUSCAS LA PRIMERA LETRA
     */
    @FXML
    private void buscarPaciente(KeyEvent event) {
        
    }
    }


