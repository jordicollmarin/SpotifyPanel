package cat.jorcollmar.spotifypanel.di

import cat.jorcollmar.spotifypanel.ui.SpotifyPanelActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SpotifyPanelModule {

    @ContributesAndroidInjector
    abstract fun contributeSpotifyPanelActivity(): SpotifyPanelActivity
}