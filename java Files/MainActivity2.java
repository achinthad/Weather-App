package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    String location;
    String gotSearch;
    int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        gotSearch = getIntent().getStringExtra("search");

        FetchData fetchData = new FetchData();
        fetchData.execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {

            case R.id.about:
                // do your code
                return true;

            case R.id.set:
                Intent intent= new Intent(getBaseContext(),MainActivity4.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class FetchData extends AsyncTask<String,Void,String> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void onPostExecute(String s) {
            try {

                JSONObject root = new JSONObject(forecastJsonStr);
                ListView listView =findViewById(R.id.dayList);
                final List<String> disLocation = new ArrayList<>();

                final String[] date  =  new String[7];
                final String[] tempValue  =new String[7];
                final Integer[] icon_list = new Integer[7];
                final String[] dayDec = new String[7];
                final String[] humidity = new String[7];

                JSONArray array= root.getJSONArray("daily");

                for(int i=0;i<7;i++)
                {
                    JSONObject object= array.getJSONObject(i);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                    String unixTimeString = object.getString("dt");
                    long unixTimeInt = Integer.parseInt(unixTimeString);
                    Date dateFormat = new java.util.Date(unixTimeInt * 1000);
                    String day = sdf.format(dateFormat );

                    if (i == 0){
                        date[i] = day+"(TODAY)";
                    }else{
                        date[i] = day;
                    }
                    JSONObject tempObj= array.getJSONObject(i);
                    JSONObject tempObjDay= tempObj.getJSONObject("temp");
                    tempValue[i] = tempObjDay.getString("day");

                    JSONArray weaDes = object.getJSONArray("weather");
                    JSONObject desc = weaDes.getJSONObject(0);
                    String Desc1 = desc.getString("description");
                    String icon = desc.getString("icon");
                    String Desc2 = Desc1.substring(0, 1).toUpperCase() + Desc1.substring(1);
                    dayDec[i] = Desc2;

                    String humidity1 = object.getString("humidity");

                    humidity[i] = "Humidity : " + humidity1 + " %";
                    icon_list[i] = R.drawable.d09d;
                    disLocation.add(location);
                    

                }

                CustomListAdapter adapter = new CustomListAdapter(MainActivity2.this, date, tempValue, icon_list, dayDec);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String displayDay = date[+position];
                        String displayTemp = tempValue[+position];
                        String cityName = disLocation.get(+position);
                        String dayDesc = dayDec[+position];
                        String dayHum = humidity[+position];
                        Integer sel = icon_list[+position];

                       Intent intent = new Intent(MainActivity2.this,MainActivity3.class);

                        intent.putExtra("disDay", displayDay);
                        intent.putExtra("disTemperature", displayTemp);
                        intent.putExtra("disLocation", cityName);
                        intent.putExtra("Listviewclickicon", sel);
                        intent.putExtra("dayDesc", dayDesc);
                        intent.putExtra("dayHum", dayHum);

                        startActivity(intent);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                List<Double> ll = null;

                location = gotSearch;
                Geocoder gc = new Geocoder(getApplicationContext());
                List<Address> addresses= gc.getFromLocationName(location, 5);

                ll = new ArrayList<>(addresses.size()); // A list to save the coordinates if they are available
                for(Address a : addresses) {
                    if (a.hasLatitude() && a.hasLongitude()) {
                        ll.add(a.getLatitude());
                        ll.add(a.getLongitude());
                    }
                }
                final String BASE_URL ="https://api.openweathermap.org/data/2.5/onecall?lat="+ll.get(0)+"&lon="+ll.get(1)+"&exclude=minutely,hourly,alerts&appid=e1b571feaf5cfa3c8fbaf4f191149813";
                URL url = new URL(BASE_URL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) { return null; }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line1;

                while ((line1 = reader.readLine()) != null) { buffer.append(line1 + "\n"); }
                if (buffer.length() == 0) { return null; }
                forecastJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e("Hi", "Error ", e);
                return null;
            } finally{
                if (urlConnection != null) { urlConnection.disconnect(); }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Hi", "Error closing stream", e);
                    }
                }
            }
            return null;
        }
    }
}