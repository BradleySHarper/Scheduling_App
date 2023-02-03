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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller controls the Add Customer screen.
 *
 * @author Bradley Harper
 */
public class AddCustomerController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    /**
     * The Dependent division list.
     */
    ObservableList<Division> dependentDivisionList = FXCollections.observableArrayList();

    @FXML
    private TextField addressTxt;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private ComboBox<Division> divisionComboBox;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneNumberTxt;

    @FXML
    private TextField postalCodeTxt;


    /**
     * Associated with the cancel button.
     * Opens to the Customer Screen.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionBackToCustomer(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/Customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Associated with the clear button.
     * Confirmation which asks the user to confirm clear.
     * Clears all text fields, and sets country combo box to first option.
     *
     * @param event the event
     */
    @FXML
    void onActionClear(ActionEvent event) {

        if (AlertDAO.confirm("Are you sure you want to clear all fields?")) {
            nameTxt.clear();
            addressTxt.clear();
            phoneNumberTxt.clear();
            postalCodeTxt.clear();
            countryComboBox.getSelectionModel().selectFirst();

        } else {
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
     * Creates String objects for adding to Customer object
     * Includes error check for blank fields.
     * Confirmation asking to confirm save.
     * Adds Customer to database.
     * Opens to Customer Screen.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionSubmit(ActionEvent event) throws IOException {

        try {
            String name = nameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = postalCodeTxt.getText();
            String phoneNumber = phoneNumberTxt.getText();
            String division = String.valueOf(divisionComboBox.getSelectionModel().getSelectedItem().getDivisionId());

            if (name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phoneNumber.isEmpty()) {
                AlertDAO.notification("Input Error", "Empty Field/s", "Please complete all fields");

            } else {
                if (!AlertDAO.confirm("Are you sure you want to save information entered?")) {
                    return;
                }
                CustomerDAO.addCustomer(name, address, postalCode, phoneNumber, division);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../view/Customer.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        } catch (Exception e) {
            AlertDAO.notification("Input Error", "Empty Field/s", "Please complete all fields");
        }
    }

    /**
     * Associated with the division combo box.
     *
     * @param event the event
     */
    @FXML
    void onActionDivisionCombo(ActionEvent event) {
    }

    /**
     * Associated with the Country combo box.
     * Calls dependentDivisions method.
     *
     * @param event the event
     */
    @FXML
    void onActionCountryCombo(ActionEvent event) {

        dependentDivisions();
    }


    /**
     * Method for setting division combo box.
     * Loops through Division list to match Country ID.
     */
    public void dependentDivisions() {

        int countryId = countryComboBox.getSelectionModel().getSelectedItem().getCountryID();
        dependentDivisionList.clear();
        for (Division division : DivisionDAO.getDivisionList()) {
            if (countryId == division.getCountryId()) {
                dependentDivisionList.add(division);
            }
        }
        divisionComboBox.setItems(dependentDivisionList);
        divisionComboBox.getSelectionModel().selectFirst();
    }


    /**
     * Initializes Controller.
     * Sets country and division combo boxes
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryComboBox.setItems(CountryDAO.getCountryList());
        countryComboBox.setVisibleRowCount(3);
        countryComboBox.getSelectionModel().selectFirst();
        divisionComboBox.setItems(DivisionDAO.getDivisionList());

        ObservableList<Division> areas = DivisionDAO.getDivisionList();
        divisionComboBox.setItems(areas);

        dependentDivisions();
    }
}
