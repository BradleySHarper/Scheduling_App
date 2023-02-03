package controller;

import DAO.CustomerDAO;
import DAO.AlertDAO;
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
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import java.util.ResourceBundle;


/**
 * Controller controls the Customer screen.
 *
 * @author Bradley Harper
 */
public class CustomerController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;


    @FXML
    private TableView<Customer> CustomerTblView;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    private TableColumn<Customer, String> customerDivisionCol;

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerPostCodeCol;


    /**
     * Associated with the add button.
     * Opens to the Add Customer Screen.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionAdd(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Associated with the Delete Customer button.
     * Deletes selected customer's appointments first, then customer record.
     * Notifies user of what was deleted
     * Includes error check if no customer selected.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionDelete(ActionEvent event) throws IOException {

        Customer selCustomer = CustomerTblView.getSelectionModel().getSelectedItem();

        if (!CustomerTblView.getSelectionModel().isEmpty()) {

            if (AlertDAO.confirm("This will remove from all records of " + selCustomer.getName() + ", including all Appointments, Please Confirm.")) {
                CustomerDAO.deleteCustomer(CustomerTblView.getSelectionModel().getSelectedItem());

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../view/Customer.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

                AlertDAO.notification("Deletion Complete", "Customer Deleted", "All records of " + selCustomer.getName() + " have been deleted.");
            }
        } else {
            AlertDAO.notification("INPUT ERROR", "No customer selected!", "Please select a customer to be deleted");
        }
    }

    /**
     * Associated with the return to main button.
     * Opens to the Main Menu Screen.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionReturnMain(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Associated with the update button.
     * Includes error check if no customer selected.
     * Opens to the Update Customer Screen.
     * Calls method setUpdateCustomer to bring Customer info to the Update Customer Screen
     *
     * @param event the event
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    @FXML
    void onActionUpdate(ActionEvent event) throws IOException, SQLException {

        Customer selCustomer = CustomerTblView.getSelectionModel().getSelectedItem();
        if (selCustomer == null) {
            AlertDAO.notification("Input Error", "No Customer Selected!", "Please select a customer to update.");
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateCustomer.fxml"));
            loader.load();

            UpdateCustomerController sendCustomer = loader.getController();
            sendCustomer.setUpdateCustomer(selCustomer);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Initializes Controller.
     * Creates Observable List to hold Customer data.
     * Sets table columns and fills with data
     * Sorts Table view by customer ID
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> customerTableData = CustomerDAO.getCustomerList();

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));

        CustomerTblView.setItems(customerTableData);

        CustomerTblView.getSortOrder().add(customerIDCol);
        CustomerTblView.sort();
    }
}
