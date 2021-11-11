package com.abcsoftwares.einvoice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.abcsoftwares.einvoice.Components.DaggerNavigationControllerComponent;
import com.abcsoftwares.einvoice.Modules.NavigationControllerModule;
import com.abcsoftwares.einvoice.databinding.ActivityMainBinding;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends AppCompatActivity {

    @Inject
    NavHostFragment navHostFragment;
    public NavController navController;
    private boolean doubleBackToExitPressedOnce;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        DaggerNavigationControllerComponent.builder().navigationControllerModule(new NavigationControllerModule(this)).build()
                .inject(this);

        navController = navHostFragment.getNavController();

        boolean doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.please_press_back_again), Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}