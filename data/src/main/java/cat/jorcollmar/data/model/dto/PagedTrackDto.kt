package cat.jorcollmar.data.model.dto

data class PagedTrackDto(
    val href: String,
    val items: List<TrackDto>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)