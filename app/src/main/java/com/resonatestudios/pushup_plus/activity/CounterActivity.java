package com.resonatestudios.pushup_plus.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.resonatestudios.pushup_plus.R;

public class CounterActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {
    private SensorManager sensorManager;
    private Sensor sensorProximity;

    TextView textViewProximityReadings;
    TextView textViewCounter;

    Button buttonStart;
    Button buttonStop;

    double prevValue;
    double currValue;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        textViewProximityReadings = findViewById(R.id.text_view_proximity_readings);
        textViewProximityReadings.setText("0");
        textViewCounter = findViewById(R.id.text_view_counter);
        textViewCounter.setText("Jumlah push-up: 0");

        buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(this);
        buttonStop = findViewById(R.id.button_stop);
        buttonStop.setOnClickListener(this);

        prevValue = sensorProximity.getMaximumRange();
        currValue = 0;

        if (sensorProximity != null) {
            // ada sensor accelerometer
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("Press start to begin\nPress stop to end and return to Home");
            alertDialog.show();
        } else {
            // tidak ada sensor accelerometer
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("Proximity sensor not found");
            alertDialog.show();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double distance = event.values[0];
        currValue = distance;
        if (currValue == sensorProximity.getMaximumRange() && prevValue == 0) {
            count++;
            String newValue = "Jumlah push-up: " + count;
            textViewCounter.setText(newValue);
        }
        prevValue = currValue;
        textViewProximityReadings.setText(String.valueOf(distance));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                // start sensor manager
                sensorManager.registerListener(this, sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
                buttonStart.setEnabled(false);
                buttonStop.setEnabled(true);
                break;
            case R.id.button_stop:
                // stop sensor manager and insert values to sqlite(?)
                sensorManager.unregisterListener(this);
                buttonStart.setEnabled(true);
                buttonStop.setEnabled(false);
                break;
        }
    }
}
