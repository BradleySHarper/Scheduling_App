package model;

/**
 * The type User.
 *
 * @author Bradley Harper
 */
public class User {

    private int userId;
    private String userName;
    private String password;

    /**
     * Class constructor.
     */
    public User() {
    }

    /**
     * Class constructor.
     *
     * @param userId   the user id
     * @param userName the user name
     */
    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    /**
     * Class constructor.
     *
     * @param userName the user name
     * @param password the password
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Class constructor.
     *
     * @param userId   the user id
     * @param userName the user name
     * @param password the password
     */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
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
     * toString method
     *
     * @return userName
     */
    @Override
    public String toString() {

        return userName;
    }
}
