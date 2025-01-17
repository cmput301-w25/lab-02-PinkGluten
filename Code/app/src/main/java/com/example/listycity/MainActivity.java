package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    String selectedCity = null;  // To hold the currently selected city
    EditText cityInput;
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        Button addCityButton = findViewById(R.id.add_city_button);
        Button deleteCityButton = findViewById(R.id.delete_city_button);
        cityList = findViewById(R.id.city_list);
        cityInput = findViewById(R.id.city_input);
        confirmButton = findViewById(R.id.confirm_button);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        cityList.setAdapter(cityAdapter);

        // Set an item click listener on the ListView to select a city
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = dataList.get(position);  // Save the selected city
                Toast.makeText(MainActivity.this, selectedCity + " selected", Toast.LENGTH_SHORT).show();  // Display a toast message
            }
        });

        // Add city button behavior
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show EditText and confirm button to enter a city name
                cityInput.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.VISIBLE);
            }
        });

        // Confirm button behavior (to add a new city)
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newCity = cityInput.getText().toString().trim();
                if (!newCity.isEmpty()) {
                    dataList.add(newCity);  // Add the new city to the list
                    cityAdapter.notifyDataSetChanged();  // Notify the adapter to update the ListView
                    cityInput.setText("");  // Clear the input field
                    cityInput.setVisibility(View.GONE);  // Hide the EditText
                    confirmButton.setVisibility(View.GONE);  // Hide the confirm button
                    Toast.makeText(MainActivity.this, newCity + " added", Toast.LENGTH_SHORT).show();  // Show confirmation
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a city name", Toast.LENGTH_SHORT).show();  // Show error if input is empty
                }
            }
        });

        // Delete city button behavior
        deleteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedCity != null) {
                    dataList.remove(selectedCity);  // Remove the selected city
                    cityAdapter.notifyDataSetChanged();  // Notify the adapter to update the ListView
                    selectedCity = null;  // Clear the selected city
                    Toast.makeText(MainActivity.this, "City removed", Toast.LENGTH_SHORT).show();  // Show confirmation message
                } else {
                    Toast.makeText(MainActivity.this, "Please select a city first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}