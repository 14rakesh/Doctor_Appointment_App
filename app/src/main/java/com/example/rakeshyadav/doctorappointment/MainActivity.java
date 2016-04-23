package com.example.rakeshyadav.doctorappointment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] dataArray = new String[10];
    private ListView listView;
    Context c;
    ArrayList<String> dataItems = new ArrayList<String>();
    PatientsList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = this;
        try {
            // Creating JSONObject from String
            JSONObject jsonObjMain = new JSONObject(new LoadJSONData().loadJSONFromAsset(c));

            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.getJSONArray("patients");

            // JSONArray has four JSONObject
            for (int i = 0; i < jsonArray.length(); i++) {

                // Creating JSONObject from JSONArray
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                // Getting data from individual JSONObject
                String name = jsonObj.getString("name");
                String id = jsonObj.getString("id");
                String city = jsonObj.getString("city");
                String mobileNo = jsonObj.getString("mobileNo");
                String date = jsonObj.getString("date");

                String dataArrays = "Date: " + date + "\n" + "Name: " + name + "\n" + "Id: "
                        + id + "\n" + "City: " + city + "\n" + "MobileNo: " + mobileNo;
                dataArray[i] = dataArrays;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<String> dataTemp = Arrays.asList(dataArray);
        dataItems.addAll(dataTemp);
        listView = (ListView) findViewById(R.id.listView);

        Collections.sort(dataItems);

        adapter = new PatientsList(this, dataItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position + 1;
                String text = "" + position;
                Intent intent = new Intent(MainActivity.this, PatientDetails.class);
                intent.putExtra("selected-item", text);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
