package cat.jorcollmar.domain.repository

import cat.jorcollmar.domain.model.AlbumDomain
import cat.jorcollmar.domain.model.PagedAlbumsDomain
import io.reactivex.Observable
import io.reactivex.Single

interface SpotifyAlbumsRepositoryContract {
    fun getAlbums(offset: Int): Observable<PagedAlbumsDomain>
    fun getAlbumDetails(albumId: String): Single<AlbumDomain>
}