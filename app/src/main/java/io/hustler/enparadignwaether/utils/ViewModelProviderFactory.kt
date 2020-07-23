package io.hustler.enparadignwaether.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelProviderFactory<T : ViewModel>(private val kClass: KClass<T>, private val creator: () -> T) : ViewModelProvider.NewInstanceFactory() {
    @Throws(IllegalAccessException::class)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(kClass.java)) return creator() as T
        throw IllegalArgumentException("Unknown Class Name")
    }
}