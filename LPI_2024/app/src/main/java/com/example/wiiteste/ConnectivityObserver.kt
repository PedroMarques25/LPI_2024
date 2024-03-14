package com.example.wiiteste
import kotlinx.coroutines.flow.Flow
import android.net.http.UrlRequest.Status

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status{
         Available,Unavailable,Losing,Lost
    }
}