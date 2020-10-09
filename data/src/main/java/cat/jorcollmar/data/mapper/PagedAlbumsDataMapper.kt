package cat.jorcollmar.data.mapper

import cat.jorcollmar.data.common.base.Mapper
import cat.jorcollmar.data.model.PagedAlbumsData
import cat.jorcollmar.domain.model.PagedAlbumsDomain
import javax.inject.Inject

class PagedAlbumsDataMapper @Inject constructor(
    private val albumDataMapper: AlbumDataMapper
) : Mapper<PagedAlbumsData, PagedAlbumsDomain>() {
    override fun map(unmapped: PagedAlbumsData): PagedAlbumsDomain {
        return PagedAlbumsDomain(
            unmapped.href,
            unmapped.items?.let { albumDataMapper.map(it) },
            unmapped.limit,
            unmapped.next,
            unmapped.offset,
            unmapped.previous,
            unmapped.total

        )
    }
}