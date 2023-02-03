package DAO;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DivisionDAO contains a method for getting the country list from the database.
 *
 * @author Bradley Harper
 */
public class DivisionDAO {

    /**
     * method for getting the Division list from the database
     *
     * @return the list
     */
    public static ObservableList<Division> getDivisionList() {

        ObservableList<Division> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from first_level_divisions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("country_ID");
                String division = rs.getString("Division");
                int divisionID = rs.getInt("Division_ID");
                list.add(new Division(divisionID, division, countryID));
            }
        } catch (SQLException e) {
            System.out.println("Error in SQL");
        }
        return list;
    }
}
