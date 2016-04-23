package com.example.rakeshyadav.doctorappointment;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by SAMEER COMPTR on 21-Apr-16.
 */
public class LoadJSONData {

    public String loadJSONFromAsset(Context context) {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(context.getAssets().open("patients.json")));
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
