package com.resonatestudios.pushupplus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resonatestudios.pushupplus.R;
import com.resonatestudios.pushupplus.model.HistoryItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.Row> {
    ArrayList<HistoryItem> historyItems;
    Context context;

    public HistoryListAdapter(Context context) {
        this.context = context;
        historyItems = new ArrayList<>();
    }

    public ArrayList<HistoryItem> getHistoryItems() {
        return historyItems;
    }

    public void setHistoryItems(ArrayList<HistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    public void addToList(HistoryItem historyItem) {
        historyItems.add(historyItem);
    }

    @NonNull
    @Override
    public Row onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_list, parent, false);
        return new Row(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Row holder, int position) {
        HistoryItem historyItem = historyItems.get(position);

        holder.textViewNumber.setText(String.valueOf(position + 1));
        holder.textViewAmount.setText(String.valueOf(historyItem.getAmount()));
        holder.textViewDuration.setText(String.valueOf(historyItem.getDuration()));
        holder.textViewDate.setText(String.valueOf(historyItem.getDate()));
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    class Row extends RecyclerView.ViewHolder {
        TextView textViewNumber;
        TextView textViewAmount;
        TextView textViewDuration;
        TextView textViewDate;

        Row(@NonNull View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.text_view_number);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
            textViewDuration = itemView.findViewById(R.id.text_view_duration);
            textViewDate = itemView.findViewById(R.id.text_view_date);
        }
    }
}
