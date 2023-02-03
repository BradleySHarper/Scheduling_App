package BHC195Main;

import database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The purpose of this application is to provide a user-friendly scheduling application for a multi-national corporation.
 * The application pulls company data from a shared relational database.
 * The application provides the user with the ability to manage customers and appointments, as well as view specific report information.
 *
 * @author Bradley Harper
 */
public class Main extends Application {

    /**
     * Opens login window.
     * Sets title for all windows.
     * sets window dimensions.
     *
     * @throws Exception e
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        primaryStage.setTitle("Scheduler Pro");
        primaryStage.setScene(new Scene(root, 875, 625));
        primaryStage.show();
    }

    /**
     * This method launches the JavaFX runtime and JavaFX application.
     * Opens database connection when launching application
     * Closes database connection when terminating application
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
