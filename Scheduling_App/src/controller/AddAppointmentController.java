package controller;

import DAO.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.*;

/**
 * Controller controls the Appointment screen.
 *
 * @author Bradley Harper
 */
public class AddAppointmentController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;


    @FXML
    private TextField TypeTxt;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private TextField descTxt;

    @FXML
    private ComboBox<LocalTime> endTimeComboBox;

    @FXML
    private TextField locTxt;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<LocalTime> startTimeComboBox;

    @FXML
    private TextField titleTxt;

    @FXML
    private ComboBox<Customer> custIDComboBox;

    @FXML
    private ComboBox<User> userIDComboBox;

    /**
     * Associated with the Start Date picker.
     * Calls the endDatePickerAdjust method for adjusting End Date picker.
     *
     * @param event the event
     */
    @FXML
    void onActionStartDate(ActionEvent event) {

        endDatePickerAdjust();
    }

    /**
     * Associated with the Start time combo box.
     * Calls the endDatePickerAdjust method for adjusting End Date Picker.
     *
     * @param event the event
     */
    @FXML
    void onActionStartTime(ActionEvent event) {

        endDatePickerAdjust();
    }

    /**
     * Associated with the End time combo box.
     * Calls the endDatePickerAdjust method for adjusting End Date Picker.
     *
     * @param event the event
     */
    @FXML
    void onActionEndTime(ActionEvent event) {

        endDatePickerAdjust();
    }

    /**
     * Associated with the cancel button.
     * Opens to the Appointment Screen.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/Appointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Associated with the clear button.
     * Confirmation which asks the user to confirm clear.
     * Clears all text fields, and resets combo boxes and date pickers.
     *
     * @param event the event
     */
    @FXML
    void onActionClear(ActionEvent event) {

        if (AlertDAO.confirm("Are you sure you want to clear all fields?")) {
            titleTxt.clear();
            descTxt.clear();
            locTxt.clear();
            TypeTxt.clear();

            contactComboBox.getSelectionModel().clearSelection();
            contactComboBox.getSelectionModel().selectFirst();
            custIDComboBox.getSelectionModel().clearSelection();
            custIDComboBox.getSelectionModel().selectFirst();
            userIDComboBox.getSelectionModel().clearSelection();
            userIDComboBox.getSelectionModel().selectFirst();
            startTimeComboBox.getSelectionModel().selectFirst();
            endTimeComboBox.getSelectionModel().selectFirst();
            startDatePicker.setValue(LocalDate.now());
            endDatePicker.setValue(LocalDate.now());

        }
    }

    /**
     * Associated with the return home button.
     * Opens to the Main Menu Screen.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionReturnHome(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Associated with the submit button.
     * Creates objects for adding to Appointment object
     * Includes error check for blank fields.
     * Confirmation asking to confirm save.
     * Checks if appointment is within business hrs in EST 0800-2200.
     * Checks if there is an appointment time conflict with another existing appointment.
     * Adds appointment to database
     * Opens to Appointment Screen.
     *
     * @param event the eventw
     */
    @FXML
    void onActionSubmit(ActionEvent event) {

        try {
            String title = titleTxt.getText();
            String location = locTxt.getText();
            String description = descTxt.getText();
            String type = TypeTxt.getText();
            int contactID = Integer.parseInt(String.valueOf(contactComboBox.getValue().getContactId()));
            int customerId = Integer.parseInt(String.valueOf(custIDComboBox.getValue().getCustomerId()));
            int userId = Integer.parseInt(String.valueOf(userIDComboBox.getValue().getUserId()));

            if (title.isEmpty() || location.isEmpty() || description.isEmpty() || type.isEmpty()) {

                AlertDAO.notification("Input Error", "Empty Fields", "Please complete all fields to add an appointment");
                return;
            }

            LocalDate startLocalDate = startDatePicker.getValue();
            LocalDate endLocalDate = endDatePicker.getValue();
            LocalTime startLT = startTimeComboBox.getValue();
            LocalTime endLT = endTimeComboBox.getValue();

            LocalDateTime apptStart = LocalDateTime.of(startLocalDate, startLT);
            LocalDateTime apptEnd = LocalDateTime.of(endLocalDate, endLT);

            if (apptStart.isBefore(LocalDateTime.now())) {
                AlertDAO.notification("Input Error", "Invalid Appointment Times", "Appointment time cannot be in the past");
                return;
            }

            ZonedDateTime startSystemDefZonedDT = ZonedDateTime.of(apptStart, ZoneId.systemDefault());
            ZonedDateTime startESTZonedDT = startSystemDefZonedDT.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalTime startESTZonedDTLocalTime = startESTZonedDT.toLocalTime();

            ZonedDateTime endSystemDefZonedDT = ZonedDateTime.of(apptEnd, ZoneId.systemDefault());
            ZonedDateTime endESTZonedDT = endSystemDefZonedDT.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalTime endESTZonedDTLocalTime = endESTZonedDT.toLocalTime();


            LocalTime startOfBusinessHours = LocalTime.of(8, 0, 0);
            LocalTime endOfBusinessHours = LocalTime.of(22, 0, 0);


            if (startESTZonedDTLocalTime.isBefore(startOfBusinessHours) ||
                    startESTZonedDTLocalTime.isAfter(endOfBusinessHours) ||
                    endESTZonedDTLocalTime.isBefore(startOfBusinessHours) ||
                    endESTZonedDTLocalTime.isAfter(endOfBusinessHours)) {
                AlertDAO.notification("Input Error", "Outside Normal Business Hours", "Please select times within normal operating hours: 8:00am to 10:00pm EST");
                return;
            }

            ObservableList<Appointment> appointments = AppointmentDAO.getAppointmentList();
            for (Appointment appointment : appointments) {
                LocalDateTime stT = appointment.getStart();
                LocalDateTime edT = appointment.getEnd();
                int iDCheck = appointment.getCustomerId();
                if (customerId == iDCheck) {
                    if ((apptStart.isAfter(stT) ||
                            apptStart.isEqual(stT)) &&
                            (apptStart.isBefore(edT))) {
                        AlertDAO.notification("Input Error", "Appointment Time Conflict", "Appointment times entered conflict with one or more existing appointments");
                        return;
                    }
                    if ((apptEnd.isAfter(stT) &&
                            apptEnd.isBefore(edT)) ||
                            (apptEnd.isEqual(edT))) {
                        AlertDAO.notification("Input Error", "Appointment Time Conflict", "Appointment times entered conflict with one or more existing appointments");
                        return;
                    }
                    if ((apptStart.isBefore(stT) ||
                            apptStart.isEqual(stT)) &&
                            (apptEnd.isAfter(edT) || (apptEnd.isEqual(edT)))) {
                        AlertDAO.notification("Input Error", "Appointment Time Conflict", "Appointment times entered conflict with one or more existing appointments");
                        return;
                    }
                }
            }

            AppointmentDAO.addAppointment(title, description, location, type, contactID, customerId, userId, apptStart, apptEnd);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/Appointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (Exception e) {

            AlertDAO.notification("Input Error", "Empty Field/s", "Please complete all fields");
        }
    }


    /**
     * Method for auto adjusting the End Date Picker
     * When method called, the End Date is set based on the Start Date, and start and end times.
     * If the end time is after start time, end date is set to start date.
     * If the end time is before start time, end date is set to start date + 1 day.
     * This prevents an error when scheduling past midnight in users local time.
     * End date is also disabled in the fxml file.
     */
    public void endDatePickerAdjust() {
        LocalDate startDatePickerValue = startDatePicker.getValue();
        LocalTime startTime = startTimeComboBox.getSelectionModel().getSelectedItem();
        LocalTime endTime = endTimeComboBox.getSelectionModel().getSelectedItem();

        if (endTime.isAfter(startTime)) {
            endDatePicker.setValue(startDatePickerValue);
        }
        if (startTime.isAfter(endTime)) {
            endDatePicker.setValue(startDatePickerValue.plusDays(1));
        }
    }


    /**
     * Initializes Controller.
     * Sets combo boxes
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contactComboBox.setItems(ContactDAO.getContactList());
        contactComboBox.setVisibleRowCount(3);
        contactComboBox.getSelectionModel().selectFirst();

        userIDComboBox.setItems(UserDAO.getUserList());
        userIDComboBox.setVisibleRowCount(3);
        userIDComboBox.getSelectionModel().selectFirst();

        custIDComboBox.setItems(CustomerDAO.getCustomerList());
        custIDComboBox.setVisibleRowCount(3);
        custIDComboBox.getSelectionModel().selectFirst();

        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());

        LocalTime apptStartTime = LocalTime.of(0, 00);
        LocalTime apptEndTime = LocalTime.of(23, 30);

        while (apptStartTime.isBefore(apptEndTime.plusSeconds(1))) {

            startTimeComboBox.getItems().add(LocalTime.parse(String.valueOf(apptStartTime)));
            apptStartTime = apptStartTime.plusMinutes(15);
            endTimeComboBox.getItems().add(LocalTime.parse(String.valueOf(apptStartTime)));
        }
        startTimeComboBox.getSelectionModel().selectFirst();
        endTimeComboBox.getSelectionModel().selectFirst();
    }
}

