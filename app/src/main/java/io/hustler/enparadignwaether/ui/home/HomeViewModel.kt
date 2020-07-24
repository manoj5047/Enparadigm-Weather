package io.hustler.enparadignwaether.ui.home

import androidx.lifecycle.MutableLiveData
import io.hustler.enparadignwaether.data.respository.UserRepository
import io.hustler.enparadignwaether.data.respository.WeatherRepository
import io.hustler.enparadignwaether.ui.base.BaseViewModel
import io.hustler.enparadignwaether.utils.common.Resource
import io.hustler.enparadignwaether.utils.network.NetworkHelper
import io.hustler.enparadignwaether.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val userRepository: UserRepository,
    private val weatherRepository: WeatherRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    val weatherLiveData: MutableLiveData<Resource<Any>> = MutableLiveData()
    val bangaloreLat = 12.971599
    val bangaloreLong = 77.594566
    override fun onCreate() {
        getWeatherData(bangaloreLat, bangaloreLong)
    }

    private fun getWeatherData(lat: Double, long: Double) {
        if (checkIntenrnetConnectionWithMessage()) {
            weatherLiveData.postValue(Resource.loading("Getting Updated Weather Details."))
            compositeDisposable.addAll(
                weatherRepository
                    .getTodayWeather(lat, long)
                    .subscribeOn(schedulerProvider.io())
                    .subscribe({
                        weatherLiveData.postValue(Resource.success(it))
                        loadUpdateWeatherDataToDatabase()
                    }, {
                        handleNetworkError(it)
                        weatherLiveData.postValue(Resource.error(it.message))
                    })
            )
        } else {
            loadWeatherDataFromDb()
        }
    }

    private fun loadWeatherDataFromDb() {

    }

    private fun loadUpdateWeatherDataToDatabase() {

    }

}