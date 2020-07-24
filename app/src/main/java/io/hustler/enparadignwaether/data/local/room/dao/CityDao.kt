package io.hustler.enparadignwaether.data.local.room.dao

import androidx.room.*
import io.hustler.enparadignwaether.data.local.room.entity.CityData
import io.reactivex.Single
import javax.inject.Singleton

@Dao
@Singleton
interface CityDao {

    @Query("Select * from city_data c where c.city_name = :cityName")
    fun getCityWeatherData(cityName: String): Single<CityData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCityData(cityData: CityData)

    @Update
    fun updateCityData(cityData: CityData)

    @Delete
    fun deleteCityData(cityData: CityData)
}