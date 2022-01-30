package com.example.rentx.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rentx.R;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView price, shortDescription,description;
    private String pri, des, shdes, img, latitude, longitude;
    private AppCompatButton locationButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageView = findViewById(R.id.imageView);
        price = findViewById(R.id.price);
        shortDescription = findViewById(R.id.short_description);
        description = findViewById(R.id.description);
        locationButton = findViewById(R.id.location_button);

        pri = getIntent().getStringExtra("price");
        des = getIntent().getStringExtra("description");
        img = getIntent().getStringExtra("image");
        shdes = getIntent().getStringExtra("shortDescription");

        // Latitude and Longitude Variables :-
        latitude = getIntent().getStringExtra("Latitude");
        longitude = getIntent().getStringExtra("Longitude");
        Log.d("My Tag", "The Value of Latitude is"+latitude);
        Log.d("My Tag", "The Value of Latitude is"+longitude);

        price.setText("$"+pri);
        description.setText(des);
        shortDescription.setText(shdes);

        Glide.with(this)
                .load(img)
                .centerCrop()
                .placeholder(R.drawable.ic_account)
                .into(imageView);

//        Converting the Strings Latitude and Longitude into Double
        Double d_latitude = Double.parseDouble(latitude);
        Double d_longitude = Double.parseDouble(longitude);


        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code to be added for going to the map after fetching the longitude and lattitude positions
                // USE d_latitude and d_longitude FOR MAPS .
            }
        });

    }

}