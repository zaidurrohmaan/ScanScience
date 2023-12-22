package com.example.scanscience.data.dummy

data class Post(
    val userName: String,
    val objectCount: Int,
    val photoUrl: String,
    val description: String,
    val location: String,
    val date: String,
    val objects: ArrayList<String>
)
