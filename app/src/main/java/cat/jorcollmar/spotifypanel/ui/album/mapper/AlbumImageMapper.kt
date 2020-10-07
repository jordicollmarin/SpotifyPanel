package cat.jorcollmar.spotifypanel.ui.album.mapper

import cat.jorcollmar.data.common.base.Mapper
import cat.jorcollmar.domain.model.AlbumImageDomain
import cat.jorcollmar.spotifypanel.ui.album.model.AlbumImage
import javax.inject.Inject

class AlbumImageMapper @Inject constructor() : Mapper<AlbumImageDomain, AlbumImage>() {
    override fun map(unmapped: AlbumImageDomain): AlbumImage {
        return AlbumImage(
            unmapped.height,
            unmapped.width,
            unmapped.url
        )
    }
}