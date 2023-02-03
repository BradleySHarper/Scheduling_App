package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller controls the Update Appointment screen.
 *
 * @author Bradley Harper
 */
public class ReportController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    /**
     * The Dependent contact list.
     */
    ObservableList<Appointment> dependentContactList = FXCollections.observableArrayList();
    /**
     * The Types filtered.
     */
    ObservableList<String> typesFiltered = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;

    @FXML
    private TableView<Appointment> appointmentsTblView;

    @FXML
    private TableColumn<Appointment, String> contactCol;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private TextField countryCountTxt;

    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;

    @FXML
    private TableColumn<Appointment, String > descriptionCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endTimeCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private TextField monthTypeCountTxt;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startTimeCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TableColumn<Appointment, Integer> userIDCol;


    /**
     * Associated with the return home button.
     * Opens to the Main Menu Screen.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Associated with the Contact combo box.
     * Calls the dependentContact method.
     *
     * @param event the event
     */
    @FXML
    void onActionContactCombo(ActionEvent event) {

        dependentContact();
    }

    /**
     * Associated with the Country combo box.
     * Calls the countryCount method.
     *
     * @param event the event
     */
    @FXML
    void onActionCountry(ActionEvent event) {

        countryCount();
    }

    /**
     * Associated with the Month combo box.
     * Calls the monthTypeCount method.
     *
     * @param event the event
     * @throws SQLException the sql exception
     */
    @FXML
    void onActionMonth(ActionEvent event) throws SQLException {

        monthTypeCount();
    }

    /**
     * Associated with the Type combo box.
     * Calls the monthTypeCount method.
     *
     * @param event the event
     * @throws SQLException the sql exception
     */
    @FXML
    void onActionType(ActionEvent event) throws SQLException {

        monthTypeCount();
    }

    /**
     * Method for setting Appointments table view.
     * First clears list, from previous contact selected
     * Then loops through Appointment list and matches ContactID and adds to list
     */
    public void dependentContact(){

        int depContact = contactComboBox.getSelectionModel().getSelectedItem().getContactId();
        dependentContactList.clear();

        for(Appointment app : AppointmentDAO.getAppointmentList()){
            if(depContact == app.getContactId()){
                dependentContactList.add(app);
            }
        }appointmentsTblView.setItems(dependentContactList);
    }

    /**
     * Method for setting Country count text field.
     * Loops through Customer list to find matching country IDs, then adds to count.
     */
    public void countryCount () {

        int cCount = countryComboBox.getSelectionModel().getSelectedItem().getCountryID();
        int i = 0;
        for(Customer c : CustomerDAO.getCustomerList()){
            if(cCount == c.getCountryId()){
                i++;
            }
        }countryCountTxt.setText(i + " customer(s)");
    }

    /**
     * Method for setting the Month/Type count text field.
     * Calls the apptCountByMonthAndType method to obtain a count of those appointments.
     *
     * @throws SQLException the sql exception
     */
    public void monthTypeCount() throws SQLException {

        String selType = typeComboBox.getSelectionModel().getSelectedItem();
        String selMonth = monthComboBox.getSelectionModel().getSelectedItem();
        String apptCount = Integer.toString(Month.apptCountByMonthAndType(selMonth, selType));
        monthTypeCountTxt.setText(apptCount + " appointment(s)");
    }


    /**
     * Initializes Controller.
     * Sets appointment table view
     * Sets combo boxes
     * A Lambda forEach loop is used here to ensure the appointment types are not repeated in the combo box.
     * This lambda cuts down on the necessary code of creating a method.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Appointment> appointmentTblData = AppointmentDAO.getAppointmentList();
        ObservableList<Country> countries = CountryDAO.getCountryList();

        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("UserId"));

        appointmentsTblView.setItems(appointmentTblData);

        contactComboBox.setItems(ContactDAO.getContactList());
        contactComboBox.setVisibleRowCount(3);
        contactComboBox.getSelectionModel().selectFirst();

        dependentContact();

        countryComboBox.setItems(countries);
        countryComboBox.getSelectionModel().selectFirst();

        countryCount();

        monthComboBox.setItems(Month.getMonthList());
        monthComboBox.getSelectionModel().selectFirst();

        //Lambda using forEach loop
        AppointmentDAO.getTypeList().forEach((appointment -> {
            if(!typesFiltered.contains(appointment)){
                typesFiltered.add(appointment);}}));

        typeComboBox.setItems(typesFiltered);
        typeComboBox.getSelectionModel().selectFirst();

        try {
            monthTypeCount();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
