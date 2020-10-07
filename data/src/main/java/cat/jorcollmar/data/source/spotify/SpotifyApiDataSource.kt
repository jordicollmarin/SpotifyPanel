package cat.jorcollmar.data.source.spotify

import cat.jorcollmar.data.mapper.dto.AlbumDtoMapper
import cat.jorcollmar.data.model.AlbumData
import cat.jorcollmar.data.source.SpotifyWebservice
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class SpotifyApiDataSource @Inject constructor(
    private val spotifyWebservice: SpotifyWebservice,
    private val albumDtoMapper: AlbumDtoMapper
) {
    fun getAlbums(): Observable<List<AlbumData>> =
        spotifyWebservice.getAlbums().flatMapObservable {
            it.albums?.let { pagedAlbumDto ->
                Single.just(albumDtoMapper.map(pagedAlbumDto.items)).toObservable()
            }
        }

}