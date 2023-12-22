package com.example.scanscience.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.scanscience.data.Repository
import com.example.scanscience.data.Result
import com.example.scanscience.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {
    val loginResult: MutableLiveData<Result<LoginResponse>> = MutableLiveData()
    val token: LiveData<String?> = repository.getUserToken().asLiveData()
    val session: LiveData<Boolean> = repository.getUserSession().asLiveData()

    fun loginUser(email: String, password: String) {
        loginResult.postValue(Result.Loading())
        viewModelScope.launch {
            try {
                val response = repository.loginUser(email, password)
                if (response.isSuccessful) {
                    loginResult.postValue(Result.Success(response.body()))
                } else {
                    loginResult.postValue(Result.Error(response.body()?.message))
                }
            }
            catch (ex: Exception) {
                loginResult.value = Result.Error(ex.message)
            }
        }
    }

    fun saveUserToken(token: String) {
        viewModelScope.launch {
            repository.saveUserToken(token)
        }
    }

    fun saveUserSession(isLogin: Boolean) {
        viewModelScope.launch {
            repository.saveUserSession(isLogin)
        }
    }
}