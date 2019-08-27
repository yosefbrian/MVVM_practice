package com.yudha.navigation.di

import com.yudha.navigation.viewmodel.ListViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by yudha on 27,August,2019
 */

@Singleton
@Component(modules = [ApiModule::class, PrefsModule::class, AppModule::class])
interface ViewModelComponent {
    fun inject(viewModel: ListViewModel )
}