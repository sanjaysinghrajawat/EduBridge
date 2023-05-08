package com.example.edubridge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // find IDs
        bnView = findViewById(R.id.bnView);

        // set by default Home Fragment when App open
        loadFrag(new HomeFragment());

        // Set Click listeners  on nav items
        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                // setting condition for nav items
                /*if(id == R.id.nav_Feed)
                {
                    loadFrag(new FeedFragment());
                }*/
                if (id == R.id.nav_Explore)
                {
                    loadFrag(new MoreFragment());
                }
                else if (id == R.id.nav_Profile)
                {
                    loadFrag(new SearchFragment());
                }
                else if (id == R.id.nav_Activity)
                {
                    loadFrag(new ActivityFragment());
                }
                else
                {// home fragment
                    loadFrag(new HomeFragment() );
                }
                return true;
            }
        });
    }

    public void loadFrag(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
}