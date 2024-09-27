package com.example.final_simple_shimmer_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
//import android.content.res.Resources;
//
//import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import android.os.Handler;

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
        graph.addSeries(series);  // Add the series to the graph

        // Set the viewport to initially display the first portion of the data
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10); // Adjust to your preferred window size (10 units in this case)

        // Enable scrolling and scaling
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true); // Allow zooming

        // Handler to animate data points being added
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            private int currentIndex = 0;

            @Override
            public void run() {
                if (currentIndex < data.size()) {
                    // Get the next data point
                    double[] point = data.get(currentIndex);
                    double x = point[0];
                    double y = point[1];

                    // Add the data point to the series
                    series.appendData(new DataPoint(x, y), true, data.size());

                    // Update the viewport to scroll to the right
                    graph.getViewport().setMinX(x - 10);  // Display the last 10 units
                    graph.getViewport().setMaxX(x);  // Shift to the right as new data is added

                    // Increment index for next data point
                    currentIndex++;

                    // Run this code again after a delay (e.g., 200 milliseconds)
                    handler.postDelayed(this, 200); // Adjust the delay for smoothness
                }
            }
        };

        // Start the animation
        handler.post(runnable);
    }


}