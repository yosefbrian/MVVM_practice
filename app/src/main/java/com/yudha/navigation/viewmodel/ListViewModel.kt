package com.yudha.navigation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yudha.navigation.di.AppModule
import com.yudha.navigation.di.CONTEXT_APP
import com.yudha.navigation.di.DaggerViewModelComponent
import com.yudha.navigation.di.TypeOfContext
import com.yudha.navigation.model.Animal
import com.yudha.navigation.model.AnimalApiService
import com.yudha.navigation.model.ApiKey
import com.yudha.navigation.utils.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by yudha on 20,August,2019
 */
class ListViewModel(application: Application) : AndroidViewModel(application) {
    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }


    private val disposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var apiService: AnimalApiService

    @Inject
    @field:TypeOfContext(CONTEXT_APP    )
    lateinit var prefs: SharedPreferencesHelper
    private var invalidApiKey = false

    init {
        DaggerViewModelComponent.
            builder()
            .appModule(AppModule(getApplication()))
            .build()
            .inject(this)
    }

    fun refresh() {
        val key = prefs.getApiKey()
        invalidApiKey = false
        if(key.isNullOrEmpty())
            getKey()
        else
            getAnimals(key)
    }

    fun hardRefresh(){
        loading.value = true
        getKey()
    }

    private fun getKey() {
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(key: ApiKey) {
                        if (key.key.isNullOrEmpty()) {
                            loadError.value = true
                            loading.value = false
                        } else {
                            prefs.saveApiKey(key.key)
                            getAnimals(key.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (!invalidApiKey){
                            invalidApiKey = true
                            getKey()
                        }
                        else{
                        e.printStackTrace()
                        loading.value = false
                        loadError.value = false
                    }
                    }
                }
                )
        )
    }


    private fun getAnimals(key: String) {
        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Animal>>() {
                    override fun onSuccess(list: List<Animal>) {
                        loadError.value = false
                        loading.value = false
                        animals.value = list
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        animals.value = null
                        loadError.value = false
                    }
                }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}