package cat.jorcollmar.spotifypanel.ui.album.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.jorcollmar.domain.usecase.GetAlbums
import javax.inject.Inject

class AlbumViewModelFactory @Inject constructor(
    private val getAlbums: GetAlbums
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlbumViewModel(
                getAlbums
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
