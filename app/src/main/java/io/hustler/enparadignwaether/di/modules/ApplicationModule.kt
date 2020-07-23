package io.hustler.enparadignwaether.di.modules

import android.content.Context
import io.hustler.enparadignwaether.EnparadignApplication
import io.hustler.enparadignwaether.data.remote.firebase.UserRepository
import io.hustler.enparadignwaether.di.qualifiers.ApplicationContextQualifier
import io.hustler.enparadignwaether.utils.SharedPrefsUtils
import io.hustler.enparadignwaether.utils.network.NetworkHelper
import io.hustler.enparadignwaether.utils.rx.RxSchedulerProvider
import io.hustler.enparadignwaether.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
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
    fun getUserRepository(): UserRepository = UserRepository(provideSharedPrefs())


    @Provides
    @Singleton
    fun getCompositeDisposable(): CompositeDisposable = CompositeDisposable()




}