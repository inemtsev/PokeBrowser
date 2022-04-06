package com.example.pokeBrowser.pokeClient

import android.content.Context
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import retrofit2.Response as RetrofitResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val CACHE_CONTROL_HEADER = "Cache-Control"
private const val CACHE_CONTROL_NO_CACHE = "no-cache"

class PokeClientImpl(context: Context) : PokeClient {
    private val pokeClientService: PokeClientService
    private val cacheSize = 50 * 1024 * 1024L    // 50 MB

    init {
        val cache = Cache(context.cacheDir, cacheSize)
        val okHttpClient = buildOkHttp(cache)

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

        pokeClientService = retrofit.create(PokeClientService::class.java)
    }

    private fun buildOkHttp(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(CacheInterceptor())
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .followRedirects(true)
            .build()
    }

    private fun <T : Any?> logCacheHits(response: RetrofitResponse<T>) {
        if (response.raw().cacheResponse() != null) {
            Log.e("Network", "response came from cache");
        }

        if (response.raw().networkResponse() != null) {
            Log.e("Network", "response came from server");
        }
    }

    override suspend fun getPokemonList(number: Int, offset: Int): GetPokemonListResponse? {
        val result = pokeClientService.getPokemonRange(number, offset)

        //logCacheHits(result)

        if(result.isSuccessful) {
            return result.body()
        }

        return null
    }

    override suspend fun getPokemonData(pokemonUrl: String): GetPokemonDataResponse? {
        val requestUrl = pokemonUrl.replace("https://pokeapi.co/api/v2/pokemon/", "")
        val result = pokeClientService.getPokemonData(requestUrl)

        //logCacheHits(result)

        if(result.isSuccessful) {
            return result.body()
        }

        return null
    }
}

interface PokeClientService {
    @GET("pokemon")
    suspend fun getPokemonRange(
        @Query("limit") limit: Int,
        @Query("offSet") offset: Int
    ): RetrofitResponse<GetPokemonListResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemonData(
        @Path("id") id: String
    ): RetrofitResponse<GetPokemonDataResponse>
}

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse = chain.proceed(request)

        val shouldUseCache = request.header(CACHE_CONTROL_HEADER) != CACHE_CONTROL_NO_CACHE
        if (!shouldUseCache) return originalResponse

        val cacheControl = CacheControl.Builder()
            .maxAge(30, TimeUnit.DAYS)
            .build()

        return originalResponse.newBuilder()
            .header(CACHE_CONTROL_HEADER, cacheControl.toString())
            .build()
    }
}

