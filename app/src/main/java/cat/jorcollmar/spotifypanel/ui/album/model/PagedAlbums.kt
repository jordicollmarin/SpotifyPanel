package cat.jorcollmar.spotifypanel.ui.album.model

data class PagedAlbums(
    val href: String?,
    val items: List<Album>?,
    val limit: Int?,
    val next: String?,
    val offset: Int?,
    val previous: String?,
    val total: Int?
)