package com.abcsoftwares.einvoice.Modules;

import com.abcsoftwares.einvoice.MainActivity;
import com.abcsoftwares.einvoice.R;

import javax.inject.Singleton;

import androidx.navigation.fragment.NavHostFragment;
import dagger.Module;
import dagger.Provides;

@Module
public class NavigationControllerModule {

    private MainActivity activity;

    public NavigationControllerModule(MainActivity activity){

        this.activity = activity;
    }

    @Singleton
    @Provides
    NavHostFragment navHostFragment(){

        return  (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);

    }
}
