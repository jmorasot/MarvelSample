package xyz.moratech.marvelheroes.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.DecelerateInterpolator
import kotlinx.android.synthetic.main.activity_splash.*
import xyz.moratech.marvelheroes.R
import xyz.moratech.marvelheroes.base.view.BaseActivity


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)

        act_splash_logo.animate().apply {
            alpha(1F)
            duration = 1000
            interpolator = DecelerateInterpolator()
        }.setStartDelay(300L).start()
    }
}