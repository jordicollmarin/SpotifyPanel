package cat.jorcollmar.data.mapper.dto

import cat.jorcollmar.data.common.base.Mapper
import cat.jorcollmar.data.model.AlbumImageData
import cat.jorcollmar.data.model.dto.AlbumImageDto
import cat.jorcollmar.domain.model.AlbumImageDomain
import javax.inject.Inject

class AlbumImageDtoMapper @Inject constructor() : Mapper<AlbumImageDto, AlbumImageData>() {
    override fun map(unmapped: AlbumImageDto): AlbumImageData {
        return AlbumImageData(
            unmapped.height,
            unmapped.width,
            unmapped.url
        )
    }
}