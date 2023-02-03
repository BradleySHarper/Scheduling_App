package DAO;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ContactDAO contains a method for getting contact list from the database.
 *
 * @author Bradley Harper
 */
public class ContactDAO {

    /**
     * method for getting the Contact list from the database
     *
     * @return the list
     */
    public static ObservableList<Contact> getContactList() {

        ObservableList<Contact> list = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int contactID = rs.getInt("contact_ID");
                String nameOfContact = rs.getString("contact_name");
                list.add(new Contact(contactID, nameOfContact));
            }

        }
        catch(SQLException e) {
            System.out.println("Error in SQL");
        }
        return list;
    }
}
