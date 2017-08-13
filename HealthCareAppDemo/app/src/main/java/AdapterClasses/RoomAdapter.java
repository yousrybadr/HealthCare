package AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yousry.healthcareapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import DataProviderClasses.GetDoctorsDataProvider;
import DataProviderClasses.RoomDataProvider;

/**
 * Created by mahmoud on 2016-05-05.
 */
public class RoomAdapter extends ArrayAdapter {
    private List list = new ArrayList();
    Context context;
    public RoomAdapter(Context context, int resource) {
        super(context, resource);
        this.context=context;
    }
    static class DataHandler {
        TextView name;
        TextView email;
        TextView phone;
        ImageView call;
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
            row = inflater.inflate(R.layout.row_room,parent,false);
            handler = new DataHandler();
            handler.name = (TextView)row.findViewById(R.id.row_room_textView_name);
            handler.email = (TextView)row.findViewById(R.id.row_room_textView_email);
            handler.phone = (TextView) row.findViewById(R.id.row_room_textView_phone);
            handler.call =(ImageView) row.findViewById(R.id.row_room_imageView_phone);
            row.setTag(handler);
        }
        else
        {
            handler = (DataHandler)row.getTag();
        }
        final RoomDataProvider dataProvider;
        dataProvider = (RoomDataProvider) this.getItem(position);
        if(handler ==null) Log.d("Error","handler = null");
        handler.name.setText(dataProvider.getName());
        handler.email.setText(dataProvider.getEmail());
        handler.phone.setText(dataProvider.getPhone());
        handler.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialContactPhone(dataProvider.getPhone());
            }
        });
        return row;

    }
    //make call :D ;)
    private void dialContactPhone(final String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}
