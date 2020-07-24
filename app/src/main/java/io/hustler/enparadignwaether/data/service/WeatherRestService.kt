package io.hustler.enparadignwaether.data.service

import io.hustler.enparadignwaether.data.model.ResWeatherData
import io.hustler.enparadignwaether.data.service.EndPoints.GET_WEATHER_UPDATE
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherRestService {

    @GET(GET_WEATHER_UPDATE)
    fun getWeatherData(
        @Query(value = "lat") cityLat: Double,
        @Query(value = "lon") cityLong: Double,
        @Query(value = "exclude") exclude: String,
        @Query(value = "appid") apiKey: String
    ): Single<ResWeatherData>


}

public object EndPoints {
    const val GET_WEATHER_UPDATE = "onecall"
    const val API_KEY = "fd31304e779bb2bcdbba2692b9810ef0"
    const val EXCLUDE = "hourly,daily"

}
