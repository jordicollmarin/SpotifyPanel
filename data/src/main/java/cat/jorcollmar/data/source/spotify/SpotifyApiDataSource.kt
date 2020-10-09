package cat.jorcollmar.data.source.spotify

import cat.jorcollmar.data.mapper.dto.AlbumDtoMapper
import cat.jorcollmar.data.mapper.dto.PagedAlbumsDtoMapper
import cat.jorcollmar.data.model.AlbumData
import cat.jorcollmar.data.model.PagedAlbumsData
import cat.jorcollmar.data.source.SpotifyWebservice
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class SpotifyApiDataSource @Inject constructor(
    private val spotifyWebservice: SpotifyWebservice,
    private val albumDtoMapper: AlbumDtoMapper,
    private val pagedAlbumsDtoMapper: PagedAlbumsDtoMapper
) {
    fun getAlbums(offset: Int): Observable<PagedAlbumsData> =
        spotifyWebservice.getAlbums(offset).flatMapObservable {
            it.albums?.let { pagedAlbumsDto ->
                Single.just(pagedAlbumsDtoMapper.map(pagedAlbumsDto)).toObservable()
            }
        }

    fun getAlbumDetails(albumId: String): Single<AlbumData> =
        spotifyWebservice.getAlbumDetails(albumId).map { albumDtoMapper.map(it) }

}