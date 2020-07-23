package io.hustler.enparadignwaether.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.hustler.enparadignwaether.EnparadignApplication
import io.hustler.enparadignwaether.di.components.DaggerFragmentComponent
import io.hustler.enparadignwaether.di.components.FragmentComponent
import io.hustler.enparadignwaether.di.modules.FragmentModule
import io.hustler.enparadignwaether.utils.display.Toaster


import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    @Inject
    lateinit var viewModel: VM

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun injectDependencies(fragmentComponent: FragmentComponent)
    protected abstract fun setupView(view: View)


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildFragmentComponent())
        super.onCreate(savedInstanceState)
        setupObservers()
        viewModel.onCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(provideLayoutId(), container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)

    }

    private fun buildFragmentComponent(): FragmentComponent =
        DaggerFragmentComponent
            .builder()
            .applicationComponent((context!!.applicationContext as EnparadignApplication).applicationComponent)
            .fragmentModule(FragmentModule(this)).build()

    protected open fun setupObservers() {
        viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })
    }

    fun showMessage(message: String) = context?.let { Toaster.makeSmallToast(it, message) }

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))
    override fun onDetach() {
        super.onDetach()
    }
}