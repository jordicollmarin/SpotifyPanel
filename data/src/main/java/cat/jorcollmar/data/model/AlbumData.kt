package cat.jorcollmar.data.model

data class AlbumData(
    val id: String,
    val name: String?,
    val images: List<AlbumImageData>?,
    val releaseDate: String?,
    val externalUrls: Map<String, String>?,
    val artist: ArtistData?,
    val label: String?,
    val tracks: Int?
)