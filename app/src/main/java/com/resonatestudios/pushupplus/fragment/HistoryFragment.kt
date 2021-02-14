package com.resonatestudios.pushupplus.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.resonatestudios.pushupplus.R
import com.resonatestudios.pushupplus.adapter.HistoryListAdapter
import com.resonatestudios.pushupplus.controller.DbHistory

/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        val recyclerViewHistory: RecyclerView = view.findViewById(R.id.recycler_view_history)
        // get data and show on recyclerViewHistory
        val historyListAdapter = HistoryListAdapter(context!!)
        val dbHistory = DbHistory(context)
        try {
            dbHistory.open()
            historyListAdapter.historyItems = dbHistory.all
            dbHistory.close()
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
        recyclerViewHistory.layoutManager = LinearLayoutManager(context)
        recyclerViewHistory.adapter = historyListAdapter
        return view
    }
}