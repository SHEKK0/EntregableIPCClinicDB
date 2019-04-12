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
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

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
    private ChoiceBox choice;
    @FXML
    private ChoiceBox ID;
    @FXML
    private TabPane tabPane;
    @FXML
    private Button newMedicoButton;
    @FXML
    private TableView<Appointment> TabAppointment;
    @FXML
    private TableColumn<Appointment, String> colPatient;
    @FXML
    private TableColumn<Appointment, String> colMedico;
    @FXML
    private TableColumn<Appointment, String> colFecha;
    @FXML
    private Button verPatient;
    @FXML
    private Button datePatient;
    @FXML
    private Button seeMedic;
    @FXML
    private Button dateMedic;
    @FXML
    private TextField searchPatient;
    @FXML
    private TextField searchDoctor;
    @FXML
    private TextField searchDate;
    @FXML
    private Button newDate;
    @FXML
    private Button seePatientDate;
    @FXML
    private Button seeMedicDate;
    @FXML
    private Button seeDate;
    @FXML
    private Button deleteDate;
    @FXML
    private GridPane gridAdd;
    @FXML
    private ImageView imageAdd;
    @FXML
    private Font x1;
    @FXML
    private TextField examinationRoom;
    @FXML
    private TextField availableDays;
    @FXML
    private TextField iniDay;
    @FXML
    private TextField fiDay;
    
        
    private ClinicDBAccess clinic;
    private ObservableList<Patient> listPatients;
    private ObservableList<Doctor> listDoctors;
    private ObservableList<Appointment> listCitas;
    private TextField salaText;
    
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
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Patient> filteredData = new FilteredList<>(listPatients, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchPatient.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(patient -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (patient.getName().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (patient.getSurname().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }else if (patient.getIdentifier().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (patient.getTelephon().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Patient> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(TabPaciente.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        TabPaciente.setItems(sortedData);


        //Añadir medicos a la lista de medicos desde el archivo



        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Doctor> filteredDataDoctor = new FilteredList<>(listDoctors, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchDoctor.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataDoctor.setPredicate(doctor -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (doctor.getName().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (doctor.getName().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }else if (doctor.getIdentifier().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (doctor.getTelephon().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Doctor> sortedDataDoctor = new SortedList<>(filteredDataDoctor);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedDataDoctor.comparatorProperty().bind(TabMedico.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        TabMedico.setItems(sortedDataDoctor);

        //Añadir citas a la lista
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).



        FilteredList<Appointment> filteredDataAppointment = new FilteredList<>(listCitas, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchDate.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataAppointment.setPredicate(appointment -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (appointment.getPatient().getName().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (appointment.getPatient().getSurname().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (appointment.getDoctor().getName().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (appointment.getDoctor().getSurname().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Appointment> sortedDataAppointment= new SortedList<>(filteredDataAppointment);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedDataAppointment.comparatorProperty().bind(TabAppointment.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        TabAppointment.setItems(sortedDataAppointment);

 //---------------------------------------------------------------------------//       
        
        choice.getItems().addAll("Paciente", "Médico", "Cita");
        choice.setValue("Paciente");
        ID.getItems().addAll("DNI","NIF","SS");
        ID.setValue("DNI");

        examinationRoom.setVisible(false);
        availableDays.setVisible(false);
        iniDay.setVisible(false);
        fiDay.setVisible(false);

        
//---------------------------------------------------------------------------//
        // TABLE VIEW PACIENTE //


        NPatient.setCellValueFactory(new PropertyValueFactory<>("name")); // Asegurarse que el nombre es el mismo que el de la clase. Asi puede recuperar el valor.
        APatient.setCellValueFactory(new PropertyValueFactory<>("surname"));
        IdPatient.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        TelPatient.setCellValueFactory(new PropertyValueFactory<>("telephon"));
 //--------------------------------------------
        verPatient.disableProperty().bind(Bindings.isEmpty(TabPaciente.getSelectionModel().getSelectedItems()));
        deletePatient.disableProperty().bind(Bindings.isEmpty(TabPaciente.getSelectionModel().getSelectedItems()));
        datePatient.disableProperty().bind(Bindings.isEmpty(TabPaciente.getSelectionModel().getSelectedItems()));

 //-------------------------------------------------------
        newPacienteButton.setOnAction( e -> {
            //NUEVO PACIENTE BOTON, A TERMINAR
            tabPane.getSelectionModel().select(3);
            choice.setValue("Paciente");
        });


        datePatient.setOnAction(e -> {
            tabPane.getSelectionModel().select(3);
            choice.setValue("Cita");
            // Patient patient = TabPaciente.getSelectionModel().getSelectedItem();
            //patientField.setText(patient.getName() + " " + patient.getSurname());
        });
        
        deletePatient.setOnAction((ActionEvent e) -> {
            deletePatient();
        });
        
        verPatient.setOnAction(e -> seePatient(TabPaciente.getSelectionModel().getSelectedItem()));        
//---------------------------------------------------------------------------//
        // TABLEVIEW MEDICO //
        NMedico.setCellValueFactory(new PropertyValueFactory<>("name")); // Asegurarse que el nombre es el mismo que el de la clase. Asi puede recuperar el valor.
        AMedico.setCellValueFactory(new PropertyValueFactory<>("surname"));
        IdMedico.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        TelMedico.setCellValueFactory(new PropertyValueFactory<>("telephon"));
    //-----------------------------------------------------------------------
        deleteMedic.disableProperty().bind(Bindings.isEmpty(TabMedico.getSelectionModel().getSelectedItems()));
        seeMedic.disableProperty().bind(Bindings.isEmpty(TabMedico.getSelectionModel().getSelectedItems()));
        dateMedic.disableProperty().bind(Bindings.isEmpty(TabMedico.getSelectionModel().getSelectedItems()));
    //------------------------------------------------------------------------       
        newMedicoButton.setOnAction( e -> {
            //NUEVO MÉDICO, A TERMINAR
            tabPane.getSelectionModel().select(3);
            choice.setValue("Médico");
        });
        
         deleteMedic.setOnMouseClicked(e -> {
            if (confirm("médico?")) {
                Doctor aEliminar = TabMedico.getSelectionModel().getSelectedItem();
                if (!clinic.hasAppointments(aEliminar)) {
                    listDoctors.remove(aEliminar); // falta añadir alerta si no se elimina
                    //eliminar de la tabla
                    TabMedico.getItems().remove(aEliminar);
                    TabMedico.getSelectionModel().setSelectionMode(null);
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle(clinic.getClinicName());
                    alert.setHeaderText("¡No se puede eliminar!");
                    alert.setContentText("El médico tiene citas pendientes.");

                    alert.showAndWait();
                }
            }
        });
         
         //VER MÉDICO, A CREAR
         seeMedic.setOnAction(e -> seeMedic(TabMedico.getSelectionModel().getSelectedItem()));    
         
   //_------------------------------------------//
        //----------- CITAS ----------------//
        seeMedicDate.disableProperty().bind(Bindings.isEmpty(TabAppointment.getSelectionModel().getSelectedItems()));
        seeDate.disableProperty().bind(Bindings.isEmpty(TabAppointment.getSelectionModel().getSelectedItems()));
        seePatientDate.disableProperty().bind(Bindings.isEmpty(TabAppointment.getSelectionModel().getSelectedItems()));
        deleteDate.disableProperty().bind(Bindings.isEmpty(TabAppointment.getSelectionModel().getSelectedItems()));

        seePatientDate.setOnAction(e -> seePatient(TabAppointment.getSelectionModel().getSelectedItem().getPatient()));

        colPatient.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getPatient().getName() + " "
                + cellData.getValue().getPatient().getSurname()
        ));
        colMedico.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getDoctor().getName() + " "
                + cellData.getValue().getDoctor().getSurname()
        ));
        colFecha.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getAppointmentDateTime().toString()
        ));

        deleteDate.setOnAction(e -> {
            if (confirm("cita?")) {
                Appointment aEliminar = TabAppointment.getSelectionModel().getSelectedItem();
                if (true) { // La cita aun no ha sucedido 
                    listCitas.remove(aEliminar);
                    //eliminar de la tabla
                    TabAppointment.getItems().remove(aEliminar);
                    TabAppointment.getSelectionModel().setSelectionMode(null);
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle(clinic.getClinicName());
                    alert.setHeaderText("¡No se puede eliminar!");
                    alert.setContentText("La cita ya ha sucedido.");

                    alert.showAndWait();
                }
            }
        });
        newDate.setOnAction(e -> {
            //NUEVO MÉDICO, A TERMINAR
            tabPane.getSelectionModel().select(3);
            choice.setValue("Cita");
        });      
         

// ----------------------------------------------------------------------//
        // AnchorPane add // COMPLETAR
        choice.getSelectionModel().selectedIndexProperty().addListener((observable,oldValue,newValue) -> { // Para cambiar los textfield al decir paciente medico tal 
            TextField examination = new TextField(); // En algún momento habrá que montar esto como global.
            switch (newValue.intValue()) {
                case 0:
                    examinationRoom.setVisible(false);
                    availableDays.setVisible(false);
                    iniDay.setVisible(false);
                    fiDay.setVisible(false);
                case 1:
                    examinationRoom.setVisible(true);
                    availableDays.setVisible(true);
                    iniDay.setVisible(true);
                    fiDay.setVisible(true);

                    break;
                case 2: 
                    examinationRoom.setVisible(false);
                    availableDays.setVisible(false);
                    iniDay.setVisible(false);
                    fiDay.setVisible(false);

                    break;
            }
        });
        
        
// ----------------------------------------------------------------------// 

    }
   
    @FXML
    private void accept() {
        //AÑADIR BOTON ACEPTAR, A TERMINAR
        switch (choice.getValue().toString()) {
            case ("Paciente"):
                Patient patient = null;
                if (!checkInputsPatient()) {somethingEmpty();break;}
                boolean aux = existePaciente(listPatients, id.getText());
                if (!aux) {
                    patient = new Patient(
                            id.getText(),
                            name.getText(),
                            surname.getText(),
                            tel.getText(),
                            imageAdd.getImage()
                    );
                    patient.setPhoto(imageAdd.getImage());
                    listPatients.add(patient);
                    acceptAlert("Paciente");
                    newInput();
                    TabPaciente.setItems(listPatients); // Refresh
                } else {
                    duplicate();
                }

                break;
            case ("Médico"):
                Doctor doctor = null;
                if(!checkInputsPatient()) {somethingEmpty();break;}
                if (!existeMedico(listDoctors, id.getText())) {
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
                    acceptAlert("Médico");
                    newInput();
                    TabMedico.setItems(listDoctors);
                } else {
                    duplicate();
                }

                break;
            case ("Cita"):
                Appointment cita = null;
                if(!checkInputsDoctor()) {somethingEmpty();break;}
                //cita = new Appointment(LocalDateTime.MIN, doctorField.getText(), patientField.getText());
                if (!existeCita(listCitas,cita)) { // FIX ME !existeCita(listCitas)
                    listCitas.add(cita);
                    acceptAlert("Cita");
                    newInput();
                    TabAppointment.setItems(listCitas);
                } else {
                    duplicate();
                }
                break;
        }
    }
     private boolean checkInputsPatient() { // Comprueba que los campos no estén vacíos. Un poco la doble negación rara.
        return !(id.getText().equals("")
                || name.getText().equals("")
                || surname.getText().equals("")
                || tel.getText().equals(""));
    }
     
     //Esto esta bien?
    private boolean checkInputsDoctor(){
        return !(id.getText().equals("")
                || name.getText().equals("")
                || surname.getText().equals("")
                || tel.getText().equals("")
                || (salaText.visibleProperty().getValue() && salaText.getText().equals("")));
    } 
     
    @FXML
    private void newInput() {
        id.setText("");
        name.setText("");
        surname.setText("");
        tel.setText("");
        imageAdd.setImage(new Image(getClass().getResource("/images/default.png").toExternalForm())); // Pa encontrar bien la foto, se lo dejas hacer a java
    }
     
    
    private void deletePatient(){
        if (confirm("paciente?")) {
                Patient aEliminar = TabPaciente.getSelectionModel().getSelectedItem();
                if (!clinic.hasAppointments(aEliminar)) {
                    listPatients.remove(aEliminar); //falta añadir alerta si no se elimin
                    TabPaciente.getItems().remove(aEliminar);
                    TabPaciente.getSelectionModel().select(null);
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle(clinic.getClinicName());
                    alert.setHeaderText("¡No se puede eliminar!");
                    alert.setContentText("El paciente tiene citas pendientes.");

                    alert.showAndWait();
                }
            }
    }
     
     /**
      * 
      * @param list
      * @param id
      * @return booleano de si existe o no ese paciente ya
      */
     private boolean existePaciente(ObservableList<Patient> list, String id){
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
      private boolean existeMedico(ObservableList<Doctor> list, String id){
         Boolean res = false;
         for(int i = 0; i < list.size(); i ++){
             if(list.get(i).getIdentifier().compareTo(id) == 0) return true;
         }
         return res;
     }
      private boolean existeCita(ObservableList<Appointment> list, Appointment cita) {
          System.out.println(cita);
            for (Appointment aux : list) {
                if (cita == null || aux.equals(cita)) return true;
            }
        return false;
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
            controller.setImage(patient.getPhoto()); 
            controller.setTelf(patient.getTelephon());
            controller.setTable(clinic.getPatientAppointments(patient.getIdentifier()));
            controller.setClinic(clinic);
            controller.setId(patient.getIdentifier());
            stage.show();
            controller.getTable();
            listCitas = FXCollections.observableList(controller.getTable());
        } catch (IOException er) {
            System.out.println("adkñlsjf");
        }
    }

    @FXML
    private void verPaciente(ActionEvent event) {
    }
    
    
    //---------------------//
// ALERTAS // 

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
        } catch (Exception e) {
        }
    }
    
    private void somethingEmpty() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(clinic.getClinicName());
        alert.setHeaderText("¡Rellena los campos obligatorios!");

        alert.showAndWait();
    }
        private boolean confirm(String string) { // Los delete
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(clinic.getClinicName());
        alert.setHeaderText("¿Eliminar " + string);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;

    }
        
    private void duplicate() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(clinic.getClinicName());
        alert.setHeaderText("¡Identificación duplicada!");

        alert.showAndWait();
    }

    private void acceptAlert(String string) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(clinic.getClinicName());
        alert.setHeaderText("¡" + string + " añadido!");

        alert.showAndWait();
    }


    private void seeMedic(Doctor doctor) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXMLWatchDoctor.fxml"));
            loader.load();
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            FXMLWatchDoctorController controller = loader.getController();
            controller.setName(doctor.getName() + ", " + doctor.getSurname());
            controller.setImage(doctor.getPhoto()); 
            controller.setTelf(doctor.getTelephon());
            controller.setTable(clinic.getDoctorAppointments(doctor.getIdentifier()));
            controller.setClinic(clinic);
            controller.setSala(doctor.getExaminationRoom());
            controller.setId(doctor.getIdentifier());
            controller.setTableDays(doctor.getVisitDays());
            stage.show();
            controller.getTable();
            listCitas = FXCollections.observableList(controller.getTable());
        } catch (IOException er) {
            System.out.println("adkñlsjf");
        }
    }
    
}


