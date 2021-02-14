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
import java.text.ParseException

/**
 * A simple [Fragment] subclass.
 */
class LeaderboardFragment : Fragment() {
    private var recyclerViewLeaderboard: RecyclerView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_leaderboard, container, false)
        recyclerViewLeaderboard = view.findViewById(R.id.recycler_view_leaderboard)
        // get data and show on recyclerViewLeaderboard
        val historyListAdapter = HistoryListAdapter(context!!)
        val dbHistory = DbHistory(context)
        try {
            dbHistory.open()
            historyListAdapter.historyItems = dbHistory.getAll(DbHistory.columns[1])
            dbHistory.close()
        } catch (e: ParseException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
        recyclerViewLeaderboard!!.layoutManager = LinearLayoutManager(context)
        recyclerViewLeaderboard!!.adapter = historyListAdapter
        return view
    }
}