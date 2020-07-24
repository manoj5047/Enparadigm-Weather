package io.hustler.enparadignwaether.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_data")
class CityData {
    @PrimaryKey(autoGenerate = true)
    public var id: Int = 0

    @ColumnInfo(name = "city_name")
  public var city_name = ""

    @ColumnInfo(name = "city_weather_data")
   public var weather_data = ""

    @ColumnInfo(name = "timeStamp")
    public var timeStamp:Long = 0L





}