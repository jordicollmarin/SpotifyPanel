package cat.jorcollmar.data.source

import cat.jorcollmar.data.model.dto.SpotifyAlbumsResultDto
import io.reactivex.Single
import retrofit2.http.GET

interface SpotifyWebservice {
    @GET(ALBUMS_NEW_RELEASES_ENDPOINT)
    fun getAlbums(): Single<SpotifyAlbumsResultDto>

    companion object {
        const val ALBUMS_NEW_RELEASES_ENDPOINT = "browse/new-releases"
    }
}