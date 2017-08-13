package BackgroundTaskFolder;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * Created by yousry on 3/18/2016.
 */
public class BackgroundTask extends AsyncTask<String,Void,String> {

    // set Constructor with parameters to take context from running activity even make dialog
    public BackgroundTask(Context context) {
        this.URL_KEY="";
        this.context = context;
        items=new ArrayList<String>();

    }

    //filter the operations
    public enum  TAGS{
        NULL,
        LOGIN_DOCTOR,
        LOGIN_PATIENT,
        REGISTERATION_DOCTOR,
        REGISTERATION_PATIENT,
        SEARCH_CLINIC_FIELD,
        SEARCH_DOCTOR_FIELD,
        SEARCH_PATIENT,
        CREATE_CLINIC,
        UPDATE_CLINIC,
        UPDATE_DOCTOR_PROFILE,
        GET_CLINICS,
        SHOW_CLINIC_ACTIVITY,
        Make_APPOINTMENT,
        VIEW_HISTORY,
        ADD_HISTORY,
        ADD_USER_CHAT,
        SHOW_ROOMS
    };

    //member Variables
    private TAGS tag;
    private String URL_KEY;  // this variable will take link URL
    private Context context;
    private ArrayList<String> items;
    private String Result;



    private RequestBody DetectionRequestBody(RequestBody body)
    {

        switch (getTag()) {
            case LOGIN_DOCTOR:
                body = getRequesBodyLoginDoctor();
                break;
            case LOGIN_PATIENT:
                body = getRequesBodyLoginPatient();
                break;
            case REGISTERATION_DOCTOR:
                body = getRequestBodyRegisteratonDoctor();
                break;
            case REGISTERATION_PATIENT:
                body = getRequestBodyRegisteratonPatient();
                break;
            case SEARCH_CLINIC_FIELD:
                body = getRequestBodySearchClinicField();
                break;
            case SEARCH_DOCTOR_FIELD:
                body = getRequestBodySearchDoctorField();
                break;
            case SEARCH_PATIENT:
                body = getRequestBodySearchPatient();
                break;
            case CREATE_CLINIC:
                body = getRequestBodyCreateClinic();
                break;
            case UPDATE_CLINIC:
                body = getRequestBodyUpdateClinic();
                break;
            case UPDATE_DOCTOR_PROFILE:
                body = getRequestBodyUpdateDoctorProfile();
                break;
            case GET_CLINICS:
                body= getRequesBodyGetClinics();
                break;
            case SHOW_CLINIC_ACTIVITY:
                body= getRequestBodyGetClinics();
                break;
            case Make_APPOINTMENT:
                body = getRequestBodyMakeAppointment();
                break;
            case VIEW_HISTORY:
                body = getRequestBodyViewHistory();
                break;
            case ADD_HISTORY:
                body = getRequestBodyAddHistory();
                break;
            case ADD_USER_CHAT:
                body =getRequesBodyAddUserChat();
                break;
            case SHOW_ROOMS:
                body=getRequesBodyShowRooms();
                break;
            case NULL:
                body = null;
                break;
            default:
                body = null;
                break;
        }
        return body;
    }



    //RequestBodies for posting
    private RequestBody getRequesBodyLoginDoctor(){
        RequestBody body = new FormEncodingBuilder()
                .add("user_name", items.get(0))
                .build();
        return body;
    }
    private RequestBody getRequesBodyLoginPatient(){
        RequestBody body = new FormEncodingBuilder()
                .add("user_name", items.get(0))
                .build();
        return body;
    }
    private RequestBody getRequesBodyAddUserChat(){
        RequestBody body = new FormEncodingBuilder()
                .add("patient_id", items.get(0))
                .add("doctor_id", items.get(1))
                .build();
        return body;
    }
    private RequestBody getRequesBodyShowRooms(){
        RequestBody body = new FormEncodingBuilder()
                .add("user_id", items.get(0))
                .build();
        return body;
    }
    private RequestBody getRequestBodyRegisteratonDoctor(){
        RequestBody body = new FormEncodingBuilder()
                .add("first_name", items.get(0))
                .add("last_name", items.get(1))
                .add("user_name", items.get(2))
                .add("password", items.get(3))
                .add("email", items.get(4))
                .add("sex", items.get(5))
                .add("field", items.get(6))
                .add("phone",items.get(7))
                .add("image",items.get(8))
                .build();
        return body;
    }
    private RequestBody getRequestBodyRegisteratonPatient(){
        RequestBody body = new FormEncodingBuilder()
                .add("first_name", items.get(0))
                .add("last_name", items.get(1))
                .add("user_name", items.get(2))
                .add("password", items.get(3))
                .add("email", items.get(4))
                .add("phone",items.get(5))
                .add("sex", items.get(6))
                .add("job", items.get(7))
                .add("image", items.get(8))
                .build();
        return body;
    }
    private RequestBody getRequestBodySearchPatient(){
        RequestBody body = new FormEncodingBuilder()
                .add("user_name", items.get(0))
                .build();
        return body;
    }
    private RequestBody getRequestBodySearchClinicField(){
        RequestBody body = new FormEncodingBuilder()
                .add("field", items.get(0))
                .build();
        return body;
    }

