package io.hustler.enparadignwaether.data.respository

import androidx.room.RawQuery
import com.google.gson.Gson
import io.hustler.enparadignwaether.data.local.room.CityWeatherDatabase
import io.hustler.enparadignwaether.data.local.room.dao.CityDao
import io.hustler.enparadignwaether.data.local.room.entity.CityData
import io.hustler.enparadignwaether.data.model.ResWeatherData
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRespository (val cityDao: CityDao){

    fun getCityByName(cityName:String):Single<CityData> = cityDao.getCityWeatherData(cityName)

    fun insertCity(cityData: CityData) = cityDao.insertCityData(cityData)
    fun saveNewWeatherData(it: ResWeatherData?, cityName:  String){
        val cityData = CityData().apply {
            this.city_name  = cityName
            this.weather_data = Gson().toJson(it)
            this.timeStamp = System.currentTimeMillis()
        }
        cityDao.insertCityData(cityData)

    }
}