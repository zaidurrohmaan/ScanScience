package com.example.scanscience.data

import com.example.scanscience.data.api.ApiService
import com.example.scanscience.data.response.FactResponse
import com.example.scanscience.data.response.LoginResponse
import com.example.scanscience.data.response.RegisterResponse
import com.example.scanscience.data.response.SaveResponse
import com.example.scanscience.utils.UserPreferences
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File

class Repository private constructor(
    private val apiService: ApiService,
    private val pref: UserPreferences
) {

    suspend fun registerUser (name: String, email: String, password: String) : Response<RegisterResponse> {
        return apiService.userRegister(name, email, password)
    }

    suspend fun loginUser(email: String, password: String): Response<LoginResponse> {
        return apiService.userLogin(email, password)
    }

    fun getUserToken(): Flow<String> {
        return pref.getUserToken()
    }

    suspend fun saveUserToken(token: String) {
        pref.saveUserToken(token)
    }

    fun getUserSession(): Flow<Boolean> {
        return pref.getUserSession()
    }

    suspend fun saveUserSession(isLogin: Boolean) {
        pref.saveUserSession(isLogin)
    }

//    suspend fun saveImage(
//        token: String,
//        multipartBody: MultipartBody.Part,
//        requestBody1: RequestBody,
//        requestBody2: RequestBody
//    ): Response<SaveResponse> {
//        return apiService.saveImage("Bearer $token", multipartBody, requestBody1, requestBody2)
//    }

//    suspend fun saveImage(
//        token: String,
//        file: File,
//        nama: String,
//        deskripsi: String
//    ): Response<SaveResponse> {
//        return apiService.saveImage("Bearer $token", file, nama, deskripsi)
//    }

    suspend fun saveImage(
        token: String,
        file: MultipartBody.Part,
        nama: RequestBody,
        deskripsi: RequestBody
    ): Response<SaveResponse> {
        return apiService.saveImage("Bearer $token", file, nama, deskripsi)
    }

    suspend fun getFact(
        token: String,
        objek: String
    ): Response<FactResponse> {
        return apiService.getFact("Bearer $token", objek)
    }
    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences,
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(apiService, pref)
        }.also { instance = it }
    }
}