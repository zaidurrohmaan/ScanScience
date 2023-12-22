package com.example.scanscience.data

sealed class Result<out T> {
    data class Success<out T>(val data: T? = null) : Result<T>()
    data class Loading(val nothing: Nothing? = null) : Result<Nothing>()
    data class Error(val msg: String?) : Result<Nothing>()
}