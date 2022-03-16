package sc.stqa.pft.addressbook.models;

import java.util.Objects;

public class ContactData {
    private int id;
    private String firstname;
    private final String lastname;
    private final String email;
    private final String address;
    private final String mobile;
    private String group;

    public ContactData(String firstname, String lastname, String email, String address, String mobile, String group){
        this.id = Integer.MAX_VALUE;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.mobile = mobile;
        this.group = group;
    }
    public ContactData(int id, String firstname, String lastname, String email, String address, String mobile, String group){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.mobile = mobile;
        this.group = group;
    }

    public String getFirstname() { return firstname; }

    public String getLastname() { return lastname; }

    public String getEmail() { return email; }

    public String getAddress() { return address; }

    public String getMobile() { return mobile; }

    public String getGroup() { return group; }

    public int getId() { return id; }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(email, that.email) && Objects.equals(address, that.address) && Objects.equals(mobile, that.mobile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, email, address, mobile);
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }


}
