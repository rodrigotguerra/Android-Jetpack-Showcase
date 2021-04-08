package com.rodrigotguerra.dogsapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigotguerra.dogsapp.model.DogBreed
import com.rodrigotguerra.dogsapp.model.DogDatabase
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {
    val dogLiveData = MutableLiveData<DogBreed>()

    fun fetch(dogUuid: Int) {
       launch {
           val dog = DogDatabase(getApplication()).dogDao().getDog(dogUuid)
           dogLiveData.value = dog
       }
    }

}