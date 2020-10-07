package cat.jorcollmar.spotifypanel.ui.application

import cat.jorcollmar.spotifypanel.di.DaggerSpotifyPanelComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class SpotifyPanelApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerSpotifyPanelComponent.builder().application(this).build()
}