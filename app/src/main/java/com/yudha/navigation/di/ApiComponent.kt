package com.yudha.navigation.di

import com.yudha.navigation.model.AnimalApiService
import dagger.Component
import javax.inject.Singleton

/**
 * Created by yudha on 27,August,2019
 */

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: AnimalApiService)
}