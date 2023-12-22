package com.example.scanscience.ui.resultfact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.scanscience.data.Repository
import com.example.scanscience.data.Result
import com.example.scanscience.data.response.FactResponse
import com.example.scanscience.data.response.RegisterResponse
import kotlinx.coroutines.launch

class ResultFactViewModel (private val repository: Repository) : ViewModel() {

    val factResult: MutableLiveData<Result<FactResponse>> = MutableLiveData()
    val token: LiveData<String?> = repository.getUserToken().asLiveData()

    fun getFact(token: String, objek:String) {
        factResult.postValue(Result.Loading())
        viewModelScope.launch {
            try {
                val response = repository.getFact(token, objek)
                if (response.isSuccessful) {
                    factResult.postValue(Result.Success(response.body()))
                } else {
                    factResult.postValue(Result.Error(response.body().toString()))
                }
            } catch (ex: Exception) {
                factResult.value = Result.Error(ex.message)
            }
        }
    }
}