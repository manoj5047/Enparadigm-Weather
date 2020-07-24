package io.hustler.enparadignwaether.data.respository

import io.hustler.enparadignwaether.utils.SharedPrefsUtils
import io.hustler.enparadignwaether.utils.constants.Constants
import javax.inject.Singleton

@Singleton
class UserRepository(
    private val sharedPreferences: SharedPrefsUtils
) {
    fun saveUserCityPreference(index:Int) {
        sharedPreferences.putInt(Constants.SHARED_PREFS_USER_CITY,index)
    }
    fun getUserCityPreference():Int = sharedPreferences.getInt(Constants.SHARED_PREFS_USER_CITY)

}