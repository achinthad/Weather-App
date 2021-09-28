package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText searchEdit;
    Button searchbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEdit = findViewById(R.id.searchEditText);
        searchbtn = findViewById(R.id.searchButton);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String search = searchEdit.getText().toString();
                Toast toast = Toast.makeText(getApplicationContext(),search + " search results here...",Toast.LENGTH_SHORT);
                toast.show();

                Intent intent= new Intent(getBaseContext(),MainActivity2.class);
                intent.putExtra("search",search);
                startActivity(intent);

            }
        });

    }
}