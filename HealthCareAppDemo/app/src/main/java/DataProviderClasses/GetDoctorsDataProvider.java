package DataProviderClasses;

/**
 * Created by yousry on 4/15/2016.
 */
public class GetDoctorsDataProvider {
    private int image;
    private String name;
    private String email;

    public GetDoctorsDataProvider(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
