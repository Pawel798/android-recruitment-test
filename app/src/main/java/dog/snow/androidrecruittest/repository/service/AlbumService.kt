package dog.snow.androidrecruittest.repository.service

import dog.snow.androidrecruittest.repository.model.RawAlbum
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumService {
    @GET("/albums/{id}")
    fun getAlbums(@Path("id") albumId:Int): Observable<RawAlbum>
}