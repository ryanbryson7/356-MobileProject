package com.cs356.mobile;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.cs356.mobile.ui.calender.CalenderFragment;
import com.cs356.mobile.ui.event.CreateEventFragment;
import com.cs356.mobile.ui.home.HomeFragment;
import com.cs356.mobile.ui.notifications.NotificationsFragment;
import com.cs356.mobile.ui.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        currentFragment = new HomeFragment();

        //hiding default app icon
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);

        //displaying custom ActionBar
        View mActionBarView = getLayoutInflater().inflate(R.layout.my_action_bar, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ImageButton notificationButton = findViewById(R.id.notification_button);
        ImageButton profileButton = findViewById(R.id.profile_button);
        fragmentManager.beginTransaction().add(R.id.fragment_holder, currentFragment).commit();
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        currentFragment = new HomeFragment();
                        break;
                    case R.id.navigation_create_event:
                        currentFragment = new CreateEventFragment();
                        break;
                    case R.id.navigation_calender:
                        currentFragment = new CalenderFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.fragment_holder, currentFragment).commit();
                return true;
            }
        });

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.fragment_holder, new NotificationsFragment()).commit();
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.fragment_holder, new SettingsFragment()).commit();
            }
        });



        //TODO: decide if we want to keep the default code

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_create_event, R.id.navigation_calender)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.fragment_holder);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.top_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.navigation_notifications:
//                fragmentManager.beginTransaction().replace(R.id.fragment_holder, new NotificationsFragment()).commit();
//                return true;
//
//            case R.id.navigation_settings:
//                fragmentManager.beginTransaction().replace(R.id.fragment_holder, new SettingsFragment()).commit();
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}