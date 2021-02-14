package com.resonatestudios.pushupplus.activity

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.resonatestudios.pushupplus.R
import com.resonatestudios.pushupplus.fragment.HomeFragment
import com.resonatestudios.pushupplus.fragment.HistoryFragment
import com.resonatestudios.pushupplus.fragment.NewsFragment
import com.resonatestudios.pushupplus.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemReselectedListener
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private var actionBar: ActionBar? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_home -> {
                actionBar!!.setTitle(R.string.app_name)
                return@OnNavigationItemSelectedListener loadFragment(HomeFragment())
            }
            R.id.navigation_history -> {
                actionBar!!.setTitle(R.string.title_history)
                return@OnNavigationItemSelectedListener loadFragment(HistoryFragment())
            }
            R.id.navigation_news -> {
                actionBar!!.title = "News"
                return@OnNavigationItemSelectedListener loadFragment(NewsFragment())
            }
            R.id.navigation_profile -> {
                actionBar!!.setTitle(R.string.title_profile)
                return@OnNavigationItemSelectedListener loadFragment(ProfileFragment())
            }
        }
        false
    }
    private val mOnNavigationItemReselectedListener = OnNavigationItemReselectedListener {
        // do nothing
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar = supportActionBar
        if (actionBar != null) actionBar!!.elevation = 0f
        loadFragment(HomeFragment())
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.setOnNavigationItemReselectedListener(mOnNavigationItemReselectedListener)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction
                    .setCustomAnimations(R.anim.vertical_fade_in, R.anim.vertical_fade_out)
                    .replace(R.id.frame_layout, fragment)
                    .commit()
            return true
        }
        return false
    }
}