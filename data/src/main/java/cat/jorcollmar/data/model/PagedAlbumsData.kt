package cat.jorcollmar.data.model

data class PagedAlbumsData(
    val href: String?,
    val items: List<AlbumData>?,
    val limit: Int?,
    val next: String?,
    val offset: Int?,
    val previous: String?,
    val total: Int?
)