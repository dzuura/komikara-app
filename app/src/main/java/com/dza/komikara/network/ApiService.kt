package com.dza.komikara.network

import com.dza.komikara.model.Komiks
import com.dza.retrofit.model.Users
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("user")
    fun getAllUsers(): Call<List<Users>>

    @GET("user/{id}")
    fun getUserById(@Path("id") komikId: String): Call<List<Users>>

    @POST("user")
    fun register(@Body user: Users): Call<Void>

    @POST("user")
    fun login(@Body user: Users): Call<Users>

    @GET("user")
    fun loginUserByEmail(@Query("email") email: String, @Query("password") password: String): Call<List<Users>>

    @GET("komik")
    fun getAllKomiks(): Call<List<Komiks>>

    @GET("komik/{id}")
    fun getKomiksById(@Path("id") id: String): Call<Komiks>

    @POST("komik")
    fun addKomiks(@Body komik: Komiks): Call<Void>

    @POST("komik/{id}")
    fun updateKomik(@Path("id") id: String, @Body komik: Komiks): Call<Void>

    @DELETE("komik/{id}")
    fun deleteKomik(@Path("id") id: String): Call<Void>
}