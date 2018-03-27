package tw.jacklai.pixabay.model.api

import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import tw.jacklai.pixabay.model.Response
import java.util.concurrent.TimeUnit

/**
 * Created by jacklai on 27/03/2018.
 */

interface PixabayService {
    @GET("./")
    fun search(@Query("key") key: String, @Query("q") query: String): Observable<Response>

    companion object {
        fun create(): PixabayService {
            val client = OkHttpClient.Builder()
                    .readTimeout(8, TimeUnit.SECONDS)
                    .connectTimeout(8, TimeUnit.SECONDS)
                    .build()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl("https://pixabay.com/api/")
                    .build()

            return retrofit.create(PixabayService::class.java)
        }
    }
}