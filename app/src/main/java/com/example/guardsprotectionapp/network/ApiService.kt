package com.example.guardsprotectionapp.network

import android.util.Log
import com.example.guardsprotectionapp.models.EmployeeStatusModel
import com.example.guardsprotectionapp.models.LoginModel
import com.example.guardsprotectionapp.models.LoginResponse
import com.example.guardsprotectionapp.models.OfferModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private const val BASE_URL = "https://gssmanagementapi.softwarespace.eu/api/"
private const val POST_LOGIN_URL = "jwt/token"
private const val GET_JOB_OFFERS = "JobOffers"
private const val POST_UPDATE_ACCEPTANCE = "JobOffers/UpdateAcceptance/{jobOfferId}"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface GuardAPiService {

    @POST(POST_LOGIN_URL)
    suspend fun postLogin(@Body login: LoginModel): Response<LoginResponse>

    @GET(GET_JOB_OFFERS)
    suspend fun getJobOffers(): Response<List<OfferModel>>

    @POST(POST_UPDATE_ACCEPTANCE)
    suspend fun postUpdateAcceptance(
        @Body employee: EmployeeStatusModel,
        @Path("jobOfferId") offerId: Long
    ): Response<Boolean>
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