package io.hustler.enparadignwaether.ui.home.frags

import android.view.View
import io.hustler.enparadignwaether.R
import io.hustler.enparadignwaether.di.components.FragmentComponent
import io.hustler.enparadignwaether.ui.base.BaseFragment
import io.hustler.enparadignwaether.ui.home.HomeViewModel


class CitiesFragment : BaseFragment<HomeViewModel>() {

    companion object {
        val FRAG_TAG: String? = "CITIES_FRAG_TAG"

        fun newInstance(): CitiesFragment {

            return CitiesFragment()
        }
    }

    override fun provideLayoutId(): Int = R.layout.orders_fragment

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupView(view: View) {

    }

    override fun setupObservers() {
        super.setupObservers()

    }

}