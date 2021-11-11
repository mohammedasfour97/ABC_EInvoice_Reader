package com.abcsoftwares.einvoice.Components;


import com.abcsoftwares.einvoice.MainActivity;
import com.abcsoftwares.einvoice.Modules.NavigationControllerModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = NavigationControllerModule.class)
public interface NavigationControllerComponent {

    void inject(MainActivity mainActivity);
}
