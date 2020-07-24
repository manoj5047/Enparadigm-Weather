package io.hustler.enparadignwaether.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.hustler.enparadignwaether.data.local.room.dao.CityDao
import io.hustler.enparadignwaether.data.local.room.entity.CityData
import javax.inject.Singleton

@Singleton
@Database(entities = [CityData::class], exportSchema = false, version = 1)
abstract class CityWeatherDatabase : RoomDatabase() {
    lateinit var instance: CityWeatherDatabase
   companion object{
       val DB_NAME = "city_weather_db"
   }

    abstract fun cityDao(): CityDao


}