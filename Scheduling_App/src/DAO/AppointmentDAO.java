package DAO;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * AppointmentDAO contains methods for manipulating/retrieving data in database
 *
 * @author Bradley Harper
 */
public class AppointmentDAO {


    /**
     * Method for getting Appointment list from the database
     * Joins Appointment, Customer, User, and Contact tables to get desired data.
     *
     * @return Appointments. appointment list
     */
    public static ObservableList<Appointment> getAppointmentList() {

        ObservableList<Appointment> Appointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT a.appointment_id, a.title, a.description, a.location, a.type, a.start, a.end, c.Customer_ID, u.User_ID, co.Contact_ID, co.Contact_Name " +
                    "FROM appointments a, customers c, users u, contacts co " +
                    "WHERE a.Customer_ID = c.Customer_ID AND a.User_ID = u.User_ID AND a.Contact_ID = co.Contact_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Appointment Appointment = new Appointment();
                Appointment.setAppointmentId(rs.getInt("appointment_id"));
                Appointment.setTitle(rs.getString("title"));
                Appointment.setDescription(rs.getString("description"));
                Appointment.setLocation(rs.getString("location"));
                Appointment.setType(rs.getString("type"));
                Appointment.setCustomerId(rs.getInt("Customer_ID"));
                Appointment.setUserId(rs.getInt("User_ID"));
                Appointment.setContactId(rs.getInt("Contact_ID"));
                Appointment.setContact(rs.getString("Contact_Name"));
                Timestamp startStamp = rs.getTimestamp("start");
                Timestamp endStamp = rs.getTimestamp("end");
                Appointment.setStart(startStamp.toLocalDateTime());
                Appointment.setEnd(endStamp.toLocalDateTime());
                Appointments.add(Appointment);
            }

        } catch (SQLException e) {
            System.out.println("Error in SQL");
        }
        return Appointments;
    }


    /**
     * Method for adding an Appointment to the database
     *
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     * @param contactID   the contact id
     * @param customerId  the customer id
     * @param userId      the user id
     * @param startTime   the start time
     * @param endTime     the end time
     * @return the result.
     * @throws SQLException the sql exception
     */
    public static int addAppointment(String title, String description, String location, String type, int contactID, int customerId, int userId, LocalDateTime startTime, LocalDateTime endTime) throws SQLException {

        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_Id, Contact_Id) VALUES (?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(startTime));
        ps.setTimestamp(6, Timestamp.valueOf(endTime));
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactID);

        return ps.executeUpdate();
    }


    /**
     * Method for updating an Appointment in the database
     * Uses the id as a parameter to update the selected appointment
     *
     * @param id          the id
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     * @param contactID   the contact id
     * @param customerId  the customer id
     * @param userId      the user id
     * @param startTime   the start time
     * @param endTime     the end time
     * @return the result.
     * @throws SQLException the sql exception
     */
    public static int updateAppointment(int id, String title, String description, String location, String type, int contactID, int customerId, int userId, LocalDateTime startTime, LocalDateTime endTime) throws SQLException {

        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_Id = ?, Contact_Id = ? " +
                "WHERE Appointment_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(startTime));
        ps.setTimestamp(6, Timestamp.valueOf(endTime));
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactID);
        ps.setInt(10, id);

        return ps.executeUpdate();
    }

    /**
     * Method for deleting an Appointment from the database
     * Uses the id as a parameter to delete the selected appointment
     *
     * @param appointment the appointment to delete
     */
    public static void deleteAppointment(Appointment appointment) {

        try {
            String sql = "DELETE from appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointment.getAppointmentId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for getting a list of Types from the Appointment table
     * returns the list of Types
     *
     * @return the type list
     */
    public static ObservableList<String> getTypeList() {

        ObservableList<String> typeList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM APPOINTMENTS";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Type");
                typeList.add(String.valueOf(new Appointment(type)));
            }
        } catch (SQLException e) {
            System.out.println("Error in SQL");
        }
        return typeList;
    }


}

