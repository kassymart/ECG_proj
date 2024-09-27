package com.example.final_simple_shimmer_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    // added button features
    Button accelBtn;
    Button tableBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view first before accessing UI elements
        setContentView(R.layout.activity_main);

        // Initialize the Accelerometer button
        accelBtn = findViewById(R.id.button);
        accelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });

        // Initialize the Table View button
        tableBtn = findViewById(R.id.button2);
        tableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                startActivity(intent);
          }
        });

        // Enable edge-to-edge content //
        EdgeToEdge.enable(this);

        // Set window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
