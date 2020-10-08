package cat.jorcollmar.data.mapper.dto

import cat.jorcollmar.data.common.base.Mapper
import cat.jorcollmar.data.model.AlbumData
import cat.jorcollmar.data.model.dto.AlbumDto
import javax.inject.Inject

class AlbumDtoMapper @Inject constructor(
    private val albumImageDataMapper: AlbumImageDtoMapper,
    private val artistDataMapper: ArtistDtoMapper
) : Mapper<AlbumDto, AlbumData>() {
    override fun map(unmapped: AlbumDto): AlbumData {
        return AlbumData(
            unmapped.id,
            unmapped.name,
            unmapped.images?.let { albumImageDataMapper.map(it) },
            unmapped.release_date,
            unmapped.external_urls,
            unmapped.artists?.let { artistDataMapper.map(it[0]) },
            unmapped.label,
            unmapped.tracks?.total

        )
    }
}