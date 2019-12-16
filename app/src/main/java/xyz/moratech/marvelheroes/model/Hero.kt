package xyz.moratech.marvelheroes.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Hero : Serializable {
    @SerializedName("name")
    var name: String = ""
    @SerializedName("photo")
    var photo: String = ""
    @SerializedName("realName")
    var realName: String = ""
    @SerializedName("height")
    var height: String = ""
    @SerializedName("power")
    var power: String = ""
    @SerializedName("abilities")
    var abilities: String = ""
    @SerializedName("groups")
    var groups: String = ""
}
