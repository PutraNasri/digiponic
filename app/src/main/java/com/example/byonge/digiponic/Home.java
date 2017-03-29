package com.example.byonge.digiponic;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;


public class Home extends Activity {
    private TextView textsuhu;
    private TextView textlembab;
    private TextView textair1;
    private TextView textair2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textsuhu = (TextView) findViewById(R.id.Tsuhu);
        textair1 = (TextView) findViewById(R.id.Tada);
        textair2 = (TextView) findViewById(R.id.Ttank);
        getEmployee();

    }

    private void getEmployee() {
        class GetEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Home.this, "Menampilkan Data...", "Loading...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(config.URL_GET_DATA);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);


            String suhu = c.getString(config.TAG_SUHU);
            String lembab = c.getString(config.TAG_LEMBAB);
            String air1 = c.getString(config.TAG_WATER);
            String air2 = c.getString(config.TAG_WATERTANK);

            int a = Integer.parseInt(air1);

            if (a > 300){
                textair1.setText("OK");
            }
            else{
                textair1.setText("BAD");
            }



            textsuhu.setText(suhu);
            textair2.setText(air2);




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
