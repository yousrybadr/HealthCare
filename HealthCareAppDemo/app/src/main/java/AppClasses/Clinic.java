package AppClasses;

import java.io.Serializable;

/**
 * Created by Mustafa_X on 3/21/2016.
 */
public class Clinic implements Serializable {

    private int id;
    private String Name ;
    private String Description ;
    private String Phone;
    private String StartTime;
    private String EndTime;
    private Double Longitude;
    private Double Latitude;
    private String Address;

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    private int patient_id;

    public Clinic() {
        id =0;
        Name=new String();
        Description=new String();
        Phone =new String();
        StartTime=new String();
        EndTime=new String();
        Longitude=0.0;
        Latitude=0.0;
        Address=new String();
        patient_id=0;
    }


    public Clinic(int id, String name, String description, String phone, String startTime, String endTime, Double longitude, Double latitude, String address,int patient_id) {
        this.id = id;
        Name = name;
        Description = description;
        Phone = phone;
        StartTime = startTime;
        EndTime = endTime;
        Longitude = longitude;
        Latitude = latitude;
        Address = address;
        this.patient_id =patient_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
