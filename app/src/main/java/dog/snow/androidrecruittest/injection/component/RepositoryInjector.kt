package dog.snow.androidrecruittest.injection.component


import dagger.Component
import dog.snow.androidrecruittest.injection.module.NetworkModule
import dog.snow.androidrecruittest.repository.SplashRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface RepositoryInjector {
    fun inject(splashRepository: SplashRepository)
    @Component.Builder
    interface Builder {
        fun build(): RepositoryInjector
        fun networkModule(networkModule: NetworkModule): Builder
    }
}