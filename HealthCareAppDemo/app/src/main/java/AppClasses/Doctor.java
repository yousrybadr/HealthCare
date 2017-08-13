package AppClasses;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

import com.example.yousry.healthcareapp.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yousry on 2/29/2016.
 */
public class    Doctor extends User implements Serializable {

    private String Field;
    private ArrayList<Clinic> ClinicsList;

    public Doctor() {
        super();

        Field="";
        ClinicsList=new ArrayList<Clinic>();
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public ArrayList<Clinic> getClinicsList() {
        return ClinicsList;
    }

    public void setClinicsList(ArrayList<Clinic> clinicsList) {
        ClinicsList = clinicsList;
    }



}
