package com.example.final_simple_shimmer_app;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity2 extends AppCompatActivity {

    Button returnButton;

    // Create series for all three graphs
    private LineGraphSeries<DataPoint> series1, series2, series3;
    private Handler handler = new Handler();
    private Runnable dataUpdater;
    private int lastX = 0; // Tracks the X-axis for all graphs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize the button
        returnButton = findViewById(R.id.button_return);

        // Initialize the graphs
        GraphView graph1 = findViewById(R.id.graph);
        GraphView graph2 = findViewById(R.id.graph2);
        GraphView graph3 = findViewById(R.id.graph3);

        // Initialize LineGraphSeries for each graph
        series1 = new LineGraphSeries<>();
        series2 = new LineGraphSeries<>();
        series3 = new LineGraphSeries<>();

        // Add series to the corresponding graphs
        graph1.addSeries(series1);
        graph2.addSeries(series2);
        graph3.addSeries(series3);

        // Configure appearance for all graphs (optional)
        configureGraph(graph1);
        configureGraph(graph2);
        configureGraph(graph3);

        // Start updating all three graphs with moving data points
        startMovingGraph();

        // Set onClickListener to navigate back to MainActivity
        returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: remove MainActivity2 from the back stack
        });
    }

    private void configureGraph(GraphView graph) {
        // Set viewport for real-time scrolling
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-10);
        graph.getViewport().setMaxY(10);

        // Enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
    }

    private void startMovingGraph() {
        dataUpdater = new Runnable() {
            @Override
            public void run() {
                addNewDataPoint(); // Add new data points to each graph
                handler.postDelayed(this, 500); // Update every 500ms
            }
        };
        handler.post(dataUpdater); // Start the data updates
    }

    private void addNewDataPoint() {
        lastX += 1;

        // Add new data points for each series
        double y1 = (lastX == 1) ? 0 : Math.random() * 20 - 10; // First point starts at 0 for graph1
        double y2 = (lastX == 1) ? 0 : Math.random() * 20 - 10; // First point starts at 0 for graph2
        double y3 = (lastX == 1) ? 0 : Math.random() * 20 - 10; // First point starts at 0 for graph3

        series1.appendData(new DataPoint(lastX, y1), true, 40);
        series2.appendData(new DataPoint(lastX, y2), true, 40);
        series3.appendData(new DataPoint(lastX, y3), true, 40);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(dataUpdater); // Stop updates when activity is destroyed
        super.onDestroy();
    }
}
