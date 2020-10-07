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
) {
    fun getShareLink(): String? = externalUrls?.get(SPOTIFY)

    fun getSmallImage(): String? = images?.get(1)?.url

    fun getBigImage(): String? = images?.get(0)?.url

    companion object {
        const val SPOTIFY = "spotify"
    }
}