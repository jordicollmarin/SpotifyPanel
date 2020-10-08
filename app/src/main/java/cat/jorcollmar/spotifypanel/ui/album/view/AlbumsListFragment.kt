package cat.jorcollmar.spotifypanel.ui.album.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cat.jorcollmar.spotifypanel.R
import cat.jorcollmar.spotifypanel.commons.extensions.observe
import cat.jorcollmar.spotifypanel.commons.factories.AlertDialogFactory
import cat.jorcollmar.spotifypanel.commons.utils.IntentUtils
import cat.jorcollmar.spotifypanel.databinding.FragmentAlbumsListBinding
import cat.jorcollmar.spotifypanel.ui.album.model.Album
import cat.jorcollmar.spotifypanel.ui.album.view.AlbumViewModel.Companion.GENERAL_ERROR_ALBUMS
import cat.jorcollmar.spotifypanel.ui.album.view.AlbumViewModel.Companion.TOKEN_ERROR
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        viewModel.getAlbums()
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
        initObservers()
        binding.rcvAlbums.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = albumsAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun onAlbumClick(album: Album) {
        viewModel.setSelectedAlbum(album.id)
        findNavController().navigate(AlbumsListFragmentDirections.actionAlbumsListFragmentToAlbumDetailsFragment())
    }

    private fun onShareAlbumClick(albumShareLink: String) {
        startActivity(IntentUtils.getChooserIntent(albumShareLink, getString(R.string.share_album)))
    }

    private fun initObservers() {
        viewLifecycleOwner.observe(viewModel.loading, {
            it?.let {
                if (it) {
                    binding.prbAlbums.visibility = View.VISIBLE
                    binding.rcvAlbums.visibility = View.GONE
                } else {
                    binding.prbAlbums.visibility = View.GONE
                    binding.rcvAlbums.visibility = View.VISIBLE
                }
            } ?: run {
                binding.prbAlbums.visibility = View.GONE
            }
        })

        viewLifecycleOwner.observe(viewModel.albums, {
            it?.let {
                if (it.isEmpty()) {
                    binding.rcvAlbums.visibility = View.GONE
                } else {
                    binding.rcvAlbums.visibility = View.VISIBLE
                    albumsAdapter.updateItems(it)
                }
            }
        })

        viewLifecycleOwner.observe(viewModel.error, {
            it?.let {
                when (it) {
                    GENERAL_ERROR_ALBUMS -> showAlbumsListErrorDialog()
                    TOKEN_ERROR -> showTokenErrorDialog()
                }
            }
        })
    }

    private fun showTokenErrorDialog() {
        AlertDialogFactory.createAlertDialog(
            requireContext(),
            false,
            getString(R.string.error_token_error_message),
            getString(R.string.button_retry)
        ) { dialog ->
            dialog.dismiss()
            viewModel.getAlbums()
        }.show()
    }

    private fun showAlbumsListErrorDialog() {
        AlertDialogFactory.createAlertDialog(
            requireContext(),
            false,
            getString(R.string.error_albums_error_message),
            getString(R.string.button_retry)
        ) { dialog ->
            dialog.dismiss()
            viewModel.getAlbums()
        }.show()
    }
}