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
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

/**
 * Controller controls the Update Appointment screen.
 *
 * @author Bradley Harper
 */
public class UpdateAppointmentController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    /**
     * The Check contact list.
     */
    ObservableList<Contact> checkContactList = FXCollections.observableArrayList();
    /**
     * The Check customer list.
     */
    ObservableList<Customer> checkCustomerList = FXCollections.observableArrayList();
    /**
     * The Check user list.
     */
    ObservableList<User> checkUserList = FXCollections.observableArrayList();

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField apptIdTxt;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private ComboBox<Customer> custIdComboBox;

    @FXML
    private TextField descTxt;

    @FXML
    private ComboBox<LocalTime> endTimeComboBox;

    @FXML
    private TextField locTxt;

    @FXML
    private ComboBox<LocalTime> startTimeComboBox;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private ComboBox<User> userIdComboBox;


    /**
     * Associated with the End Date picker.
     * Calls the endDatePickerAdjust method for adjusting End Date picker.
     *
     * @param event the event
     */
    @FXML
    void onActionEndDate(ActionEvent event) {

        endDatePickerAdjust();
    }

    /**
     * Associated with the End Time Combo box.
     * Calls the endDatePickerAdjust method for adjusting End Date picker.
     *
     * @param event the event
     */
    @FXML
    void onActionEndTime(ActionEvent event) {

        endDatePickerAdjust();
    }

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
     * Associated with the Start Time Combo box.
     * Calls the endDatePickerAdjust method for adjusting End Date picker.
     *
     * @param event the event
     */
    @FXML
    void onActionStartTime(ActionEvent event) {

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
            typeTxt.clear();

            contactComboBox.getSelectionModel().clearSelection();
            contactComboBox.getSelectionModel().selectFirst();
            custIdComboBox.getSelectionModel().clearSelection();
            custIdComboBox.getSelectionModel().selectFirst();
            userIdComboBox.getSelectionModel().clearSelection();
            userIdComboBox.getSelectionModel().selectFirst();
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
     * @param event the event
     */
    @FXML
    void onActionSubmit(ActionEvent event) {

        try {
            int apptId = Integer.parseInt(apptIdTxt.getText());
            String title = titleTxt.getText();
            String location = locTxt.getText();
            String description = descTxt.getText();
            String type = typeTxt.getText();
            int contactID = Integer.parseInt(String.valueOf(contactComboBox.getValue().getContactId()));
            int customerId = Integer.parseInt(String.valueOf(custIdComboBox.getValue().getCustomerId()));
            int userId = Integer.parseInt(String.valueOf(userIdComboBox.getValue().getUserId()));

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
                int apptIDCheck = appointment.getAppointmentId();
                if (apptIDCheck == apptId) {
                    continue;
                }
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

            AppointmentDAO.updateAppointment(apptId, title, description, location, type, contactID, customerId, userId, apptStart, apptEnd);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/Appointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (Exception e) {

            AlertDAO.notification("Input Error", "Empty Field/s", "Please complete all fields");
        }
    }


    /**
     * Method used by Appointment controller to set selected customer object.
     * Retrieves all info of selected customer.
     * Sets all combo boxes of selected appointment
     * Utilizes modifyCustomer method to update customer.
     * Opens to Customer Screen.
     *
     * @param selAppointment the appointment to update
     */
    public void setUpdateAppointment(Appointment selAppointment) {

        try {

            checkContactList = ContactDAO.getContactList();
            checkCustomerList = CustomerDAO.getCustomerList();
            checkUserList = UserDAO.getUserList();
            apptIdTxt.setText(Integer.toString(selAppointment.getAppointmentId()));
            titleTxt.setText(selAppointment.getTitle());
            descTxt.setText(selAppointment.getDescription());
            locTxt.setText(selAppointment.getLocation());
            typeTxt.setText(selAppointment.getType());
            startDatePicker.setValue(selAppointment.getStart().toLocalDate());
            endDatePicker.setValue(selAppointment.getEnd().toLocalDate());

            LocalTime apptStartTime = LocalTime.of(0, 00);
            LocalTime apptEndTime = LocalTime.of(23, 30);

            while (apptStartTime.isBefore(apptEndTime.plusSeconds(1))) {

                startTimeComboBox.getItems().add(LocalTime.parse(String.valueOf(apptStartTime)));
                apptStartTime = apptStartTime.plusMinutes(15);
                endTimeComboBox.getItems().add(LocalTime.parse(String.valueOf(apptStartTime)));
            }

            LocalTime finalStart = LocalTime.from(selAppointment.getStart());
            LocalTime finalEnd = LocalTime.from(selAppointment.getEnd());
            startTimeComboBox.setValue(LocalTime.parse(String.valueOf(finalStart)));
            endTimeComboBox.setValue(LocalTime.parse(String.valueOf(finalEnd)));

            Contact contact = null;
            for (Contact checkContact : checkContactList) {
                if (!(checkContact.getContactId() == selAppointment.getContactId())) {
                } else {
                    contact = checkContact;
                    break;
                }
            }
            Customer customer = null;
            for (Customer checkCustomer : checkCustomerList) {
                if (!(checkCustomer.getCustomerId() == selAppointment.getCustomerId())) {
                } else {
                    customer = checkCustomer;
                    break;
                }
            }
            User user = null;
            for (User checkUser : checkUserList) {
                if (!(checkUser.getUserId() == selAppointment.getUserId())) {
                } else {
                    user = checkUser;
                    break;
                }
            }

            contactComboBox.setItems(ContactDAO.getContactList());
            contactComboBox.setVisibleRowCount(3);
            contactComboBox.setValue(contact);
            custIdComboBox.setItems(CustomerDAO.getCustomerList());
            custIdComboBox.setVisibleRowCount(3);
            custIdComboBox.setValue(customer);
            userIdComboBox.setItems(UserDAO.getUserList());
            userIdComboBox.setVisibleRowCount(3);
            userIdComboBox.setValue(user);

        } catch (Exception ignored) {
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

    }


}
