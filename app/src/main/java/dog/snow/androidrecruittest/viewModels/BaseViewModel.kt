package dog.snow.androidrecruittest.viewModels

import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.injection.component.DaggerViewModelInjector
import dog.snow.androidrecruittest.injection.component.ViewModelInjector
import dog.snow.androidrecruittest.injection.module.RepositoryModule

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .repositoryModule(RepositoryModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is SplashViewModel -> injector.inject(this)
        }
    }
}