package controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.DivisionDAO;
import DAO.AlertDAO;
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
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;


/**
 * Controller controls the Update Customer screen.
 *
 * @author Bradley Harper
 */
public class UpdateCustomerController implements Initializable {

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
    /**
     * The Parent country list.
     */
    ObservableList<Country> parentCountryList = FXCollections.observableArrayList();

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneNumberTxt;

    @FXML
    private TextField postalCodeTxt;

    @FXML
    private ComboBox<Country> CountryComboBox;

    @FXML
    private ComboBox<Division> DivisionComboBox;


    /**
     * Associated with the cancel button.
     * Opens to the Customer Screen.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {

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
            CountryComboBox.getSelectionModel().selectFirst();
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
     * Creates objects for updating Customer object
     * Includes error check for blank fields.
     * Confirmation asking to confirm save.
     * Utilizes modifyCustomer method to update customer.
     * Opens to Customer Screen.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionSubmit(ActionEvent event) throws IOException {

        try {
            int id = parseInt(customerIdTxt.getText());
            String name = nameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = postalCodeTxt.getText();
            String phoneNumber = phoneNumberTxt.getText();
            String division = String.valueOf(DivisionComboBox.getSelectionModel().getSelectedItem().getDivisionId());

            if (name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phoneNumber.isEmpty()) {
                AlertDAO.notification("Input Error", "Empty Field/s", "Please complete all fields");

            } else {
                if (!AlertDAO.confirm("Are you sure you want to save information entered?")) {
                    return;
                }
                try {
                    Customer customer = new Customer(id, name, address, postalCode, phoneNumber, division);
                    CustomerDAO.modifyCustomer(customer);
                } catch (Exception e) {
                    System.out.println("NOT WORKING");
                }
            }
        } catch (Exception e) {
            AlertDAO.notification("Input Error", "Empty Field/s", "Please complete all fields");
        }

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/Customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Method used by Customer controller to set selected customer object.
     * Retrieves all info of selected customer.
     * Loops through country list to find selected customer's country.
     * Then, loops through division list to find and set division combo box.
     * Utilizes modifyCustomer method to update customer.
     * Opens to Customer Screen.
     *
     * @param selCustomer the customer to update
     */
    public void setUpdateCustomer(Customer selCustomer) {
        try {
            dependentDivisions(selCustomer.getCountryId());
            parentCountryList = CountryDAO.getCountryList();
            customerIdTxt.setText(Integer.toString(selCustomer.getCustomerId()));
            nameTxt.setText(selCustomer.getName());
            addressTxt.setText(selCustomer.getAddress());
            postalCodeTxt.setText(selCustomer.getPostalCode());
            phoneNumberTxt.setText(selCustomer.getPhoneNumber());

            Country parentCountry = null;

            for (Country country : parentCountryList) {
                if (!(country.getCountryID() == selCustomer.getCountryId())) {
                } else {
                    parentCountry = country;
                    break;
                }
            }

            CountryComboBox.setItems(CountryDAO.getCountryList());
            CountryComboBox.setVisibleRowCount(3);
            CountryComboBox.getSelectionModel().select(parentCountry);
            DivisionComboBox.setItems(dependentDivisionList);

            for (Division division : dependentDivisionList) {
                if (division.getDivisionId() == selCustomer.getDivisionID()) {
                    DivisionComboBox.getSelectionModel().select(division);
                    break;
                }
            }
        } catch (Exception ignored) {
        }
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
     * Associated with the division combo box.
     *
     * @param event the event
     */
    @FXML
    void onActionDivisionCombo(ActionEvent event) {
    }


    /**
     * Method for setting division combo box.
     * This method is used by setUpdate Customer
     * This overloaded method uses an int country ID as parameter.
     * Loops through Division list to match Country ID.
     *
     * @param depCountryId the country ID to filter division list
     */
    public void dependentDivisions(int depCountryId) {
        dependentDivisionList.clear();
        for (Division division : DivisionDAO.getDivisionList()) {
            if (division.getCountryId() == depCountryId) {
                dependentDivisionList.add(division);
            }
        }
    }

    /**
     * Method for setting division combo box.
     * Loops through Division list to match Country ID.
     */
    public void dependentDivisions() {
        dependentDivisionList.clear();
        int countryId = CountryComboBox.getSelectionModel().getSelectedItem().getCountryID();
        for (Division division : DivisionDAO.getDivisionList()) {
            if (division.getCountryId() == countryId) {
                dependentDivisionList.add(division);
            }
        }
        DivisionComboBox.setItems(dependentDivisionList);
        DivisionComboBox.getSelectionModel().selectFirst();
    }

    /**
     * Initializes Controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
