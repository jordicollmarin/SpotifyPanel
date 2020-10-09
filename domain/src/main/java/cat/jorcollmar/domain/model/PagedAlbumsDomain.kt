package cat.jorcollmar.domain.model

data class PagedAlbumsDomain(
    val href: String?,
    val items: List<AlbumDomain>?,
    val limit: Int?,
    val next: String?,
    val offset: Int?,
    val previous: String?,
    val total: Int?
)