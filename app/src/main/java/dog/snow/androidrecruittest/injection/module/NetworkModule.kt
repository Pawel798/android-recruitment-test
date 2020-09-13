package dog.snow.androidrecruittest.injection.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dog.snow.androidrecruittest.BASE_URL
import dog.snow.androidrecruittest.repository.service.AlbumService
import dog.snow.androidrecruittest.repository.service.PhotoService
import dog.snow.androidrecruittest.repository.service.UserService
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
object NetworkModule {
    @Provides
    @Reusable
    @JvmStatic
    internal fun providePhotoService(retrofit: Retrofit): PhotoService {
        return retrofit.create(PhotoService::class.java)
    }
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideAlbumService(retrofit: Retrofit): AlbumService {
        return retrofit.create(AlbumService::class.java)
    }
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

}