package com.example.scanscience.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.scanscience.data.Repository
import com.example.scanscience.data.Result
import com.example.scanscience.data.response.SaveResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ResultViewModel (private val repository: Repository) : ViewModel() {
    val saveImageResult: MutableLiveData<Result<SaveResponse>> = MutableLiveData()
    val token: LiveData<String> = repository.getUserToken().asLiveData()

//    fun saveImage(token: String, multipartBody: MultipartBody.Part, requestBody1: RequestBody, requestBody2: RequestBody) {
//        viewModelScope.launch {
//            try {
//                val response = repository.saveImage(token, multipartBody, requestBody1, requestBody2)
//                if (response.isSuccessful) {
//                    saveImageResult.postValue(Result.Success(response.body()))
//                } else {
//                    saveImageResult.postValue(Result.Error(response.body()?.message))
//                }
//            } catch (ex: Exception) {
//                saveImageResult.value = Result.Error(ex.message)
//            }
//        }
//    }

//    fun saveImage(token: String, file: File, nama: String, deskripsi: String) {
//        viewModelScope.launch {
//            try {
//                val response = repository.saveImage(token, file, nama, deskripsi)
//                if (response.isSuccessful) {
//                    saveImageResult.postValue(Result.Success(response.body()))
//                } else {
//                    saveImageResult.postValue(Result.Error(response.body()?.message))
//                }
//            } catch (ex: Exception) {
//                saveImageResult.value = Result.Error(ex.message)
//            }
//        }
//    }

    fun saveImage(token: String, file: MultipartBody.Part, nama: RequestBody, deskripsi: RequestBody) {
        viewModelScope.launch {
            try {
                val response = repository.saveImage(token, file, nama, deskripsi)
                if (response.isSuccessful) {
                    saveImageResult.postValue(Result.Success(response.body()))
                } else {
                    saveImageResult.postValue(Result.Error(response.body()?.message))
                }
            } catch (ex: Exception) {
                saveImageResult.value = Result.Error(ex.message)
            }
        }
    }
}