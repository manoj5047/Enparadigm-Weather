package io.hustler.enparadignwaether.data.respository

import io.hustler.enparadignwaether.data.model.ResWeatherData
import io.hustler.enparadignwaether.data.service.EndPoints
import io.hustler.enparadignwaether.data.service.WeatherRestService
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class WeatherRepository(private val weatherRestService: WeatherRestService) {

    fun getTodayWeather(lat: Double, long: Double): Single<ResWeatherData> {
        return weatherRestService.getWeatherData(lat, long, EndPoints.EXCLUDE, EndPoints.API_KEY)
    }
}