package io.hustler.enparadignwaether.di.modules

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.hustler.enparadignwaether.data.respository.UserRepository
import io.hustler.enparadignwaether.ui.home.HomeViewModel
import io.hustler.enparadignwaether.utils.ViewModelProviderFactory
import io.hustler.enparadignwaether.utils.network.NetworkHelper
import io.hustler.enparadignwaether.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.hustler.enparadignwaether.data.local.room.dao.CityDao
import io.hustler.enparadignwaether.data.respository.CityRespository
import io.hustler.enparadignwaether.data.respository.WeatherRepository
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun providesHoeViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        userRepository: UserRepository,
        weatherRepository: WeatherRepository,
        cityRespository: CityRespository
    ): HomeViewModel =
        ViewModelProvider(activity,
            ViewModelProviderFactory(HomeViewModel::class) {
                HomeViewModel(
                    schedulerProvider,
                    compositeDisposable,
                    networkHelper,
                    userRepository,weatherRepository,cityRespository
                )
            }).get(HomeViewModel::class.java)


}


