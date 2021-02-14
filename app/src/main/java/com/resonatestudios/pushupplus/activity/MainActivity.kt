package com.resonatestudios.pushupplus.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.resonatestudios.pushupplus.R;
import com.resonatestudios.pushupplus.fragment.HistoryFragment;
import com.resonatestudios.pushupplus.fragment.HomeFragment;
import com.resonatestudios.pushupplus.fragment.NewsFragment;
import com.resonatestudios.pushupplus.fragment.ProfileFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private ActionBar actionBar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            actionBar.setTitle(R.string.app_name);
                            return loadFragment(new HomeFragment());
                        case R.id.navigation_history:
                            actionBar.setTitle(R.string.title_history);
                            return loadFragment(new HistoryFragment());
//                        case R.id.navigation_leaderboard:
//                            actionBar.setTitle(R.string.title_leaderboard);
//                            return loadFragment(new LeaderboardFragment());
                        case R.id.navigation_news:
                            actionBar.setTitle("News");
                            return loadFragment(new NewsFragment());
                        case R.id.navigation_profile:
                            actionBar.setTitle(R.string.title_profile);
                            return loadFragment(new ProfileFragment());
                    }
                    return false;
                }
            };

    private BottomNavigationView.OnNavigationItemReselectedListener mOnNavigationItemReselectedListener =
            new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                    // do nothing
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setElevation(0);

        loadFragment(new HomeFragment());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setOnNavigationItemReselectedListener(mOnNavigationItemReselectedListener);
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