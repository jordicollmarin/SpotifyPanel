package cat.jorcollmar.spotifypanel.ui.album.mapper

import cat.jorcollmar.data.common.base.Mapper
import cat.jorcollmar.domain.model.AlbumDomain
import cat.jorcollmar.spotifypanel.ui.album.model.Album
import javax.inject.Inject

class AlbumMapper @Inject constructor(
    private val albumImageMapper: AlbumImageMapper,
    private val artistMapper: ArtistMapper
) : Mapper<AlbumDomain, Album>() {
    override fun map(unmapped: AlbumDomain): Album {
        return Album(
            unmapped.id,
            unmapped.name,
            unmapped.images?.let { albumImageMapper.map(it) },
            unmapped.releaseDate,
            unmapped.externalUrls,
            unmapped.artist?.let { artistMapper.map(it) },
            unmapped.label,
            unmapped.tracks

        )
    }
}