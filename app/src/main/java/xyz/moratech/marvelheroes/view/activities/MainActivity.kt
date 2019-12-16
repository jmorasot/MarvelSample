package xyz.moratech.marvelheroes.view.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import xyz.moratech.marvelheroes.R
import xyz.moratech.marvelheroes.base.view.BaseActivity
import xyz.moratech.marvelheroes.model.Hero
import xyz.moratech.marvelheroes.presenter.MainPresenter
import xyz.moratech.marvelheroes.view.adapters.HeroesPagerAdapter
import xyz.moratech.marvelheroes.view.fragments.HeroFragment.Companion.EXTRA_HERO

/**
 * @author Juan Mora
 * 11/12/2019 05:55 PM
 *
 */

class MainActivity : BaseActivity() {

    private val mMainPresenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initObservers()

        act_main_dots.setupWithViewPager(act_main_pager, true)
        setTypefaceFonts()

        mMainPresenter.requestHeroesData()
    }

    private fun initObservers() {
        mMainPresenter.getSuccess()
            .observe(this, Observer {
                showMessage(act_main_progress, "Se encontraron ${it.size} héroes")
                act_main_progress.visibility = View.GONE
                act_main_dots.visibility = View.VISIBLE
                act_main_pager.visibility = View.VISIBLE

                val adapter = HeroesPagerAdapter(it, supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
                act_main_pager.adapter = adapter
            })

        mMainPresenter.getError()
            .observe(this, Observer {
                act_main_progress.visibility = View.GONE
                showMessageWithAction(act_main_progress, it, "Reintentar", object : SnackBarDismissListener {
                    override fun onDismiss() {
                        mMainPresenter.requestHeroesData()
                        act_main_progress.visibility = View.VISIBLE
                    }
                })
            })
    }

    private fun setTypefaceFonts() {
        val typeface = Typeface.createFromAsset(assets, "fonts/marvel.ttf")
        act_main_welcome.typeface = typeface
        act_main_choose.typeface = typeface
    }

    fun onHeroSelected(hero: Hero) {
        val intent = Intent(this, HeroDetailsActivity::class.java)
        intent.putExtra(EXTRA_HERO, hero)
        startActivity(intent)
    }
}
