package com.example.callacabclone

import android.app.Application
import android.util.Log
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.parse.Parse
import com.parse.ParseACL
import com.parse.ParseObject
import com.parse.ParseUser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ParseSetup: Application() {

    override fun onCreate() {
        super.onCreate()

        //bitnami password 2z4zc49SD0Pt
        //appId: "df510d952d8b4bc49dbef8dcf86380aa51117362",
        //masterKey: "551edc3f932c7e41cd8fbb03f6b9f1379bbb42dc",
        //serverURL: "http://18.219.114.244:80/parse",

        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG)

        // Use for monitoring Parse OkHttp traffic
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
        val builder = OkHttpClient.Builder()
        //if(BuildConfig) {
        builder.addInterceptor(OkHttpProfilerInterceptor())
        //}
        //val client = builder.build()


        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.networkInterceptors().add(httpLoggingInterceptor)
//        val client = builder.build()
//        val client = Parse.Configuration.Builder( builder).build()

        Parse.initialize(Parse.Configuration.Builder (this)
            .applicationId("df510d952d8b4bc49dbef8dcf86380aa51117362")
            .clientKey("551edc3f932c7e41cd8fbb03f6b9f1379bbb42dc")
            .clientBuilder(builder)
            .server("http://18.219.114.244:80/parse/")
            .build()
        )

        val nameValueObject = ParseObject("NameValue")
        nameValueObject.put("name", "vish")
        nameValueObject.put("value", "9001")
        nameValueObject.put("etc","random field")

        nameValueObject.saveInBackground {
            Log.d("DEBUG", "Object saved. Id: " + nameValueObject.objectId)
        }

        ParseUser.enableAutomaticUser()
        val defaultACL = ParseACL()
        defaultACL.publicReadAccess = true // .setPublicReadAccess(true)
        defaultACL.publicWriteAccess = true // setPublicWriteAccess(true)
        ParseACL.setDefaultACL(defaultACL, true)

    }
}