package dog.snow.androidrecruittest.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import dog.snow.androidrecruittest.repository.SplashRepository
import dog.snow.androidrecruittest.repository.model.RawPhoto
import javax.inject.Inject

class SplashViewModel : BaseViewModel() {
    @Inject
    lateinit var repository: SplashRepository
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    var photos:MutableLiveData<List<RawPhoto>> = MutableLiveData()
    init {
        loadPhotos()
    }

    override fun onCleared() {
        super.onCleared()
        repository.subscription.dispose()
    }
    fun loadPhotos(){
        repository.loadPhotos(this)
    }

    fun onRetrievePhotosStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    fun onRetrievePhotosFinish() {
        loadingVisibility.value = View.GONE
    }

    fun onRetrievePhotoSuccess(photos:List<RawPhoto>) {
        this.photos.value = photos
    }

    fun onRetrievePhotosError() {
        errorMessage.value = "Error"
    }
}