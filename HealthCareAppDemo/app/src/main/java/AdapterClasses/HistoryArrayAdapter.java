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

import DataProviderClasses.HistoryDataProvider;
import DataProviderClasses.ShowClinicsDataProvider;

/**
 * Created by mahmoud on 2016-04-19.
 */
public class HistoryArrayAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public HistoryArrayAdapter(Context context, int resource) {
        super(context, resource);
    }
    static class DataHandler {
        TextView doctor_name;
        TextView case_desription;
        TextView drugs;
        TextView notes;
        TextView date;
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
    public void clear() {
        super.clear();
        list.clear();
    }

    @Override
    public void remove(Object object) {
        super.remove(object);
        list.remove(object);
    }
    public int length()
    {
        return list.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        row = convertView;
        DataHandler handler ;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_history,parent,false);
            handler = new DataHandler();
            handler.doctor_name = (TextView)row.findViewById(R.id.row_history_textView_doctorName);
            handler.case_desription = (TextView)row.findViewById(R.id.row_history_textView_caseDescription);
            handler.drugs = (TextView)row.findViewById(R.id.row_history_textView_drugs);
            handler.notes = (TextView)row.findViewById(R.id.row_history_textView_note);
            handler.date = (TextView)row.findViewById(R.id.row_history_textView_date);

            row.setTag(handler);
        }
        else
        {
            handler = (DataHandler)row.getTag();
        }
        HistoryDataProvider dataProvider;
        dataProvider =(HistoryDataProvider)this.getItem(position);
        handler.doctor_name.setText(dataProvider.getmDoctorName());
        handler.case_desription.setText(dataProvider.getmCaseDescription());
        handler.drugs.setText(dataProvider.getmDrugs());
        handler.notes.setText(dataProvider.getmNotes());
        handler.date.setText(dataProvider.getmDate());

        return row;

    }
}
