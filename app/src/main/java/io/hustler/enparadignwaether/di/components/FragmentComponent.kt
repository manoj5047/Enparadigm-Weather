package io.hustler.enparadignwaether.di.components

import io.hustler.enparadignwaether.di.scopes.ActivityScope
import io.hustler.enparadignwaether.di.scopes.FragmentScope
import io.hustler.enparadignwaether.ui.home.frags.WeatherHomeFragment
import io.hustler.enparadignwaether.ui.home.frags.CitiesFragment
import dagger.Component
import io.hustler.enparadignwaether.di.modules.FragmentModule

@Component(dependencies = [ApplicationComponent::class], modules = [FragmentModule::class])
@FragmentScope
@ActivityScope
interface FragmentComponent {
    fun injectHomeFragment(fragmentWeather: WeatherHomeFragment)
    fun inject(citiesFragment: CitiesFragment)

}