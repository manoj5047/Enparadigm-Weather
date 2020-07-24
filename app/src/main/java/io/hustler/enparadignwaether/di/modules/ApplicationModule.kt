package io.hustler.enparadignwaether.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.hustler.enparadignwaether.EnparadignApplication
import io.hustler.enparadignwaether.data.local.room.CityWeatherDatabase
import io.hustler.enparadignwaether.data.local.room.dao.CityDao
import io.hustler.enparadignwaether.data.respository.CityRespository
import io.hustler.enparadignwaether.data.respository.UserRepository
import io.hustler.enparadignwaether.data.respository.WeatherRepository
import io.hustler.enparadignwaether.data.service.Networking
import io.hustler.enparadignwaether.data.service.WeatherRestService
import io.hustler.enparadignwaether.di.qualifiers.ApplicationContextQualifier
import io.hustler.enparadignwaether.utils.SharedPrefsUtils
import io.hustler.enparadignwaether.utils.network.NetworkHelper
import io.hustler.enparadignwaether.utils.rx.RxSchedulerProvider
import io.hustler.enparadignwaether.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val enparadignApplication: EnparadignApplication) {


    @Provides
    @Singleton
    fun providesApplication(): EnparadignApplication = enparadignApplication

    @Provides
    @Singleton
    @ApplicationContextQualifier
    fun providesContext(): Context = enparadignApplication


    @Provides
    @Singleton
    fun provideSharedPrefs(): SharedPrefsUtils {
        return SharedPrefsUtils.getInstance(providesContext())
    }


    @Provides
    @Singleton
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(providesContext())

    @Provides
    @Singleton
    fun getSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()


    @Provides
    @Singleton
    fun getUserRepository(): UserRepository =
        UserRepository(
            provideSharedPrefs()
        )

    @Provides
    @Singleton
    fun getWeatherRepository(): WeatherRepository =
        WeatherRepository(getWeatherRestService())

    @Provides
    @Singleton
    fun getWeatherRestService(): WeatherRestService =
        Networking.createWeatherRemoteService(
            "https://api.openweathermap.org/data/2.5/",
            enparadignApplication.cacheDir,
            10 * 1024 * 1024
        )

    @Provides
    @Singleton
    fun getCompositeDisposable(): CompositeDisposable = CompositeDisposable()


    @Provides
    @Singleton
    fun getDb(): CityWeatherDatabase =
        Room.databaseBuilder(
            enparadignApplication.applicationContext!!,
            CityWeatherDatabase::class.java, CityWeatherDatabase.DB_NAME
        ).build()

    @Provides
    @Singleton
    fun cityDao():CityDao =
        getDb().cityDao()

    @Provides
    @Singleton
    fun getCityRepository():CityRespository=
        CityRespository(cityDao())

}