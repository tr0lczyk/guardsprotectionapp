package com.example.guardsprotectionapp.network

import android.util.Log
import com.example.guardsprotectionapp.models.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://gssmanagementapi.softwarespace.eu/api/"
private const val POST_LOGIN_URL = "jwt/token"
private const val GET_JOB_OFFERS = "JobOffers"
private const val POST_UPDATE_ACCEPTANCE = "JobOffers/SetEmployeeAcceptance"
private const val POST_USER_DATA = "Employees/UpdateFirebaseMobileAppToken"
private const val GET_USER_DATA = "Employees/{id}"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface GuardAPiService {

    @POST(POST_LOGIN_URL)
    suspend fun postLogin(@Body login: LoginModel): Response<LoginResponse>

    @GET(GET_JOB_OFFERS)
    suspend fun getJobOffers(): Response<List<OfferModel>>

    @POST(POST_UPDATE_ACCEPTANCE)
    suspend fun postEmployeeAcceptance(
        @Body employee: EmployeeStatusModel
    ): Response<Boolean>

    @GET(GET_USER_DATA)
    suspend fun getUserData(@Path("id") id: Long): Response<UserModel>

    @POST(POST_USER_DATA)
    suspend fun postUserData(@Body userData: UserModel): Response<Boolean>
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