package sc.stqa.pft.addressbook.models;

public class ContactData {
    private final String firstname;
    private final String lastname;
    private final String title;
    private final String address;
    private final String mobile;
    private String group;

    public ContactData (String firstname, String lastname, String title, String address, String mobile, String group){
        this.firstname = firstname;
        this.lastname = lastname;
        this.title = title;
        this.address = address;
        this.mobile = mobile;
        this.group = group;
    }

    public String getFirstname() { return firstname; }

    public String getLastname() { return lastname; }

    public String getTitle() { return title; }

    public String getAddress() { return address; }

    public String getMobile() { return mobile; }

    public String getGroup() { return group; }
}
