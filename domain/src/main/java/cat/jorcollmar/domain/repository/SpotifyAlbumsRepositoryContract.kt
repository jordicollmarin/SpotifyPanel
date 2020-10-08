package cat.jorcollmar.domain.repository

import cat.jorcollmar.domain.model.AlbumDomain
import io.reactivex.Observable
import io.reactivex.Single

interface SpotifyAlbumsRepositoryContract {
    fun getAlbums(): Observable<List<AlbumDomain>>
    fun getAlbumDetails(albumId: String): Single<AlbumDomain>
}