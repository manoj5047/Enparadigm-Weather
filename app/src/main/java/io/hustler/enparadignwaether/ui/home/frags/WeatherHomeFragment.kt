package io.hustler.enparadignwaether.ui.home.frags

import android.view.View
import io.hustler.enparadignwaether.R
import io.hustler.enparadignwaether.di.components.FragmentComponent
import io.hustler.enparadignwaether.ui.base.BaseFragment
import io.hustler.enparadignwaether.ui.home.HomeViewModel


class WeatherHomeFragment : BaseFragment<HomeViewModel>() {

    companion object {
        const val FRAG_TAG: String = "WEATHER_FRAG_TAG"

        fun newInstance(): WeatherHomeFragment {

            return WeatherHomeFragment()
        }
    }

    override fun provideLayoutId(): Int = R.layout.home_fragment

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.injectHomeFragment(this)
    }

    override fun setupView(view: View) {
    }

}