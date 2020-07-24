package io.hustler.enparadignwaether.utils.constants

import io.hustler.enparadignwaether.utils.log.TimberLogger

object Constants{
      var weatherIcons = "http://openweathermap.org/img/wn/iconId@2x.png"
    fun getIconImageUrl (iconId:String):String{
        val iconUrl = weatherIcons.replace("iconId",iconId)
        TimberLogger.d("ICON URL",iconUrl)
        return iconUrl
    }
}