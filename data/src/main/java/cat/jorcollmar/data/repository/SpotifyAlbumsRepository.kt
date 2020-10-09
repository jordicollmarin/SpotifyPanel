package cat.jorcollmar.data.repository

import cat.jorcollmar.data.mapper.AlbumDataMapper
import cat.jorcollmar.data.mapper.PagedAlbumsDataMapper
import cat.jorcollmar.data.source.spotify.SpotifyApiDataSource
import cat.jorcollmar.domain.model.AlbumDomain
import cat.jorcollmar.domain.model.PagedAlbumsDomain
import cat.jorcollmar.domain.repository.SpotifyAlbumsRepositoryContract
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class SpotifyAlbumsRepository @Inject constructor(
    private val spotifyApiDataSource: SpotifyApiDataSource,
    private val albumDataMapper: AlbumDataMapper,
    private val pagedAlbumsDataMapper: PagedAlbumsDataMapper
) : SpotifyAlbumsRepositoryContract {

    override fun getAlbums(offset: Int): Observable<PagedAlbumsDomain> =
        spotifyApiDataSource.getAlbums(offset).map {
            pagedAlbumsDataMapper.map(it)
        }

    override fun getAlbumDetails(albumId: String): Single<AlbumDomain> =
        spotifyApiDataSource.getAlbumDetails(albumId).map { albumDataMapper.map(it) }
}