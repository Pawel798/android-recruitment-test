package dog.snow.androidrecruittest.repository.service

import dog.snow.androidrecruittest.repository.model.RawPhoto
import io.reactivex.Observable
import retrofit2.http.GET

interface PhotoService {
    @GET("/photos?_limit=100")
    fun getPhotos(): Observable<List<RawPhoto>>
}