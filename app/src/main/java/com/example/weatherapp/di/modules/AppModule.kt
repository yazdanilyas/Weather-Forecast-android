package com.example.weatherapp.di.modules

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /*@Provides
    fun provideLocationManager(): LocationManagerClient {
       return LocationManagerClient()
    }
    @Provides
    fun provideLocationSettingViewModel():LocationSettingViewModel
    {
        return LocationSettingViewModel()
    }*/
}