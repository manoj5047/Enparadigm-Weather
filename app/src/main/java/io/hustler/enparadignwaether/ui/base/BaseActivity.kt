package io.hustler.enparadignwaether.ui.base

import io.hustler.enparadignwaether.di.modules.ActivityModule
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import io.hustler.enparadignwaether.EnparadignApplication
import io.hustler.enparadignwaether.di.components.ActivityComponent
import io.hustler.enparadignwaether.di.components.DaggerActivityComponent
import io.hustler.enparadignwaether.utils.common.Status
import io.hustler.enparadignwaether.utils.display.Toaster
import io.hustler.enparadignwaether.utils.log.TimberLogger

import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
    @Inject
    lateinit var viewModel: VM


    lateinit var TAG: String

    /*Abstract methods all the child classes should implement*/
    @LayoutRes
    protected abstract fun provideLayout(): Int

    protected abstract fun injectDependencies(component: ActivityComponent)
    protected abstract fun setupView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        setContentView(provideLayout())
        setupObservers()
        setupView(savedInstanceState)
        setupInternet()
        viewModel.onCreate()
        TAG = viewModel.javaClass.name
    }

    override fun onStart() {
        super.onStart()
        viewModel.registerNetworkListener()
        TimberLogger.d(TAG, "Network Registered", TAG)

    }
    private fun setupInternet() {


    }

    protected open fun setupObservers() {

        viewModel.messageString.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.run { showMessage(this) }
                }
                Status.ERROR -> {
                    it.data?.run { showErrorMessage(this) }
                }
                Status.LOADING -> {
                }
                Status.UNKNOWN -> {
                }
            }
        })

        viewModel.messageStringId.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.run { showMessage(this) }
                }
                Status.ERROR -> {
                    it.data?.run { showErrorMessage(this) }
                }
                Status.LOADING -> {
                }
                Status.UNKNOWN -> {
                }
            }
        })

    }

    private fun showErrorMessage(s: String) {
        Toaster.makeSmallErrorToast(applicationContext, s)
    }

    private fun showErrorMessage(i: Int) {
        Toaster.makeBigErrorToast(applicationContext, getString(i))
    }

    private fun showMessage(s: String) {
        Toaster.makeSmallToast(applicationContext, s)
    }

    private fun showMessage(i: Int) {
        Toaster.makeBigToast(applicationContext, getString(i))
    }

    private fun buildActivityComponent(): ActivityComponent =
            DaggerActivityComponent
                    .builder()
                    .applicationComponent((application as EnparadignApplication).applicationComponent)
                    .activityModule(ActivityModule(this))
                    .build()

    override fun onBackPressed() {
        super.onBackPressed()
    }


}