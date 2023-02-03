package model;

/**
 * The type Country.
 *
 * @author Bradley Harper
 */
public class Country {
    private final String countryName;
    private int countryID;

    /**
     * Class constructor.
     *
     * @param countryID   the country id
     * @param countryName the country name
     */
    public Country(int countryID, String countryName) {
        this.countryName = countryName;
        this.countryID = countryID;
    }

    /**
     * Class constructor.
     *
     * @param countryName the country name
     */
    public Country(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * toString method
     *
     * @return countryName
     */
    @Override
    public String toString() {

        return countryName;
    }
}
