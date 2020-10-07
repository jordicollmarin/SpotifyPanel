package cat.jorcollmar.domain.repository

import cat.jorcollmar.domain.model.AlbumDomain
import io.reactivex.Observable

interface SpotifyAlbumsRepositoryContract {
    fun getAlbums(): Observable<List<AlbumDomain>>
}