package com.example.pokebrowser.pokeClient

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class PokeClient {
    private val pokeClientService: PokeClientService

    init {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        pokeClientService = retrofit.create(PokeClientService::class.java)
    }


    suspend fun getPokemonList(number: Int, offset: Int): GetPokemonListResponse? {
        val result = pokeClientService.getPokemonRange(number, offset)

        if(result.isSuccessful) {
            return result.body()
        }

        return null
    }

    suspend fun getPokemonData(pokemonUrl: String): GetPokemonDataResponse? {
        val result = pokeClientService.getPokemonData(
            pokemonUrl.replace("https://pokeapi.co/api/v2/pokemon/", "")
        )

        if(result.isSuccessful) {
            return result.body()
        }

        return null
    }
}

interface PokeClientService {
    @GET("pokemon?limit={Limit}&offset={Offset}")
    suspend fun getPokemonRange(
        @Query("Limit") limit: Int,
        @Query("OffSet") offset: Int
    ): Response<GetPokemonListResponse>

    @GET("pokemon/{Id}")
    suspend fun getPokemonData(
        @Path("Id") id: String
    ): Response<GetPokemonDataResponse>
}
