package cat.jorcollmar.spotifypanel.ui.album.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cat.jorcollmar.domain.usecase.GetAlbumDetails
import cat.jorcollmar.domain.usecase.GetAlbums
import cat.jorcollmar.spotifypanel.ui.album.mapper.AlbumMapper
import cat.jorcollmar.spotifypanel.ui.album.model.Album
import io.reactivex.functions.Consumer
import javax.inject.Inject

class AlbumViewModel @Inject constructor(
    private val getAlbums: GetAlbums,
    private val getAlbumDetails: GetAlbumDetails,
    private val albumMapper: AlbumMapper
) : ViewModel() {

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>>
        get() = _albums

    private lateinit var _selectedAlbumId: String

    private var _albumDetailsLoaded: Boolean = false
    val albumDetailsLoaded: Boolean
        get() = _albumDetailsLoaded

    private val _selectedAlbum = MutableLiveData<Album>()
    val selectedAlbum: LiveData<Album>
        get() = _selectedAlbum

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int>
        get() = _error

    fun getAlbums() {
        _loading.value = true

        getAlbums.execute(
            Consumer {
                Log.d(TAG, "GetAlbums: OK. Albums: ${it.size}")
                _loading.value = false
                _albums.value = albumMapper.map(it)
            }, Consumer {
                Log.e(TAG, "GetAlbums: KO. Error: ${it.localizedMessage}")
                _loading.value = false
                _error.value = ERROR_ALBUMS
            }, GetAlbums.Params()
        )
    }

    fun getAlbumDetails() {
        _loading.value = true

        getAlbumDetails.execute(
            Consumer {
                Log.d(TAG, "GetAlbumDetails: OK. Album id: ${it.id}")
                _loading.value = false
                _selectedAlbum.value = albumMapper.map(it)
            }, Consumer {
                Log.e(TAG, "GetAlbumDetails: KO. Error: ${it.localizedMessage}")
                _loading.value = false
                _error.value = ERROR_ALBUM_DETAILS
            }, GetAlbumDetails.Params(_selectedAlbumId)
        )
    }

    fun setSelectedAlbum(albumId: String) {
        _selectedAlbumId = albumId
    }

    fun changeDetailLoaded(isLoaded: Boolean) {
        _albumDetailsLoaded = isLoaded
    }

    override fun onCleared() {
        getAlbums.dispose()
        getAlbumDetails.dispose()
        super.onCleared()
    }

    companion object {
        private const val TAG = "AlbumViewModel"
        const val ERROR_ALBUMS = 1000
        const val ERROR_ALBUM_DETAILS = 1001
    }
}