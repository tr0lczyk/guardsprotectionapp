package com.example.guardsprotectionapp.network

import android.util.Log
import com.example.guardsprotectionapp.models.LoginModel
import com.example.guardsprotectionapp.models.LoginResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


private const val BASE_URL = "https://gssmanagementapi.softwarespace.eu/api/"
private const val POST_LOGIN_URL = "Account/Login"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface GuardAPiService {

//    @Headers("Content-Type: application/json-patch+json", "Accept: application/json")
    @POST(POST_LOGIN_URL)
    suspend fun postLogin(@Body login: LoginModel): LoginResponse

}

object GuardApi {
    val retrofitService: GuardAPiService by lazy {
        Log.d("WebAccess", "Creating retrofit client")

        val logging = HttpLoggingInterceptor()
        logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .build()

        return@lazy retrofit.create(GuardAPiService::class.java)
    }
}