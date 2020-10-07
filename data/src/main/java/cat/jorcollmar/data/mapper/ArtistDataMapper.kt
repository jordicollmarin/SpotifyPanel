package cat.jorcollmar.data.mapper

import cat.jorcollmar.data.common.base.Mapper
import cat.jorcollmar.data.model.ArtistData
import cat.jorcollmar.domain.model.ArtistDomain
import javax.inject.Inject

class ArtistDataMapper @Inject constructor() : Mapper<ArtistData, ArtistDomain>() {
    override fun map(unmapped: ArtistData): ArtistDomain {
        return ArtistDomain(
            unmapped.id,
            unmapped.name
        )
    }
}