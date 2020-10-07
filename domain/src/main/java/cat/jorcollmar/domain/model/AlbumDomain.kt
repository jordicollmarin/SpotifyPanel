package cat.jorcollmar.domain.model

data class AlbumDomain(
    val id: String,
    val name: String?,
    val images: List<AlbumImageDomain>?,
    val releaseDate: String?,
    val externalUrls: Map<String, String>?,
    val artist: ArtistDomain?,
    val label: String?,
    val tracks: Int?
)