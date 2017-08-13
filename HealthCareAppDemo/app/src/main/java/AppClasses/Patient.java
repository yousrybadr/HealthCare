package AppClasses;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yousry on 2/29/2016.
 */
public class Patient extends User implements Serializable{


    private String Job;


    public Patient() {
        super();
        Job=new String();
    }


    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }
}
