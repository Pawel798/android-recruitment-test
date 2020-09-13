package dog.snow.androidrecruittest.injection.component

import dagger.Component
import dog.snow.androidrecruittest.injection.module.RepositoryModule
import dog.snow.androidrecruittest.viewModels.SplashViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [(RepositoryModule::class)])
interface ViewModelInjector {
    fun inject(splashViewModel: SplashViewModel)
    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun repositoryModule(repositoryModule: RepositoryModule): Builder
    }
}