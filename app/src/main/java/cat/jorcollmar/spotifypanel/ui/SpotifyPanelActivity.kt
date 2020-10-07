package cat.jorcollmar.spotifypanel.ui

import android.os.Bundle
import cat.jorcollmar.spotifypanel.R
import cat.jorcollmar.spotifypanel.databinding.ActivitySpotifyPanelBinding
import dagger.android.support.DaggerAppCompatActivity

class SpotifyPanelActivity : DaggerAppCompatActivity() {
    lateinit var binding: ActivitySpotifyPanelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Replace Splash theme with a NoActionBar theme
        setTheme(R.style.AppTheme_NoActionBar)

        super.onCreate(savedInstanceState)
        binding = ActivitySpotifyPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}