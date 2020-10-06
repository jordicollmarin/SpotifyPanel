package cat.jorcollmar.spotifypanel.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cat.jorcollmar.spotifypanel.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector

class SpotifyPanelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spotify_panel)
    }
}