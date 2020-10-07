package cat.jorcollmar.data.mapper

import cat.jorcollmar.data.common.base.Mapper
import cat.jorcollmar.data.model.AlbumData
import cat.jorcollmar.domain.model.AlbumDomain
import javax.inject.Inject

class AlbumDataMapper @Inject constructor(
    private val albumImageDataMapper: AlbumImageDataMapper,
    private val artistDataMapper: ArtistDataMapper
) : Mapper<AlbumData, AlbumDomain>() {
    override fun map(unmapped: AlbumData): AlbumDomain {
        return AlbumDomain(
            unmapped.id,
            unmapped.name,
            unmapped.images?.let { albumImageDataMapper.map(it) },
            unmapped.releaseDate,
            unmapped.externalUrls,
            unmapped.artist?.let { artistDataMapper.map(it) },
            unmapped.label,
            unmapped.tracks

        )
    }
}