package cat.jorcollmar.data.mapper.dto

import cat.jorcollmar.data.common.base.Mapper
import cat.jorcollmar.data.model.ArtistData
import cat.jorcollmar.data.model.dto.ArtistDto
import cat.jorcollmar.domain.model.ArtistDomain
import javax.inject.Inject

class ArtistDtoMapper @Inject constructor() : Mapper<ArtistDto, ArtistData>() {
    override fun map(unmapped: ArtistDto): ArtistData {
        return ArtistData(
            unmapped.id,
            unmapped.name
        )
    }
}