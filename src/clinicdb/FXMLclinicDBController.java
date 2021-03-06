package clinicdb;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DBAccess.ClinicDBAccess;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.*;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javax.imageio.ImageIO;
import model.Days;
import model.ExaminationRoom;
import model.LocalTimeAdapter;

/**
 *
 * @author jadomen, carsengi
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
    private TableColumn<Appointment, Integer> colRoom;
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
    private Button deleteDate;
    @FXML
    private ImageView imageAdd;
    @FXML
    private TextField examinationRoom;
    @FXML
    private HBox availableDays;
    @FXML
    private ComboBox<LocalTime> iniDay;
    @FXML
    private ComboBox<LocalTime> fiDay;
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
    private VBox vBoxAddPac;
    @FXML
    private VBox vBoxAddCita;
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

    private CheckBox theme = new CheckBox();
    private ChoiceBox idioma = new ChoiceBox();
    private Label idioma_label = new Label("Idioma: ");
    private Label font_label = new Label("Tamaño de la letra: ");
    private ChoiceBox font = new ChoiceBox();
    private Integer[] defaultSettings;
    @FXML
    private BorderPane root;
    @FXML
    private Font x1;
    @FXML
    private AnchorPane paneAdd;
    @FXML
    private GridPane gridAdd;
    @FXML
    private Label diasActivos;
    
    private Scene scenario;
    private String css;
    @FXML
    private Label asteriscoText;
    @FXML
    private Label asterisco;
    @FXML
    private Label asterisco2;
    @FXML
    private Label horarioText;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TabPaciente.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TabMedico.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableCitaDoc.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableCitaPac.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TabAppointment.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colRoom.prefWidthProperty().bind(TabAppointment.widthProperty().divide(90));
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

                if ((patient.getName().toLowerCase()+" "+patient.getSurname().toLowerCase()).startsWith(lowerCaseFilter) ||
                        patient.getSurname().toLowerCase().startsWith(lowerCaseFilter) ||
                        patient.getIdentifier().toLowerCase().startsWith(lowerCaseFilter) ||
                        patient.getTelephon().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches.
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
                    DialogPane dialogPane = alert.getDialogPane();
                     dialogPane.getStylesheets().clear();
                     dialogPane.getStylesheets().add(css);
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

                if ((doctor.getName().toLowerCase()+" "+doctor.getSurname().toLowerCase()).startsWith(lowerCaseFilter) ||
                        doctor.getSurname().toLowerCase().startsWith(lowerCaseFilter) ||
                        doctor.getIdentifier().toLowerCase().startsWith(lowerCaseFilter) ||
                        doctor.getTelephon().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches.
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
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().clear();
                    dialogPane.getStylesheets().add(css);
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

                if ((appointment.getPatient().getName().toLowerCase()+" "+appointment.getPatient().getSurname().toLowerCase()).startsWith(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (appointment.getPatient().getSurname().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if ((appointment.getDoctor().getName()+" " +appointment.getDoctor().getSurname().toLowerCase()).toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (appointment.getDoctor().getSurname().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (appointment.getAppointmentDateTime().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
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
                toFormat(cellData.getValue().getAppointmentDateTime().getDayOfMonth())+"-"+
                toFormat(cellData.getValue().getAppointmentDateTime().getMonthValue())+"-"+
                toFormat(cellData.getValue().getAppointmentDateTime().getYear())+" "+
                toFormat(cellData.getValue().getAppointmentDateTime().getHour())+":"+
                toFormat(cellData.getValue().getAppointmentDateTime().getMinute())
        ));
        colRoom.
                setCellValueFactory(
                        cellData ->
               new SimpleIntegerProperty(cellData.getValue().getDoctor().getExaminationRoom().getIdentNumber()).asObject()
        );
        // new SimpleIntegerProperty(integer_value).asObject()
//---------------------------------------------------------------------//
        seePatientDate.setOnAction(e -> seePatient(TabAppointment.getSelectionModel().getSelectedItem().getPatient()));
        seeMedicDate.setOnAction(e -> seeMedic(TabAppointment.getSelectionModel().getSelectedItem().getDoctor()));
//---------------------------------------------------------------------//
        deleteDate.setOnAction(e -> {
            if (confirm("cita?")) {
                Appointment aEliminar = TabAppointment.getSelectionModel().getSelectedItem();
                if (aEliminar.getAppointmentDateTime().compareTo(LocalDateTime.now())>0) { // La cita aun no ha sucedido
                    listCitas.remove(aEliminar);
                    //eliminar de la tabla
                    TabAppointment.getItems().remove(aEliminar);
                    TabAppointment.getSelectionModel().setSelectionMode(null);
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle(clinic.getClinicName());
                    alert.setHeaderText("¡No se puede eliminar!");
                    alert.setContentText("La cita ya ha sucedido.");
                    DialogPane dialogPane = alert.getDialogPane();
                     dialogPane.getStylesheets().clear();
                     dialogPane.getStylesheets().add(css);
                    alert.showAndWait();
                }
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
        ID.getItems().addAll("DNI", "NIE", "SS");
        ID.setValue("DNI");
        datePicker.setValue(LocalDate.now());
        vBoxAddCita.setVisible(false);
        vBoxAddPac.setVisible(true);
        
        vBoxAddPac.setDisable(false);
        examinationRoom.setVisible(false);
        availableDays.setVisible(false);
        iniDay.setVisible(false);
        fiDay.setVisible(false);
        asterisco.setVisible(false);
        asterisco2.setVisible(false);
        asteriscoText.setVisible(false);

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
                    examinationRoom.setText(newValue.replaceAll("[^\\d]", ""));
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
        tableCitaPac.getSelectionModel().selectedItemProperty().addListener((o,old,newValue)-> {
                    if(newValue!=null) addCitaPatientSearch.setPromptText(newValue.getName() + " " + newValue.getSurname());
                    else {addCitaPatientSearch.setPromptText("Buscar Paciente");}
            });

// ----------------------------------------------------------------------//
// HORAS DISPONIBLES DE CADA MEDICO (CONTANDO LAS CITAS EN EL DIA YA MARCADO EN EL DATEPICKER)
        tableCitaDoc.getSelectionModel().selectedItemProperty().addListener((o,old,newValue)->{
            if(newValue!=null) addCitaDoctorSearch.setPromptText(newValue.getName()+" "+newValue.getSurname());
            else {addCitaDoctorSearch.setPromptText("Buscar Doctor");}
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
// ----------------------------------------------------------------------//
// LAS SETTINGS

        theme.setText("Tema oscuro");
        idioma.getItems().addAll("Castellano","Inglés");
        //idioma.setValue(idioma.getItems().get(0));
        font.getItems().addAll("Pequeña","Mediana","Grande");
        //font.setValue(font.getItems().get(1));
        defaultSettings = getDefaultSettings();

        root.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                scenario = newScene;
                setSettings(); // a modo local.
            }
        });
    }

    private String toFormat(int value) {
        return String.format("%02d", value);

    }

    // ----------------------------------------------------------------------//
// F    I   N        D  E   L       I   N   I   T   I   A   L   I   Z    E
// ----------------------------------------------------------------------//
    private void setDefaultSettings() { // Nivel global
        try {
            Properties props = new Properties(); // Herramienta para sacar los datos
            FileOutputStream out = new FileOutputStream("First.properties"); // Fichero donde vamos a guardar las cosas
            props.setProperty("tema",defaultSettings[0].toString()); // Metes la primera variable de la lista settings y así todo
           props.setProperty("idioma", defaultSettings[1].toString());
           props.setProperty("letra", defaultSettings[2].toString());
            props.setProperty("fontSize", defaultSettings[3].toString());

            props.store(out, null); // Guarda el archivo
            out.close();

        } catch (Exception e) {System.out.println("Algo salio mal intentando escribir");}
    }
    private void setSettings() { // Nivel Local
        if(defaultSettings[0] == 0) {theme.setSelected(false);
        setScene();
        }
        else {theme.setSelected(true);
        setScene();
        }
        idioma.setValue(idioma.getItems().get(defaultSettings[1]));
        font.setValue(font.getItems().get(defaultSettings[2]));
        DoubleProperty fontSize = new SimpleDoubleProperty(defaultSettings[3]);
        root.styleProperty().bind(Bindings.concat("-fx-font-size: ",fontSize.asString(), ";"));
    }

    private Integer[] getSettings() { // Actualiza  a nivel local (con los objetos de la ventana ajustes) la lista de defaultSettings
        Integer[] aux = new Integer[4];
        if(theme.isSelected()) aux[0] = 1;
        else aux[0]= 0;
        aux[1] = idioma.getSelectionModel().getSelectedIndex();
        aux[2] = font.getSelectionModel().getSelectedIndex();
        switch(font.getSelectionModel().getSelectedIndex()){
            case 0:
                aux[3] = 10;
                break;
            case 1:
                aux[3] = 14;
                break;
            case 2:
                aux[3] = 20;
                break;
        }
        setScene();
        return aux;

    }
    private Integer[] getDefaultSettings() { // Creando default settings IMPORTANTE QUE EXISTA UN FILE PRIMERO
        Properties props = new Properties();
        Integer[] aux = new Integer[4];
        try {
            FileInputStream in = new FileInputStream("First.properties");
            props.load(in);

            aux[0] = Integer.parseInt(props.getProperty("tema"));
            aux[1] = Integer.parseInt(props.getProperty("idioma"));
            aux[2] = Integer.parseInt(props.getProperty("letra"));
            aux[3] = Integer.parseInt(props.getProperty("fontSize"));
            in.close();
        } catch (Exception e) {System.out.println("Algo salio mal intentando leer");}

        return aux;
    }


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
                if(!checkIdentifier()) {errorAlert("Identifiación incorrecta!"); break;}
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
                if(!checkIdentifier()) {errorAlert("Identifiación incorrecta!"); break;}
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
                if(!checkChronology()) {errorAlert("La fecha es anterior a hoy!");break;}
                if (isErrorDate(datePicker.getValue(),cita.getDoctor())) {errorAlert("Ese día el doctor no admite visitas!");break;}
                listCitas.add(cita);
                acceptAlert("Cita");
                newInput();
                TabAppointment.setItems(listCitas);
                break;
        }
    }

    private boolean checkIdentifier() {
        boolean bool = false;
        String aux = id.getText().toUpperCase();
        switch (ID.getValue().toString()) {
            case("SS"):
                if(aux.length()==12) {
                    bool = aux.matches("\\d+");
                    break;
                }
                default:
                if(aux.length() == 9) {
                    bool = aux.substring(0,8).matches("\\d+") && aux.substring(8).matches("[A-Z]");
                    break;
                }



        }
        return bool;
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
        try {
            return !(tableCitaDoc.getSelectionModel().getSelectedItem().equals(null)
                    || tableCitaPac.getSelectionModel().getSelectedItem().equals(null));
        } catch (Exception e) {return false;}
        }

    private boolean checkChronology() {
        int days = datePicker.getValue().compareTo(LocalDate.now());
        if(days < 0) { // Si es ayer o mas, a tomar por culo
            return false;
        } else if (days ==0) { // Si es hoy Y antes de ahora, pa fuera.
            return iniCita.getValue().compareTo(LocalTime.now())>0;
        }   else return true; // si es luego, pues luego
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
        if (x <= listSalas.size() && x >= 0) return true;
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
        tableCitaDoc.getSelectionModel().select(null);
        iniCita.setItems(listHours);
        iniCita.setValue(iniCita.getItems().get(0));
        tableCitaPac.getSelectionModel().select(null);
        datePicker.setValue(LocalDate.now());
        addCitaDoctorSearch.setText("");
                addCitaPatientSearch.setText("");
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
            controller.setTextSize(defaultSettings[3]);
            controller.setScene(theme.isSelected());
            controller.setCSS(css);
            stage.setMinHeight(500);
            stage.setMinWidth(600);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
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
            controller.setTableDays(doctor.getVisitDays(),theme.isSelected());
             stage.setMinHeight(600);
            stage.setMinWidth(700);
            controller.setCSS(css);
            controller.setScene(theme.isSelected());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            controller.getTable();
            controller.setTextSize(defaultSettings[3]);
            listCitas = FXCollections.observableList(controller.getTable());
        } catch (IOException er) {
            System.out.println("adkñlsjf");
        }
    }

    @FXML
    private void settingsApplication(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().setContent(
                new VBox(8,
                        theme,
                        new HBox(font_label,font))
                );

        dialog.getDialogPane().setHeaderText("Ajustes");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL,ButtonType.OK);
        dialog.setY(300);
        dialog.setTitle(clinic.getClinicName());
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().clear();
        dialogPane.getStylesheets().add(css);
        Optional<ButtonType> result = dialog.showAndWait();
        int sizeLetra = 14;
        if(result.get() == ButtonType.OK) {
            if(idioma.getSelectionModel().getSelectedIndex() == 0) System.out.println("Hola quiero todo en castellano");
            else System.out.println("Well well well how the turntables");
            defaultSettings = getSettings();

            setDefaultSettings(); // store in properties
            setSettings(); // a modo local.
        }
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
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().clear();
            dialogPane.getStylesheets().add(css);
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
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().clear();
        dialogPane.getStylesheets().add(css);
        alert.showAndWait();
    }

    private boolean confirm(String string) { // Los delete
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(clinic.getClinicName());
        alert.setHeaderText("¿Eliminar " + string);
        DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().clear();
            dialogPane.getStylesheets().add(css);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;

    }

    private void acceptAlert(String string) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(clinic.getClinicName());
        alert.setHeaderText("¡" + string + " añadido!");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().clear();
        dialogPane.getStylesheets().add(css);
        alert.showAndWait();
    }

//--------------------------------------------------//
// LOGICA DEL INITIALIZE


    private void visibility(boolean medic, boolean date) {
        vBoxAddPac.setVisible(!date);
        vBoxAddCita.setVisible(date);
        //vBoxAddCita.setDisable(!date);
        //vBoxAddPac.setDisable(false);
        horarioText.setVisible(medic);
        asterisco.setVisible(medic);
        asterisco2.setVisible(medic);
        asteriscoText.setVisible(medic);
        diasActivos.setVisible(medic);
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

    @FXML
    private void cambiarVentanPaciente(ActionEvent event) {
        tabPane.getSelectionModel().select(0);
    }

    @FXML
    private void cambiarVentanDoctor(ActionEvent event) {
        tabPane.getSelectionModel().select(1);
    }

    @FXML
    private void cambiarVentanCitas(ActionEvent event) {
        tabPane.getSelectionModel().select(2);
    }

    @FXML
    private void ventanaAddPac(ActionEvent event) {
        tabPane.getSelectionModel().select(3);
        choice.getSelectionModel().select(0);
    }

    @FXML
    private void ventanaAddDoc(ActionEvent event) {
         tabPane.getSelectionModel().select(3);
        choice.getSelectionModel().select(1);
    }

    @FXML
    private void ventanaAddDate(ActionEvent event) {
         tabPane.getSelectionModel().select(3);
        choice.getSelectionModel().select(2);
    }
    private void setScene(){
        scenario.getStylesheets().clear();
        if(theme.isSelected()){
           css = this.getClass().getResource("/Styles/dark_theme.css").toExternalForm();
            scenario.getStylesheets().add(css);
        }else if(!theme.isSelected()){
            css = this.getClass().getResource("/Styles/light_theme.css").toExternalForm();
            scenario.getStylesheets().add(css);
        }
    }
}