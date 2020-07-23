package io.hustler.enparadignwaether.ui.home.frags

import io.hustler.enparadignwaether.ui.base.BaseFragment
import io.hustler.enparadignwaether.ui.home.HomeViewModel
import android.view.View
import io.hustler.enparadignwaether.R
import io.hustler.enparadignwaether.di.components.FragmentComponent


class CitiesFragment : BaseFragment<HomeViewModel>() {
    override fun provideLayoutId(): Int = R.layout.orders_fragment

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupView(view: View) {

    }

}