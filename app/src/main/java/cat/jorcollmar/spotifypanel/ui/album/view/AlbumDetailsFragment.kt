package cat.jorcollmar.spotifypanel.ui.album.view

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cat.jorcollmar.spotifypanel.R
import cat.jorcollmar.spotifypanel.commons.extensions.loadImageCenterCrop
import cat.jorcollmar.spotifypanel.commons.extensions.observe
import cat.jorcollmar.spotifypanel.commons.factories.AlertDialogFactory
import cat.jorcollmar.spotifypanel.commons.utils.IntentUtils
import cat.jorcollmar.spotifypanel.databinding.FragmentAlbumDetailsBinding
import cat.jorcollmar.spotifypanel.ui.album.model.Album
import cat.jorcollmar.spotifypanel.ui.album.view.AlbumViewModel.Companion.GENERAL_ERROR_ALBUM_DETAILS
import cat.jorcollmar.spotifypanel.ui.album.view.AlbumViewModel.Companion.TOKEN_ERROR
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import java.lang.Math.abs
import javax.inject.Inject

class AlbumDetailsFragment : DaggerFragment() {

    lateinit var binding: FragmentAlbumDetailsBinding

    @Inject
    lateinit var viewModelFactory: AlbumViewModelFactory

    private val viewModel: AlbumViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(AlbumViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (!viewModel.albumDetailsLoaded) {
            viewModel.getAlbumDetails()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        setUpLayout()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarAlbumDetail)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_share).isChecked = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> findNavController().navigateUp()
            R.id.action_share -> shareAlbum()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    private fun shareAlbum(): Boolean {
        viewModel.selectedAlbum.value?.getShareLink()?.let {
            startActivity(IntentUtils.getChooserIntent(it, getString(R.string.share_album)))
        } ?: run {
            Snackbar.make(
                binding.root,
                getString(R.string.album_share_link_not_available),
                Snackbar.LENGTH_SHORT
            ).show()
        }

        return false
    }

    private fun initObservers() {
        viewLifecycleOwner.observe(viewModel.loading, {
            it?.let {
                if (it) {
                    binding.prbAlbumDetail.visibility = View.VISIBLE
                    binding.lytContent.scvContentAlbumDetail.visibility = View.GONE
                    binding.ablAlbumDetail.visibility = View.GONE
                    binding.fabAlbumDetail.visibility = View.GONE
                } else {
                    binding.prbAlbumDetail.visibility = View.GONE
                    binding.lytContent.scvContentAlbumDetail.visibility = View.VISIBLE
                    binding.ablAlbumDetail.visibility = View.VISIBLE
                    binding.fabAlbumDetail.visibility = View.VISIBLE
                }
            } ?: run {
                binding.prbAlbumDetail.visibility = View.GONE
            }
        })

        viewLifecycleOwner.observe(viewModel.selectedAlbum, {
            it?.let { album ->
                viewModel.changeDetailLoaded(true)
                loadAlbumData(album)
            }
        })

        viewLifecycleOwner.observe(viewModel.error, {
            it?.let {
                when (it) {
                    GENERAL_ERROR_ALBUM_DETAILS -> showDetailInfoErrorDialog()
                    TOKEN_ERROR -> showTokenErrorDialog()
                }
            }
        })
    }

    private fun setUpLayout() {
        binding.fabAlbumDetail.setOnClickListener { shareAlbum() }
        binding.ablAlbumDetail.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener
            { appBarLayout, verticalOffset ->
                binding.toolbarAlbumDetail.menu.findItem(R.id.action_share).isVisible =
                    abs(verticalOffset) - appBarLayout.totalScrollRange == 0
            })
    }

    private fun loadAlbumData(album: Album) {
        with(album) {
            binding.ctlAlbumDetail.title = name

            getBigImage()?.let { binding.imvAlbumBigImage.loadImageCenterCrop(Uri.parse(it)) }

            artist?.name?.let {
                binding.lytContent.txvAlbumArtist.text =
                    getString(R.string.album_detail_artist_label, it)
            } ?: run {
                binding.lytContent.txvAlbumArtist.visibility = View.GONE
            }

            label?.let {
                binding.lytContent.txvAlbumLabel.text =
                    getString(R.string.album_detail_label_label, it)
            } ?: run {
                binding.lytContent.txvAlbumLabel.visibility = View.GONE
            }

            tracks?.let {
                binding.lytContent.txvAlbumTracks.text =
                    getString(R.string.album_detail_tracks_label, it)
            } ?: run {
                binding.lytContent.txvAlbumTracks.visibility = View.GONE
            }
        }
    }

    private fun showDetailInfoErrorDialog() {
        AlertDialogFactory.createAlertDialog(
            requireContext(),
            false,
            getString(R.string.error_album_detail_error_message),
            getString(R.string.button_retry),
            { dialog ->
                dialog.dismiss()
                viewModel.getAlbumDetails()
            },
            getString(R.string.button_go_back)
        ) { dialog ->
            dialog.dismiss()
            findNavController().navigateUp()
        }.show()
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

    override fun onDestroy() {
        viewModel.changeDetailLoaded(false)
        super.onDestroy()
    }
}