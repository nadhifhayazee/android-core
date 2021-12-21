package com.nadhif.hayazee.core_sample

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET

interface SampleService {
    @GET("list.php?c=list")
    fun getList(): Call<JSONObject>
}