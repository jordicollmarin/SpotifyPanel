package cat.jorcollmar.data.model.dto

data class PagedAlbumDto(
    val href: String,
    val items: List<AlbumDto>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)