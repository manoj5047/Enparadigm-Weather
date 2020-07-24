package io.hustler.enparadignwaether.di.modules

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import io.hustler.enparadignwaether.data.respository.CityRespository
import io.hustler.enparadignwaether.data.respository.UserRepository
import io.hustler.enparadignwaether.data.respository.WeatherRepository
import io.hustler.enparadignwaether.ui.home.HomeViewModel
import io.hustler.enparadignwaether.utils.ViewModelProviderFactory
import io.hustler.enparadignwaether.utils.network.NetworkHelper
import io.hustler.enparadignwaether.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule(private val fragment: Fragment) {

    @Provides
    fun providesHoeViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        userRepository: UserRepository,
        weatherRepository:WeatherRepository,
        cityRespository: CityRespository
    ): HomeViewModel =
        ViewModelProvider(fragment.requireActivity(),
            ViewModelProviderFactory(HomeViewModel::class) {
                HomeViewModel(
                    schedulerProvider,
                    compositeDisposable,
                    networkHelper,
                    userRepository,
                    weatherRepository,cityRespository
                )
            }).get(HomeViewModel::class.java)


}
