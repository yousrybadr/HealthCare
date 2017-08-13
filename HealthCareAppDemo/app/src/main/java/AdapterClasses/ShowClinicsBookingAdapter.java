package AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yousry.healthcareapp.CustomDialogClass;
import com.example.yousry.healthcareapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import AppClasses.Clinic;
import BackgroundTaskFolder.BackgroundTask;

/**
 * Created by mahmoud on 2016-04-17.
 */
public class ShowClinicsBookingAdapter extends ArrayAdapter {
    List list = new ArrayList();Context context;

    private static final String URL_Make_APPOINTMENT ="http://healthcareapp.16mb.com/healthcare/BookingAppointment.php";
    public ShowClinicsBookingAdapter(Context context, int resource) {
        super(context, resource);
        this.context=context;
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
        return list.indexOf(item);

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


        View row = convertView;
        if (row == null) {
            LayoutInflater inflater=(LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_clinic_booking_layout, parent, false);
        }
        TextView name;
        TextView desription;
        TextView phone;
        TextView start;
        TextView end;
        TextView address;
        Button book;
        name = (TextView)row.findViewById(R.id.row_clinic_booking_textView_name);
        desription = (TextView)row.findViewById(R.id.row_clinic_booking_textView_description);
        phone = (TextView)row.findViewById(R.id.row_clinic_booking_textView_phone);
        start = (TextView)row.findViewById(R.id.row_clinic_booking_textView_startTime);
        end = (TextView)row.findViewById(R.id.row_clinic_booking_textView_endTime);
        address = (TextView)row.findViewById(R.id.row_clinic_booking_textView_address);
        book = (Button) row.findViewById(R.id.row_clinic_booking_buuton_book);


        final Clinic dataProviderClinic;
        dataProviderClinic =(Clinic)this.getItem(position);
        name.setText("Name : " + dataProviderClinic.getName());
        desription.setText("Description : "+dataProviderClinic.getDescription());
        phone.setText("Phone : "+dataProviderClinic.getPhone());
        start.setText(dataProviderClinic.getStartTime());
        end.setText(dataProviderClinic.getEndTime());
        address.setText("Address : "+dataProviderClinic.getAddress());
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogClass cdd = new CustomDialogClass(context);
                cdd.show();
                cdd.setDialogResult(new CustomDialogClass.OnMyDialogResult() {
                    @Override
                    public void onResult(boolean result,int mYear,int mMonth,int mDay,int mHour,int mMinute) {
                        if (result) {
                            BackgroundTask backgroundTask = new BackgroundTask(context);
                            backgroundTask.setTag(BackgroundTask.TAGS.Make_APPOINTMENT);
                            backgroundTask.setParamsItem(String.valueOf(dataProviderClinic.getPatient_id()));
                            backgroundTask.setParamsItem(String.valueOf(dataProviderClinic.getId()));
                            backgroundTask.setParamsItem(String.valueOf(mYear));
                            backgroundTask.setParamsItem(String.valueOf(mMonth));
                            backgroundTask.setParamsItem(String.valueOf(mDay));
                            backgroundTask.setParamsItem(String.valueOf(mHour));
                            backgroundTask.setParamsItem(String.valueOf(mMinute));

                            backgroundTask.execute(URL_Make_APPOINTMENT);
                            backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
                                @Override
                                public void onSuccessfulExecute(String data) {
                                    if (data.contains("true")) {
                                        Toast.makeText(context,"Done ",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(context,data,Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                        }
                    }
                });
            }
        });
        return row;

    }
}
