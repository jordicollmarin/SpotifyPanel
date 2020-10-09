package cat.jorcollmar.spotifypanel.ui.album.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import cat.jorcollmar.spotifypanel.R
import cat.jorcollmar.spotifypanel.commons.extensions.loadImage
import cat.jorcollmar.spotifypanel.databinding.AlbumItemBinding
import cat.jorcollmar.spotifypanel.ui.album.model.Album

class AlbumsAdapter(
    private val onAlbumClick: (Album) -> Unit,
    private val onShareAlbumClick: (String) -> Unit,
    private val loadMoreAlbumsAction: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var albumsList: MutableList<Album> = mutableListOf()

    fun addItems(albums: List<Album>) {
        albumsList = albums.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ALBUM_TYPE -> AlbumViewHolder(
                AlbumItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), onAlbumClick, onShareAlbumClick
            )
            else -> {
                ProgressBarViewHolder(ProgressBar(parent.context))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ALBUM_TYPE -> (holder as AlbumViewHolder).loadAlbum(albumsList[position])
            LOADING_TYPE -> loadMoreAlbumsAction(albumsList.size)
        }
    }

    override fun getItemCount(): Int {
        val itemCount = albumsList.size
        return if (itemCount == ALBUMS_MAX_RESULTS) {
            itemCount
        } else {
            itemCount + 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= albumsList.size) {
            LOADING_TYPE
        } else {
            ALBUM_TYPE
        }
    }

    inner class AlbumViewHolder(
        private val albumItemBinding: AlbumItemBinding,
        private val onAlbumClick: (Album) -> Unit,
        private val onShareAlbumClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(albumItemBinding.root) {
        fun loadAlbum(album: Album) = with(itemView) {
            setOnClickListener { onAlbumClick(album) }

            album.getShareLink()?.let { shareLink ->
                albumItemBinding.imvAlbumShare.setOnClickListener { onShareAlbumClick(shareLink) }
            } ?: run {
                albumItemBinding.imvAlbumShare.visibility = View.GONE
            }

            album.getSmallImage()?.let {
                albumItemBinding.imvAlbum.loadImage(Uri.parse(it))
            }

            album.name?.let {
                albumItemBinding.txvAlbumName.text = it
            } ?: run {
                albumItemBinding.txvAlbumName.visibility = View.GONE
            }

            album.releaseDate?.let {
                albumItemBinding.txvAlbumReleaseDate.text =
                    context.getString(R.string.release_date_title, it)
            } ?: run {
                albumItemBinding.txvAlbumReleaseDate.visibility = View.GONE
            }
        }
    }

    inner class ProgressBarViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = view.context.resources.getDimension(R.dimen.dimen_10).toInt()
                bottomMargin = view.context.resources.getDimension(R.dimen.dimen_30).toInt()
            }
        }
    }

    companion object {
        const val LOADING_TYPE = 0
        const val ALBUM_TYPE = 1

        const val ALBUMS_MAX_RESULTS = 100
    }
}