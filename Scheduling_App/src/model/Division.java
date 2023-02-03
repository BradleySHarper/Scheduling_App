package model;

/**
 * The type Division.
 *
 * @author Bradley Harper
 */
public class Division {

    private int divisionId;
    private String division;
    private int countryId;

    /**
     * Class constructor.
     *
     * @param divisionId the division id
     * @param division   the division
     * @param countryId  the country id
     */
    public Division(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * Class constructor.
     *
     * @param division  the division
     * @param countryId the country id
     */
    public Division(String division, int countryId) {
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * Class constructor.
     */
    public Division(){
    }

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public int getDivisionId() {
        return divisionId;
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
     * toString method
     * @return division
     */
    @Override
    public String toString(){

        return division;
    }
}
