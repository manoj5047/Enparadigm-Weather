package io.hustler.enparadignwaether.ui.home

import android.os.Bundle
import io.hustler.enparadignwaether.R
import io.hustler.enparadignwaether.di.components.ActivityComponent
import io.hustler.enparadignwaether.ui.base.BaseActivity

class HomeActivity : BaseActivity<HomeViewModel>(){
    override fun provideLayout(): Int = R.layout.home_activity

    override fun injectDependencies(component: ActivityComponent) {
        component.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

}