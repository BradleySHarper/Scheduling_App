package model;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * The type Month.
 *
 * @author Bradley Harper
 */
public class Month {

    private String month;
    private String type;
    private int count;

    /**
     * Class constructor.
     *
     * @param month the month
     * @param type  the type
     * @param count the count
     */
    public Month(String month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    /**
     * Class constructor.
     *
     * @param type the type
     */
    public Month(String type) {
        this.type = type;
    }

    /**
     * Class constructor.
     *
     * @param count the count
     */
    public Month(int count) {
        this.count = count;
    }


    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }


    /**
     * Method that adds all the months to a list
     *
     * @return months month list
     */
    public static ObservableList<String> getMonthList() {

        ObservableList<String> months = FXCollections.observableArrayList();

        months.add("JANUARY");
        months.add("FEBRUARY");
        months.add("MARCH");
        months.add("APRIL");
        months.add("MAY");
        months.add("JUNE");
        months.add("JULY");
        months.add("AUGUST");
        months.add("SEPTEMBER");
        months.add("OCTOBER");
        months.add("NOVEMBER");
        months.add("DECEMBER");

        return months;
    }


    /**
     * Method that takes a month and type to find associated appointments
     * Used in the report screen
     *
     * @param month the month
     * @param type  the type
     * @return the count
     * @throws SQLException the sql exception
     */
    public static int apptCountByMonthAndType(String month, String type) throws SQLException {

        String selectStatement = "SELECT * FROM APPOINTMENTS WHERE Type = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(selectStatement);
        ps.setString(1, type);
        ResultSet rs = ps.executeQuery();

        int count = 0;
        while (rs.next()) {
            LocalDateTime sTime = rs.getTimestamp("Start").toLocalDateTime();
            if(month.equals(sTime.getMonth().toString())) {
                count++;
            }
        }
        return count;
    }

    /**
     * toString method
     * @return month
     */
    @Override
    public String toString(){
        return month;
    }
}
