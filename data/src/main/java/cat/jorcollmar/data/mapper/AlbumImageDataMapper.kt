package cat.jorcollmar.data.mapper

import cat.jorcollmar.data.common.base.Mapper
import cat.jorcollmar.data.model.AlbumImageData
import cat.jorcollmar.domain.model.AlbumImageDomain
import javax.inject.Inject

class AlbumImageDataMapper @Inject constructor() : Mapper<AlbumImageData, AlbumImageDomain>() {
    override fun map(unmapped: AlbumImageData): AlbumImageDomain {
        return AlbumImageDomain(
            unmapped.height,
            unmapped.width,
            unmapped.url
        )
    }
}