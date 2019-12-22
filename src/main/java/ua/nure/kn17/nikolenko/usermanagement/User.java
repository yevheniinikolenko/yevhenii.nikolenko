package ua.nure.kn17.nikolenko.usermanagement;
import javax.jws.soap.SOAPBinding;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
* Class User contains information about user
* */
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;


    public User() {}

    public User(Long id, String firstName, String lastName, Date dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public User(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
	public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
    * @return user's full name in format "First name, Last name"
    * */
    String getFullName() {
        return lastName + ", " + firstName;
    }

    /**
     * This method expects correct birth date established in the past
     * @return user's age in years
     */
    public int getAge() {
        Calendar dateOfBirthday = Calendar.getInstance();
        dateOfBirthday.setTime(getDateOfBirth());

        Calendar today = Calendar.getInstance();

        if (dateOfBirthday.after(today)) {
            throw new IllegalArgumentException("The age can not be negative!");
        }

        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int d1 = Integer.parseInt(formatter.format(getDateOfBirth()));
        int d2 = Integer.parseInt(formatter.format(today.getTime()));
        int ageCounter = (d2 - d1) / 10000;

        return ageCounter;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + "\nSurname: " + lastName + "\nDate of Birth: " + dateOfBirth;
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return 0;
        }
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }
        if (this.getId() == null && ((User)obj).getId() == null) {
            return true;
        }

        return this.getId().equals(((User)obj).getId());
    }
}