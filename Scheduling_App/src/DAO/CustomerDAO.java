package DAO;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CustomerDAO contains methods for manipulating/retrieving data in database
 *
 * @author Bradley Harper
 */
public class CustomerDAO {

    /**
     * method for deleting the selected customer from the database
     * Deletes all associated appointments first, using customer id as a foreign key
     *
     * @param customer the customer to delete
     */
    public static void deleteCustomer(Customer customer) {

        try {
            String sql = "DELETE from appointments WHERE customer_Id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customer.getCustomerId());
            ps.executeUpdate();

            String aSql = "DELETE from customers WHERE customer_Id = ?";
            PreparedStatement aPs = JDBC.getConnection().prepareStatement(aSql);
            aPs.setInt(1, customer.getCustomerId());
            aPs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * method for getting the Customer list from the database
     * Joins Customers, first level Divisions, and Countries to obtain required info.
     * Adds customer to the database
     *
     * @return the list
     */
    public static ObservableList<Customer> getCustomerList() {

        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT c.customer_Id, c.Customer_Name, c.address, c.postal_code,  "
                    + "c.Phone, f.Country_ID, co.country, c.Division_ID,f.Division "
                    + "FROM customers c, first_level_divisions f, countries co "
                    + "WHERE c.division_ID = f.division_ID and f.country_ID = co.country_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Integer customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");

                customerList.add(new Customer(customerId, customerName, address, postalCode, phoneNumber, countryId, country, divisionId, division));
            }
        } catch (SQLException e) {
            System.out.println("Error in SQL");
        }
        return customerList;
    }

    /**
     * method for adding a customer to the database
     *
     * @param name        the name
     * @param address     the address
     * @param postalCode  the postal code
     * @param phoneNumber the phone number
     * @param division    the division
     * @return the list
     * @throws SQLException the sql exception
     */
    public static int addCustomer(String name, String address, String postalCode, String phoneNumber, String division) throws SQLException {

        String query = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?,?,?,?,?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);

        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setString(5, division);

        return ps.executeUpdate();
    }

    /**
     * method for modifying the customer in the database
     * Uses customer ID as primary key to identify selected customer
     *
     * @param customer the customer to modify
     * @return the list
     * @throws SQLException the sql exception
     */
    public static int modifyCustomer(Customer customer) throws SQLException {

        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?" +
                "WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhoneNumber());
        ps.setString(5, customer.getDivision());
        ps.setInt(6, customer.getCustomerId());

        return ps.executeUpdate();
    }
}







