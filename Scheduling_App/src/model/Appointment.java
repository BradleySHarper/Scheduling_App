package model;

import java.time.LocalDateTime;

/**
 * The type Appointment.
 *
 * @author Bradley Harper
 */
public class Appointment {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private int contactId;
    private String contact;

    private int customerId;
    private String customerName;
    private int userId;
    private String userName;

    /**
     * The Start.
     */
    LocalDateTime start;
    /**
     * The End.
     */
    LocalDateTime end;

    /**
     * Class constructor.
     */
    public Appointment(){
    }

    /**
     * Class constructor.
     *
     * @param type the type
     */
    public Appointment(String type) {
        this.type = type;
    }

    /**
     * Class constructor.
     *
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     * @param contactId   the contact id
     * @param customerId  the customer id
     * @param userId      the user id
     * @param start       the start
     * @param end         the end
     */
    public Appointment(String title, String description, String location, String type, int contactId, int customerId, int userId, LocalDateTime start, LocalDateTime end) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.contactId = contactId;
        this.customerId = customerId;
        this.userId = userId;
        this.start = start;
        this.end = end;
    }

    /**
     * Class constructor.
     *
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     * @param contact     the contact
     * @param customerId  the customer id
     * @param userId      the user id
     * @param start       the start
     * @param end         the end
     */
    public Appointment(String title, String description, String location, String type, String contact, int customerId, int userId, LocalDateTime start, LocalDateTime end) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.contact = contact;
        this.customerId = customerId;
        this.userId = userId;
        this.start = start;
        this.end = end;
    }

    /**
     * Class constructor.
     *
     * @param appointmentId the appointment id
     * @param title         the title
     * @param description   the description
     * @param location      the location
     * @param type          the type
     * @param contactId     the contact id
     * @param contact       the contact
     * @param customerId    the customer id
     * @param userId        the user id
     * @param start         the start
     * @param end           the end
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, int contactId, String contact, int customerId, int userId, LocalDateTime start, LocalDateTime end) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.contactId = contactId;
        this.contact = contact;
        this.customerId = customerId;
        this.userId = userId;
        this.start = start;
        this.end = end;
    }

    /**
     * Class constructor.
     *
     * @param appointmentId the appointment id
     * @param title         the title
     * @param description   the description
     * @param location      the location
     * @param type          the type
     * @param contact       the contact
     * @param customerId    the customer id
     * @param userId        the user id
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, String contact, int customerId, int userId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.contact = contact;
        this.customerId = customerId;
        this.userId = userId;
    }

    /**
     * Gets appointment id.
     *
     * @return the appointment id
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets appointment id.
     *
     * @param appointmentId the appointmentId to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
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
     * Sets type.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets contact id.
     *
     * @return the contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets contact id.
     *
     * @param contactId the contactId to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets contact.
     *
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets contact.
     *
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Gets customer id.
     *
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets customer id.
     *
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets user id.
     *
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets user name.
     *
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets start.
     *
     * @param start the start to set
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets end.
     *
     * @return end end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets end.
     *
     * @param end the end to set
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * toString method
     * @return type
     */
    @Override
    public String toString(){

        return type;
    }
}
