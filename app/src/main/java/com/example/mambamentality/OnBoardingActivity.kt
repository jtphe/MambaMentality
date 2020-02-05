package com.example.mambamentality

import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.pixplicity.easyprefs.library.Prefs
import com.ramotion.paperonboarding.PaperOnboardingFragment
import com.ramotion.paperonboarding.PaperOnboardingPage
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_on_boarding.*


class OnBoardingActivity : AppCompatActivity() {

    companion object {
        const val HAS_LAUNCH_APP_ONCE = "hello"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Fresco.initialize(this)
        Realm.init(this)
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        setContentView(R.layout.activity_on_boarding)

        if (Prefs.contains(HAS_LAUNCH_APP_ONCE)) {
            startMain()
        } else {
            onBoarding()
        }
    }

    private fun startMain() {
        Prefs.putBoolean(HAS_LAUNCH_APP_ONCE, true)
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun onBoarding() {
        val firstPage = PaperOnboardingPage(
            "Search informations about NBA",
            "Everything you need to know is here",
            Color.parseColor("##9b59b6"),
            R.drawable.ic_search_black_96dp,
            R.drawable.ic_search_black_24dp
        )

        val secondPage = PaperOnboardingPage(
            "Get stats about your favorite team",
            "Rockets go",
            Color.parseColor("#f1c40f"),
            R.drawable.ic_apps_black_96dp,
            R.drawable.ic_apps_black_24dp
        )


        val thirdPage = PaperOnboardingPage(
            "Add in favorite your favorites players",
            "RIP Kobe",
            Color.parseColor("#2c3e50"),
            R.drawable.ic_star_black_96dp,
            R.drawable.ic_star_black_24dp
        )

        val fragment =
            PaperOnboardingFragment.newInstance(arrayListOf(firstPage, secondPage, thirdPage))
        fragment.setOnRightOutListener {
            startMain()
        }

        activity_on_boarding_btn.setOnClickListener {
            startMain()
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.activity_on_boarding_frame, fragment)
            .commit()
    }
}
