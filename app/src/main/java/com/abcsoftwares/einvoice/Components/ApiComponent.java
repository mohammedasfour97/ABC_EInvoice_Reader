package com.abcsoftwares.einvoice.Components;

import com.abcsoftwares.einvoice.BaseClasses.BaseRepository;
import com.abcsoftwares.einvoice.Modules.ApiModule;
import com.abcsoftwares.einvoice.Modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface ApiComponent {
    void inject(BaseRepository baseRepository);
}
