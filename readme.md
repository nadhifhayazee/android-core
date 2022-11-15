# android-core
Android library for core module that was made to simplify developer to make [Retrofit](https://square.github.io/retrofit/) client object, and secured shared preferences util, and view extensions.

## Features
* Retrofit client builder with default interceptor and can be customized later You can see retrofit
* Encrypted Shared preferences util.
* View extension for visible, invisible, and gone.

## How to Install

Make sure your minSdk is **23**

Add it in your project's build.gradle at the end of repositories:

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
 
```

Add the dependency

```
dependencies {
    implementation 'com.github.nadhifhayazee:android-core:1.3.0'
}

```

## How to use Retrofit builder

**Step 1** Make the Okhttp client

```
fun provideOkhtp(): OkHttpClient {
     return OkhttpBuilder.Builder().build()
}

```

that function will return okhttp client with default logging interceptor level body, timeout 15 seconds, and header interceptors of

```
"Accept", "application/json"
"Content-Type", "application/json"
```

You can also add your own interceptors or change the timeout value like this

```
 return OkhttpBuilder.Builder()
      .addInterceptor(ChuckInterceptor())
      .addInterceptor(NetworkInterceptor())
      .readTimeout(30, TimeUnit.SECONDS)
      .writeTimeout(30, TimeUnit.SECONDS)
      .callTimeout(30, TimeUnit.SECONDS)
      .connectTimeout(30, TimeUnit.SECONDS)
      .build()

```

**Step 2** Make the Retrofit client instance

you can make retrofit client instance by calling RetrofitBuilder.create() function

```
 fun retrofitClient(): SampleService {
        return RetrofitBuilder.create("https://www.themealdb.com/api/json/v1/1/", provideOkhtp())
            .create(SampleService::class.java)
    }

```



## How to use Secured Shared Preferences

**Step 1** Create sharepreferencesutil instance

```
val sharedPrefUtil = SharedPreferenceUtil(applicationContext)
```

**Step 2** Save generic object
```
//        save string
        sharedPrefUtil.saveObject("MOVIE_KEY", "MOVIE")
//        save integer
        sharedPrefUtil.saveObject("MOVIE_KEY", 212)
//        save float
        sharedPrefUtil.saveObject("MOVIE_KEY", 3.14)
//        save long
        sharedPrefUtil.saveObject("MOVIE_KEY", 3131L)
//        save boolean
        sharedPrefUtil.saveObject("MOVIE_KEY",false)
//        save object
        sharedPrefUtil.saveObject("MOVIE_KEY", MovieModel("ID001", "Batman The Movie"))

```

**Step 3** Get generic object
```
//       get string
        val strValue = sharedPrefUtil.getObject("MOVIE_KEY", String::class.java)
        Log.d("title", "$strValue")
        
//       get integer value
        val intValue = sharedPrefUtil.getObject("MOVIE_KEY", Int::class.java)
        Log.d("title", "$intValue")
       
//        get float value
        val fValue = sharedPrefUtil.getObject("MOVIE_KEY", Float::class.java)
        Log.d("title", "$fValue")
       
//        get long value
        val lValue = sharedPrefUtil.getObject("MOVIE_KEY", Long::class.java)
        Log.d("title", "$lValue")
        
//        get boolean value
        val boolValue = sharedPrefUtil.getObject("MOVIE_KEY", Boolean::class.java)
        Log.d("title","$boolValue")
        
//        get data object value
        val movieModel = sharedPrefUtil.getObject("MOVIE_KEY", MovieModel::class.java)
        Log.d("title", movieModel?.title ?: "")
```

## How to use View Extensions
for now view extenstions are only to handle view visibility like visible, gone, and invisible. Will be updated later.

```
  val textView = findViewById<TextView>(R.id.tvTest)
  
  textView.gone()
  
  textView.visible()

  textView.invisible()

```

