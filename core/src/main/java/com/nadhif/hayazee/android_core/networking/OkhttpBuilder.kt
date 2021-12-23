package com.nadhif.hayazee.android_core.networking

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkhttpBuilder {

    class Builder {
        private var authenticator: Authenticator? = null
        private var interceptors: MutableList<Interceptor> = arrayListOf()
        private var readTimeout: Long = 15
        private var readTimeUnit = TimeUnit.SECONDS

        private var writeTimeout: Long = 15
        private var writeTimeUnit = TimeUnit.SECONDS

        private var connectTimeout: Long = 15
        private var connectTimeUnit = TimeUnit.SECONDS

        private var callTimeout: Long = 15
        private var callTimeUnit = TimeUnit.SECONDS

        init {
            interceptors.add(loggingInterceptor())
            interceptors.add(providesHeaderHolderInterceptor())
        }

        fun addInterceptor(interceptor: Interceptor): Builder {
            interceptors += interceptor
            return this
        }

        fun readTimeout(timeout: Long, timeUnit: TimeUnit): Builder {
            readTimeout = timeout
            readTimeUnit = timeUnit
            return this
        }

        fun writeTimeout(timeout: Long, timeUnit: TimeUnit): Builder {
            writeTimeout = timeout
            writeTimeUnit = timeUnit
            return this
        }


        fun connectTimeout(timeout: Long, timeUnit: TimeUnit): Builder {
            connectTimeout = timeout
            connectTimeUnit = timeUnit
            return this
        }


        fun callTimeout(timeout: Long, timeUnit: TimeUnit): Builder {
            callTimeout = timeout
            callTimeUnit = timeUnit
            return this
        }

        fun build(): OkHttpClient {
            val client = OkHttpClient().newBuilder()
                .readTimeout(readTimeout, readTimeUnit)
                .writeTimeout(writeTimeout, writeTimeUnit)
                .connectTimeout(connectTimeout, connectTimeUnit)
                .callTimeout(callTimeout, callTimeUnit)
            if (authenticator != null) client.authenticator(authenticator!!)
            interceptors.forEach {
                client.addInterceptor(it)
            }
            return client.build()
        }

        private fun loggingInterceptor(): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            return logging
        }

        private fun providesHeaderHolderInterceptor(): Interceptor {
            return Interceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
        }

    }
}