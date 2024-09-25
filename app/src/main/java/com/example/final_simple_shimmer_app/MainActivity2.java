package com.example.final_simple_shimmer_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.content.res.Resources;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this); // Comment out if not needed
        setContentView(R.layout.activity_main2);

        // Initialize the button
        returnButton = findViewById(R.id.button_return);

        GraphView graph = findViewById(R.id.graph);
        GraphView graph2 = findViewById(R.id.graph2);   // graph 2
        GraphView graph3 = findViewById(R.id.graph3);   // graph 3
        ArrayList<double[]> data = readCSV(R.raw.datatwo); // Use raw resource ID here (LA_RA)
        ArrayList<double[]> data2 = readCSV(R.raw.datathree); // second data sheet (LL_LA)
        ArrayList<double[]> data3 = readCSV(R.raw.datafour); // third data sheet (LL_RA)


        // plotting data
        plotData(graph, data);
        plotData(graph2, data2);
        plotData(graph3, data3);

        // Set onClickListener to navigate back to MainActivity
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();  // Optional: call finish() to remove MainActivity2 from the back stack
            }
        });
    }

    public ArrayList<double[]> readCSV(int resourceId) {
        ArrayList<double[]> data = new ArrayList<>();
        try {
            InputStream inputStream = getResources().openRawResource(resourceId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) { // Ensure there are two columns
                    try {
                        double x = Double.parseDouble(values[0].trim());
                        double y = Double.parseDouble(values[1].trim());
                        data.add(new double[]{x, y});
                    } catch (NumberFormatException e) {
                        e.printStackTrace(); // Handle number format exception
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void plotData(GraphView graph, ArrayList<double[]> data) {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for (double[] point : data) {
            double x = point[0];
            double y = point[1];
            series.appendData(new DataPoint(x, y), true, data.size());
        }
        graph.addSeries(series);
    }
}
