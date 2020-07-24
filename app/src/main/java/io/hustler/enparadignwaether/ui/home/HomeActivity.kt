package io.hustler.enparadignwaether.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.hustler.enparadignwaether.R
import io.hustler.enparadignwaether.data.model.ResWeatherData
import io.hustler.enparadignwaether.di.components.ActivityComponent
import io.hustler.enparadignwaether.ui.base.BaseActivity
import io.hustler.enparadignwaether.ui.home.frags.CitiesFragment
import io.hustler.enparadignwaether.ui.home.frags.WeatherHomeFragment
import io.hustler.enparadignwaether.utils.MessageUtils
import io.hustler.enparadignwaether.utils.common.Status
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : BaseActivity<HomeViewModel>() {
    private var activeFragment: Fragment? = null

    override fun provideLayout(): Int = R.layout.home_activity

    override fun injectDependencies(component: ActivityComponent) {
        component.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        showFirstFragment()
    }

    private fun showFirstFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment: WeatherHomeFragment = WeatherHomeFragment.newInstance()
        fragmentTransaction.add(R.id.frame_layout, fragment, WeatherHomeFragment.FRAG_TAG)
        fragmentTransaction.commit()
        activeFragment = fragment
    }

    private fun showWeatherFragment() {
        if (activeFragment is WeatherHomeFragment) return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        var fragment =
            supportFragmentManager.findFragmentByTag(WeatherHomeFragment.FRAG_TAG) as WeatherHomeFragment
        if (fragment == null) {
            fragment = WeatherHomeFragment.newInstance()

            fragmentTransaction.add(R.id.frame_layout, fragment, WeatherHomeFragment.FRAG_TAG)

        } else {
            fragmentTransaction.show(fragment)
        }
        if (activeFragment != null)
            fragmentTransaction.hide(activeFragment!!)

        fragmentTransaction.commit()
        activeFragment = fragment
    }

    private fun showCitiesFragment() {
        if (activeFragment is CitiesFragment) return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        var fragment =
            supportFragmentManager.findFragmentByTag(CitiesFragment.FRAG_TAG) as CitiesFragment
        if (fragment == null) {
            fragment = CitiesFragment.newInstance()

            fragmentTransaction.add(R.id.frame_layout, fragment, CitiesFragment.FRAG_TAG)

        } else {
            fragmentTransaction.show(fragment)
        }
        if (activeFragment != null)
            fragmentTransaction.hide(activeFragment!!)

        fragmentTransaction.commit()
        activeFragment = fragment
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (activeFragment is WeatherHomeFragment) {
            finish()
            super.onBackPressed()

        } else {
            showWeatherFragment()
        }
    }

    override fun setupObservers() {
        super.setupObservers()

    }
}