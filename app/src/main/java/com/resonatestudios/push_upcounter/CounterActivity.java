package com.resonatestudios.push_upcounter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensorProximity;

    TextView textViewProximityReadings;
    TextView textViewCounter;

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
        textViewProximityReadings.setText("");
        textViewCounter = findViewById(R.id.text_view_counter);
        textViewCounter.setText("Jumlah push-up: 0");

        prevValue = sensorProximity.getMaximumRange();
        currValue = 0;

        if (sensorProximity != null) {
            // ada sensor accelerometer
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("Sukses, device punya sensor proximity");
            alertDialog.show();
        } else {
            // tidak ada sensor accelerometer
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("TIdak ada sensor proximity");
            alertDialog.show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
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

    }
}
