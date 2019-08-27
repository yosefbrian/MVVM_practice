package com.yudha.navigation.model

import com.yudha.navigation.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by yudha on 21,August,2019
 */
class AnimalApiService {
    @Inject
    lateinit var api: AnimalApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getApiKey(): Single<ApiKey>{
        return api.getApiKey()
    }

    fun getAnimals(key: String): Single<List<Animal>> {
        return api.getAnimals(key)
    }
}