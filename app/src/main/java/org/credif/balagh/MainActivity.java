package org.credif.balagh;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sherd;
    SharedPreferences.Editor editor;
    int TIME_OUT_WAIT = 20000;
    public static final int REQUEST_PERMISSION = 200;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.navigation_balagh:
                    sherd = getSharedPreferences("file", Context.MODE_PRIVATE);
                    editor = sherd.edit();
                    int indicepage = sherd.getInt("Page", 0);
                    switch (indicepage) {
                        case 0:
                            editor.clear();
                            editor.apply();
                            fragment = new BalaghFragment();
                            break;
                        case 1:
                            fragment = new BalaghFragment();
                            break;
                        case 2:
                            fragment = new Balagh2Fragment();
                            break;
                        case 3:
                            fragment = new Balagh3Fragment();
                            break;
                        case 4:
                            fragment = new Balagh4Fragment();
                            break;
                        case 5:
                            fragment = new Balagh5Fragment();
                            break;
                        case 6:
                            fragment = new Balagh6Fragment();
                            break;
                    }
                    break;
                case R.id.navigation_contact:
                    fragment = new ContactFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        } else
            return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkpermission();
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new HomeFragment());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                loadFragment(new BalaghFragment());
                navigation.setSelectedItemId(R.id.navigation_balagh);

                // close this activity
            }
        }, TIME_OUT_WAIT);
    }

    public void checkpermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS},
                    REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS},
                        REQUEST_PERMISSION);
            }
        }
    }
}
