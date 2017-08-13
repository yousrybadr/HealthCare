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

import DataProviderClasses.ShowClinicsDataProvider;

/**
 * Created by yousry on 4/14/2016.
 */
public class ShowClinicsAdapter extends ArrayAdapter {
    List list = new ArrayList();

    static class DataHandler {
        TextView name;
        TextView desription;
        TextView phone;
        TextView from;
        TextView to;
        TextView address;

    }
    public ShowClinicsAdapter(Context context, int resource) {
        super(context, resource);
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
            row = inflater.inflate(R.layout.row_show_clinics_layout,parent,false);
            handler = new DataHandler();
            handler.name = (TextView)row.findViewById(R.id.row_show_clinic_textView_name);
            handler.desription = (TextView)row.findViewById(R.id.row_show_clinic_textView_description);
            handler.phone = (TextView)row.findViewById(R.id.row_show_clinic_textView_phone);
            handler.from = (TextView)row.findViewById(R.id.row_show_clinic_textView_from);
            handler.to = (TextView)row.findViewById(R.id.row_show_clinic_textView_To);
            handler.address = (TextView)row.findViewById(R.id.row_show_clinic_textView_address);

            row.setTag(handler);
        }
        else
        {
            handler = (DataHandler)row.getTag();
        }
        ShowClinicsDataProvider dataProvider;
        dataProvider =(ShowClinicsDataProvider)this.getItem(position);
        handler.name.setText("Name : "+dataProvider.getName());
        handler.desription.setText("Description : "+dataProvider.getDescription());
        handler.phone.setText("Phone : "+dataProvider.getPhone());
        handler.from.setText(dataProvider.getFrom());
        handler.to.setText(dataProvider.getTo());
        handler.address.setText("Address : "+dataProvider.getAddress());

        return row;

    }
}
