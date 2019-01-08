package com.resonatestudios.pushupplus.activity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.resonatestudios.pushupplus.R;
import com.resonatestudios.pushupplus.controller.DbHistory;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CounterActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {
    public static final String TAG = "CounterActivity";
    //<editor-fold desc="Sensor Components and Variables">
    private SensorManager sensorManager;
    private Sensor sensorProximity;
    private double prevValue;
    private double currValue;
    private int count = 0;
    //</editor-fold>
    //<editor-fold desc="Timer Variables">
    private long MillisecondTime, StartTime, TimeBuff;
    private Handler handler;
    //</editor-fold>
    //<editor-fold desc="UI Components">
    private TextView textViewTimer;
    private TextView textViewCounter;
    private TextView textViewCountdown;
    private Button buttonStart;
    private Button buttonStop;
    private Button buttonAbort;
    //</editor-fold>
    private Runnable runnableTimer = new Runnable() {
        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        @Override
        public void run() {
            // count time
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            long updateTime = TimeBuff + MillisecondTime;
            int seconds = (int) (updateTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliSeconds = (int) (updateTime % 1000);

            textViewTimer.setText("" + minutes + ":"
                    + String.format("%02d", seconds) + ":"
                    + String.format("%03d", milliSeconds));

            handler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        handler = new Handler();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        textViewTimer = findViewById(R.id.text_view_timer);
        textViewCounter = findViewById(R.id.text_view_counter);
        textViewCountdown = findViewById(R.id.text_view_countdown);
        buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(this);
        buttonStop = findViewById(R.id.button_stop);
        buttonStop.setOnClickListener(this);
        buttonAbort = findViewById(R.id.button_abort);
        buttonAbort.setOnClickListener(this);

        currValue = 0;
        prevValue = sensorProximity.getMaximumRange();

        if (sensorProximity == null) {
            // tidak ada sensor accelerometer
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("Proximity sensor not found\n" +
                    "Unfortunately, this means you cannot use this app");
            alertDialog.show();
            // tak boleh klik start
            buttonStart.setEnabled(false);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        currValue = event.values[0];
        if (currValue == sensorProximity.getMaximumRange() && prevValue == 0) {
            count++;
            String newValue = String.valueOf(count);
            textViewCounter.setText(newValue);
        }
        prevValue = currValue;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                // set UI
                buttonStart.setEnabled(false);
                textViewCountdown.setVisibility(TextView.VISIBLE);
                // countdown
                new CountDownTimer(3000, 1000) {
                    // push up starts after the countdown ends
                    @Override
                    public void onTick(long millisUntilFinished) {
                        textViewCountdown.setText(String.valueOf((millisUntilFinished / 1000) + 1));
                    }

                    @Override
                    public void onFinish() {
                        textViewCountdown.setText("GO!");
                        // start sensor
                        sensorManager.registerListener(CounterActivity.this, sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
                        // start timer
                        StartTime = SystemClock.uptimeMillis();
                        handler.postDelayed(runnableTimer, 0);
                        // set buttons
                        buttonAbort.setEnabled(true);
                        buttonStop.setEnabled(true);
                    }
                }.start();
                break;
            case R.id.button_abort:
                // stop sensor
                sensorManager.unregisterListener(this);
                // reset UI
                buttonStart.setEnabled(true);
                buttonAbort.setEnabled(false);
                buttonStop.setEnabled(false);
                textViewCountdown.setVisibility(TextView.GONE);
                // reset and stop timer
                MillisecondTime = StartTime = TimeBuff = 0L;
                handler.removeCallbacks(runnableTimer);
                // reset textViews
                textViewTimer.setText("0:00:000");
                textViewCounter.setText("0");
                // reset counter
                count = 0;
                break;
            case R.id.button_stop:
                // stop sensor
                sensorManager.unregisterListener(this);
                // reset UI
                buttonStart.setEnabled(true);
                buttonAbort.setEnabled(false);
                buttonStop.setEnabled(false);
                textViewCountdown.setVisibility(TextView.GONE);
                // stop timer
                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnableTimer);
                // get values to be inserted to DB
                int amount = count;
                long duration = TimeBuff;
                Date date = Calendar.getInstance().getTime();
                DbHistory dbHistory = new DbHistory(this);
                dbHistory.open();
                if (dbHistory.insert(amount, duration, date)){
                    Log.i(TAG, "History insert success");
                } else {
                    Log.e(TAG, "History insert failed");
                }
                dbHistory.close();
                // stop activity
                finish();
                break;
        }
    }
}
