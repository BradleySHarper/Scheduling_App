package controller;

import DAO.AppointmentDAO;
import DAO.AlertDAO;
import DAO.UserDAO;
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
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Controller controls the Log-in screen.
 *
 * @author Bradley Harper
 */
public class LoginController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;


    @FXML
    private Button exitBtn;

    @FXML
    private Label locationLbl;

    @FXML
    private Label loginLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Button submitBtn;

    @FXML
    private Label userNameLbl;

    @FXML
    private TextField userNameTxt;

    @FXML
    private Label zoneIdLbl;


    /**
     * Exits program.
     *
     * @param event the event
     */
    @FXML
    void onActionExit(ActionEvent event) {

        System.exit(0);

    }

    /**
     * Associated with the submit button.
     * Creates log file to keep track of un/successful log in attempts and saves to a txt file(login_activity.txt).
     * Exception handling for all exceptions.
     * Checks for valid log in credentials.
     * Checks for any appointments within 15 minutes in past/future from current local time.
     * If log in successful, Main Menu screen opens.
     *
     * @param event the event
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    @FXML
    void onActionSubmit(ActionEvent event) throws IOException, SQLException {
        String currentUser = userNameTxt.getText();

        Logger logger = Logger.getLogger("login_activity.txt");
        FileHandler log;
        log = new FileHandler("login_activity.txt", true);
        logger.addHandler(log);
        SimpleFormatter formatter = new SimpleFormatter();
        log.setFormatter(formatter);
        log.setLevel(Level.INFO);

        if (!inputCheck()) {
            missingInput();
            return;
        }

        boolean isVerified = UserDAO.verifyLogin(userNameTxt.getText(), passwordTxt.getText());

        if (isVerified) {

            logger.log(Level.INFO, "Username: " + currentUser + " had a successful log in attempt on " + Timestamp.valueOf(LocalDateTime.now()));
            int currentUserInt = 0;

            ObservableList<User> users = UserDAO.getUserList();
            for (User user : users) {
                if (user.getUserName().equals(currentUser)) {
                    currentUserInt = user.getUserId();
                }
            }

            ObservableList<Appointment> appointments = AppointmentDAO.getAppointmentList();
            int i = 0;
            for (Appointment appointment : appointments) {
                i++;
                if (appointment.getUserId() == currentUserInt) {
                    LocalDateTime startTime = LocalDateTime.from(appointment.getStart());
                    LocalTime lt = LocalTime.from(startTime);
                    LocalDate ld = LocalDate.from(startTime);
                    LocalDateTime currentTime = LocalDateTime.now();
                    long elapsedTime = ChronoUnit.MINUTES.between(currentTime, startTime);
                    System.out.println(i);
                    System.out.println(appointments.size());
                    if (elapsedTime > 0 && elapsedTime <= 15) {
                        AlertDAO.notification("Appointment Reminder", "User: " + appointment.getUserId() + " has an appointment in approx " + elapsedTime + " minutes!", "Appointment " + appointment.getAppointmentId() + " starts on " + ld + " at " + lt);
                        break;
                    } else if (elapsedTime >= -15 && elapsedTime <= 0) {
                        AlertDAO.notification("Appointment Reminder", "User: " + appointment.getUserId() + " has an appointment that started approx " + elapsedTime * -1 + " minutes ago!", "Appointment " + appointment.getAppointmentId() + " started on " + ld + " at " + lt);
                        break;
                    }
                }
               if (i == appointments.size()) {
                    AlertDAO.notification("Appointment Notification", "No upcoming appointments", "User: " + currentUserInt + " has no upcoming appointments within 15 minutes.");
                    break;
                }
            }
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } else {

            logger.log(Level.INFO, "Username: " + currentUser + " had a failed log-in attempt on " + Timestamp.valueOf(LocalDateTime.now()));
            invalidInput();
        }
    }


    /**
     * Associated with the save button.
     * Boolean method to check for empty userName/passWord fields
     *
     * @return the boolean
     */
    public Boolean inputCheck() {
        if (userNameTxt.getText().isEmpty()) {
            return false;
        } else return !passwordTxt.getText().isEmpty();
    }

    /**
     * Method to show an Error message if the user inputs invalid information.
     * Language is translated based on User's system language setting
     */
    public static void invalidInput() {
        ResourceBundle rb = ResourceBundle.getBundle("language/Reg", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("Input"));
            alert.setHeaderText(rb.getString("Invalid"));
            alert.setContentText(rb.getString("Valid"));
            alert.showAndWait();
        }

    }

    /**
     * Method to show an Error message if the user leaves a blank userName/password field.
     * Language is translated based on User's system language setting
     */
    public static void missingInput() {
        ResourceBundle rb = ResourceBundle.getBundle("language/Reg", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("Input"));
            alert.setHeaderText(rb.getString("Missing"));
            alert.setContentText(rb.getString("Blank"));
            alert.showAndWait();
        }

    }

    /**
     * Method utilizing resource bundle to translate language based on the user's system language setting.
     */
    public void language() {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("language/Reg", Locale.getDefault());

            if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                loginLbl.setText(rb.getString("Login"));
                userNameLbl.setText(rb.getString("Username"));
                passwordLbl.setText(rb.getString("Password"));
                locationLbl.setText(rb.getString("Location"));
                submitBtn.setText(rb.getString("Submit"));
                exitBtn.setText(rb.getString("Exit"));
            }
        } catch (Exception e) {
            System.out.println("missing resource");
        }
    }

    /**
     * Initializes Controller.
     * Initializes language method.
     * Lambda used for setting ZoneId and corresponding text label.  This reduced the amount of code necessary for this task.
     * The Lambda uses the interface LocalZoneId.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        language();

        LocalZoneID lZid = () -> String.valueOf(ZoneId.systemDefault());
        zoneIdLbl.setText(lZid.getLocalZoneID());
    }

    /**
     * Inner class Interface for ZoneId lambda
     */
    public interface LocalZoneID {

        /**
         * Gets local zone id.
         *
         * @return the local zone id
         */
        String getLocalZoneID();
    }
}
