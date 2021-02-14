package com.resonatestudios.pushupplus.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.resonatestudios.pushupplus.R;
import com.resonatestudios.pushupplus.adapter.HistoryListAdapter;
import com.resonatestudios.pushupplus.controller.DbHistory;

import java.text.ParseException;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerViewLeaderboard;


    public LeaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        recyclerViewLeaderboard = view.findViewById(R.id.recycler_view_leaderboard);
        // get data and show on recyclerViewLeaderboard
        HistoryListAdapter historyListAdapter = new HistoryListAdapter(context);
        DbHistory dbHistory = new DbHistory(context);

        try {
            dbHistory.open();
            historyListAdapter.setHistoryItems(dbHistory.getAll(DbHistory.collumns[1]));
            dbHistory.close();
        } catch (ParseException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        recyclerViewLeaderboard.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewLeaderboard.setAdapter(historyListAdapter);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
