package com.resonatestudios.pushup_plus.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.resonatestudios.pushup_plus.R;
import com.resonatestudios.pushup_plus.activity.CounterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private Context context;
    Button buttonStart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        buttonStart = view.findViewById(R.id.button_start);
        buttonStart.setOnClickListener(this);

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
}
