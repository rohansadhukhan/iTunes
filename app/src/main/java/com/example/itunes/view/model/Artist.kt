package com.example.itunes.view.model

import com.example.itunes.view.model.Result
import com.google.gson.annotations.SerializedName

data class Artist (

    @SerializedName("resultCount")
    val resultCount: Int,

    @SerializedName("results")
    val result: List<Result>

)
