package AppClasses;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yousry on 2/29/2016.
 */
public class User implements Serializable{

    private int ID;



    private String FirstName ;
    private String LastName ;
    private String Password ;
    private String UserName  ;
    private String EmailAddress ;
    private String Phone;
    private int Sex ;
    private String Image;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public User()
    {

        FirstName = new String();
        LastName = new String();
        Password = new String();
        UserName = new String();
        EmailAddress = new String();
        Phone =new String();
        Sex =0 ; //by default is male
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int sex) {
        Sex = sex;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Password='" + Password + '\'' +
                ", UserName='" + UserName + '\'' +
                ", EmailAddress='" + EmailAddress + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Sex=" + Sex +
                ", Image='" + Image + '\'' +
                '}';
    }
}
