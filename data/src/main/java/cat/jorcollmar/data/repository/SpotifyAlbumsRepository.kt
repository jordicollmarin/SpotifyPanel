package cat.jorcollmar.data.repository

import cat.jorcollmar.data.mapper.AlbumDataMapper
import cat.jorcollmar.data.source.spotify.SpotifyApiDataSource
import cat.jorcollmar.domain.model.AlbumDomain
import cat.jorcollmar.domain.repository.SpotifyAlbumsRepositoryContract
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class SpotifyAlbumsRepository @Inject constructor(
    private val spotifyApiDataSource: SpotifyApiDataSource,
    private val albumDataMapper: AlbumDataMapper
) : SpotifyAlbumsRepositoryContract {

    override fun getAlbums(offset: Int): Observable<List<AlbumDomain>> = spotifyApiDataSource.getAlbums(offset).map {
        albumDataMapper.map(it)
    }

    override fun getAlbumDetails(albumId: String): Single<AlbumDomain> =
        spotifyApiDataSource.getAlbumDetails(albumId).map { albumDataMapper.map(it) }
}