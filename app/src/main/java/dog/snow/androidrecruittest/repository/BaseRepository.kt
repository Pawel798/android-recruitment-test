package dog.snow.androidrecruittest.repository

import dog.snow.androidrecruittest.injection.component.DaggerRepositoryInjector
import dog.snow.androidrecruittest.injection.component.RepositoryInjector
import dog.snow.androidrecruittest.injection.module.NetworkModule

abstract class BaseRepository {
    private val injector: RepositoryInjector = DaggerRepositoryInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is SplashRepository -> injector.inject(this)
        }
    }
}