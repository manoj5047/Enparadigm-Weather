package io.hustler.enparadignwaether.di.components

import io.hustler.enparadignwaether.di.modules.ActivityModule
import io.hustler.enparadignwaether.di.scopes.ActivityScope
import io.hustler.enparadignwaether.ui.home.HomeActivity
import dagger.Component


@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
public interface ActivityComponent {
    fun inject(homeActivity: HomeActivity)

}