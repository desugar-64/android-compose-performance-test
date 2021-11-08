package com.sergey_y.simpletweets.data

import com.sergey_y.simpletweets.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

object ApiClient {
    fun getTweets(): Flow<List<Tweet>> {
        return flow {
            val value: List<Tweet> = withContext(Dispatchers.IO) {
                App.tweets
            }
            emit(value)
        }
    }
}