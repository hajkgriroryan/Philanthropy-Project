package PhilanthropyProject.project;

/**
 * naxatesvac e Contactnery pahelu hamar,
 * pahum e klienti anun, azgannun, kazmakerputyun, mail ev heraxosy
 */
public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private Organisation organisation;
    private String eMail;
    private String phone;

    public Contact(int id, String firstName, String lastName, Organisation organisation, String eMail, String phone) {
        if (id < 0) {
            throw new IllegalArgumentException("id cannot be negative number.");
        }
        this.id = id;
        if (firstName == null) {
            throw new IllegalArgumentException("firstName cannot be null.");
        }
        this.firstName = firstName;
        if (lastName == null) {
            throw new IllegalArgumentException("lastName cannot be null.");
        }
        this.lastName = lastName;
        if (organisation == null) {
            throw new IllegalArgumentException("organisation cannot be null.");
        }
        this.organisation = organisation;
        if (eMail == null) {
            throw new IllegalArgumentException("eMail cannot be null.");
        }
        this.eMail = eMail;
        if (phone == null) {
            throw new IllegalArgumentException("phone cannot be null.");
        }
        this.phone = phone;
    }

    public Contact() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return id == contact.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", organisation='" + organisation + '\'' +
                ", eMail='" + eMail + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("firstName cannot be null.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("lastName cannot be null.");
        }
        this.lastName = lastName;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        if (organisation == null) {
            throw new IllegalArgumentException("organisation cannot be null.");
        }
        this.organisation = organisation;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        if (eMail == null) {
            throw new IllegalArgumentException("eMail cannot be null.");
        }
        this.eMail = eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("phone cannot be null.");
        }
        this.phone = phone;
    }
}
