package com.sergey_y.simpletweets

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.sergey_y.simpletweets.data.Tweet
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ctx = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var ctx: Context

        @OptIn(ExperimentalSerializationApi::class)
        val tweets: List<Tweet> by lazy {
            val tweetsJson = ctx.assets.open("tweets.json").bufferedReader().readText()
            Json.decodeFromString<List<Tweet>>(tweetsJson)
        }
    }
}