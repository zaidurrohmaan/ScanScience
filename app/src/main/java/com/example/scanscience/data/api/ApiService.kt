package com.example.scanscience.data.api

import com.example.scanscience.data.response.FactResponse
import com.example.scanscience.data.response.LoginResponse
import com.example.scanscience.data.response.RegisterResponse
import com.example.scanscience.data.response.SaveResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File

interface ApiService {
    @FormUrlEncoded
    @POST("/user/register")
    suspend fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ) : Response<RegisterResponse>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<LoginResponse>


    @GET("/objek/fakta/{objek}")
    suspend fun getFact(
        @Header("Authorization") token: String,
        @Path("objek") objek: String
    ) : Response<FactResponse>

//    @Multipart
//    @POST("/gambar/")
//    suspend fun saveImage(
//        @Header("Authorization") token: String,
//        @Part file: MultipartBody.Part,
//        @Part("nama") nama: RequestBody,
//        @Part("deskripsi") deskripsi: RequestBody,
//    ): Response<SaveResponse>


//    @FormUrlEncoded
//    @POST("/gambar")
//    suspend fun saveImage(
//        @Header("Authorization") token: String,
//        @Field("file") file: File,
//        @Field("nama") nama: String,
//        @Field("deskripsi") deskripsi: String,
//    ): Response<SaveResponse>

    @Multipart
    @POST("/gambar")
    suspend fun saveImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("nama") nama: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
    ): Response<SaveResponse>
}