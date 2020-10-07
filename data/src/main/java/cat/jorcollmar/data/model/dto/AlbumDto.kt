package cat.jorcollmar.data.model.dto

data class AlbumDto(
    val id: String,
    val name: String?,
    val images: List<AlbumImageDto>?,
    val release_date: String?,
    val external_urls: Map<String, String>?,
    val artist: ArtistDto?,
    val label: String?,
    val tracks: PagedTrackDto?
)