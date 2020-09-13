package dog.snow.androidrecruittest.repository

import dog.snow.androidrecruittest.repository.service.AlbumService
import dog.snow.androidrecruittest.repository.service.PhotoService
import dog.snow.androidrecruittest.repository.service.UserService
import dog.snow.androidrecruittest.viewModels.SplashViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashRepository : BaseRepository() {
    @Inject
    lateinit var photoService: PhotoService
    @Inject
    lateinit var albumService: AlbumService
    @Inject
    lateinit var userService: UserService
    lateinit var subscription: Disposable

    fun loadPhotos(splashViewModel: SplashViewModel) {
        subscription = photoService.getPhotos()
            .flatMap { Observable.fromIterable(it) }
            .doOnNext { t ->
                run {
                    t.album = albumService.getAlbums(t.albumId).blockingFirst()
                    t.album.user = userService.getUsers(t.album.userId).blockingFirst()
                }
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { splashViewModel.onRetrievePhotosStart() }
            .doAfterTerminate { splashViewModel.onRetrievePhotosFinish() }
            .subscribe(
                { result -> splashViewModel.onRetrievePhotoSuccess(result) },
                { splashViewModel.onRetrievePhotosError() }
            )
    }
}