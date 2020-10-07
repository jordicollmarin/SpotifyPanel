package cat.jorcollmar.spotifypanel.ui.album.model

data class Album(
    val id: Int,
    val name: String?,
    val releaseDate: String?,
    val image: String?,
    val shareLink: String?
)