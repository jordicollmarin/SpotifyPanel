package cat.jorcollmar.spotifypanel.ui.album.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import cat.jorcollmar.spotifypanel.databinding.FragmentAlbumsListBinding
import cat.jorcollmar.spotifypanel.ui.album.model.Album
import cat.jorcollmar.spotifypanel.ui.album.view.adapter.AlbumsAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AlbumsListFragment : DaggerFragment() {

    lateinit var binding: FragmentAlbumsListBinding

    @Inject
    lateinit var viewModelFactory: AlbumViewModelFactory

    private val albumsAdapter: AlbumsAdapter by lazy {
        AlbumsAdapter(::onAlbumClick, ::onShareAlbumClick)
    }

    private val viewModel: AlbumViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(AlbumViewModel::class.java)
    }

    private fun onAlbumClick(album: Album) {
        // TODO: Navigate to album detail
    }

    private fun onShareAlbumClick(albumShareLink: String) {
        // TODO: Open intent to share link
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcvAlbums.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = albumsAdapter
        }

        viewModel.getAlbums()
    }
}