    private RequestBody getRequestBodySearchDoctorField(){
        RequestBody body = new FormEncodingBuilder()
                .add("field", items.get(0))
                .build();
        return body;
    }

    private RequestBody getRequestBodyCreateClinic(){
        RequestBody body = new FormEncodingBuilder()
                .add("doctor_id", items.get(0))
                .add("name", items.get(1))
                .add("description", items.get(2))
                .add("phone", items.get(3))
                .add("start_time", items.get(4))
                .add("end_time", items.get(5))
                .add("lat", items.get(6))
                .add("lng", items.get(7))
                .add("address", items.get(8))
                .build();
        return body;
    }
    private RequestBody getRequestBodyUpdateClinic(){
        RequestBody body = new FormEncodingBuilder()
                .add("password", items.get(0))
                .build();
        return body;
    }
    private RequestBody getRequestBodyUpdateDoctorProfile(){
        RequestBody body = new FormEncodingBuilder()
                .add("first_name", items.get(0))
                .add("last_name", items.get(1))
                .add("user_name", items.get(2))
                .add("password", items.get(3))
                .add("email", items.get(4))
                .add("sex", items.get(5))
                .add("field", items.get(6))
                .add("phone",items.get(7))
                .build();
        return body;
    }
    private RequestBody getRequesBodyGetClinics(){
        RequestBody body = new FormEncodingBuilder()
                .add("doctor_id", items.get(0))
                .build();
        return body;
    }

    private RequestBody getRequestBodyGetClinics(){
        RequestBody body = new FormEncodingBuilder()
                .add("doctor_id", items.get(0))
                .build();
        return body;
    }
    private RequestBody getRequestBodyMakeAppointment(){
        RequestBody body = new FormEncodingBuilder()
                .add("patient_id", items.get(0))
                .add("clinic_id", items.get(1))
                .add("year", items.get(2))
                .add("month",items.get(3))
                .add("day",items.get(4))
                .add("hour",items.get(5))
                .add("minute", items.get(6))
                .build();
        return body;
    }
    private RequestBody getRequestBodyViewHistory(){
        RequestBody body = new FormEncodingBuilder()
                .add("patient_id", items.get(0))
                .build();
        return body;
    }
    private RequestBody getRequestBodyAddHistory(){
        RequestBody body = new FormEncodingBuilder()
                .add("patient_id", items.get(0))
                .add("doctor_id", items.get(1))
                .add("doctor_name", items.get(2))
                .add("case_description",items.get(3))
                .add("drug",items.get(4))
                .add("note",items.get(5))
                .add("day", items.get(6))
                .add("month", items.get(7))
                .add("year", items.get(8))
                .build();
        return body;
    }



    private ProgressDialog dialog;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(getTag()!=TAGS.GET_CLINICS) {
            dialog = new ProgressDialog(this.context);
            dialog.setMessage("Loading...");
            dialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        this.URL_KEY =params[0];
        try {
            if(isNetworkAvailable()) {
                OkHttpClient client = new OkHttpClient();
                client.setConnectTimeout(15, TimeUnit.SECONDS);
                client.setReadTimeout(30, TimeUnit.SECONDS);
                client.setWriteTimeout(30, TimeUnit.SECONDS);
                RequestBody body = null;
                body = DetectionRequestBody(body);
                if(body ==null)
                {
                    return "No Request Body";
                }
                Request request = new Request.Builder().url(getURL_KEY()).post(body).build();

                Response response = client.newCall(request).execute();
                String jsonData =response.body().string();
                return jsonData;
            }
            else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private MyAsyncListener listener ;

    public void setListener(MyAsyncListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(String data) { //Finish MyDialog
        super.onPostExecute(data);
        if(getTag()!=TAGS.GET_CLINICS) {
            dialog.dismiss();
        }
        if(data==null)
        {
            Toast.makeText(context,"No Network Connection ",Toast.LENGTH_LONG).show();
        }
        items.clear();
        setURL_KEY("");
        listener.onSuccessfulExecute(data);
    }




    public interface MyAsyncListener {
        void onSuccessfulExecute(String data);
    }




    //Checking Network Connection

    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected())
        {
            isAvailable = true;

        }
        return isAvailable;
    }





    // getter and setter
    public TAGS getTag() {
        return tag;
    }
    public void setTag(TAGS tag) {
        this.tag = tag;
    }
    public String getURL_KEY() {
        return URL_KEY;
    }
    public void setURL_KEY(String URL_KEY) {
        this.URL_KEY = URL_KEY;
    }
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public void setParamsItem(String item) {
        this.items.add(item);
    }
    public void setParams(ArrayList<String> params) {
        this.items = params;
    }
    public String getResult() {
        return Result;
    }
    public void setResult(String result) {
        Result = result;
    }
    public ArrayList<String> getParams() {
        return items;
    }


}

