package com.neurosky.zonetrainer.data.remote.api

import com.neurosky.zonetrainer.data.remote.model.GraphDataResponse
import com.neurosky.zonetrainer.data.remote.model.NeuroRequest
import com.neurosky.zonetrainer.data.remote.model.RecentDataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NeuroService {

    @POST("home")
    suspend fun postNeuroData(@Body body: NeuroRequest)
    
    @GET("home/recentData/{userId}")
    suspend fun getRecentData(@Path("userId") userId: String): RecentDataResponse

    @GET("home/graphData/{userId}")
    suspend fun getGraphData(@Path("userId") userId: String): GraphDataResponse
}
