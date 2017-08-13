package AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yousry.healthcareapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import DataProviderClasses.GetDoctorsDataProvider;
import DataProviderClasses.ShowClinicsDataProvider;

/**
 * Created by yousry on 4/15/2016.
 */
public class GetDoctorsAdapters extends ArrayAdapter {
    private List list = new ArrayList();


    public GetDoctorsAdapters(Context context, int resource) {
        super(context, resource);
    }
    static class DataHandler {
        TextView name;
        TextView email;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public void addAll(Collection collection) {
        super.addAll(collection);
        list.addAll(collection);
    }

    @Override
    public int getPosition(Object item) {
        return super.getPosition(item);
    }

    @Override
    public void remove(Object object) {
        super.remove(object);
        list.remove(object);
    }

    @Override
    public void clear() {
        super.clear();
        list.clear();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        row = convertView;
        DataHandler handler ;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_search_home_patient,parent,false);
            handler = new DataHandler();
            handler.name = (TextView)row.findViewById(R.id.row_search_home_patient_name);
            handler.email = (TextView)row.findViewById(R.id.row_search_home_patient_email);

            row.setTag(handler);
        }
        else
        {
            handler = (DataHandler)row.getTag();
        }
        GetDoctorsDataProvider dataProvider;
        dataProvider = (GetDoctorsDataProvider) this.getItem(position);
        handler.name.setText(dataProvider.getName());
        handler.email.setText(dataProvider.getEmail());


        return row;

    }
}
