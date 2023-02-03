package model;

/**
 * The type Customer.
 *
 * @author Bradley Harper
 */
public class Customer {


    private int customerId;
    private final String name;
    private final String address;
    private String postalCode;
    private String phoneNumber;
    private int countryId;
    private String country;
    private int divisionID;
    private String division;

    /**
     * Class constructor.
     *
     * @param customerId the customer id
     * @param name       the name
     * @param address    the address
     */
    public Customer(int customerId, String name, String address) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
    }

    /**
     * Class constructor.
     *
     * @param customerId  the customer id
     * @param name        the name
     * @param address     the address
     * @param postalCode  the postal code
     * @param phoneNumber the phone number
     */
    public Customer(int customerId, String name, String address, String postalCode, String phoneNumber) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Class constructor.
     *
     * @param customerId  the customer id
     * @param name        the name
     * @param address     the address
     * @param postalCode  the postal code
     * @param phoneNumber the phone number
     * @param countryId   the country id
     * @param country     the country
     * @param divisionID  the division id
     * @param division    the division
     */
    public Customer(int customerId, String name, String address, String postalCode, String phoneNumber, int countryId, String country, int divisionID, String division) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.countryId = countryId;
        this.country = country;
        this.divisionID = divisionID;
        this.division = division;
    }

    /**
     * Class constructor.
     *
     * @param name        the name
     * @param address     the address
     * @param postalCode  the postal code
     * @param phoneNumber the phone number
     * @param division    the division
     */
    public Customer(String name, String address, String postalCode, String phoneNumber, String division) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.division = division;
    }

    /**
     * Class constructor.
     *
     * @param customerId  the customer id
     * @param name        the name
     * @param address     the address
     * @param postalCode  the postal code
     * @param phoneNumber the phone number
     * @param country     the country
     * @param division    the division
     */
    public Customer(int customerId, String name, String address, String postalCode, String phoneNumber, String country, String division) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.division = division;
    }

    /**
     * Class constructor.
     *
     * @param customerId  the customer id
     * @param name        the name
     * @param address     the address
     * @param postalCode  the postal code
     * @param phoneNumber the phone number
     * @param division    the division
     */
    public Customer(int customerId, String name, String address, String postalCode, String phoneNumber, String division) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.division = division;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets division.
     *
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * toString method
     * @return name
     */
    @Override
    public String toString(){

        return name;
    }





}
