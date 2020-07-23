package io.hustler.enparadignwaether.di.components

import android.content.Context
import io.hustler.enparadignwaether.EnparadignApplication
import io.hustler.enparadignwaether.data.remote.firebase.UserRepository
import io.hustler.enparadignwaether.di.modules.ApplicationModule
import io.hustler.enparadignwaether.di.qualifiers.ApplicationContextQualifier
import io.hustler.enparadignwaether.utils.SharedPrefsUtils
import io.hustler.enparadignwaether.utils.network.NetworkHelper
import io.hustler.enparadignwaether.utils.rx.SchedulerProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable

import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
public interface ApplicationComponent {

    fun injectApplication(driverApplication: EnparadignApplication?)


    fun getApplication(): EnparadignApplication

    @ApplicationContextQualifier
    fun getContext(): Context

    fun getsharedPrefsUtils(): SharedPrefsUtils

    fun getCompositeDisposable(): CompositeDisposable
    fun getNetworkHelper(): NetworkHelper
    fun getSchedulerProvider(): SchedulerProvider
    fun getUserrepositry(): UserRepository
}