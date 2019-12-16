package xyz.moratech.marvelheroes.presenter

import androidx.lifecycle.MutableLiveData
import xyz.moratech.marvelheroes.base.presenter.BasePresenter
import xyz.moratech.marvelheroes.model.Hero
import xyz.moratech.marvelheroes.model.responses.HeroesResponse
import java.io.Serializable

class MainPresenter : BasePresenter() {
    private val mSuccessLiveData = MutableLiveData<List<Hero>>()

    fun requestHeroesData() {
        val call = mServicesAPI.getHeroesList()
        val callback = object : SuccessServiceListener {
            override fun <R : Serializable> onSuccessResponse(body: R) {
                val heroesResponse = body as HeroesResponse

                if (heroesResponse.heroesList.isNotEmpty()) {
                    mSuccessLiveData.postValue(heroesResponse.heroesList)
                } else {
                    mNotifyLiveData.postValue("Error al obtener la lista de héroes")
                }
            }
        }

        requestWS(HeroesResponse::class.java, call, callback)
    }

    fun getSuccess() : MutableLiveData<List<Hero>> {
        return mSuccessLiveData
    }
}