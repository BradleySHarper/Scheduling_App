package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller controls the Main Menu screen.
 *
 * @author Bradley Harper
 */
public class MainMenuController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    /**
     * Associated with the Appointments button.
     * Opens to the appointments screen.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionAppointments(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/Appointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Associated with the Customer button.
     * Opens to the customers screen.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionCustomers(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/Customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Associated with the Reports button.
     * Opens to the reports screen.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionReports(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/Report.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Associated with the Return button.
     * Opens to the Login screen.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionReturn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Initializes Controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
