package com.resonatestudios.pushupplus.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.resonatestudios.pushupplus.R
import com.resonatestudios.pushupplus.activity.CounterActivity
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val buttonStart = view.findViewById<Button>(R.id.button_start)
        buttonStart.setOnClickListener(this)
        val textViewDate = view.findViewById<TextView>(R.id.date_today)
        textViewDate.text = todayDate
        return view
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_start -> {
                val intent = Intent(context!!.applicationContext, CounterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val todayDate: String
        get() {
            val today = Calendar.getInstance().time
            val simpleDateFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
            return simpleDateFormat.format(today)
        }
}