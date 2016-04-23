package com.example.rakeshyadav.doctorappointment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SplashScreen extends Activity {
    TextView textView;
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        textView = (TextView)findViewById(R.id.textView1);
        c = this;
        Thread timerThread = new Thread()
        {
            public void run()
            {
                try
                {
                    JSONObject jsonObjMain = new JSONObject(new LoadJSONData().loadJSONFromAsset(c));

                    // Creating JSONArray from JSONObject
                    JSONArray jsonArray = jsonObjMain.getJSONArray("patients");
                    for (int i = 0; i<jsonArray.length(); i++) {

                            JSONObject jsonObj = jsonArray.getJSONObject(i);

                            // Getting data from individual JSONObject
                            String name = jsonObj.getString("name");
                            String id = jsonObj.getString("id");
                            String city = jsonObj.getString("city");
                            String mobileNo = jsonObj.getString("mobileNo");
                            String date = jsonObj.getString("date");

                            String dataArrays = "Date: " + date + "\n" + "Name: " + name + "\n" + "Id: "
                                    + id + "\n" + "City: " + city + "\n" + "MobileNo: " + mobileNo;
                            if(id.equals("4")) {
                                textView.setText(dataArrays);
                            }
                        }
                        sleep(6000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally
                {

                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}
