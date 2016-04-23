package com.example.rakeshyadav.doctorappointment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rakesh Yadav on 19-Apr-16.
 */
public class PatientDetails extends Activity {

    TextView textView,textView1;
    MainActivity mainActivity;
    String id;
    String item;
    Context c;
    ArrayList<String> patientArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_info_layout);
        textView = (TextView) findViewById(R.id.patientdata);
        textView1 = (TextView) findViewById(R.id.patientinfo);
        c = this;
        mainActivity = new MainActivity();
        Intent intent = getIntent();

        // fetch value from key-value pair and make it visible on TextView.
        item = intent.getStringExtra("selected-item");
        try {
            // Creating JSONObject from String
            JSONObject jsonObjMain = new JSONObject(loadJSONFromAsset());
            JSONObject jsonObjPatient = new JSONObject(new LoadJSONData().loadJSONFromAsset(c));

            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.getJSONArray("patientDetails");
            JSONArray jsonPatientArray = jsonObjPatient.getJSONArray("patients");
            // JSONArray has four JSONObject
            for(int i = 0; i < jsonPatientArray.length(); i++)
            {
                JSONObject jsonObj = jsonPatientArray.getJSONObject(i);
                String image = jsonObj.getString("profile");
                String name = jsonObj.getString("name");
                String id = jsonObj.getString("id");
                String age = jsonObj.getString("age");
                String gender = jsonObj.getString("gender");
                String bloodGroup = jsonObj.getString("bloodGroup");
                String city = jsonObj.getString("city");
                String address = jsonObj.getString("address");
                String emailId = jsonObj.getString("emailId");
                String mobileNo = jsonObj.getString("mobileNo");
                String date = jsonObj.getString("date");

                String str = "Date: " + date + "\n" + "Name: " + name + "\n" + "Id: "
                        + id + "\n" + "Age: " + age + "\n" + "Gender: " + gender+ "\n" + "Blood Group: " + bloodGroup + "\n" + "city: "
                        + city + "\n" + "Address: " + address + "\n" + "E-Mail Id: " + emailId+"\n" + "Mobile No: " + mobileNo;
                if(id.equals(item))
                {
                    textView1.setText(str);
                    new GetTask().execute(image);
                }
            }
            for (int i = 0; i < jsonArray.length(); i++) {

                // Creating JSONObject from JSONArray
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                // Getting data from individual JSONObject
                String name = jsonObj.getString("name");
                id = jsonObj.getString("id");
                String diagnosis = jsonObj.getString("diagnosis");
                String symptoms = jsonObj.getString("symptoms");
                String medication = jsonObj.getString("medication");
                String toBeTaken = jsonObj.getString("toBeTaken");
                String comments = jsonObj.getString("comments");

                String dataArrays = "Diagnosis: " + diagnosis + "\n" + "Symptoms: " + symptoms + "\n" + "Medication: "
                        + medication + "\n" + "To Be Taken: " + toBeTaken + "\n" + "Comments: " + comments;
                if (id.equals(item))
                {
                    textView.setText(dataArrays);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class GetTask extends AsyncTask<String, Void, Void> {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Bitmap bitmap;

        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            try {

                bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            imageView.setImageBitmap(bitmap);
        }
    }
    public String loadJSONFromAsset() {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open("patientDetails.json")));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String myjsonstring = sb.toString();
        return myjsonstring;
    }
}
