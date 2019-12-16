package xyz.moratech.marvelheroes.view.activities

import android.graphics.Typeface
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_hero_details.*
import xyz.moratech.marvelheroes.R
import xyz.moratech.marvelheroes.base.view.BaseActivity
import xyz.moratech.marvelheroes.model.Hero
import xyz.moratech.marvelheroes.view.fragments.HeroFragment.Companion.EXTRA_HERO

class HeroDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_details)

        val hero = intent.getSerializableExtra(EXTRA_HERO) as Hero

        Glide.with(this)
            .load(hero.photo)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .centerInside()
            .into(act_details_photo)

        act_details_name.text = hero.name
        act_details_real_name.text = hero.realName
        act_details_height.text = hero.height
        act_details_power.text = hero.power
        act_details_abilities.text = hero.abilities
        act_details_groups.text = hero.groups

        setTypefaceFonts()
    }

    private fun setTypefaceFonts() {
        val typeface = Typeface.createFromAsset(assets, "fonts/marvel.ttf")
        act_details_name.typeface = typeface
        act_details_real_name_holder.typeface = typeface
        act_details_real_name.typeface = typeface
        act_details_height_holder.typeface = typeface
        act_details_height.typeface = typeface
        act_details_power_holder.typeface = typeface
        act_details_power.typeface = typeface
        act_details_abilities_holder.typeface = typeface
        act_details_abilities.typeface = typeface
        act_details_groups_holder.typeface = typeface
        act_details_groups.typeface = typeface
    }

}
