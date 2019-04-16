package clinicdb;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DBAccess.ClinicDBAccess;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.*;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Appointment;
import model.Doctor;
import model.Patient;
import java.awt.image.BufferedImage;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.E;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import model.Days;
import model.ExaminationRoom;
import model.LocalTimeAdapter;

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
    private TableColumn<Patient, String> NPatient;
    @FXML
    private TableColumn<Patient, String> APatient;
    @FXML
    private TableColumn<Patient, String> IdPatient;
    @FXML
    private TableColumn<Patient, String> TelPatient;
    @FXML
    private TableView<Doctor> TabMedico;
    @FXML
    private TableColumn<Doctor, String> NMedico;
    @FXML
    private TableColumn<Doctor, String> AMedico;
    @FXML
    private TableColumn<Doctor, String> IdMedico;
    @FXML
    private TableColumn<Doctor, String> TelMedico;
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
    private HBox availableDays;
    @FXML
    private ComboBox<LocalTime> iniDay;
    @FXML
    private ComboBox<LocalTime> fiDay;

    private TextField salaText;
    @FXML
    private ToggleButton Monday;
    @FXML
    private ToggleButton Tuesday;
    @FXML
    private ToggleButton Wednesday;
    @FXML
    private ToggleButton Thursday;
    @FXML
    private ToggleButton Friday;
    @FXML
    private ToggleButton Saturday;
    @FXML
    private AnchorPane paneAdd;
    @FXML
    private VBox vBoxAddPac;
    @FXML
    private VBox vBoxAddCita;
    private ListView<Patient> listViewPaciente;
    private ListView<Doctor> listViewDoctor;
    @FXML
    private TextField addCitaPatientSearch;
    @FXML
    private TextField addCitaDoctorSearch;
    @FXML
    private ComboBox<LocalTime> iniCita;
    @FXML
    private TableView<Patient> tableCitaPac;
    @FXML
    private TableColumn<Patient, String> colPa;
    @FXML
    private TableView<Doctor> tableCitaDoc;
    @FXML
    private TableColumn<Doctor, String> colDoc;
    @FXML
    private DatePicker datePicker;


    private ClinicDBAccess clinic;
    private ObservableList<Patient> listPatients;
    private ObservableList<Doctor> listDoctors;
    private ObservableList<Appointment> listCitas;
    private ObservableList<ExaminationRoom> listSalas;
    private ObservableList<LocalTime> listHours;
    private ArrayList<Days> listDays;
    private ObservableList<LocalTime> listHoursCita;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Añadimos la clinica al iniciar
        clinic = ClinicDBAccess.getSingletonClinicDBAccess();
        //listas de pacientes , doctores y citas
        listPatients = FXCollections.observableList(clinic.getPatients());
        listDoctors = FXCollections.observableList(clinic.getDoctors());
        listCitas = FXCollections.observableList(clinic.getAppointments());
        listSalas = FXCollections.observableList(clinic.getExaminationRooms());
        listDays = new ArrayList<>();
        try {
            listHours = FXCollections.observableList(createListHours());
        } catch (Exception ex) {}
