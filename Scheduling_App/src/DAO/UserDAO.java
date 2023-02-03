package DAO;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserDAO contains methods for manipulating/retrieving data of the User table in database
 *
 * @author Bradley Harper
 */
public class UserDAO {

    /**
     * method for verifying login credentials.
     * If user name and password match, then returns boolean value true
     *
     * @param userName the userName to verify
     * @param password the password to verify
     * @return true if match
     * @throws SQLException the sql exception
     */
    public static boolean verifyLogin(String userName, String password) throws SQLException {

        String sql = "SELECT * FROM users WHERE User_Name=? AND Password=?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, userName);
        ps.setString(2, password);
        try {
            ps.execute();
            ResultSet rs = ps.executeQuery();
            return (rs.next());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * method for getting the User list from the database
     *
     * @return the list
     */
    public static ObservableList<User> getUserList() {

        ObservableList<User> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from Users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                list.add(new User(userID, userName, password));
            }
        } catch (SQLException e) {
            System.out.println("Error in SQL");
        }
        return list;
    }
}
