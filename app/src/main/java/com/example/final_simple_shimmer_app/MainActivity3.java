package com.example.final_simple_shimmer_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    Button returnButton;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Initialize the button
        returnButton = findViewById(R.id.button_return);

        // Initialize the table layout
        tableLayout = findViewById(R.id.tableLayout);

        // Call the read CSVFile function to populate the table
        readCSVFile();

        // Set onClickListener to navigate back to MainActivity
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                startActivity(intent);
                finish();  // Optional: call finish() to remove MainActivity3 from the back stack
            }
        });
    }

    private void readCSVFile() {
        try {
            InputStream is = getResources().openRawResource(R.raw.datafive);  // Use your CSV file in the 'raw' folder
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            List<String[]> csvData = reader.readAll();
            reader.close();

            // Loop through CSV rows and add each row to the table layout
            for (String[] row : csvData) {
                addTableRow(row);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading CSV file", Toast.LENGTH_SHORT).show();
        }
    }

    private void addTableRow(String[] rowData) {
        TableRow tableRow = new TableRow(this);

        // Loop through each cell in the row and add TextView to TableRow
        for (String cellData : rowData) {
            TextView textView = new TextView(this);
            textView.setText(cellData);
            textView.setPadding(8, 8, 8, 8);  // Add some padding for a better look
            tableRow.addView(textView);
        }

        // Add the row to the table layout
        tableLayout.addView(tableRow);
    }
}
