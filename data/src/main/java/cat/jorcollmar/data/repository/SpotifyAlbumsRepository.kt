package cat.jorcollmar.data.repository

import cat.jorcollmar.data.mapper.AlbumDataMapper
import cat.jorcollmar.data.source.spotify.SpotifyApiDataSource
import cat.jorcollmar.domain.model.AlbumDomain
import cat.jorcollmar.domain.repository.SpotifyAlbumsRepositoryContract
import io.reactivex.Observable
import javax.inject.Inject

class SpotifyAlbumsRepository @Inject constructor(
    private val spotifyApiDataSource: SpotifyApiDataSource,
    private val albumDataMapper: AlbumDataMapper
) : SpotifyAlbumsRepositoryContract {

    override fun getAlbums(): Observable<List<AlbumDomain>> = spotifyApiDataSource.getAlbums().map {
        albumDataMapper.map(it)
    }
}