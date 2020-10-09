package cat.jorcollmar.spotifypanel.ui.album.mapper

import cat.jorcollmar.data.common.base.Mapper
import cat.jorcollmar.domain.model.PagedAlbumsDomain
import cat.jorcollmar.spotifypanel.ui.album.model.PagedAlbums
import javax.inject.Inject

class PagedAlbumsMapper @Inject constructor(
    private val albumMapper: AlbumMapper
) : Mapper<PagedAlbumsDomain, PagedAlbums>() {
    override fun map(unmapped: PagedAlbumsDomain): PagedAlbums {
        return PagedAlbums(
            unmapped.href,
            unmapped.items?.let { albumMapper.map(it) },
            unmapped.limit,
            unmapped.next,
            unmapped.offset,
            unmapped.previous,
            unmapped.total

        )
    }
}