package model;

/**
 * The type Contact.
 *
 * @author Bradley Harper
 */
public class Contact {

private final int contactId;
private final String contactName;

    /**
     * Class constructor.
     *
     * @param contactId   the contact id
     * @param contactName the contact name
     */
    public Contact(int contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * toString method
     *
     * @return contactName
     */
    @Override
    public String toString(){
        return contactName;
    }
}
