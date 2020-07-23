package io.hustler.enparadignwaether.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.hustler.enparadignwaether.utils.log.TimberLogger
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import java.io.IOException
import java.net.ConnectException
import javax.inject.Singleton


@Singleton
class NetworkHelper constructor(private val context: Context) {
    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private var networkObservable: Observable<Boolean>? = null
    private val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var observableEmitter: ObservableEmitter<Boolean>? = null
    private var isCallbackAttached: Boolean = false

    companion object {
        private const val TAG = "NetworkHelper"
    }

    fun observerNetworkData(): Observable<Boolean>? {
        networkObservable = Observable.create { emitter: ObservableEmitter<Boolean> ->
            observableEmitter = emitter
        }


        return networkObservable
    }

    fun registerNetworkConnectivityChanges() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            networkCallback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    if (null != observableEmitter) {
                        observableEmitter?.onNext(true)
                        TimberLogger.e("Network Connected", "CONNECTED")
                    } else {
                        TimberLogger.e("Network Emitter", "DISPOSED")
                    }

                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    if (null != observableEmitter) {
                        observableEmitter?.onNext(false)
                        TimberLogger.e("Network Disconnected", "DISCONNECTED")
                    } else {
                        TimberLogger.e("Network Emitter", "DISPOSED")

                    }

                }
            }

        }
        if (!isCallbackAttached) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    cm.registerDefaultNetworkCallback(networkCallback)
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val request: NetworkRequest = NetworkRequest.Builder()
                        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
                    cm.registerNetworkCallback(request, networkCallback)
                }
                isCallbackAttached = true
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

    }

    fun unRegisterNetworkConnectivityChanges() {

        if (null != networkCallback) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cm.unregisterNetworkCallback(networkCallback)
            }
        }
    }


    fun isNetworkConnected(): Boolean {
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }

    fun castToNetworkError(throwable: Throwable): NetworkError {
        val defaultNetworkError = NetworkError()
        try {
            if (throwable is ConnectException) return NetworkError(0, "0")
            if (throwable !is HttpException) return defaultNetworkError
            val error = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .fromJson(throwable.response().errorBody()?.string(), NetworkError::class.java)
            return NetworkError(throwable.code(), error.statusCode, error.message)
        } catch (e: IOException) {
            TimberLogger.e(TAG, e.toString())
        } catch (e: JsonSyntaxException) {
            TimberLogger.e(TAG, e.toString())
        } catch (e: NullPointerException) {
            TimberLogger.e(TAG, e.toString())
        }
        return defaultNetworkError
    }
}