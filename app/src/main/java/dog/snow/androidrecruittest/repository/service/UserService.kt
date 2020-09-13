package dog.snow.androidrecruittest.repository.service

import dog.snow.androidrecruittest.repository.model.RawUser
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/users/{id}")
    fun getUsers(@Path("id") userId:Int): Observable<RawUser>
}