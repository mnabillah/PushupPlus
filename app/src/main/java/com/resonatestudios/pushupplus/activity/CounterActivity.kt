package com.resonatestudios.pushupplus.activity

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.*
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.resonatestudios.pushupplus.R
import com.resonatestudios.pushupplus.controller.DbHistory
import java.util.*

class CounterActivity : AppCompatActivity(), SensorEventListener, View.OnClickListener {
    //<editor-fold desc="Sensor Components and Variables">
    private var sensorManager: SensorManager? = null
    private var sensorProximity: Sensor? = null
    private var prevValue = 0.0
    private var currValue = 0.0
    private var count = 0

    //</editor-fold>
    //<editor-fold desc="Timer Variables">
    private var millisecondTime: Long = 0
    private var startTime: Long = 0
    private var timeBuff: Long = 0
    private var handler: Handler? = null

    //</editor-fold>
    //<editor-fold desc="UI Components">
    private var textViewTimer: TextView? = null
    private var textViewCounter: TextView? = null
    private var textViewCountdown: TextView? = null
    private var buttonStart: Button? = null
    private var buttonStop: Button? = null
    private var buttonAbort: Button? = null

    //</editor-fold>
    private val runnableTimer: Runnable = object : Runnable {
        @SuppressLint("DefaultLocale", "SetTextI18n")
        override fun run() {
            // count time
            millisecondTime = SystemClock.uptimeMillis() - startTime
            val updateTime = timeBuff + millisecondTime
            var seconds = (updateTime / 1000).toInt()
            val minutes = seconds / 60
            seconds %= 60
            val milliSeconds = (updateTime % 1000).toInt()
            textViewTimer!!.text = ("" + minutes + ":"
                    + String.format("%02d", seconds) + ":"
                    + String.format("%03d", milliSeconds))
            Handler(Looper.getMainLooper()).postDelayed(this, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)
        Objects.requireNonNull(supportActionBar)!!.elevation = 0f
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorProximity = sensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        textViewTimer = findViewById(R.id.text_view_timer)
        textViewCounter = findViewById(R.id.text_view_counter)
        textViewCountdown = findViewById(R.id.text_view_countdown)
        buttonStart = findViewById(R.id.button_start)
        buttonStart!!.setOnClickListener(this)
        buttonStop = findViewById(R.id.button_stop)
        buttonStop!!.setOnClickListener(this)
        buttonAbort = findViewById(R.id.button_abort)
        buttonAbort!!.setOnClickListener(this)
        currValue = 0.0
        prevValue = sensorProximity!!.maximumRange.toDouble()
        if (sensorProximity == null) {
            // tidak ada sensor accelerometer
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.setMessage("""
    Proximity sensor not found
    Unfortunately, this means you cannot use this app
    """.trimIndent())
            alertDialog.show()
            // tak boleh klik start
            buttonStart!!.isEnabled = false
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        currValue = event.values[0].toDouble()
        if (currValue == sensorProximity!!.maximumRange.toDouble() && prevValue == 0.0) {
            count++
            val newValue = count.toString()
            textViewCounter!!.text = newValue
        }
        prevValue = currValue
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        //
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_start -> {
                // set UI
                buttonStart!!.isEnabled = false
                textViewCountdown!!.visibility = TextView.VISIBLE
                // countdown
                object : CountDownTimer(3000, 1000) {
                    // push up starts after the countdown ends
                    override fun onTick(millisUntilFinished: Long) {
                        textViewCountdown!!.text = (millisUntilFinished / 1000 + 1).toString()
                    }

                    override fun onFinish() {
                        textViewCountdown!!.text = "GO!"
                        // start sensor
                        sensorManager!!.registerListener(this@CounterActivity, sensorProximity, SensorManager.SENSOR_DELAY_NORMAL)
                        // start timer
                        startTime = SystemClock.uptimeMillis()
                        handler!!.postDelayed(runnableTimer, 0)
                        // set buttons
                        buttonAbort!!.isEnabled = true
                        buttonStop!!.isEnabled = true
                    }
                }.start()
            }
            R.id.button_abort -> {
                // stop sensor
                sensorManager!!.unregisterListener(this)
                // reset UI
                buttonStart!!.isEnabled = true
                buttonAbort!!.isEnabled = false
                buttonStop!!.isEnabled = false
                textViewCountdown!!.visibility = TextView.GONE
                run {
                    timeBuff = 0L
                    startTime = timeBuff
                    millisecondTime = startTime
                }
                handler!!.removeCallbacks(runnableTimer)
                // reset textViews
                textViewTimer!!.text = "0:00:000"
                textViewCounter!!.text = "0"
                // reset counter
                count = 0
            }
            R.id.button_stop -> {
                // stop sensor
                sensorManager!!.unregisterListener(this)
                // reset UI
                buttonStart!!.isEnabled = true
                buttonAbort!!.isEnabled = false
                buttonStop!!.isEnabled = false
                textViewCountdown!!.visibility = TextView.GONE
                // stop timer
                timeBuff += millisecondTime
                handler!!.removeCallbacks(runnableTimer)
                // get values to be inserted to DB
                val amount = count
                val duration = timeBuff
                val date = Calendar.getInstance().time
                val dbHistory = DbHistory(this)
                dbHistory.open()
                if (dbHistory.insert(amount, duration, date)) {
                    Log.i(TAG, "History insert success")
                } else {
                    Log.e(TAG, "History insert failed")
                }
                dbHistory.close()
                // stop activity
                finish()
            }
        }
    }

    companion object {
        const val TAG = "CounterActivity"
    }
}