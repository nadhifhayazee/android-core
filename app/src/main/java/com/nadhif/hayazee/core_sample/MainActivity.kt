package com.nadhif.hayazee.core_sample

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nadhif.hayazee.android_core.extension.gone
import com.nadhif.hayazee.android_core.extension.invisible
import com.nadhif.hayazee.android_core.extension.visible
import com.nadhif.hayazee.android_core.networking.OkhttpBuilder
import com.nadhif.hayazee.android_core.networking.RetrofitBuilder
import com.nadhif.hayazee.android_core.preference.SharedPreferenceUtil
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefUtil = SharedPreferenceUtil(applicationContext)


//        save string
        sharedPrefUtil.saveObject("STR_KEY", "MOVIE")
//        save integer
        sharedPrefUtil.saveObject("INT_KEY", 212)
//        save float
        sharedPrefUtil.saveObject("FLOAT_KEY", 3.14F)
//        save long
        sharedPrefUtil.saveObject("LONG_KEY", 31131331L)
//        save boolean
        sharedPrefUtil.saveObject("BOOL_KEY", true)
//        save data object
        sharedPrefUtil.saveObject("MOVIE_KEY", MovieModel("ID001", "Batman The Movie"))

//       get string
        val strValue = sharedPrefUtil.getObject("STR_KEY", String::class.java)
        Log.d("title", "$strValue")

//       get integer value
        val intValue = sharedPrefUtil.getObject("INT_KEY", Int::class.java)
        Log.d("title", "$intValue")

//        get float value
        val fValue = sharedPrefUtil.getObject("FLOAT_KEY", Float::class.java)
        Log.d("title", "$fValue")

//        get long value
        val lValue = sharedPrefUtil.getObject("LONG_KEY", Long::class.java)
        Log.d("title", "$lValue")

//        get boolean value
        val boolValue = sharedPrefUtil.getObject("BOOL_KEY", Boolean::class.java)
        Log.d("title", "$boolValue")

//        get data object value
        val movieModel = sharedPrefUtil.getObject("MOVIE_KEY", MovieModel::class.java)
        Log.d("title", movieModel?.title ?: "")

        val textView = findViewById<TextView>(R.id.tvTest)
        textView.gone()

        textView.visible()

        textView.invisible()

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