//-----------------------------------------------------------------------//
/* INICIALIZAR PACIENTES */

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
                } else if (patient.getIdentifier().toLowerCase().startsWith(lowerCaseFilter)) {
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
//---------------------------------------------------------------------------//
/*

 TABLE VIEW PACIENTE

 */


        NPatient.setCellValueFactory(new PropertyValueFactory<>("name")); // Asegurarse que el nombre es el mismo que el de la clase. Asi puede recuperar el valor.
        APatient.setCellValueFactory(new PropertyValueFactory<>("surname"));
        IdPatient.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        TelPatient.setCellValueFactory(new PropertyValueFactory<>("telephon"));
//---------------------------------------------------------------------//
        verPatient.disableProperty().bind(Bindings.isEmpty(TabPaciente.getSelectionModel().getSelectedItems()));
        deletePatient.disableProperty().bind(Bindings.isEmpty(TabPaciente.getSelectionModel().getSelectedItems()));
        datePatient.disableProperty().bind(Bindings.isEmpty(TabPaciente.getSelectionModel().getSelectedItems()));
//---------------------------------------------------------------------//
        newPacienteButton.setOnAction(e -> {
            //NUEVO PACIENTE BOTON, A TERMINAR
            tabPane.getSelectionModel().select(3);
            choice.setValue("Paciente");
        });
//---------------------------------------------------------------------//
        datePatient.setOnAction(e -> {
            tabPane.getSelectionModel().select(3);
            choice.setValue("Cita");
            tableCitaPac.getSelectionModel().select(TabPaciente.getSelectionModel().getSelectedItem());
            addCitaPatientSearch.setText(TabPaciente.getSelectionModel().getSelectedItem().getName());
        });
//---------------------------------------------------------------------//
        deletePatient.setOnAction((ActionEvent e) -> {
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
        });
//---------------------------------------------------------------------//
        verPatient.setOnAction(e -> seePatient(TabPaciente.getSelectionModel().getSelectedItem()));
//---------------------------------------------------------------------//
/* INICIALIZAR MEDICOS */

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
                } else if (doctor.getIdentifier().toLowerCase().startsWith(lowerCaseFilter)) {
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

//---------------------------------------------------------------------------//

// TABLEVIEW MEDICO //

        NMedico.setCellValueFactory(new PropertyValueFactory<>("name")); // Asegurarse que el nombre es el mismo que el de la clase. Asi puede recuperar el valor.
        AMedico.setCellValueFactory(new PropertyValueFactory<>("surname"));
        IdMedico.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        TelMedico.setCellValueFactory(new PropertyValueFactory<>("telephon"));
//---------------------------------------------------------------------//
        deleteMedic.disableProperty().bind(Bindings.isEmpty(TabMedico.getSelectionModel().getSelectedItems()));
        seeMedic.disableProperty().bind(Bindings.isEmpty(TabMedico.getSelectionModel().getSelectedItems()));
        dateMedic.disableProperty().bind(Bindings.isEmpty(TabMedico.getSelectionModel().getSelectedItems()));
//---------------------------------------------------------------------//
        newMedicoButton.setOnAction(e -> {
            tabPane.getSelectionModel().select(3);
            choice.setValue("Médico");
        });
//---------------------------------------------------------------------//
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
//---------------------------------------------------------------------//
        seeMedic.setOnAction(e -> seeMedic(TabMedico.getSelectionModel().getSelectedItem()));
//---------------------------------------------------------------------//
        dateMedic.setOnAction(e -> {
            tabPane.getSelectionModel().select(3);
            choice.setValue("Cita");
            tableCitaDoc.getSelectionModel().select(TabMedico.getSelectionModel().getSelectedItem());
            addCitaDoctorSearch.setText(TabMedico.getSelectionModel().getSelectedItem().getName());
        });
//---------------------------------------------------------------------//

/* INICIALIZAR CITAS */
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
        SortedList<Appointment> sortedDataAppointment = new SortedList<>(filteredDataAppointment);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedDataAppointment.comparatorProperty().bind(TabAppointment.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        TabAppointment.setItems(sortedDataAppointment);

//---------------------------------------------------------------------//
//----------- CITAS ----------------//


        seeMedicDate.disableProperty().bind(Bindings.isEmpty(TabAppointment.getSelectionModel().getSelectedItems()));
        seeDate.disableProperty().bind(Bindings.isEmpty(TabAppointment.getSelectionModel().getSelectedItems()));
        seePatientDate.disableProperty().bind(Bindings.isEmpty(TabAppointment.getSelectionModel().getSelectedItems()));
        deleteDate.disableProperty().bind(Bindings.isEmpty(TabAppointment.getSelectionModel().getSelectedItems()));
//---------------------------------------------------------------------//
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
//---------------------------------------------------------------------//
        seePatientDate.setOnAction(e -> seePatient(TabAppointment.getSelectionModel().getSelectedItem().getPatient()));
        seeMedicDate.setOnAction(e -> seeMedic(TabAppointment.getSelectionModel().getSelectedItem().getDoctor()));
//---------------------------------------------------------------------//
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
                try {
                    iniCita.setItems(FXCollections.observableList(createListHours(tableCitaDoc.getSelectionModel().getSelectedItem())));
                } catch (Exception ex) {
                    Logger.getLogger(FXMLclinicDBController.class.getName()).log(Level.SEVERE, null, ex);
                }
                iniCita.setValue(iniCita.getItems().get(0));
            }

        });
//---------------------------------------------------------------------//
        newDate.setOnAction(e -> {
            tabPane.getSelectionModel().select(3);
            choice.setValue("Cita");
        });
