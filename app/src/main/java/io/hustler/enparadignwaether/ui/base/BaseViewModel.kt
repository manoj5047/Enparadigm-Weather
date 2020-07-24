package io.hustler.enparadignwaether.ui.base


import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.hustler.enparadignwaether.R
import io.hustler.enparadignwaether.utils.common.Resource
import io.hustler.enparadignwaether.utils.log.TimberLogger
import io.hustler.enparadignwaether.utils.network.NetworkHelper
import io.hustler.enparadignwaether.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

import java.net.HttpURLConnection

abstract class BaseViewModel(
    protected val schedulerProvider: SchedulerProvider,
    protected val compositeDisposable: CompositeDisposable,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    val messageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()
    val messageString: MutableLiveData<Resource<String>> = MutableLiveData()
    val errormessageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()
    val errormessageString: MutableLiveData<Resource<String>> = MutableLiveData()
    val networkConnectionLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val addWindowListnersLiveData: MutableLiveData<Boolean> = MutableLiveData()


    override fun onCleared() {
        compositeDisposable.clear()
        TimberLogger.e("ONCLEARED", "ONCLEARED")
        super.onCleared()
    }


    abstract fun onCreate()

    @SuppressLint("CheckResult")
    fun registerNetworkListener() {
        networkHelper
            .observerNetworkData()!!
            .subscribeOn(schedulerProvider.io()).subscribe({
                networkConnectionLiveData.postValue(it)
                addWindowListnersLiveData.postValue(it)
            }, {
                handleNetworkError(it)
            })
    }



    protected fun checkIntenrnetConnectionWithMessage(): Boolean {
        return if (networkHelper.isNetworkConnected()) {
            true
        } else {
            errormessageStringId.postValue(Resource.error(R.string.no_internet))
            networkConnectionLiveData.postValue(false)
            false
        }
    }

    protected fun handleNetworkError(err: Throwable?) =
        err?.let {
            TimberLogger.e("API ERROR",err.message.toString())
            networkHelper.castToNetworkError(throwable = err).run {
                when (status) {
                    -1 ->
                        errormessageStringId.postValue(Resource.error(R.string.something_wrong_happened))

                    0 -> {
                        errormessageStringId.postValue(Resource.error(R.string.unable_to_connect))
                    }
                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
                        forceLogoutUser()
                        errormessageStringId.postValue(Resource.error(R.string.access_denied_for_unauthorised_user))
                    }
                    HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                        errormessageStringId.postValue(Resource.error(R.string.system_error))
                    }
                    HttpURLConnection.HTTP_UNAVAILABLE -> {
                        errormessageStringId.postValue(Resource.error(R.string.system_under_mainteance))
                    }
                    else -> {
                        errormessageString.postValue(Resource.error(message))
                    }
                }

            }
        }

    protected open fun forceLogoutUser() {}

}