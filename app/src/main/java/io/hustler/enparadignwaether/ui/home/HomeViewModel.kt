package io.hustler.enparadignwaether.ui.home

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import io.hustler.enparadignwaether.data.model.ResWeatherData
import io.hustler.enparadignwaether.data.respository.CityRespository
import io.hustler.enparadignwaether.data.respository.UserRepository
import io.hustler.enparadignwaether.data.respository.WeatherRepository
import io.hustler.enparadignwaether.ui.base.BaseViewModel
import io.hustler.enparadignwaether.utils.common.Resource
import io.hustler.enparadignwaether.utils.network.NetworkHelper
import io.hustler.enparadignwaether.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class HomeViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val userRepository: UserRepository,
    private val weatherRepository: WeatherRepository,
    private val cityRepo: CityRespository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    val weatherLiveData: MutableLiveData<Resource<Any>> = MutableLiveData()
    val cityNameLiveData: MutableLiveData<String> = MutableLiveData()
    val isMorningLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val userCityLiveData: MutableLiveData<Int> = MutableLiveData()
    override fun onCreate() {
        getDayLight()
        getUserCity()
    }

    private fun getUserCity() {
        userCityLiveData.postValue(userRepository.getUserCityPreference())
    }

    private fun getDayLight() {

        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) in 6..18) {
            isMorningLiveData.postValue(true)
        } else {
            isMorningLiveData.postValue(false)
        }
    }

    fun getWeatherData(lat: Double, long: Double,cityName:String) {
        if (checkIntenrnetConnectionWithMessage()) {
            weatherLiveData.postValue(Resource.loading("Getting Updated Weather Details."))
            compositeDisposable.addAll(
                weatherRepository
                    .getTodayWeather(lat, long)
                    .subscribeOn(schedulerProvider.io())
                    .subscribe({
                        weatherLiveData.postValue(Resource.success(it))
                        saveToDb(it,cityName)

                    }, {
                        handleNetworkError(it)
                        weatherLiveData.postValue(Resource.error(it.message))
                    })
            )
        } else {
            weatherLiveData.postValue(Resource.error("Connection Not Available"))
            loadWeatherDataFromDb(cityName)
        }
    }

    private fun saveToDb(
        it: ResWeatherData?,
        cityName: String
    ) {
        cityRepo.saveNewWeatherData(it,cityName)
    }

    private fun loadWeatherDataFromDb(cityName: String) {
        compositeDisposable.addAll(
            cityRepo.getCityByName(cityName)
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    val resWeatherData =
                        Gson().fromJson(it.weather_data, ResWeatherData::class.java)
                    weatherLiveData.postValue(Resource.success(resWeatherData))
                }, {
                    handleNetworkError(it)
                    weatherLiveData.postValue(Resource.error("Offline Data UnAvailable"))

                })
        )
    }



    fun onCityNameChange(admin: String) {
        cityNameLiveData.postValue(admin)
    }

    fun changeCityIndex(index: Int) {
        userRepository.saveUserCityPreference(index)
    }




}