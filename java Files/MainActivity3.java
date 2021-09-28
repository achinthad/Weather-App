package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity3 extends AppCompatActivity {
    TextView disTemp,disCity,date,disHum,disWind,disPre,disDes,disDate;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().setTitle("Detail View");

        disTemp=(TextView) findViewById(R.id.tempCF);
        disCity=(TextView) findViewById(R.id.cityName);
        date=(TextView) findViewById(R.id.date);

        disHum=(TextView) findViewById(R.id.humid);
        disDes=(TextView) findViewById(R.id.climat);
        disDate=(TextView) findViewById(R.id.day);

        imageView=(ImageView) findViewById(R.id.icon);

        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O){
            LocalDateTime time = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateTime = time.format(dateTimeFormatter);
            date.setText(String.valueOf(dateTime));

        }
        Bundle bundle = getIntent().getExtras();
        Integer Tempicon = bundle.getInt("Listviewclickicon");
        String tempDis = getIntent().getStringExtra("disTemperature");
        String cityDis = getIntent().getStringExtra("disLocation");

        String disHum1 = getIntent().getStringExtra("dayHum");
        String disWind1 = getIntent().getStringExtra("dayWind");
        String disPre1 = getIntent().getStringExtra("dayPre");
        String disDes1 = getIntent().getStringExtra("dayDesc");
        String disDate1 = getIntent().getStringExtra("disDay");

        imageView.setImageResource(Tempicon);
        disTemp.setText(tempDis);
        disCity.setText(cityDis);
        disDes.setText(disDes1);
        disHum.setText(disHum1);
        disDate.setText(disDate1);
    }

}