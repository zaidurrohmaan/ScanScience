package com.example.scanscience.data.response

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class FactResponse(
    @field:SerializedName("success")
    val success: Boolean,
    @field:SerializedName("data")
    val data: ItemFactResponse,
)

data class ItemFactResponse(
    @field:SerializedName("idobjek")
    val idobjek: String,
    @field:SerializedName("nama")
    val nama: String,
    @field:SerializedName("definisi")
    val definisi: String,
    @field:SerializedName("referensi")
    val referensi: String,
    @field:SerializedName("jenis")
    val jenis: String,
    @field:SerializedName("deskripsi_umum")
    val deskripsi_umum: List<ItemDeskripsiUmum>,
    @field:SerializedName("fakta")
    val fakta: List<ItemFakta>
)

data class ItemDeskripsiUmum(
    @field:SerializedName("iddeskripsi")
    val iddeskripsi: String,
    @field:SerializedName("judul")
    val judul: String,
    @field:SerializedName("isi")
    val isi: String,
    @field:SerializedName("createdAt")
    val createdAt: String,
    @field:SerializedName("updatedAt")
    val updatedAt: String,
)

data class ItemFakta(
    @field:SerializedName("idfakta")
    val idfakta: String,
    @field:SerializedName("judul")
    val judul: String,
    @field:SerializedName("isi")
    val isi: String,
    @field:SerializedName("createdAt")
    val createdAt: String,
    @field:SerializedName("updatedAt")
    val updatedAt: String,
)
