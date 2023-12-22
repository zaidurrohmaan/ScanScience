package com.example.scanscience.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.scanscience.data.Repository
import kotlinx.coroutines.launch

class ProfileViewModel (private val repository: Repository) : ViewModel() {

    val session: LiveData<Boolean> = repository.getUserSession().asLiveData()
    fun saveUserToken(token: String) {
        viewModelScope.launch {
            repository.saveUserToken(token)
        }
    }

    fun saveUserSession(isLogin: Boolean) {
        viewModelScope.launch {
            repository.saveUserSession(isLogin)
            if(!isLogin) repository.saveUserToken("")
        }
    }
}