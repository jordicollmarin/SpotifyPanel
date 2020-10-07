package cat.jorcollmar.spotifypanel.ui.album.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cat.jorcollmar.spotifypanel.databinding.FragmentAlbumsListBinding
import dagger.android.support.DaggerFragment

class AlbumsListFragment : DaggerFragment() {

    lateinit var binding: FragmentAlbumsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumsListBinding.inflate(inflater, container, false)
        return binding.root
    }
}