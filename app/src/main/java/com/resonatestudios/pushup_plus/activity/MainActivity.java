package com.resonatestudios.pushup_plus.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.resonatestudios.pushup_plus.R;
import com.resonatestudios.pushup_plus.fragment.HistoryFragment;
import com.resonatestudios.pushup_plus.fragment.HomeFragment;
import com.resonatestudios.pushup_plus.fragment.LeaderboardFragment;
import com.resonatestudios.pushup_plus.fragment.ProfileFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    actionBar.setTitle(R.string.app_name);
                    return loadFragment(new HomeFragment());
                case R.id.navigation_history:
                    actionBar.setTitle(R.string.title_history);
                    return loadFragment(new HistoryFragment());
                case R.id.navigation_leaderboard:
                    actionBar.setTitle(R.string.title_leaderboard);
                    return loadFragment(new LeaderboardFragment());
                case R.id.navigation_profile:
                    actionBar.setTitle(R.string.title_profile);
                    return loadFragment(new ProfileFragment());
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();

        loadFragment(new HomeFragment());
        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction
                    .setCustomAnimations(R.anim.vertical_fade_in, R.anim.vertical_fade_out)
                    .replace(R.id.frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