//---------------------------------------------------------------------------//
/* AÑADIR */

        choice.getItems().addAll("Paciente", "Médico", "Cita");
        choice.setValue("Paciente");
        ID.getItems().addAll("DNI", "NIF", "SS");
        ID.setValue("DNI");
        datePicker.setValue(LocalDate.now());
        vBoxAddCita.setVisible(false);
        vBoxAddPac.setVisible(true);
        vBoxAddPac.setDisable(false);
        examinationRoom.setVisible(false);
        availableDays.setVisible(false);
        iniDay.setVisible(false);
        fiDay.setVisible(false);

/*
 * LAS LISTAS DE LAS HORAS EN ADD MEDICO
 */
        iniDay.getItems().removeAll();
        iniDay.setItems(listHours);
        iniDay.setValue(listHours.get(0));
        fiDay.getItems().removeAll();
        fiDay.setItems(listHours);
        fiDay.setValue(listHours.get(listHours.size() - 1));
// --------------------------------------------//
// EL SELECTOR DE LA HORA EN CITAS
        iniCita.getItems().removeAll();
        iniCita.setItems(listHours);
        iniCita.setValue(listHours.get(0));
// --------------------------------------------//
// EL CALENDARIO EN LAS CITAS
        datePicker.setEditable(false);
        datePicker.setShowWeekNumbers(false);

