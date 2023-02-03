package controller;

import DAO.AppointmentDAO;
import DAO.AlertDAO;
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
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller controls the Appointment screen.
 *
 * @author Bradley Harper
 */
public class AppointmentController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @FXML
    private Tab currentMonthTab;

    @FXML
    private Tab currentWeekTab;

    @FXML
    private Tab viewAllTab;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;

    @FXML
    private TableView<Appointment> appointmentsTblView;

    @FXML
    private TableColumn<Appointment, String> contactCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endTimeCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startTimeCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, Integer> userIDCol;

    @FXML
    private TableColumn<Appointment, Integer> monthApptIdCol;

    @FXML
    private TableColumn<Appointment, String> monthContactCol;

    @FXML
    private TableColumn<Appointment, Integer> monthCustIdCol;

    @FXML
    private TableColumn<Appointment, String> monthDescCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> monthEndCol;

    @FXML
    private TableColumn<Appointment, String> monthLocCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> monthStartCol;

    @FXML
    private TableColumn<Appointment, String> monthTitleCol;

    @FXML
    private TableColumn<Appointment, String> monthTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> monthUserIdCol;


    @FXML
    private TableColumn<Appointment, Integer> weekApptIdCol;

    @FXML
    private TableColumn<Appointment, String> weekContactCol;

    @FXML
    private TableColumn<Appointment, Integer> weekCustIdCol;

    @FXML
    private TableColumn<Appointment, String> weekDescCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> weekEndCol;

    @FXML
    private TableColumn<Appointment, String> weekLocCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> weekStartCol;

    @FXML
    private TableColumn<Appointment, String> weekTitleCol;

    @FXML
    private TableColumn<Appointment, String> weekTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> weekUserIdCol;

    @FXML
    private TableView<Appointment> weekTblView;

    @FXML
    private TableView<Appointment> monthTblView;


    /**
     * Associated with the return to main button.
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
     * Associated with the add button.
     * Opens to the Add Appointment Screen.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionAdd(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Associated with the Delete Appointment button.
     * Retrieves selected item from each tab, then deletes selected appointment based on tab selection.
     * Notifies user of what was deleted
     * Includes error check if no appointment selected.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionDelete(ActionEvent event) throws IOException {

        Appointment appointment = appointmentsTblView.getSelectionModel().getSelectedItem();
        Appointment monthAppointment = monthTblView.getSelectionModel().getSelectedItem();
        Appointment weekAppointment = weekTblView.getSelectionModel().getSelectedItem();

        if (viewAllTab.isSelected()) {

            if (!appointmentsTblView.getSelectionModel().isEmpty()) {

                if (AlertDAO.confirm("This will remove appointment # " + appointment.getAppointmentId() + " of Type: " + appointment.getType() + " , Please Confirm.")) {
                    AppointmentDAO.deleteAppointment(appointmentsTblView.getSelectionModel().getSelectedItem());
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("../view/Appointment.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                    AlertDAO.notification("Deletion Complete", "Appointment Deleted", "Appointment # " + appointment.getAppointmentId() + " of Type: " + appointment.getType() + " has been deleted.");
                }
            } else {
                AlertDAO.notification("INPUT ERROR", "No appointment selected!", "Please select an appointment to be deleted");
            }
        }

        if (currentMonthTab.isSelected()) {

            if (!monthTblView.getSelectionModel().isEmpty()) {

                if (AlertDAO.confirm("This will remove appointment # " + monthAppointment.getAppointmentId() + " of Type: " + monthAppointment.getType() + " , Please Confirm.")) {
                    AppointmentDAO.deleteAppointment(monthTblView.getSelectionModel().getSelectedItem());
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("../view/Appointment.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                    AlertDAO.notification("Deletion Complete", "Appointment Deleted", "Appointment # " + monthAppointment.getAppointmentId() + " of Type: " + monthAppointment.getType() + " has been deleted.");
                }
            } else {
                AlertDAO.notification("INPUT ERROR", "No appointment selected!", "Please select an appointment to be deleted");
            }
        }

        if (currentWeekTab.isSelected()) {

            if (!weekTblView.getSelectionModel().isEmpty()) {

                if (AlertDAO.confirm("This will remove appointment # " + weekAppointment.getAppointmentId() + " of Type: " + weekAppointment.getType() + " , Please Confirm.")) {
                    AppointmentDAO.deleteAppointment(weekTblView.getSelectionModel().getSelectedItem());
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("../view/Appointment.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                    AlertDAO.notification("Deletion Complete", "Appointment Deleted", "Appointment # " + weekAppointment.getAppointmentId() + " of Type: " + weekAppointment.getType() + " has been deleted.");
                }
            } else {
                AlertDAO.notification("INPUT ERROR", "No appointment selected!", "Please select an appointment to be deleted");
            }
        }
    }

    /**
     * Associated with the update appointment button.
     * Includes error check if no customer selected.
     * Opens to the Update Appointment Screen.
     * Calls method setUpdateAppointment method to bring Appointment info to the Update Appointment Screen
     *
     * @param event the event
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    @FXML
    void onActionUpdate(ActionEvent event) throws IOException, SQLException {

        Appointment selAppointment = appointmentsTblView.getSelectionModel().getSelectedItem();

        if (selAppointment == null) {

            AlertDAO.notification("Input Error", "No Customer Selected!", "Please select a customer to update.");

        } else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
            loader.load();

            UpdateAppointmentController sendAppointment = loader.getController();
            sendAppointment.setUpdateAppointment(selAppointment);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }

    /**
     * Initializes Controller.
     * Creates Observable Lists to hold Appointment data.
     * Sets table columns on all 3 tabs and fills with data
     * A Lambda is used here using a forEach loop, reducing the amount of code needed.
     * The loops sort appointments by current month and week, then adds to lists
     * Sorts Table view by Appointment ID
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Appointment> appointmentTblData = AppointmentDAO.getAppointmentList();

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

        ObservableList<Appointment> monthAppointmentTblData = FXCollections.observableArrayList();

        monthApptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        monthTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        monthDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        monthLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        monthTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        monthCustIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        monthStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        monthEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        monthUserIdCol.setCellValueFactory(new PropertyValueFactory<>("UserId"));

        LocalDateTime currentMonthStart = LocalDateTime.now().withDayOfMonth(1);
        LocalDateTime currentMonthEnd = LocalDateTime.now().withDayOfMonth(30);

        ObservableList<Appointment> weekAppointmentTblData = FXCollections.observableArrayList();

        weekApptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        weekTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        weekDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        weekLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        weekTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        weekContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        weekCustIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        weekStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        weekEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        weekUserIdCol.setCellValueFactory(new PropertyValueFactory<>("UserId"));

        LocalDateTime startWeek = LocalDateTime.now().with(DayOfWeek.MONDAY);
        LocalDateTime endWeek = LocalDateTime.now().with(DayOfWeek.SUNDAY);

        appointmentTblData.forEach((appointment -> {
            System.out.println(appointment.getEnd());
            if (appointment.getEnd().isAfter(currentMonthStart) && appointment.getEnd().isBefore(currentMonthEnd)) {
                monthAppointmentTblData.add(appointment);
            }
            if (appointment.getEnd().isAfter(startWeek) && appointment.getEnd().isBefore(endWeek)) {
                weekAppointmentTblData.add(appointment);
            }
        }));

        monthTblView.setItems(monthAppointmentTblData);
        weekTblView.setItems(weekAppointmentTblData);

        appointmentsTblView.getSortOrder().add(appointmentIDCol);
        appointmentsTblView.sort();

        monthTblView.getSortOrder().add(appointmentIDCol);
        monthTblView.sort();

        weekTblView.getSortOrder().add(appointmentIDCol);
        weekTblView.sort();
    }
}
