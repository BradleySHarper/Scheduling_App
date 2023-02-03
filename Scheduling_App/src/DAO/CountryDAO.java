package DAO;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CountryDAO contains a method for getting the country list from the database.
 *
 * @author Bradley Harper
 */
public class CountryDAO {

    /**
     * method for getting the Country list from the database
     *
     * @return the list
     */
    public static ObservableList<Country> getCountryList() {

        ObservableList<Country> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int countryId = rs.getInt("country_ID");
                String nameOfCountry = rs.getString("Country");
                list.add(new Country(countryId, nameOfCountry));
            }
        }
        catch(SQLException e) {
            System.out.println("Error in SQL");
        }
        return list;
    }
}
