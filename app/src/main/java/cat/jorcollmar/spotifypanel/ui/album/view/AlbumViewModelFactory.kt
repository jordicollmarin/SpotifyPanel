package cat.jorcollmar.spotifypanel.ui.album.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.jorcollmar.domain.usecase.albums.GetAlbumDetails
import cat.jorcollmar.domain.usecase.albums.GetAlbums
import cat.jorcollmar.spotifypanel.ui.album.mapper.AlbumMapper
import javax.inject.Inject

class AlbumViewModelFactory @Inject constructor(
    private val getAlbums: GetAlbums,
    private val getAlbumDetails: GetAlbumDetails,
    private val albumMapper: AlbumMapper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlbumViewModel(
                getAlbums, getAlbumDetails, albumMapper
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
