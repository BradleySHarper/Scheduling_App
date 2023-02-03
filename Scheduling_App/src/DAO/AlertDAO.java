package DAO;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * AlertDAO contains methods for Alerts
 *
 * @author Bradley Harper
 */
public class AlertDAO {

    /**
     * boolean CONFIRMATION method, used when a response (confirmation) is necessary
     *
     * @param content the content used in Confirmation
     * @return result.get() == ButtonType.OK.
     */
    public static boolean confirm(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * Notification method used to provide the user information
     *
     * @param title   the title to inform
     * @param header  the header to inform
     * @param content the content to inform
     */
    public static void notification(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
