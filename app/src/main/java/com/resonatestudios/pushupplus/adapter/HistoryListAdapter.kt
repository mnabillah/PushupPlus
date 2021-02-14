package com.resonatestudios.pushupplus.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.resonatestudios.pushupplus.R
import com.resonatestudios.pushupplus.model.HistoryItem
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryListAdapter(private val context: Context) : RecyclerView.Adapter<HistoryListAdapter.Row>() {
    var historyItems: ArrayList<HistoryItem> = ArrayList()
    fun addToList(historyItem: HistoryItem) {
        historyItems.add(historyItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Row {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history_list, parent, false)
        return Row(view)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: Row, position: Int) {
        val historyItem = historyItems[position]
        holder.textViewNumber.text = (position + 1).toString()
        holder.textViewAmount.text = historyItem.amount.toString()
        val totalMilliseconds = historyItem.duration
        var seconds = (totalMilliseconds / 1000).toInt()
        val minutes = seconds / 60
        seconds %= 60
        val milliSeconds = (totalMilliseconds % 1000).toInt()
        holder.textViewDuration.text = String.format("%02d:%02d:%03d", minutes, seconds, milliSeconds)
        holder.textViewDate.text = DateFormat.getDateInstance().format(historyItem.date)
    }

    override fun getItemCount(): Int {
        return historyItems.size
    }

    inner class Row(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewNumber: TextView = itemView.findViewById(R.id.text_view_number)
        var textViewAmount: TextView = itemView.findViewById(R.id.text_view_amount)
        var textViewDuration: TextView = itemView.findViewById(R.id.text_view_duration)
        var textViewDate: TextView = itemView.findViewById(R.id.text_view_date)

    }

    init {
        historyItems = ArrayList()
    }
}