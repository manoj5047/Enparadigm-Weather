package io.hustler.enparadignwaether.ui.home

import io.hustler.enparadignwaether.data.remote.firebase.UserRepository
import io.hustler.enparadignwaether.ui.base.BaseViewModel
import io.hustler.enparadignwaether.utils.network.NetworkHelper
import io.hustler.enparadignwaether.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val userRepository: UserRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    override fun onCreate() {
        TODO("Not yet implemented")
    }

}