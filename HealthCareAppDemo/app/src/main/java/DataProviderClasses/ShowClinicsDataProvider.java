package DataProviderClasses;

/**
 * Created by yousry on 4/14/2016.
 */
public class ShowClinicsDataProvider {
    private String name,description,phone,to,from,address;

    public ShowClinicsDataProvider() {
        name=new String();
        description=new String();
        phone=new String();
        from=new String();
        to=new String();
        address=new String();
    }

    public ShowClinicsDataProvider(String name, String description, String phone,  String from,String to, String address) {
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.to = to;
        this.from = from;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
