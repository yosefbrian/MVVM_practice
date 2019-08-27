package com.yudha.navigation.di

import android.app.Application
import dagger.Module
import dagger.Provides

/**
 * Created by yudha on 27,August,2019
 */
@Module
class AppModule( val app: Application) {
    @Provides
    fun providesApp(): Application = app
}