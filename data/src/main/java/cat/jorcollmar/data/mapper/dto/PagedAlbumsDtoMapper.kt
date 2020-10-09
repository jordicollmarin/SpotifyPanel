package cat.jorcollmar.data.mapper.dto

import cat.jorcollmar.data.common.base.Mapper
import cat.jorcollmar.data.model.PagedAlbumsData
import cat.jorcollmar.data.model.dto.PagedAlbumsDto
import javax.inject.Inject

class PagedAlbumsDtoMapper @Inject constructor(
    private val albumDtoMapper: AlbumDtoMapper
) : Mapper<PagedAlbumsDto, PagedAlbumsData>() {
    override fun map(unmapped: PagedAlbumsDto): PagedAlbumsData {
        return PagedAlbumsData(
            unmapped.href,
            unmapped.items?.let { albumDtoMapper.map(it) },
            unmapped.limit,
            unmapped.next,
            unmapped.offset,
            unmapped.previous,
            unmapped.total

        )
    }
}