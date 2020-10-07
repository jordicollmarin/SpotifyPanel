package cat.jorcollmar.spotifypanel.ui.album.mapper

import cat.jorcollmar.data.common.base.Mapper
import cat.jorcollmar.domain.model.ArtistDomain
import cat.jorcollmar.spotifypanel.ui.album.model.Artist
import javax.inject.Inject

class ArtistMapper @Inject constructor() : Mapper<ArtistDomain, Artist>() {
    override fun map(unmapped: ArtistDomain): Artist {
        return Artist(
            unmapped.id,
            unmapped.name
        )
    }
}