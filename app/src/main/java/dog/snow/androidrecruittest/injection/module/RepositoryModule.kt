package dog.snow.androidrecruittest.injection.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dog.snow.androidrecruittest.repository.SplashRepository

@Module
object RepositoryModule {
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideSplashRepository(): SplashRepository {
        return SplashRepository()
    }
}