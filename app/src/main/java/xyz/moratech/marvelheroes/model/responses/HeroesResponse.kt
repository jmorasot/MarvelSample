package xyz.moratech.marvelheroes.model.responses

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import xyz.moratech.marvelheroes.model.Hero
import java.io.Serializable

class HeroesResponse : Serializable {
    @SerializedName("superheroes")
    var heroesList = ArrayList<Hero>()
}