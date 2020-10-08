package cat.jorcollmar.data.source

import cat.jorcollmar.data.model.dto.AlbumDto
import cat.jorcollmar.data.model.dto.SpotifyAlbumsResultDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface SpotifyWebservice {
    @GET(ALBUMS_NEW_RELEASES_ENDPOINT)
    fun getAlbums(): Single<SpotifyAlbumsResultDto>

    @GET(ALBUM_DETAILS_ENDPOINT)
    fun getAlbumDetails(@Path("id") id: String): Single<AlbumDto>

    companion object {
        const val ALBUMS_NEW_RELEASES_ENDPOINT = "browse/new-releases"
        const val ALBUM_DETAILS_ENDPOINT = "albums/{id}"
    }
}