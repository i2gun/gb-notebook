package notebook.model;

import notebook.util.mapper.Mapper;
import notebook.util.mapper.impl.UserMapper;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;

    public User(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public User(Long id, String firstName, String lastName, String phone) {
        this(firstName, lastName, phone);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User usr)) return false;
        return firstName.equals(usr.firstName) && lastName.equals(usr.lastName) && phone.equals(usr.phone);
    }

    @Override
    public int hashCode() {
        return (new UserMapper()).toInput(this).hashCode();
    }

    @Override
    public String toString() {
        return String.format("Идентафикатор: %s\nИмя: %s,\nФамилия: %s,\nТелефон: %s", id, firstName, lastName, phone);
    }
}
