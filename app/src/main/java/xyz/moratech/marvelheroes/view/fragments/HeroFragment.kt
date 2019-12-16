package xyz.moratech.marvelheroes.view.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_hero.*
import xyz.moratech.marvelheroes.R
import xyz.moratech.marvelheroes.model.Hero
import xyz.moratech.marvelheroes.view.activities.MainActivity

class HeroFragment : Fragment() {

    companion object {
        const val EXTRA_HERO = "extraHero"
    }

    private lateinit var hero: Hero

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        hero = arguments?.get(EXTRA_HERO) as Hero
        return inflater.inflate(R.layout.fragment_hero, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(view.context)
            .load(hero.photo)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(fr_hero_photo)


        fr_hero_name.text = hero.name
        fr_hero_real_name.text = hero.realName

        setTypefaceFonts()
        setListener()
    }

    private fun setListener() {
        fr_hero_photo.setOnClickListener {
            // This can be replaced with a shared view model, but MVP is required in this test
            if (isAdded) {
                ((requireActivity()) as MainActivity).onHeroSelected(hero)
            }
        }
    }

    private fun setTypefaceFonts() {
        val typeface = Typeface.createFromAsset(requireActivity().assets, "fonts/marvel.ttf")
        fr_hero_name.typeface = typeface
        fr_hero_real_name.typeface = typeface
    }
}