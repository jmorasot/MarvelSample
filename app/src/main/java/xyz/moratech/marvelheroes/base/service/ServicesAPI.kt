package xyz.moratech.marvelheroes.base.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ServicesAPI {

    @GET("bins/bvyob/")
    fun getHeroesList(): Call<ResponseBody>
}