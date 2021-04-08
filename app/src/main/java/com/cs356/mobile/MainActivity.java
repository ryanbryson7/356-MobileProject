package com.cs356.mobile;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cs356.mobile.model.Data;
import com.cs356.mobile.ui.calendar.CalendarFragment;
import com.cs356.mobile.ui.event.CreateEventFragment;
import com.cs356.mobile.ui.home.HomeFragment;
import com.cs356.mobile.ui.notifications.NotificationsFragment;
import com.cs356.mobile.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment currentFragment;
    TextView pageTitle;
    ImageButton notificationButton;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        currentFragment = new HomeFragment();

        //hiding default app icon
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(false);

        //displaying custom ActionBar
        View topMenu = getLayoutInflater().inflate(R.layout.top_menu, null);
        actionBar.setCustomView(topMenu);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        notificationButton = findViewById(R.id.notification_button);
        setNotificationStatus();

        ImageButton profileButton = findViewById(R.id.profile_button);
        pageTitle = findViewById(R.id.page_title);
        fragmentManager.beginTransaction().add(R.id.fragment_holder, currentFragment).commit();

        //Setup navigation for bottom menu
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        currentFragment = new HomeFragment();
                        pageTitle.setText("Home");

                        break;
                    case R.id.navigation_create_event:
                        currentFragment = new CreateEventFragment();
                        pageTitle.setText("Create Event");
                        break;
                    case R.id.navigation_calendar:
                        currentFragment = new CalendarFragment();
                        pageTitle.setText("Calendar");
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.fragment_holder, currentFragment).commit();
                return true;
            }
        });


        //Setup navigation for top menu
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.fragment_holder, new NotificationsFragment()).commit();
                pageTitle.setText("Notifications");
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.fragment_holder, new ProfileFragment()).commit();
                pageTitle.setText("Profile");
            }
        });
    }

    public void setTitleText(String title) {
        pageTitle.setText(title);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setNotificationStatus() {
        //Set notification icon if there are notifications
        if (Data.getInstance().getNotifications().size() > 0) {
            notificationButton.setImageDrawable(getDrawable(R.drawable.ic_notification_active));
        }
        else {
            notificationButton.setImageDrawable(getDrawable(R.drawable.ic_notification_icon));
        }
    }


        //TODO: decide if we want to keep the default code

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_create_event, R.id.navigation_calendar)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.fragment_holder);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.top_menu_old, menu);
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