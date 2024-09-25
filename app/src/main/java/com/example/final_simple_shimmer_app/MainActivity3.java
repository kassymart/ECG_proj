package com.example.final_simple_shimmer_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.InputStream;

public class MainActivity3 extends AppCompatActivity {

    Button returnButton;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        // Initialize the button
        returnButton = findViewById(R.id.button_return);

        // Initialize the table
        tableLayout = findViewById(R.id.tableLayout);

        // Call the read CSVFile function
        readCSVFile();

        // Set onClickListener to navigate back to MainActivity
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                startActivity(intent);
                finish();  // Optional: call finish() to remove MainActivity2 from the back stack
            }
        });
    }

    private void readCSVFile() {
        try {
            InputStream is = getResources().openRawResource(R.raw.datafive);  // Use your CSV file in the 'raw' folder
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            List<String[]> csvData = reader.readAll();
            reader.close();

            // Loop through CSV rows
            for (String[] row : csvData) {
                addTableRow(row);  // Display each row in the table
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading CSV file", Toast.LENGTH_SHORT).show();
        }
    }

    private void addTableRow(String[] rowData) {
        TableRow tableRow = new TableRow(this);

        for (String cellData : rowData) {
            TextView textView = new TextView(this);
            textView.setText(cellData);
            textView.setPadding(16, 16, 16, 16);
            tableRow.addView(textView);
        }

        tableLayout.addView(tableRow);
    }
}
