package cat.jorcollmar.spotifypanel.ui.album.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.jorcollmar.spotifypanel.commons.extensions.loadImage
import cat.jorcollmar.spotifypanel.databinding.AlbumItemBinding
import cat.jorcollmar.spotifypanel.ui.album.model.Album

class AlbumsAdapter(
    private val onAlbumClick: (Album) -> Unit,
    private val onShareAlbumClick: (String) -> Unit
) : RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    private var albumsList: MutableList<Album> = mutableListOf()

    fun updateItems(albums: List<Album>) {
        albumsList = albums.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AlbumItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onAlbumClick, onShareAlbumClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.loadAlbum(albumsList[position])
    }

    override fun getItemCount(): Int = albumsList.size

    inner class ViewHolder(
        private val albumItemBinding: AlbumItemBinding,
        private val onAlbumClick: (Album) -> Unit, private val onShareAlbumClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(albumItemBinding.root) {

        fun loadAlbum(album: Album) = with(itemView) {
            setOnClickListener { onAlbumClick(album) }

            album.externalUrls?.let { shareLink ->
                albumItemBinding.imvAlbumShare.setOnClickListener {
                    onShareAlbumClick(
                        shareLink["spotify"] ?: run { "" }
                    )
                }
            }

            album.images?.let {
                albumItemBinding.imvAlbum.loadImage(Uri.parse(it[0].url))
            }

            album.name?.let {
                albumItemBinding.txvAlbumName.text = it
            }

            album.releaseDate?.let {
                // TODO: Format date
                albumItemBinding.txvAlbumReleaseDate.text = it
            }
        }
    }
}