/*
*
* FILTROS PARA LOS CAMPOS DE TEXTO O NUMEROS
*
* */
        name.textProperty().addListener((observable, oldValue, newValue) -> { // SOLO TEXTO
            if (!newValue.matches("\\sa-zA-Z*")) {
                name.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
        surname.textProperty().addListener((observable, oldValue, newValue) -> { // SOLO TEXTO
            if (!newValue.matches("\\sa-zA-Z*")) {
                surname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
        id.textProperty().addListener((observable, oldValue, newValue) -> { // TEXTO Y NUMEROS
            if (!newValue.matches("\\sa-zA-Z*\\d*")) {
                id.setText(newValue.replaceAll("[^\\sa-zA-Z\\d]", ""));
            }
        });

        tel.textProperty().addListener(new ChangeListener<String>() { //SOLO NUMEROS
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tel.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        examinationRoom.textProperty().addListener(new ChangeListener<String>() { // SOLO NUMEROS
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tel.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
// ----------------------------------------------------------------------//
        choice.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> { // Para cambiar los textfield al decir paciente medico tal
            switch (newValue.intValue()) {
                case 0:
                    visibility(false,false);
                    break;
                case 1:
                    visibility(true,false);
                    break;
                case 2:
                    visibility(false,true);
                    break;
            }
        });

// ----------------------------------------------------------------------//
// TABLE VIEW PACIENTE Y MEDIC EN ADD DATE

        colPa.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName() + " " + cellData.getValue().getSurname()));
        FilteredList<Patient> filteredDataDatePatient = new FilteredList<>(listPatients, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        addCitaPatientSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataDatePatient.setPredicate(patient -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (patient.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (patient.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Patient> sortedDataDatePatient = new SortedList<>(filteredDataDatePatient);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedDataDatePatient.comparatorProperty().bind(tableCitaPac.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableCitaPac.setItems(sortedDataDatePatient);

        colDoc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName() + " " + cellData.getValue().getSurname()));
        FilteredList<Doctor> filteredDataDateDoctor = new FilteredList<>(listDoctors, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        addCitaDoctorSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataDateDoctor.setPredicate(doctor -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (doctor.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (doctor.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Doctor> sortedDataDateDoctor = new SortedList<>(filteredDataDateDoctor);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedDataDateDoctor.comparatorProperty().bind(tableCitaDoc.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableCitaDoc.setItems(sortedDataDateDoctor);

// ----------------------------------------------------------------------//
// BINDINGS ENTRE MEDICOS HORAS DISPONIBLES Y DIAS DISPONIBLES EN ADD DATE

        iniCita.disableProperty().bind(Bindings.isEmpty(tableCitaDoc.getSelectionModel().getSelectedItems()));
        datePicker.disableProperty().bind(Bindings.isEmpty(tableCitaDoc.getSelectionModel().getSelectedItems()));

// ----------------------------------------------------------------------//
// HORAS DISPONIBLES DE CADA MEDICO (CAMBIANDO ENTRE DIAS, SI HAY CITAS EN ESE DIA NO SALEN)
        datePicker.valueProperty().addListener((obs,old,newVaule)->{
            try {
                iniCita.setItems(FXCollections.observableList(createListHours(tableCitaDoc.getSelectionModel().getSelectedItem())));
                iniCita.setValue(iniCita.getItems().get(0));
            } catch(Exception e){}
        });

// ----------------------------------------------------------------------//
// HORAS DISPONIBLES DE CADA MEDICO (CONTANDO LAS CITAS EN EL DIA YA MARCADO EN EL DATEPICKER)
        tableCitaDoc.getSelectionModel().selectedItemProperty().addListener((o,old,newValue)->{
            try {
                iniCita.setItems(FXCollections.observableList(createListHours(newValue)));
            } catch (Exception e) {
            }
            iniCita.setValue(iniCita.getItems().get(0));
        });

// ----------------------------------------------------------------------//
// CREACION INICIAL DE LOS DIAS DISPONIBLES DEL MEDICO EN EL CALENDARIO
        tableCitaDoc.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    Days[] values = Days.values();
                    List<Days> list = new LinkedList<Days>(Arrays.asList(values));
                    try {
                        for (Days day : newValue.getVisitDays()) {
                            for (Days d : values) {
                                if (d.equals(day)) list.remove(d);
                            }
                        }
                    } catch(Exception e){}

                datePicker.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        for (Days day_list : list) {
                            if (date.getDayOfWeek() == DayOfWeek.MONDAY && day_list.equals(Days.Monday))
                                setStyle("-fx-background-color: #ffc0cb;");
                            if (date.getDayOfWeek() == DayOfWeek.TUESDAY && day_list.equals(Days.Tuesday))
                                setStyle("-fx-background-color: #ffc0cb;");
                            if (date.getDayOfWeek() == DayOfWeek.WEDNESDAY && day_list.equals(Days.Wednesday))
                                setStyle("-fx-background-color: #ffc0cb;");
                            if (date.getDayOfWeek() == DayOfWeek.THURSDAY && day_list.equals(Days.Thursday))
                                setStyle("-fx-background-color: #ffc0cb;");
                            if (date.getDayOfWeek() == DayOfWeek.FRIDAY && day_list.equals(Days.Friday))
                                setStyle("-fx-background-color: #ffc0cb;");
                            if (date.getDayOfWeek() == DayOfWeek.SATURDAY && day_list.equals(Days.Saturday))
                                setStyle("-fx-background-color: #ffc0cb;");
                            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                });
        });
    }

// ----------------------------------------------------------------------//
// F    I   N        D  E   L       I   N   I   T   I   A   L   I   Z    E
// ----------------------------------------------------------------------//


// ----------------------------------------------------------------------//
// TODA LA LOGICA DEL BOTÓN ACEPTAR
    @FXML
    private void accept() throws Exception {
        //AÑADIR BOTON ACEPTAR, A TERMINAR
        switch (choice.getValue().toString()) {
            case ("Paciente"):
                Patient patient = null;
                if (!checkInputsPatient()) {
                    errorAlert("Rellena los campos obligatorios!");
                    break;
                }
                if (tel.getText().length() != 9) {
                    errorAlert("El número de teléfono no es correcto!");
                    break;
                }
                boolean aux = existePaciente(listPatients, id.getText().toUpperCase());
                if (aux) {
                    errorAlert("Identificación duplicada!");
                    break;
                }
                patient = new Patient(
                        id.getText().toUpperCase(),
                        name.getText(),
                        surname.getText(),
                        tel.getText(),
                        imageAdd.getImage()
                );
                patient.setPhoto(imageAdd.getImage());
                listPatients.add(patient);
                acceptAlert("Paciente");
                newInput(); // Borra todos los campos
                TabPaciente.setItems(listPatients); // Refresh
                break;
            case ("Médico"):
                Doctor doctor = null;
                if (!checkInputsDoctor()) {
                    errorAlert("Rellena los campos obligatorios!");
                    break;
                }
                if (existeMedico(listDoctors, id.getText().toUpperCase())) {
                    errorAlert("Identifiación duplicada!");
                    break;
                }
                if (!salaInBounds(Integer.parseInt(examinationRoom.getText()))) {
                    errorAlert("Número de sala incorrecto!");
                    break;
                }
                if (tel.getText().length() != 9) {
                    errorAlert("El número de teléfono no es correcto!");
                    break;
                }
                if (iniDay.getValue().compareTo(fiDay.getValue()) > 0) {
                    errorAlert("Hora de inicio mayor que de final!");
                    break;
                }
                doctor = new Doctor(
                        listSalas.get(Integer.parseInt(examinationRoom.getText())),
                        null,//abajo lo creamos tranqui
                        iniDay.getValue(),//iniDay.getText(),
                        fiDay.getValue(),//fiDay.getText(),
                        id.getText().toUpperCase(),
                        name.getText(),
                        surname.getText(),
                        tel.getText(),
                        imageAdd.getImage());
                doctor.setVisitDays(listDays);
                listDoctors.add(doctor);
                acceptAlert("Médico");
                newInput();
                TabMedico.setItems(listDoctors);
                break;
            case ("Cita"):
                Appointment cita = null;
                if (!checkInputsCita()) {
                    errorAlert("Rellena los campos obligatorios!");
                    break;
                }
                cita = new Appointment(
                        LocalDateTime.of(datePicker.getValue(), iniCita.getValue()),
                        tableCitaDoc.getSelectionModel().getSelectedItem(),
                        tableCitaPac.getSelectionModel().getSelectedItem());
                if (existeCita(listCitas, cita)) {
                    errorAlert("La cita ya existe!");
                    break;
                }
                if (isErrorDate(datePicker.getValue(),cita.getDoctor())) {errorAlert("Ese día el doctor no admite visitas!");break;}
                listCitas.add(cita);
                acceptAlert("Cita");
                newInput();
                TabAppointment.setItems(listCitas);
                iniCita.setItems(FXCollections.observableList(createListHours(tableCitaDoc.getSelectionModel().getSelectedItem())));
                iniCita.setValue(iniCita.getItems().get(0));
                break;
        }
    }

//-----------------------------------------------------------------//
// COMPROBACIONES ASI EN GENERAL

    private boolean isErrorDate(LocalDate value, Doctor doctor) {
        for(Days day : doctor.getVisitDays()) {
            if(value.getDayOfWeek().toString().equals(day.toString().toUpperCase())) return false;
        }
        return true;
    }

    private boolean checkInputsPatient() { // Comprueba que los campos no estén vacíos. Un poco la doble negación rara.
        return !(id.getText().equals("")
                || name.getText().equals("")
                || surname.getText().equals("")
                || tel.getText().equals(""));
    }

    private boolean checkInputsDoctor() {
        return !(id.getText().equals("")
                || name.getText().equals("")
                || surname.getText().equals("")
                || tel.getText().equals("")
                || examinationRoom.getText().equals(""));
    }

    private boolean checkInputsCita() {
        return !(tableCitaDoc.getSelectionModel().getSelectedItem().equals(null)
                || tableCitaPac.getSelectionModel().getSelectedItem().equals(null));
    }
    private boolean existePaciente(ObservableList<Patient> list, String id) {
        Boolean res = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdentifier().compareTo(id) == 0) return true;
        }
        return res;
    }

    private boolean existeMedico(ObservableList<Doctor> list, String id) {
        Boolean res = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdentifier().compareTo(id) == 0) return true;
        }
        return res;
    }

    private boolean existeCita(ObservableList<Appointment> list, Appointment cita) {
        for (Appointment aux : list) {
            if (cita == null || aux.getAppointmentDateTime().equals(cita.getAppointmentDateTime()) && aux.getDoctor().equals(cita.getDoctor()))return true;
        }
        return false;
    }

    private boolean salaInBounds(int x) {
        if (x <= listSalas.size()) return true;
        else return false;
    }





    //-----------------------------------------------------------------//
// CONTROLADORES DEL ACCEPT
    @FXML
    private void newInput() {
        id.setText("");
        name.setText("");
        surname.setText("");
        tel.setText("");
        imageAdd.setImage(new Image(getClass().getResource("/images/default.png").toExternalForm())); // Pa encontrar bien la foto, se lo dejas hacer a java
        examinationRoom.setText("");
        Monday.setSelected(false);
        Tuesday.setSelected(false);
        Wednesday.setSelected(false);
        Thursday.setSelected(false);
        Friday.setSelected(false);
        Saturday.setSelected(false);
        iniDay.setValue(listHours.get(0));
        fiDay.setValue(listHours.get(listHours.size() - 1));
        //tableCitaDoc.getSelectionModel().select(null);
        tableCitaPac.getSelectionModel().select(null);
        datePicker.setValue(LocalDate.now());
    }
    @FXML
    private void cargarImagen(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Cargar imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif", "*.PNG"),
                new FileChooser.ExtensionFilter("Todos", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            //Falta completar, no se como transformar de file a image
            String path = selectedFile.getPath();
            BufferedImage Bufferedimage = ImageIO.read(selectedFile);
            javafx.scene.image.Image image = SwingFXUtils.toFXImage(Bufferedimage, null);
            imageAdd.setImage(image);
        }
    }

//-----------------------------------------------------------------//
// VER NUEVOS FXML
    private void seePatient(Patient patient) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXMLWatchPatient.fxml"));
            loader.load();
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle(clinic.getClinicName());
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


    private void seeMedic(Doctor doctor) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXMLWatchDoctor.fxml"));
            loader.load();
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle(clinic.getClinicName());
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

    // ESTO HAY QUE QUITARLO
    @FXML
    private void verPaciente(ActionEvent event) {
    }

//-----------------------------------------------------------------//
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

    private void errorAlert(String s) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(clinic.getClinicName());
        alert.setHeaderText(s);

        alert.showAndWait();
    }

    private boolean confirm(String string) { // Los delete
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(clinic.getClinicName());
        alert.setHeaderText("¿Eliminar " + string);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;

    }

    private void acceptAlert(String string) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(clinic.getClinicName());
        alert.setHeaderText("¡" + string + " añadido!");

        alert.showAndWait();
    }

//--------------------------------------------------//
// LOGICA DEL INITIALIZE


    private void visibility(boolean medic, boolean date) {
        vBoxAddPac.setVisible(!date);
        vBoxAddCita.setVisible(date);
        //vBoxAddCita.setDisable(!date);
        //vBoxAddPac.setDisable(false);
        examinationRoom.setVisible(medic);
        availableDays.setVisible(medic);
        iniDay.setVisible(medic);
        fiDay.setVisible(medic);
    }

    @FXML
    private void getDays(ActionEvent event) {
        ToggleButton button = (ToggleButton) event.getSource();
        String day = button.getId();
        if (button.isSelected()) listDays.add(Days.valueOf(day));
        else listDays.remove(Days.valueOf(day));
    }



    private ArrayList<LocalTime> createListHours() throws Exception {
        return createListHours(LocalTime.of(8,0,0),LocalTime.of(20,0,0));
    }



private ArrayList<LocalTime> createListHours(LocalTime ini, LocalTime fin) throws Exception {
        ArrayList<LocalTime> res = new ArrayList();
        long elapsedMinutes = Duration.between(ini, fin).toMinutes();
        for (int i = 0; i <= elapsedMinutes; i=i+15) {
            LocalTime aux = ini.plusMinutes(i);

            res.add(aux);
        }
        return res;
    }
private ArrayList<LocalTime> createListHours(Doctor doc) throws Exception {
        ArrayList<LocalTime> res = createListHours(doc.getVisitStartTime(),doc.getVisitEndTime()); //lista resultante con las horas disponibles del doctor en question
        LocalDate picked = datePicker.getValue(); //fecha del datepicker
        int year = picked.getYear();    //año del datePicker
        int month = picked.getMonthValue(); //mes del datePicker
        int dayOfMonth = picked.getDayOfMonth(); //dia del datePicker

    //for(LocalTime aux : res) {
    for(int i = 0; i < res.size(); i++) {
            for(int j = 0; j < listCitas.size(); j ++){  //miramos en la lista de citas(la completa no la del medico)  si hay alguna cita que concuerde con la hora y el medico que tenemos seleccionados
                int hour = res.get(i).getHour();
                int minute = res.get(i).getMinute();
                LocalDateTime time = LocalDateTime.of(year, month, dayOfMonth, hour, minute); //creamos el LocalDateTime con los datos aux

                if(doc.getIdentifier().equals(listCitas.get(j).getDoctor().getIdentifier()) // Si tienen el mismo dni
                    && time.equals(listCitas.get(j).getAppointmentDateTime())) { // Si tienen la misma fecha de cita.
                    res.remove(res.get(i)); // Actualizamos la lista sin la fecha esa ya ocupada.
                }
            }
        }
        return res; //devuelve la lista res con las horas disponibles?
    }

}