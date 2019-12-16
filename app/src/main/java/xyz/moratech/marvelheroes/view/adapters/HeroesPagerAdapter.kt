package xyz.moratech.marvelheroes.view.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import xyz.moratech.marvelheroes.model.Hero
import xyz.moratech.marvelheroes.view.fragments.HeroFragment

class HeroesPagerAdapter(val heroesList: List<Hero>, fm: FragmentManager, behavior: Int) :
            FragmentPagerAdapter(fm, behavior) {

    private val fragmentList = ArrayList<HeroFragment> ()

    init {
        for (hero in heroesList) {
            val args = Bundle()
            args.putSerializable(HeroFragment.EXTRA_HERO, hero)

            val fragment = HeroFragment()
            fragment.arguments = args

            fragmentList.add(fragment)
        }
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return heroesList.size
    }

}