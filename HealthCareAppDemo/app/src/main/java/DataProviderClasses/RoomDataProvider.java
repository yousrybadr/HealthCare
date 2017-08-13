package DataProviderClasses;

/**
 * Created by mahmoud on 2016-05-05.
 */
public class RoomDataProvider {
    String Name;
    String Email;
    String Phone;

    public int chat_id;

    public RoomDataProvider(String name, String email, String phone) {
        Name = name;
        Email = email;
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
