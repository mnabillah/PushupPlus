package com.resonatestudios.pushupplus.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.resonatestudios.pushupplus.R;
import com.resonatestudios.pushupplus.activity.CounterActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button buttonStart = view.findViewById(R.id.button_start);
        buttonStart.setOnClickListener(this);

        TextView textViewDate = view.findViewById(R.id.date_today);
        textViewDate.setText(getTodayDate());

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                Intent intent = new Intent(context.getApplicationContext(), CounterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private String getTodayDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM d", Locale.getDefault());
        return simpleDateFormat.format(today);
    }
}
