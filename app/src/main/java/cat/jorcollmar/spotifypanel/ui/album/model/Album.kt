package cat.jorcollmar.spotifypanel.ui.album.model

data class Album(
    val id: String,
    val name: String?,
    val images: List<AlbumImage>?,
    val releaseDate: String?,
    val externalUrls: Map<String, String>?,
    val artist: Artist?,
    val label: String?,
    val tracks: Int?
)