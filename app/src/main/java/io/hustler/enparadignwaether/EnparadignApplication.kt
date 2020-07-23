package io.hustler.enparadignwaether

import android.app.Application
import android.content.Context
import io.hustler.enparadignwaether.di.components.ApplicationComponent
import io.hustler.enparadignwaether.di.components.DaggerApplicationComponent
import io.hustler.enparadignwaether.di.modules.ApplicationModule
import io.hustler.enparadignwaether.utils.network.NetworkHelper
import javax.inject.Inject

class EnparadignApplication : Application() {
    var applicationComponent: ApplicationComponent? = null

    @Inject
    lateinit var networkHelper: NetworkHelper

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
        networkHelper.registerNetworkConnectivityChanges()
    }

    override fun onTerminate() {
        super.onTerminate()
        networkHelper.unRegisterNetworkConnectivityChanges()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent!!.injectApplication(this)
    }

    override fun getApplicationContext(): Context? {
        return super.getApplicationContext()
    }
}