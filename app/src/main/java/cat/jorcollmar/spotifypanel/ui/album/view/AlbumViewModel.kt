package cat.jorcollmar.spotifypanel.ui.album.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cat.jorcollmar.domain.usecase.GetAlbums
import cat.jorcollmar.spotifypanel.ui.album.model.Album
import io.reactivex.functions.Consumer
import javax.inject.Inject

class AlbumViewModel @Inject constructor(
    private val getAlbums: GetAlbums
) : ViewModel() {

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>>
        get() = _albums

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
                Log.d("TRYYYYYYYY", it.size.toString())
            }, Consumer {
                Log.d("TRYYYYYYYY", it?.localizedMessage ?: "")
            }, GetAlbums.Params()
        )

    }
}