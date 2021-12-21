package com.nadhif.hayazee.core_sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nadhif.hayazee.android_core.networking.OkhttpBuilder
import com.nadhif.hayazee.android_core.networking.RetrofitBuilder
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofitClient().getList().enqueue(object : Callback<JSONObject> {
            override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "${response.body()}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun provideOkhtp(): OkHttpClient {
        return OkhttpBuilder.Builder().build()
    }

    fun retrofitClient(): SampleService {
        return RetrofitBuilder.create("https://www.themealdb.com/api/json/v1/1/", provideOkhtp())
            .create(SampleService::class.java)
    }
}